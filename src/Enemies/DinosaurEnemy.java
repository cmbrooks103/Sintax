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

// This class is for the green dinosaur enemy that shoots fireballs
public class DinosaurEnemy extends Enemy {
    protected Point startLocation;
    protected Point endLocation;
    protected float movementSpeed = 1f;
    private Direction startFacingDirection;
    protected Direction facingDirection;
    protected AirGroundState airGroundState;

    protected int shootWaitTimer;
    protected int shootTimer;

    protected DinosaurState dinosaurState;
    protected DinosaurState previousDinosaurState;

    private int customHealth = 4; // Set health to 10 for this dinosaur

    public DinosaurEnemy(Point startLocation, Point endLocation, Direction facingDirection) {
        super(startLocation.x, startLocation.y, new SpriteSheet(ImageLoader.load("DinosaurEnemy.png"), 14, 17), "WALK_RIGHT");
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.startFacingDirection = facingDirection;
        initialize();
    }

    @Override
    public void initialize() {
        super.initialize();
        this.Health = customHealth; // Override the default health with custom health
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
    }

    @Override
    public void update(Player player) {
        float startBound = startLocation.x;
        float endBound = endLocation.x;

        if (shootWaitTimer == 0 && dinosaurState != DinosaurState.SHOOT_WAIT) {
            dinosaurState = DinosaurState.SHOOT_WAIT;
        } else {
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
            } else {
                shootTimer--;
            }
        }

        if (dinosaurState == DinosaurState.SHOOT) {
            int fireballX;
            float movementSpeed;
            if (facingDirection == Direction.RIGHT) {
                fireballX = Math.round(getX()) + getWidth();
                movementSpeed = 1.5f;
            } else {
                fireballX = Math.round(getX() - 21);
                movementSpeed = -1.5f;
            }

            int fireballY = Math.round(getY()) + 4;
            Fireball fireball = new Fireball(new Point(fireballX, fireballY), movementSpeed, 60, isUpdateOffScreen);
            map.addEnemy(fireball);
            dinosaurState = DinosaurState.WALK;
            shootWaitTimer = 130;
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
