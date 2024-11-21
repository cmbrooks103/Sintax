package Enemies;

import Builders.FrameBuilder;
import Engine.ImageLoader;
import Engine.GraphicsHandler;
import GameObject.Frame;
import GameObject.ImageEffect;
import GameObject.SpriteSheet;
import Level.Enemy;
import Level.MapEntity;
import Level.Player;
import Utils.AirGroundState;
import Utils.Direction;
import Utils.Point;
import java.util.HashMap;

public class Judy extends Enemy {
    protected Point startLocation;
    protected Point endLocation;
    protected float movementSpeed = 1.6f;
    private Direction startFacingDirection;
    protected Direction facingDirection;
    protected AirGroundState airGroundState;

    protected int shootWaitTimer;
    protected int shootTimer;

    protected DinosaurState dinosaurState;
    protected DinosaurState previousDinosaurState;

    private int customHealth = 55; // Set health to 55
    private float gravity = 0.5f; // Gravity effect
    private float moveAmountY = 0; // Vertical movement amount
    private int directionChangeTimer = 0; // Timer for direction changes
    private int directionChangeInterval = 80; // Time in frames before changing direction

    private boolean musicChanged = false; // Tracks if the boss music is playing
    private final int musicStartDistance = 15 * 16; // Start playing music at 15 blocks
    private final int musicStopDistance = 18 * 16; // Stop playing music at 18 blocks

    public Judy(Point startLocation, Point endLocation, Direction facingDirection) {
        super(startLocation.x, startLocation.y, new SpriteSheet(ImageLoader.load("judy.png"), 14, 17), "WALK_RIGHT");
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.startFacingDirection = facingDirection;
        initialize();
        maxHealth = 55;
    }

    @Override
    public void initialize() {
        super.initialize();
        this.Health = customHealth;
        dinosaurState = DinosaurState.WALK;
        previousDinosaurState = dinosaurState;
        facingDirection = startFacingDirection;

        if (facingDirection == Direction.RIGHT) {
            currentAnimationName = "WALK_RIGHT";
        } else {
            currentAnimationName = "WALK_LEFT";
        }
        airGroundState = AirGroundState.GROUND;

        shootWaitTimer = 25;
    }

    @Override
    public void update(Player player) {
        float moveAmountX = 0;

        // Apply gravity to vertical movement
        if (airGroundState == AirGroundState.AIR) {
            moveAmountY += gravity; // Falling down
        } else {
            moveAmountY = 0; // Reset vertical movement if on the ground
        }

        // Movement logic
        if (airGroundState == AirGroundState.GROUND) {
            if (facingDirection == Direction.RIGHT) {
                moveAmountX += movementSpeed;
            } else {
                moveAmountX -= movementSpeed;
            }

            // Check for bounds and flip direction if needed
            if (directionChangeTimer <= 0) {
                if (getX1() + getWidth() >= endLocation.x || getX1() <= startLocation.x) {
                    facingDirection = (facingDirection == Direction.RIGHT) ? Direction.LEFT : Direction.RIGHT;
                    currentAnimationName = (facingDirection == Direction.RIGHT) ? "WALK_RIGHT" : "WALK_LEFT";
                    directionChangeTimer = directionChangeInterval; // Reset the timer
                }
            } else {
                directionChangeTimer--; // Decrease the timer
            }
        }

        // Handle movements
        moveYHandleCollision(moveAmountY); // Handle Y movement for gravity
        moveXHandleCollision(moveAmountX); // Handle X movement

        // Shooting logic
        if (shootWaitTimer == 0 && dinosaurState != DinosaurState.SHOOT_WAIT) {
            dinosaurState = DinosaurState.SHOOT_WAIT;
        } else {
            shootWaitTimer--;
        }

        if (dinosaurState == DinosaurState.SHOOT_WAIT) {
            if (previousDinosaurState == DinosaurState.WALK) {
                shootTimer = 65; // Delay before shooting
                currentAnimationName = facingDirection == Direction.RIGHT ? "SHOOT_RIGHT" : "SHOOT_LEFT";
            } else if (shootTimer == 0) {
                dinosaurState = DinosaurState.SHOOT;
            } else {
                shootTimer--;
            }
        }

        if (dinosaurState == DinosaurState.SHOOT) {
            shootCash(); // Shoot a large fireball
            dinosaurState = DinosaurState.WALK; // Return to walking state
            shootWaitTimer = 130; // Reset shooting wait timer
        }

        // Check distance to player and toggle music
        checkDistanceAndToggleMusic(player);

        super.update(player);
        previousDinosaurState = dinosaurState;
    }

