package EnhancedMapTiles;

import Builders.FrameBuilder;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.SpriteSheet;
import Level.EnhancedMapTile;
import Level.Player;
import Level.TileType;
import Utils.Point;

import java.util.HashMap;

// This class represents a portal tile that teleports the player when touched.
public class PortalTile extends EnhancedMapTile {
    private Point teleportDestination; // Coordinates to teleport the player to

    // Constructor that only takes a location point
    public PortalTile(Point location) {
        super(location.x, location.y, new SpriteSheet(ImageLoader.load("portal.png"), 16, 16), TileType.PASSABLE);
    }

    // Method to set the teleport destination
    public void setTeleportDestination(Point destination) {
        this.teleportDestination = destination; // Initialize with the destination coordinates
    }

    @Override
    public void update(Player player) {
        super.update(player);
        // If the player intersects with the tile, teleport the player
        if (intersects(player) && teleportDestination != null) {
            player.setLocation(teleportDestination); // Teleport the player using the new setLocation method
        }
    }

    @Override
    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {{
            put("DEFAULT", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(0, 0), 10)
                        .withScale(3)
                        .withBounds(1, 1, 14, 14)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(0, 1), 10)
                        .withScale(3)
                        .withBounds(1, 1, 14, 14)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(0, 2), 10)
                        .withScale(3)
                        .withBounds(1, 1, 14, 14)
                        .build()
            });
        }};
    }
}
