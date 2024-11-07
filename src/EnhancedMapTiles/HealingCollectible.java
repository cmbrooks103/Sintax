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

public class HealingCollectible extends EnhancedMapTile {
    private boolean collected = false; // Ensure collectible activates only once

    public HealingCollectible(Point location) {
        super(location.x, location.y, new SpriteSheet(ImageLoader.load("HealingCollectible.png"), 16 , 16), TileType.PASSABLE);
    }

    @Override
    public void update(Player player) {
        super.update(player);
        if(player.getPlayerHealth() <3){
            // When the player collects the power-up
            if (intersects(player) && !collected) {
                collected = true; // Prevent multiple activations
                mapEntityStatus = MapEntityStatus.REMOVED; // Remove the collectible

                player.setPlayerHealth(player.getPlayerHealth()+1);
            }
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