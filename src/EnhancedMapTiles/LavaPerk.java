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

public class LavaPerk extends EnhancedMapTile {
    private static final long IMMUNITY_DURATION = 20000; // 20 seconds for lava immunity
    private boolean collected = false; // Ensure collectible activates only once

    public LavaPerk(Point location) {
        super(location.x, location.y, new SpriteSheet(ImageLoader.load("lavaperk.png"), 16, 16), TileType.PASSABLE);
    }

    @Override
    public void update(Player player) {
        super.update(player);

        // When the player collects the LavaPerk
        if (intersects(player) && !collected) {
            collected = true; // Prevent multiple activations
            mapEntityStatus = MapEntityStatus.REMOVED; // Remove the collectible from the map

            // Enable lava immunity for the player
            player.setWaterDamageImmune(true); // Assume water immunity applies to lava in this context

            // Schedule immunity reset after the duration ends
            new Thread(() -> resetLavaImmunityAfterDuration(player)).start();
        }
    }

    // Reset the player's lava immunity after the effect duration ends
    private void resetLavaImmunityAfterDuration(Player player) {
        try {
            Thread.sleep(IMMUNITY_DURATION); // Wait 20 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        player.setWaterDamageImmune(false); // Disable lava immunity by re-enabling damage
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
