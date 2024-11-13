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

public class Door extends EnhancedMapTile {
    public Door(Point location) {
        super(location.x, location.y, new SpriteSheet(ImageLoader.load("blade.png"), 16, 16), TileType.NOT_PASSABLE); // Initially NOT_PASSABLE
    }

    @Override
    public void update(Player player) {
        super.update(player);

        // If the player has the key, set the door to be passable
        if (player.hasKey()) {
            this.tileType = TileType.PASSABLE;  // Change door to passable
        }

        // When the player intersects with the door and has the key, remove the door from the map
        if (intersects(player) && player.hasKey()) {
            mapEntityStatus = MapEntityStatus.REMOVED; // Remove the door from the map
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
