package Players;

import Builders.FrameBuilder;
import Engine.GraphicsHandler;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.ImageEffect;
import GameObject.SpriteSheet;
import Level.Map;
import Level.Player;
import Level.PlayerState;
import Utils.AirGroundState;
import Utils.Direction;
import Engine.Keyboard;
import java.util.HashMap;

public class PlayerTwo extends Player {
    private boolean canDoubleJump; // Track if double jump is available

    // Constructor for Player Two
    public PlayerTwo(float x, float y, Map map) {
        super(new SpriteSheet(ImageLoader.load("prof2.png"), 24, 24), x, y, "STAND_RIGHT", map);
        gravity = 0.6f;
        terminalVelocityY = 6f;
        jumpHeight = 15f;
        jumpDegrade = 0.5f;
        walkSpeed = 4.5f;
        momentumYIncrease = 0.4f;
        canDoubleJump = true; // Initialize to true
    }

    @Override
    public void update() {
        // Reset double jump if the player is on the ground
        if (airGroundState == AirGroundState.GROUND) {
            canDoubleJump = true;
        }
        super.update();
    }

    @Override
    protected void playerJumping() {
        // If the player is grounded and jumping, allow for a second jump
        if (previousAirGroundState == AirGroundState.GROUND && airGroundState == AirGroundState.GROUND) {
            currentAnimationName = facingDirection == Direction.RIGHT ? "JUMP_RIGHT" : "JUMP_LEFT";
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
        // If the player is in the air and hasn't used the double jump yet
        else if (airGroundState == AirGroundState.AIR && canDoubleJump && Keyboard.isKeyDown(JUMP_KEY) && !keyLocker.isKeyLocked(JUMP_KEY)) {
            keyLocker.lockKey(JUMP_KEY); // Lock key to prevent multiple immediate jumps
            jumpForce = jumpHeight; // Reset jump force for double jump
            canDoubleJump = false; // Double jump is used up
            moveAmountY -= jumpForce;
            jumpForce -= jumpDegrade;
            if (jumpForce < 0) {
                jumpForce = 0;
            }
        }
        // Continue jumping logic for air movement and momentum
        else if (airGroundState == AirGroundState.AIR) {
            if (jumpForce > 0) {
                moveAmountY -= jumpForce;
                jumpForce -= jumpDegrade;
                if (jumpForce < 0) {
                    jumpForce = 0;
                }
            }

            if (Keyboard.isKeyDown(MOVE_LEFT_KEY)) {
                moveAmountX -= walkSpeed;
            } else if (Keyboard.isKeyDown(MOVE_RIGHT_KEY)) {
                moveAmountX += walkSpeed;
            }

            if (moveAmountY > 0) {
                increaseMomentum();
            }
        } else if (previousAirGroundState == AirGroundState.AIR && airGroundState == AirGroundState.GROUND) {
            playerState = PlayerState.STANDING;
        }
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
    }

    @Override
    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {{
            put("STAND_RIGHT", new Frame[]{
                new FrameBuilder(spriteSheet.getSprite(0, 0))
                    .withScale(3)
                    .withBounds(8, 9, 8, 9)
                    .build()
            });

            put("STAND_LEFT", new Frame[]{
                new FrameBuilder(spriteSheet.getSprite(0, 0))
                    .withScale(3)
                    .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                    .withBounds(8, 9, 8, 9)
                    .build()
            });

            put("WALK_RIGHT", new Frame[]{
                new FrameBuilder(spriteSheet.getSprite(1, 0), 14)
                    .withScale(3)
                    .withBounds(8, 9, 8, 9)
                    .build(),
                new FrameBuilder(spriteSheet.getSprite(1, 1), 14)
                    .withScale(3)
                    .withBounds(8, 9, 8, 9)
                    .build(),
                new FrameBuilder(spriteSheet.getSprite(1, 2), 14)
                    .withScale(3)
                    .withBounds(8, 9, 8, 9)
                    .build(),
                new FrameBuilder(spriteSheet.getSprite(1, 3), 14)
                    .withScale(3)
                    .withBounds(8, 9, 8, 9)
                    .build()
            });

            put("WALK_LEFT", new Frame[]{
                new FrameBuilder(spriteSheet.getSprite(1, 0), 14)
                    .withScale(3)
                    .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                    .withBounds(8, 9, 8, 9)
                    .build(),
                new FrameBuilder(spriteSheet.getSprite(1, 1), 14)
                    .withScale(3)
                    .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                    .withBounds(8, 9, 8, 9)
                    .build(),
                new FrameBuilder(spriteSheet.getSprite(1, 2), 14)
                    .withScale(3)
                    .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                    .withBounds(8, 9, 8, 9)
                    .build(),
                new FrameBuilder(spriteSheet.getSprite(1, 3), 14)
                    .withScale(3)
                    .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                    .withBounds(8, 9, 8, 9)
                    .build()
            });

            put("JUMP_RIGHT", new Frame[]{
                new FrameBuilder(spriteSheet.getSprite(2, 0))
                    .withScale(3)
                    .withBounds(8, 9, 8, 9)
                    .build()
            });

            put("JUMP_LEFT", new Frame[]{
                new FrameBuilder(spriteSheet.getSprite(2, 0))
                    .withScale(3)
                    .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                    .withBounds(8, 9, 8, 9)
                    .build()
            });

            put("FALL_RIGHT", new Frame[]{
                new FrameBuilder(spriteSheet.getSprite(3, 0))
                    .withScale(3)
                    .withBounds(8, 9, 8, 9)
                    .build()
            });

            put("FALL_LEFT", new Frame[]{
                new FrameBuilder(spriteSheet.getSprite(3, 0))
                    .withScale(3)
                    .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                    .withBounds(8, 9, 8, 9)
                    .build()
            });

            put("CROUCH_RIGHT", new Frame[]{
                new FrameBuilder(spriteSheet.getSprite(4, 0))
                    .withScale(3)
                    .withBounds(8, 12, 8, 6)
                    .build()
            });

            put("CROUCH_LEFT", new Frame[]{
                new FrameBuilder(spriteSheet.getSprite(4, 0))
                    .withScale(3)
                    .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                    .withBounds(8, 12, 8, 6)
                    .build()
            });

            put("DEATH_RIGHT", new Frame[]{
                new FrameBuilder(spriteSheet.getSprite(5, 0), 8)
                    .withScale(3)
                    .build(),
                new FrameBuilder(spriteSheet.getSprite(5, 1), 8)
                    .withScale(3)
                    .build(),
                new FrameBuilder(spriteSheet.getSprite(5, 2), -1)
                    .withScale(3)
                    .build()
            });

            put("DEATH_LEFT", new Frame[]{
                new FrameBuilder(spriteSheet.getSprite(5, 0), 8)
                    .withScale(3)
                    .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                    .build(),
                new FrameBuilder(spriteSheet.getSprite(5, 1), 8)
                    .withScale(3)
                    .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                    .build(),
                new FrameBuilder(spriteSheet.getSprite(5, 2), -1)
                    .withScale(3)
                    .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                    .build()
            });

            put("SWIM_STAND_RIGHT", new Frame[]{
                new FrameBuilder(spriteSheet.getSprite(6, 0))
                    .withScale(3)
                    .withBounds(8, 9, 8, 9)
                    .build()
            });

            put("SWIM_STAND_LEFT", new Frame[]{
                new FrameBuilder(spriteSheet.getSprite(6, 0))
                    .withScale(3)
                    .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                    .withBounds(8, 9, 8, 9)
                    .build()
            });
        }};
    }
}
