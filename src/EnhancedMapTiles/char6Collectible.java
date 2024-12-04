package EnhancedMapTiles;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import Builders.FrameBuilder;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.SpriteSheet;
import Level.EnhancedMapTile;
import Level.MapEntityStatus;
import Level.Player;
import Level.TileType;
import Utils.Point;


// This class is for a collectible 
// when the player touches it, it will disapear
public class char6Collectible extends EnhancedMapTile {

    public char6Collectible(Point location) {
        super(location.x, location.y, new SpriteSheet(ImageLoader.load("Resources/slayerCollectible.png"), 16, 16), TileType.PASSABLE);
    }

    @Override
    public void update(Player player) {
        super.update(player);
        if (intersects(player)) {
           mapEntityStatus = MapEntityStatus.REMOVED;
           unlockCharacter();
        }
    }


    @Override
    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {{
            put("DEFAULT", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(0, 0), 40)
                        .withScale(3)
                        .withBounds(1, 1, 14, 14)
                        .build(),
            });
        }};
    }

    public void unlockCharacter(){
        FileWriter fWriter;
        try {
            fWriter = new FileWriter("player6State.txt", false);
            fWriter.write("1");
            fWriter.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


}