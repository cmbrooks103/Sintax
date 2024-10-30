package EnhancedMapTiles;

import Builders.FrameBuilder;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.SpriteSheet;
import Level.EnhancedMapTile;
import Level.MapEntityStatus;
import Level.TileType;
import Utils.Point;

import java.util.HashMap;

public class Trail extends EnhancedMapTile {
    private long spawnTime; // Time when the trail is created
    private static final long LIFESPAN = 20000; // 20 seconds lifespan in milliseconds

    public Trail(Point location) {
        super(location.x, location.y, new SpriteSheet(ImageLoader.load("trail.png"), 16, 16), TileType.PASSABLE);
        this.spawnTime = System.currentTimeMillis();
    }

    @Override
    public void update() {
        super.update();
        // Remove trail after 20 seconds
        if (System.currentTimeMillis() - spawnTime >= LIFESPAN) {
            mapEntityStatus = MapEntityStatus.REMOVED;
        }
    }

    @Override
    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {{
            put("DEFAULT", new Frame[]{
                new FrameBuilder(spriteSheet.getSprite(0, 0), 40)
                    .withScale(3)
                    .withBounds(1, 1, 14, 14)
                    .build(),
                new FrameBuilder(spriteSheet.getSprite(0, 1), 40)
                    .withScale(3)
                    .withBounds(1, 1, 14, 14)
                    .build(),
                new FrameBuilder(spriteSheet.getSprite(0, 2), 40)
                    .withScale(3)
                    .withBounds(1, 1, 14, 14)
                    .build()
            });
        }};
    }
}
