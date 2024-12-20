package Level;

import java.util.ArrayList;

import Engine.Key;
import Engine.KeyLocker;
import Engine.Keyboard;
import EnhancedMapTiles.Trail;
import GameObject.GameObject;
import GameObject.SpriteSheet;
import Screens.PlayLevelScreen;
import Utils.AirGroundState;
import Utils.Direction;
import Utils.Point;
import java.util.Timer;
import java.util.TimerTask;



public abstract class Player extends GameObject {
    // values that affect player movement
    // these should be set in a subclass
    protected float walkSpeed = 0;
    protected float gravity = 0;
    protected float jumpHeight = 0;
    protected float jumpDegrade = 0;
    protected float terminalVelocityY = 0;
    protected float momentumYIncrease = 0;

    // values used to handle player movement
    protected float jumpForce = 0;
    protected float momentumY = 0;
    protected float moveAmountX, moveAmountY;
    protected float lastAmountMovedX, lastAmountMovedY;

    protected boolean isSpeedBoostActive = false;  // Track if speed boost is active
    protected float originalWalkSpeed;  // Store the original walk speed
    protected static Map map;  // Reference to the map instance

    // values used to keep track of player's current state
    protected PlayerState playerState;
    protected PlayerState previousPlayerState;
    protected int Health;
    protected int damageTimer;
    protected Direction facingDirection;
    protected AirGroundState airGroundState;
    protected AirGroundState previousAirGroundState;
    protected LevelState levelState;

    // classes that listen to player events can be added to this list
    protected ArrayList<PlayerListener> listeners = new ArrayList<>();

    // define keys
    protected KeyLocker keyLocker = new KeyLocker();
    protected Key JUMP_KEY = Key.UP;
    protected Key MOVE_LEFT_KEY = Key.LEFT;
    protected Key MOVE_RIGHT_KEY = Key.RIGHT;
    protected Key CROUCH_KEY = Key.DOWN;
    protected Key SHOOT_KEY = Key.X;
    protected Key SLASH_KEY = Key.Z;
    protected Key DASH_KEY = Key.SHIFT;

    // flags
    protected boolean isInvincible = false; // if true, player cannot be hurt by enemies (good for testing)
    public boolean isWaterDamageImmune = false; // Flag to track water damage immunity

// Getter for isWaterDamageImmune
public boolean isWaterDamageImmune() {
    return isWaterDamageImmune;
}

// Setter for isWaterDamageImmune
public void setWaterDamageImmune(boolean isWaterDamageImmune) {
    this.isWaterDamageImmune = isWaterDamageImmune;
}

public Player(SpriteSheet spriteSheet, float x, float y, String startingAnimationName, Map map) {
    super(spriteSheet, x, y, startingAnimationName);
    this.map = map;  // Store the map reference
        facingDirection = Direction.RIGHT;
        airGroundState = AirGroundState.AIR;
        previousAirGroundState = airGroundState;
        playerState = PlayerState.STANDING;
        previousPlayerState = playerState;
        levelState = LevelState.RUNNING;
        Health = 3;
        damageTimer = 0;
    }
    
    private boolean hasKey = false;

    // Other player properties and methods

    public boolean hasKey() {
        return hasKey;
    }

    public void setHasKey(boolean hasKey) {
        this.hasKey = hasKey;
    }
    
    private boolean canShootFireballs = false;

    public void enableFireballAbility() {
        this.canShootFireballs = true;
    }

    private void shootFireball() {
        // Determine the initial position of the fireball based on the player's facing direction
        PlayLevelScreen.playFireballShootSound();
        int fireballOffsetX = facingDirection == Direction.RIGHT ? this.getWidth() : 10; // Offset X based on direction
        int fireballOffsetY = this.getHeight() / 2; // Adjust the Y position to come from a little lower
    
        Point fireballStart = new Point((int) this.x + fireballOffsetX, (int) this.y + fireballOffsetY);
    
        // Create a new PlayerFireball and add it to the map
        PlayerFireball fireball = new PlayerFireball(fireballStart, 5.0f, 1, 100, facingDirection == Direction.RIGHT);
        map.addProjectile(fireball);
    }
    
