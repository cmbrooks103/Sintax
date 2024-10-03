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

// This class represents a puzzle platform that gets triggered by a puzzle tile
// The platform will move from its start location to its destination once activated
public class PuzzlePlatform extends EnhancedMapTile {
    private Point startLocation;
    private Point endLocation;
    private float movementSpeed = 3f; // Movement speed of the platform
    private Direction direction;
    private boolean isActivated; // Whether the platform has been triggered to move

    public PuzzlePlatform(BufferedImage image, Point startLocation, Point endLocation, TileType tileType, float scale, Rectangle bounds, Direction startDirection) {
        super(startLocation.x, startLocation.y, new FrameBuilder(image).withBounds(bounds).withScale(scale).build(), tileType);
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.direction = startDirection;
        this.isActivated = false; // Platform is inactive by default
    }

    // Method to activate the platform (called by the puzzle tile)
    public void activate() {
        isActivated = true; // Set activated to true
    }

    @Override
    public void update(Player player) {
        if (isActivated) {  // Only move the platform if it's been activated
            // Calculate the direction to move based on the current position relative to the destination
            float targetX = endLocation.x;
            float currentX = getX1();
            float moveAmountX = 0;

            // Determine if we need to move towards the target
            if (currentX < targetX) {
                moveAmountX = Math.min(movementSpeed, targetX - currentX); // Move right
            } else if (currentX > targetX) {
                moveAmountX = -Math.min(movementSpeed, currentX - targetX); // Move left
            }

            moveX(moveAmountX); // Move the platform

            // Check if the platform has reached the destination
            if (Math.abs(currentX - targetX) <= movementSpeed) {
                setX(targetX); // Snap to the target position
                isActivated = false; // Deactivate after reaching the destination
            }

            // Adjust player's position if they are standing on the platform
            if (touching(player) && (player.getBounds().getY2() + 1) == getBounds().getY1() && player.getAirGroundState() == AirGroundState.GROUND) {
                // Allow the player to move with the platform
                player.moveXHandleCollision(moveAmountX);
            }
        }

        super.update(player); // Call the superclass update
    }

    

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler); // Draw the platform
    }
}
