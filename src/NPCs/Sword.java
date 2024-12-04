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

public class Sword extends NPC {

    private boolean hasTalked = false;  // Tracks if the NPC has been interacted with

    public Sword(Point location) {
        super(location.x, location.y, new SpriteSheet(ImageLoader.load("Resources/Sword.png"), 24, 24), "NORMAL");
        isInteractable = true;
        talkedToTime = 200;
        textbox.setText("b roke");
        textboxOffsetX = -4;
        textboxOffsetY = -800;
    }

    public void update(Player player) {
        if (talkedTo && !hasTalked) {
            currentAnimationName = "BROKEN";
            hasTalked = true;  // Set to true to ensure it doesn't switch back to NORMAL
        }
    
        super.update(player);
    }

    @Override
    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {{
           put("NORMAL", new Frame[] {
                   new FrameBuilder(spriteSheet.getSprite(0, 0))
                           .withScale(3)
                           .build()
           });
            put("BROKEN", new Frame[] {
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