    public void activateSpeedBoost(float multiplier) {
        if (!isSpeedBoostActive) {
            originalWalkSpeed = walkSpeed;  // Store original speed
            walkSpeed *= multiplier;  // Apply speed multiplier
            isSpeedBoostActive = true;
}}


// Method to deactivate speed boost
public void deactivateSpeedBoost() {
    if (isSpeedBoostActive) {
        walkSpeed = originalWalkSpeed;  // Reset to original speed
        isSpeedBoostActive = false;
    }
}

// Getter method to access the map
public Map getMap() {
    return map;
}



private boolean canSwingSword = false;

public void enableSwordSwingAbility() {
    this.canSwingSword = true;
}

private void swingSword() {
    // Determine the initial position of the sword slash based on the player's facing direction
    PlayLevelScreen.playSwordSwingSound();
    int swordOffsetX = facingDirection == Direction.RIGHT ? this.getWidth() : -10; // Offset X based on direction
    int swordOffsetY = this.getHeight() / 2; // Adjust the Y position for the sword swing

    Point swordStart = new Point((int) this.x + swordOffsetX, (int) this.y + swordOffsetY);

    // Create a new PlayerSword and add it to the map
    PlayerSword sword = new PlayerSword(swordStart, 5.0f, 1, 30, facingDirection == Direction.RIGHT);
    map.addProjectile(sword);
}




@Override
public void update() {
    moveAmountX = 0;
    moveAmountY = 0;

    // Handle shooting fireballs
    if (canShootFireballs && Keyboard.isKeyDown(SHOOT_KEY) && !keyLocker.isKeyLocked(SHOOT_KEY)) {
        keyLocker.lockKey(SHOOT_KEY);
        shootFireball();
    }
    if (Keyboard.isKeyUp(SHOOT_KEY)) {
        keyLocker.unlockKey(SHOOT_KEY);
    }

    // Handle sword swinging
    if (canSwingSword && Keyboard.isKeyDown(SLASH_KEY) && !keyLocker.isKeyLocked(SLASH_KEY)) {
        keyLocker.lockKey(SLASH_KEY);
        swingSword();
    }
    if (Keyboard.isKeyUp(SLASH_KEY)) {
        keyLocker.unlockKey(SLASH_KEY);
    }

    // Handle level running, gravity, and player state
    if (levelState == LevelState.RUNNING) {
        applyGravity();

        do {
            previousPlayerState = playerState;
            handlePlayerState();
        } while (previousPlayerState != playerState);

        previousAirGroundState = airGroundState;

        lastAmountMovedX = super.moveXHandleCollision(moveAmountX);
        lastAmountMovedY = super.moveYHandleCollision(moveAmountY);

        handlePlayerAnimation();
        updateLockedKeys();

        if (damageTimer > 0) {
            damageTimer--;
        }

        super.update();
    } else if (levelState == LevelState.LEVEL_COMPLETED) {
        updateLevelCompleted();
    } else if (levelState == LevelState.PLAYER_DEAD) {
        updatePlayerDead();
    }
}


    // add gravity to player, which is a downward force
    protected void applyGravity() {
        moveAmountY += gravity + momentumY;
    }

    // based on player's current state, call appropriate player state handling method
    protected void handlePlayerState() {
        switch (playerState) {
            case STANDING:
                playerStanding();
                break;
            case WALKING:
                playerWalking();
                break;
            case CROUCHING:
                playerCrouching();
                break;
            case JUMPING:
                playerJumping();
                break;
        }
    }

    // player STANDING state logic
    protected void playerStanding() {
        // if walk left or walk right key is pressed, player enters WALKING state
        if (Keyboard.isKeyDown(MOVE_LEFT_KEY) || Keyboard.isKeyDown(MOVE_RIGHT_KEY)) {
            playerState = PlayerState.WALKING;
        }

        // if jump key is pressed, player enters JUMPING state
        else if (Keyboard.isKeyDown(JUMP_KEY) && !keyLocker.isKeyLocked(JUMP_KEY)) {
            keyLocker.lockKey(JUMP_KEY);
            playerState = PlayerState.JUMPING;
        }

        // if crouch key is pressed, player enters CROUCHING state
        else if (Keyboard.isKeyDown(CROUCH_KEY)) {
            playerState = PlayerState.CROUCHING;
        }
    }

    // player WALKING state logic
    protected void playerWalking() {
        // if walk left key is pressed, move player to the left
        if (Keyboard.isKeyDown(MOVE_LEFT_KEY)) {
            moveAmountX -= walkSpeed;
            facingDirection = Direction.LEFT;
        }

        // if walk right key is pressed, move player to the right
        else if (Keyboard.isKeyDown(MOVE_RIGHT_KEY)) {
            moveAmountX += walkSpeed;
            facingDirection = Direction.RIGHT;
        } else if (Keyboard.isKeyUp(MOVE_LEFT_KEY) && Keyboard.isKeyUp(MOVE_RIGHT_KEY)) {
            playerState = PlayerState.STANDING;
        }

        // if jump key is pressed, player enters JUMPING state
        if (Keyboard.isKeyDown(JUMP_KEY) && !keyLocker.isKeyLocked(JUMP_KEY)) {
            keyLocker.lockKey(JUMP_KEY);
            playerState = PlayerState.JUMPING;
        }

        // if crouch key is pressed,
        else if (Keyboard.isKeyDown(CROUCH_KEY)) {
            playerState = PlayerState.CROUCHING;
        }
    }

