package NPCs;

import java.util.HashMap;

import Builders.FrameBuilder;
import Engine.GraphicsHandler;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.SpriteSheet;
import Level.NPC;
import Level.Player;
import Utils.Point;


public class Grave extends NPC {

    public Grave(Point location) {
        super(location.x, location.y, new SpriteSheet(ImageLoader.load("Grave.png"), 24, 24), "RESTING");
        isInteractable = true;
        talkedToTime = 200;
        textbox.setText("↸𝙹リ'ℸᓵꖌ∷ℸ");
        textboxOffsetX = -4;
        textboxOffsetY = -50;
    }

    public void update(Player player) {
        if (talkedTo) {
            currentAnimationName = "WAKEN";
        } else {
            currentAnimationName = "RESTING";
        }
    
        super.update(player);
    }
    

    @Override
    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {{
           put("RESTING", new Frame[] {
                   new FrameBuilder(spriteSheet.getSprite(0, 0))
                           .withScale(3)
                           .build()
           });
            put("WAKEN", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(1, 0))
                            .withScale(3)
                            .build()
            });
        }};
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
    }
}
