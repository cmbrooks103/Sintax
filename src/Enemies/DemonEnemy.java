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

// Demon enemy, floats up and down
public class DemonEnemy extends Enemy {

    private float movementSpeed = 2f;
    private Direction startFacingDirection;
    private Direction facingDirection;
    private AirGroundState airGroundState;
    
    // New variables for vertical movement tracking
    private float verticalMoveAmount = 0;
    private final float maxVerticalMovement = 140; // 4 tiles = 64 pixels

    public DemonEnemy(Point location, Direction facingDirection) {
        super(location.x, location.y, new SpriteSheet(ImageLoader.load("DemonEnemy.png"), 22, 15), "FLOAT_DOWN");
        this.startFacingDirection = facingDirection;
        this.initialize();
    }

    @Override
    public void initialize() {
        super.initialize();
        facingDirection = startFacingDirection;
        if (facingDirection == Direction.UP) {
            currentAnimationName = "FLOAT_UP";
        } else if (facingDirection == Direction.DOWN) {
            currentAnimationName = "FLOAT_DOWN";
        }
        airGroundState = AirGroundState.AIR;
    }

    @Override
    public void update(Player player) {
        float moveAmountY = 0;

        // Move up and down
        if (facingDirection == Direction.DOWN) {
            moveAmountY = movementSpeed;
        } else if (facingDirection == Direction.UP) {
            moveAmountY = -movementSpeed;
        }

        // Update the vertical movement tracker
        verticalMoveAmount += moveAmountY;

        // Reverse direction when reaching max vertical movement (4 tiles) or ground
        if (verticalMoveAmount >= maxVerticalMovement && facingDirection == Direction.DOWN) {
            facingDirection = Direction.UP;
            currentAnimationName = "FLOAT_UP";
            verticalMoveAmount = 0; // Reset the tracker
        } else if (verticalMoveAmount <= -maxVerticalMovement && facingDirection == Direction.UP) {
            facingDirection = Direction.DOWN;
            currentAnimationName = "FLOAT_DOWN";
            verticalMoveAmount = 0; // Reset the tracker
        }

        // Move demon vertically
        moveYHandleCollision(moveAmountY);

        super.update(player);
    }

    @Override
    public void onEndCollisionCheckX(boolean hasCollided, Direction direction,  MapEntity entityCollidedWith) {
        // Do nothing for horizontal collision
    }

    @Override
    public void onEndCollisionCheckY(boolean hasCollided, Direction direction, MapEntity entityCollidedWith) {
        // If the demon hits the ground, reverse the vertical direction
        if (hasCollided && direction == Direction.DOWN) {
            facingDirection = Direction.UP;
            currentAnimationName = "FLOAT_UP";
            verticalMoveAmount = 0; // Reset the tracker
        }
    }

    @Override
    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {{
            put("FLOAT_DOWN", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(0, 0), 8)
                            .withScale(2)
                            .withBounds(6, 6, 12, 7)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(0, 1), 8)
                            .withScale(2)
                            .withBounds(6, 6, 12, 7)
                            .build()
            });

            put("FLOAT_UP", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(0, 0), 8)
                            .withScale(2)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(6, 6, 12, 7)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(0, 1), 8)
                            .withScale(2)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(6, 6, 12, 7)
                            .build()
            });
        }};
    }
}