    // player CROUCHING state logic
    protected void playerCrouching() {
        // if crouch key is released, player enters STANDING state
        if (Keyboard.isKeyUp(CROUCH_KEY)) {
            playerState = PlayerState.STANDING;
        }

        // if jump key is pressed, player enters JUMPING state
        if (Keyboard.isKeyDown(JUMP_KEY) && !keyLocker.isKeyLocked(JUMP_KEY)) {
            keyLocker.lockKey(JUMP_KEY);
            playerState = PlayerState.JUMPING;
        }
    }

    // player JUMPING state logic
    protected void playerJumping() {
        // if last frame player was on ground and this frame player is still on ground, the jump needs to be setup
        if (previousAirGroundState == AirGroundState.GROUND && airGroundState == AirGroundState.GROUND) {

            // sets animation to a JUMP animation based on which way player is facing
            currentAnimationName = facingDirection == Direction.RIGHT ? "JUMP_RIGHT" : "JUMP_LEFT";

            // player is set to be in air and then player is sent into the air
            airGroundState = AirGroundState.AIR;
            jumpForce = jumpHeight;
            if (jumpForce > 0) {
                moveAmountY -= jumpForce;
                jumpForce -= jumpDegrade;
                if (jumpForce < 0) {
                    jumpForce = 0;
                }
            }
        }

        // if player is in air (currently in a jump) and has more jumpForce, continue sending player upwards
        else if (airGroundState == AirGroundState.AIR) {
            if (jumpForce > 0) {
                moveAmountY -= jumpForce;
                jumpForce -= jumpDegrade;
                if (jumpForce < 0) {
                    jumpForce = 0;
                }
            }

            // allows you to move left and right while in the air
            if (Keyboard.isKeyDown(MOVE_LEFT_KEY)) {
                moveAmountX -= walkSpeed;
            } else if (Keyboard.isKeyDown(MOVE_RIGHT_KEY)) {
                moveAmountX += walkSpeed;
            }

            // if player is falling, increases momentum as player falls so it falls faster over time
            if (moveAmountY > 0) {
                increaseMomentum();
            }
        }

        // if player last frame was in air and this frame is now on ground, player enters STANDING state
        else if (previousAirGroundState == AirGroundState.AIR && airGroundState == AirGroundState.GROUND) {
            playerState = PlayerState.STANDING;
        }
    }

    // while player is in air, this is called, and will increase momentumY by a set amount until player reaches terminal velocity
    protected void increaseMomentum() {
        momentumY += momentumYIncrease;
        if (momentumY > terminalVelocityY) {
            momentumY = terminalVelocityY;
        }
    }

    protected void updateLockedKeys() {
        if (Keyboard.isKeyUp(JUMP_KEY)) {
            keyLocker.unlockKey(JUMP_KEY);
        }
    }

    // anything extra the player should do based on interactions can be handled here
    protected void handlePlayerAnimation() {
        if (playerState == PlayerState.STANDING) {
            // sets animation to a STAND animation based on which way player is facing
            this.currentAnimationName = facingDirection == Direction.RIGHT ? "STAND_RIGHT" : "STAND_LEFT";

            // handles putting goggles on when standing in water
            // checks if the center of the player is currently touching a water tile
            int centerX = Math.round(getBounds().getX1()) + Math.round(getBounds().getWidth() / 2f);
            int centerY = Math.round(getBounds().getY1()) + Math.round(getBounds().getHeight() / 2f);
            MapTile currentMapTile = map.getTileByPosition(centerX, centerY);
            if (currentMapTile != null && currentMapTile.getTileType() == TileType.WATER) {
                if (!isWaterDamageImmune) {
                    levelState = LevelState.PLAYER_DEAD; // Only kill if not immune
                }
            }
        }
        else if (playerState == PlayerState.WALKING) {
            // sets animation to a WALK animation based on which way player is facing
            this.currentAnimationName = facingDirection == Direction.RIGHT ? "WALK_RIGHT" : "WALK_LEFT";
        }
        else if (playerState == PlayerState.CROUCHING) {
            // sets animation to a CROUCH animation based on which way player is facing
            this.currentAnimationName = facingDirection == Direction.RIGHT ? "CROUCH_RIGHT" : "CROUCH_LEFT";
        }
        else if (playerState == PlayerState.JUMPING) {
            // if player is moving upwards, set player's animation to jump. if player moving downwards, set player's animation to fall
            if (lastAmountMovedY <= 0) {
                this.currentAnimationName = facingDirection == Direction.RIGHT ? "JUMP_RIGHT" : "JUMP_LEFT";
            } else {
                this.currentAnimationName = facingDirection == Direction.RIGHT ? "FALL_RIGHT" : "FALL_LEFT";
            }
        }
    }

