package EnhancedMapTiles;

import Builders.FrameBuilder;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.SpriteSheet;
import Level.EnhancedMapTile;
import Level.MapEntityStatus;
import Level.Player;
import Level.TileType;
import Utils.Point;

import java.util.HashMap;

public class KeyCollectible extends EnhancedMapTile {
    private boolean collected = false; // Ensure the key activates only once

    public KeyCollectible(Point location) {
        super(location.x, location.y, new SpriteSheet(ImageLoader.load("Collectible.png"), 16, 16), TileType.PASSABLE);
    }

    @Override
    public void update(Player player) {
        super.update(player);

        // When the player collects the key
        if (intersects(player) && !collected) {
            collected = true; // Prevent multiple activations
            player.setHasKey(true); // Mark the player as having the key
            mapEntityStatus = MapEntityStatus.REMOVED; // Remove the key from the map
        }
    }

    @Override
    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {{
            put("DEFAULT", new Frame[]{
                new FrameBuilder(spriteSheet.getSprite(0, 0), 40)
                        .withScale(3)
                        .withBounds(1, 1, 14, 14)
                        .build()
            });
        }};
    }
}
