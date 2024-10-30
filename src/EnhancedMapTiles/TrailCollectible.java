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

import java.util.ArrayList;
import java.util.HashMap;

public class TrailCollectible extends EnhancedMapTile {
    private static final long TRAIL_DURATION = 20000; // 20 seconds duration
    private static final float SPEED_MULTIPLIER = 2.0f; // Speed multiplier
    private static final int TRAIL_INTERVAL = 200; // Interval in milliseconds for spawning trails

    private boolean isActive = false; // Prevents multiple activations

    public TrailCollectible(Point location) {
        super(location.x, location.y, new SpriteSheet(ImageLoader.load("collectible.png"), 16, 16), TileType.PASSABLE);
    }

    @Override
    public void update(Player player) {
        super.update(player);

        // If the player touches the collectible and it hasn't been activated yet
        if (!isActive && intersects(player)) {
            isActive = true; // Mark as activated to prevent multiple triggers
            mapEntityStatus = MapEntityStatus.REMOVED; // Remove the collectible from the map

            // Activate speed boost for the player
            player.activateSpeedBoost(SPEED_MULTIPLIER);

            // Start a thread to spawn trails and disable the speed boost after 20 seconds
            new Thread(() -> {
                long startTime = System.currentTimeMillis();
                while (System.currentTimeMillis() - startTime < TRAIL_DURATION) {
                    // Create a trail at the player's current position
                    Trail trail = new Trail(new Point((int) player.getX(), (int) player.getY()));
                    player.getMap().addEnhancedMapTile(trail); // Add the trail to the map

                    // Wait for the next trail spawn
                    try {
                        Thread.sleep(TRAIL_INTERVAL); // Pause between trail spawns
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                // Reset the player's speed after the trail effect ends
                player.deactivateSpeedBoost();
            }).start();
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