    @Override
    public void onEndCollisionCheckX(boolean hasCollided, Direction direction, MapEntity entityCollidedWith) { }

    @Override
    public void onEndCollisionCheckY(boolean hasCollided, Direction direction, MapEntity entityCollidedWith) {
        // if player collides with a map tile below it, it is now on the ground
        // if player does not collide with a map tile below, it is in air
        if (direction == Direction.DOWN) {
            if (hasCollided) {
                momentumY = 0;
                airGroundState = AirGroundState.GROUND;
            } else {
                playerState = PlayerState.JUMPING;
                airGroundState = AirGroundState.AIR;
            }
        }

        // if player collides with map tile upwards, it means it was jumping and then hit into a ceiling -- immediately stop upwards jump velocity
        else if (direction == Direction.UP) {
            if (hasCollided) {
                jumpForce = 0;
            }
        }
    }

    // other entities can call this method to hurt the player
    public void hurtPlayer(MapEntity mapEntity) {
    if (!isInvincible) {
        // Check if the map entity is an enemy and player can take damage
        if ((mapEntity instanceof Enemy) && (damageTimer == 0)) {
            damageTimer = 60; // Set damage cooldown
            if (Health == 1) {
                Health = 0;
                levelState = LevelState.PLAYER_DEAD;
            } else if (Health > 1) {
                Health -= 1;
                PlayLevelScreen.playDamageSound();
            }

            // Spawn a damage effect animation at the player's location
            DamageEffect damageEffect = new DamageEffect(new Point((int) this.x, (int) this.y), 60);
            map.addMapEntity(damageEffect);
        }
    }
}

    // other entities can call this to tell the player they beat a level
    public void completeLevel() {
        levelState = LevelState.LEVEL_COMPLETED;
    }

    // if player has beaten level, this will be the update cycle
    public void updateLevelCompleted() {
        // if player is not on ground, player should fall until it touches the ground
        if (airGroundState != AirGroundState.GROUND && map.getCamera().containsDraw(this)) {
            currentAnimationName = "FALL_RIGHT";
            applyGravity();
            increaseMomentum();
            super.update();
            moveYHandleCollision(moveAmountY);
        } 
        else {
            // tell all player listeners that the player has finished the level
            for (PlayerListener listener : listeners) {
                listener.onLevelCompleted();
            }
        }
    }

    // if player has died, this will be the update cycle
    public void updatePlayerDead() {
        // change player animation to DEATH
        if (!currentAnimationName.startsWith("DEATH")) {
            if (facingDirection == Direction.RIGHT) {
                currentAnimationName = "DEATH_RIGHT";
            } else {
                currentAnimationName = "DEATH_LEFT";
            }
            super.update();
        }
        // if death animation not on last frame yet, continue to play out death animation
        else if (currentFrameIndex != getCurrentAnimation().length - 1) {
          super.update();
        }
        // if death animation on last frame (it is set up not to loop back to start), player should continually fall until it goes off screen
        else if (currentFrameIndex == getCurrentAnimation().length - 1) {
            if (map.getCamera().containsDraw(this)) {
                moveY(3);
            } else {
                // tell all player listeners that the player has died in the level
                for (PlayerListener listener : listeners) {
                    listener.onDeath();
                }
            }
        }
    }

    public PlayerState getPlayerState() {
        return playerState;
    }

    public int getPlayerHealth(){
        return this.Health;
    }

    public void setPlayerHealth(int health){
        Health = health;
    }

    public void setPlayerState(PlayerState playerState) {
        this.playerState = playerState;
    }

    public AirGroundState getAirGroundState() {
        return airGroundState;
    }

    public Direction getFacingDirection() {
        return facingDirection;
    }

    public void setFacingDirection(Direction facingDirection) {
        this.facingDirection = facingDirection;
    }

    public void setLevelState(LevelState levelState) {
        this.levelState = levelState;
    }

    public void addListener(PlayerListener listener) {
        listeners.add(listener);
    }

    public void setLocation(Point playerStartPosition) {
        // Set the player's position to the specified coordinates
        this.x = playerStartPosition.x; // Assuming 'x' is the player's current x-coordinate
        this.y = playerStartPosition.y; // Assuming 'y' is the player's current y-coordinate
    }

    public void setAirGroundState(AirGroundState newState) {
        if (newState != airGroundState) {
            previousAirGroundState = airGroundState; // Store the previous state
            airGroundState = newState; // Update to the new state
        }
    }
    
    

        
    // Uncomment this to have game draw player's bounds to make it easier to visualize
    /*
    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
        drawBounds(graphicsHandler, new Color(255, 0, 0, 100));
    }
    */
}