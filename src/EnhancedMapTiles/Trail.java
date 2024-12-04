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
    private static final long LIFESPAN = 100; // 200ms lifespan for each trail
    private long spawnTime; // Record when the trail was created
    private boolean animationStopped = false; // Track if animation has stopped

    public Trail(Point location) {
        super(location.x, location.y, new SpriteSheet(ImageLoader.load("Resources/trail.png"), 16, 16), TileType.PASSABLE);
        this.spawnTime = System.currentTimeMillis(); // Set creation time
    }

    @Override
    public void update() {
        super.update();

        // Check if the trail has lived beyond its lifespan
        if (System.currentTimeMillis() - spawnTime >= LIFESPAN && !animationStopped) {
            stopAnimationAtLastFrame(); // Stop animation at the last frame
            animationStopped = true; // Ensure it only runs once
        }
    }

    private void stopAnimationAtLastFrame() {
        currentFrameIndex = getCurrentAnimation().length - 1; // Set to the last frame
        mapEntityStatus = MapEntityStatus.ACTIVE; // Keep the trail on the map
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
                new FrameBuilder(spriteSheet.getSprite(0, 2), -1) // Final frame, no loop (-1 duration)
                    .withScale(3)
                    .withBounds(1, 1, 14, 14)
                    .build()
            });
        }};
    }
}
