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


public class Fproof extends NPC {

    public Fproof(Point location) {
        super(location.x, location.y, new SpriteSheet(ImageLoader.load("Fproof.png"), 24, 24), "DORMANT");
        isInteractable = true;
        talkedToTime = 200;
        textbox.setText("You are now immune to heat damage");
        textboxOffsetX = -4;
        textboxOffsetY = -50;
    }

    public void update(Player player) {
        if (talkedTo) {
            currentAnimationName = "ACTIVATE";
            player.isWaterDamageImmune = true; // Grant damage immunity
        } else {
            currentAnimationName = "DORMANT";
        }
    
        super.update(player);
    }
    

    @Override
    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {{
           put("DORMANT", new Frame[] {
                   new FrameBuilder(spriteSheet.getSprite(0, 0))
                           .withScale(3)
                           .build()
           });
            put("ACTIVATE", new Frame[] {
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
