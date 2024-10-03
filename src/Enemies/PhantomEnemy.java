package Enemies;

import Builders.FrameBuilder;
import Engine.ImageLoader;
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

public class PhantomEnemy extends Enemy {

    protected Point startLocation;
    protected Point endLocation;

    protected float movementSpeed = .7f;
    private Direction startFacingDirection;
    protected Direction facingDirection;
    protected AirGroundState airGroundState;

    protected int shootWaitTimer;
    protected int shootTimer;

    protected int fireballCount;  // New variable to track the number of fireballs shot
    protected int fireballShootDelayTimer;  // Timer to delay between fireballs

    protected DinosaurState dinosaurState;
    protected DinosaurState previousDinosaurState;

    public PhantomEnemy(Point startLocation, Point endLocation, Direction facingDirection) {
        super(startLocation.x, startLocation.y, new SpriteSheet(ImageLoader.load("phantom.png"), 14, 17), "WALK_RIGHT");
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.startFacingDirection = facingDirection;
        this.initialize();
    }

    @Override
    public void initialize() {
        super.initialize();
        dinosaurState = DinosaurState.WALK;
        previousDinosaurState = dinosaurState;
        facingDirection = startFacingDirection;
        if (facingDirection == Direction.RIGHT) {
            currentAnimationName = "WALK_RIGHT";
        } else if (facingDirection == Direction.LEFT) {
            currentAnimationName = "WALK_LEFT";
        }
        airGroundState = AirGroundState.GROUND;

        shootWaitTimer = 65;
        fireballCount = 0;  // Initialize the fireball count
        fireballShootDelayTimer = 0;  // Initialize the delay timer
    }

    @Override
    public void update(Player player) {
        float startBound = startLocation.x;
        float endBound = endLocation.x;

        if (shootWaitTimer == 0 && dinosaurState != DinosaurState.SHOOT_WAIT) {
            dinosaurState = DinosaurState.SHOOT_WAIT;
        }
        else {
            shootWaitTimer--;
        }

        if (dinosaurState == DinosaurState.WALK) {
            if (facingDirection == Direction.RIGHT) {
                currentAnimationName = "WALK_RIGHT";
                moveXHandleCollision(movementSpeed);
            } else {
                currentAnimationName = "WALK_LEFT";
                moveXHandleCollision(-movementSpeed);
            }

            if (getX1() + getWidth() >= endBound) {
                float difference = endBound - (getX2());
                moveXHandleCollision(-difference);
                facingDirection = Direction.LEFT;
            } else if (getX1() <= startBound) {
                float difference = startBound - getX1();
                moveXHandleCollision(difference);
                facingDirection = Direction.RIGHT;
            }
        }

        if (dinosaurState == DinosaurState.SHOOT_WAIT) {
            if (previousDinosaurState == DinosaurState.WALK) {
                shootTimer = 65;
                currentAnimationName = facingDirection == Direction.RIGHT ? "SHOOT_RIGHT" : "SHOOT_LEFT";
            } else if (shootTimer == 0) {
                dinosaurState = DinosaurState.SHOOT;
                fireballCount = 3;  // Set to shoot 3 fireballs
                fireballShootDelayTimer = 10;  // Delay between each fireball shot
            } else {
                shootTimer--;
            }
        }

        if (dinosaurState == DinosaurState.SHOOT && fireballCount > 0) {
            if (fireballShootDelayTimer == 0) {
                // Spawn a fireball
                int fireballX;
                float movementSpeed;
                if (facingDirection == Direction.RIGHT) {
                    fireballX = Math.round(getX()) + getWidth();
                    movementSpeed = 3f;
                } else {
                    fireballX = Math.round(getX() - 21);
                    movementSpeed = -3f;
                }
                int fireballY = Math.round(getY()) + 4;

                // Create Fireball with a longer duration (e.g., 120 frames instead of 60)
                BlueFireball fireball = new BlueFireball(new Point(fireballX, fireballY), movementSpeed, 120);  // Increased duration

                map.addEnemy(fireball);

                fireballCount--;  // Decrease fireball count
                fireballShootDelayTimer = 15;  // Reset the delay timer for the next fireball
            } else {
                fireballShootDelayTimer--;  // Countdown for next fireball shot
            }

            if (fireballCount == 0) {
                // Once 3 fireballs are shot, return to WALK state
                dinosaurState = DinosaurState.WALK;
                shootWaitTimer = 130;  // Reset shoot wait timer for the next shooting phase
            }
        }

        super.update(player);

        previousDinosaurState = dinosaurState;
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
        }
    }

    @Override
    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {{
            put("WALK_LEFT", new Frame[]{
                    new FrameBuilder(spriteSheet.getSprite(0, 0), 14)
                            .withScale(3)
                            .withBounds(4, 2, 5, 13)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(0, 1), 14)
                            .withScale(3)
                            .withBounds(4, 2, 5, 13)
                            .build()
            });

            put("WALK_RIGHT", new Frame[]{
                    new FrameBuilder(spriteSheet.getSprite(0, 0), 14)
                            .withScale(3)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(4, 2, 5, 13)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(0, 1), 14)
                            .withScale(3)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(4, 2, 5, 13)
                            .build()
            });

            put("SHOOT_LEFT", new Frame[]{
                    new FrameBuilder(spriteSheet.getSprite(1, 0))
                            .withScale(3)
                            .withBounds(4, 2, 5, 13)
                            .build(),
            });

            put("SHOOT_RIGHT", new Frame[]{
                    new FrameBuilder(spriteSheet.getSprite(1, 0))
                            .withScale(3)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(4, 2, 5, 13)
                            .build(),
            });
        }};
    }

    public enum DinosaurState {
        WALK, SHOOT_WAIT, SHOOT
    }
}