    private void shootCash() {
        int cashX;
        float cashSpeed;

        // Determine the initial position and direction of the cash projectile
        if (facingDirection == Direction.RIGHT) {
            cashX = Math.round(getX()) + getWidth();
            cashSpeed = 2.0f; // Adjust speed as needed
        } else {
            cashX = Math.round(getX() - 21);
            cashSpeed = -2.0f; // Adjust speed as needed
        }

        int cashY = Math.round(getY()) + 4;

        // Create a new Cash instance instead of LargeFireball
        Cash cash = new Cash(new Point(cashX, cashY), cashSpeed, 100); // Adjust existence frames if needed
        map.addEnemy(cash);
    }

    private void checkDistanceAndToggleMusic(Player player) {
        int playerX = Math.round(player.getX());
        int playerY = Math.round(player.getY());
        int judyX = Math.round(getX());
        int judyY = Math.round(getY());

        // Calculate the distance
        double distance = Math.sqrt(Math.pow(playerX - judyX, 2) + Math.pow(playerY - judyY, 2));

        if (distance <= musicStartDistance && !musicChanged) {
            playBossMusic();
            musicChanged = true;
        } else if (distance > musicStopDistance && musicChanged) {
            stopBossMusic();
            musicChanged = false;
        }
    }

    private void playBossMusic() {
        MusicHandler.pause("default_background_music.mp3"); // Pause the default background music
        MusicHandler.play("src/Sounds/Super Metroid Music - Ridley Draygon Boss Theme (online-audio-converter.com).wav"); // Play boss music
    }

    private void stopBossMusic() {
        MusicHandler.stop("src/Sounds/Super Metroid Music - Ridley Draygon Boss Theme (online-audio-converter.com).wav"); // Stop boss music
        MusicHandler.resume("default_background_music.mp3"); // Resume default background music
    }

    @Override
    public void onEndCollisionCheckX(boolean hasCollided, Direction direction, MapEntity entityCollidedWith) {
        if (hasCollided) {
            if (direction == Direction.RIGHT) {
                facingDirection = Direction.LEFT;
                currentAnimationName = "WALK_LEFT";
            } else {
                facingDirection = Direction.RIGHT;
                currentAnimationName = "WALK_RIGHT";
            }
            directionChangeTimer = 0; // Reset direction change timer on collision
        }
    }

    @Override
    public void onEndCollisionCheckY(boolean hasCollided, Direction direction, MapEntity entityCollidedWith) {
        if (direction == Direction.DOWN) {
            if (hasCollided) {
                airGroundState = AirGroundState.GROUND; // Colliding with the ground
            } else {
                airGroundState = AirGroundState.AIR; // Not colliding means in the air
            }
        }
    }

    @Override
    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {{
            put("WALK_LEFT", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(0, 0), 14)
                        .withScale(8)
                        .withBounds(4, 2, 5, 13)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(0, 1), 14)
                        .withScale(8)
                        .withBounds(4, 2, 5, 13)
                        .build()
            });

            put("WALK_RIGHT", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(0, 0), 14)
                        .withScale(8)
                        .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                        .withBounds(4, 2, 5, 13)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(0, 1), 14)
                        .withScale(8)
                        .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                        .withBounds(4, 2, 5, 13)
                        .build()
            });

            put("SHOOT_LEFT", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(1, 0))
                        .withScale(8)
                        .withBounds(4, 2, 5, 13)
                        .build(),
            });

            put("SHOOT_RIGHT", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(1, 0))
                        .withScale(8)
                        .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                        .withBounds(4, 2, 5, 13)
                        .build(),
            });
        }};
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler); // Call super to draw 
        drawHealthBar(graphicsHandler); // Draw the health bar 
    }

    public enum DinosaurState {
        WALK, SHOOT_WAIT, SHOOT
    }
}
