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

public class TrailCollectible extends EnhancedMapTile {
    private static final long TRAIL_DURATION = 20000; // 20 seconds for the power-up effect
    private boolean collected = false; // Ensure collectible activates only once

    public TrailCollectible(Point location) {
        super(location.x, location.y, new SpriteSheet(ImageLoader.load("Resources/lightcollectible.png"), 16, 16), TileType.PASSABLE);
    }

    @Override
    public void update(Player player) {
        super.update(player);

        // When the player collects the power-up
        if (intersects(player) && !collected) {
            collected = true; // Prevent multiple activations
            mapEntityStatus = MapEntityStatus.REMOVED; // Remove the collectible

            // Activate the speed boost (2x speed for the player)
            player.activateSpeedBoost(2.0f);

            // Start generating trails for 20 seconds
            new Thread(() -> generateTrails(player)).start();

            // Schedule speed reset after 20 seconds
            new Thread(() -> resetSpeedAfterDuration(player)).start();
        }
    }

    private void generateTrails(Player player) {
        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < TRAIL_DURATION) {
            // Create a new trail at the player's current location
            Trail trail = new Trail(new Point((int) player.getX(), (int) player.getY()));
            player.getMap().addEnhancedMapTile(trail); // Add the trail to the map

            try {
                Thread.sleep(200); // Wait 200ms before placing the next trail
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Reset the player's speed after the power-up duration ends
    private void resetSpeedAfterDuration(Player player) {
        try {
            Thread.sleep(TRAIL_DURATION); // Wait 20 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        player.deactivateSpeedBoost(); // Reset the speed
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
