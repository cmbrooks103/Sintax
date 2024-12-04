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

public class GuardianEnemy extends Enemy {

    protected Point startLocation;
    protected Point endLocation;

    protected float movementSpeed = 1.5f;  // Increased movement speed
    private Direction startFacingDirection;
    protected Direction facingDirection;
    protected AirGroundState airGroundState;

    protected int shootWaitTimer;
    protected int shootTimer;

    protected int arrowCount;  // New variable to track the number of arrows shot
    protected int arrowShootDelayTimer;  // Timer to delay between arrows

    protected GuardianState guardianState;
    protected GuardianState previousGuardianState;

    public GuardianEnemy(Point startLocation, Point endLocation, Direction facingDirection) {
        super(startLocation.x, startLocation.y, new SpriteSheet(ImageLoader.load("Resources/guardian.png"), 14, 17), "WALK_RIGHT");
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.startFacingDirection = facingDirection;
        this.initialize();
    }

    @Override
    public void initialize() {
        super.initialize();
        guardianState = GuardianState.WALK;
        previousGuardianState = guardianState;
        facingDirection = startFacingDirection;
        if (facingDirection == Direction.RIGHT) {
            currentAnimationName = "WALK_RIGHT";
        } else if (facingDirection == Direction.LEFT) {
            currentAnimationName = "WALK_LEFT";
        }
        airGroundState = AirGroundState.GROUND;

        shootWaitTimer = 65;
        arrowCount = 0;  // Initialize the arrow count
        arrowShootDelayTimer = 0;  // Initialize the delay timer
    }

    @Override
    public void update(Player player) {
        float startBound = startLocation.x;
        float endBound = endLocation.x;

        if (shootWaitTimer == 0 && guardianState != GuardianState.SHOOT_WAIT) {
            guardianState = GuardianState.SHOOT_WAIT;
        } else {
            shootWaitTimer--;
        }

        if (guardianState == GuardianState.WALK) {
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

        if (guardianState == GuardianState.SHOOT_WAIT) {
            if (previousGuardianState == GuardianState.WALK) {
                shootTimer = 65;
                currentAnimationName = facingDirection == Direction.RIGHT ? "SHOOT_RIGHT" : "SHOOT_LEFT";
            } else if (shootTimer == 0) {
                guardianState = GuardianState.SHOOT;
                arrowCount = 1;  // Set to shoot 1 arrow
                arrowShootDelayTimer = 0;  // No delay needed between shots
            } else {
                shootTimer--;
            }
        }

        if (guardianState == GuardianState.SHOOT && arrowCount > 0) {
            if (arrowShootDelayTimer == 0) {
                // Spawn an arrow
                int arrowX;
                float arrowSpeed;
                if (facingDirection == Direction.RIGHT) {
                    arrowX = Math.round(getX()) + getWidth();
                    arrowSpeed = 6f;  // Fast arrow speed
                } else {
                    arrowX = Math.round(getX() - 21);
                    arrowSpeed = -6f;  // Fast arrow speed
                }
                int arrowY = Math.round(getY()) + 4;

                // Create Arrow with a shorter duration (e.g., 60 frames)
                arrow arrow = new arrow(new Point(arrowX, arrowY), arrowSpeed, 60);  // Shorter duration

                map.addEnemy(arrow);

                arrowCount--;  // Decrease arrow count
            }

            if (arrowCount == 0) {
                // Once 1 arrow is shot, return to WALK state
                guardianState = GuardianState.WALK;
                shootWaitTimer = 130;  // Reset shoot wait timer for the next shooting phase
            }
        }

        super.update(player);

        previousGuardianState = guardianState;
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
            put("WALK_LEFT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(0, 0), 14)
                            .withScale(3)
                            .withBounds(4, 2, 5, 13)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(0, 1), 14)
                            .withScale(3)
                            .withBounds(4, 2, 5, 13)
                            .build()
            });

            put("WALK_RIGHT", new Frame[] {
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

            put("SHOOT_LEFT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(1, 0))
                            .withScale(3)
                            .withBounds(4, 2, 5, 13)
                            .build(),
            });

            put("SHOOT_RIGHT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(1, 0))
                            .withScale(3)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(4, 2, 5, 13)
                            .build(),
            });
        }};
    }

    public enum GuardianState {
        WALK, SHOOT_WAIT, SHOOT
    }
}
