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

// This class represents a puzzle tile that activates a PuzzlePlatform when the player touches it.
public class PuzzleTile extends EnhancedMapTile {
    private PuzzlePlatform puzzlePlatform; // Reference to the platform that this tile activates

    // Constructor that only takes a location point
    public PuzzleTile(Point location) {
        super(location.x, location.y, new SpriteSheet(ImageLoader.load("Resources/PuzzleTile.png"), 16, 16), TileType.PASSABLE);
    }

    // Method to connect the tile to a PuzzlePlatform
    public void setPuzzlePlatform(PuzzlePlatform puzzlePlatform) {
        this.puzzlePlatform = puzzlePlatform; // Initialize with the platform this tile will activate
    }

    @Override
    public void update(Player player) {
        super.update(player);
        // If the player intersects with the tile, activate the puzzle platform
        if (intersects(player) && puzzlePlatform != null) {
            puzzlePlatform.activate(); // Trigger the platform to start moving
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
