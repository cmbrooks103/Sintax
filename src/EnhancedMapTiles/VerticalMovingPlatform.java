package EnhancedMapTiles;

import Builders.FrameBuilder;
import Engine.GraphicsHandler;
import GameObject.Rectangle;
import Level.EnhancedMapTile;
import Level.Player;
import Level.TileType;
import Utils.AirGroundState;
import Utils.Direction;
import Utils.Point;

import java.awt.image.BufferedImage;

// A vertical moving platform that smoothly bounces between two points.
public class VerticalMovingPlatform extends EnhancedMapTile {
    private Point startLocation;
    private Point endLocation;
    private float movementSpeed = 1f;
    private Direction direction; // UP or DOWN

    public VerticalMovingPlatform(BufferedImage image, Point startLocation, Point endLocation, TileType tileType,
                                  float scale, Rectangle bounds, Direction startDirection) {
        super(startLocation.x, startLocation.y,
              new FrameBuilder(image).withBounds(bounds).withScale(scale).build(), tileType);
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.direction = startDirection;
        this.initialize();
    }

    @Override
    public void initialize() {
        super.initialize();
    }

    @Override
    public void update(Player player) {
        float startBound = startLocation.y;
        float endBound = endLocation.y;

        // Determine movement direction and amount.
        float moveAmountY = (direction == Direction.DOWN) ? movementSpeed : -movementSpeed;

        // Move the platform.
        moveY(moveAmountY);

        // Reverse direction at the boundaries.
        if (getY1() >= endBound) {
            moveY(endBound - getY1()); // Align to end bound
            direction = Direction.UP;
        } else if (getY1() <= startBound) {
            moveY(startBound - getY1()); // Align to start bound
            direction = Direction.DOWN;
        }

        // Handle interaction with the player.
        if (isPlayerOnPlatform(player)) {
            player.moveYHandleCollision(moveAmountY); // Move player with the platform.

            // Ensure the player can jump by overriding the state if grounded.
            if (player.getAirGroundState() == AirGroundState.AIR) {
                forcePlayerToGround(player); // Override falling state if platform is moving.
            }
        }

        super.update(player);
    }

    private void forcePlayerToGround(Player player) {
        // Simulate the player being grounded to allow jumping.
        player.setAirGroundState(AirGroundState.GROUND);
    }

    private boolean isPlayerOnPlatform(Player player) {
        // Small buffer to prevent jittering issues.
        final int BUFFER = 2;
        return player.getBounds().getY2() >= this.getBounds().getY1() - BUFFER &&
               player.getBounds().getY2() <= this.getBounds().getY1() + BUFFER &&
               player.getBounds().getX2() > this.getBounds().getX1() &&
               player.getBounds().getX1() < this.getBounds().getX2();
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
    }
}
