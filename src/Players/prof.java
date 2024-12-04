package Players;

import Builders.FrameBuilder;
import Engine.GraphicsHandler;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.ImageEffect;
import GameObject.SpriteSheet;
import Level.Map;
import Level.Player;

import java.util.HashMap;

// Class representing Player One
public class prof extends Player {
    // Constructor for Player One
    public prof(float x, float y, Map map) {
        super(new SpriteSheet(ImageLoader.load("Resources/profsheet.png"), 24, 24), x, y, "STAND_RIGHT", map);
        gravity = 0.6f;
        terminalVelocityY = 6f;
        jumpHeight = 15; // Cast to int directly
        jumpDegrade = 0.5f;
        walkSpeed = 5.4f;
        momentumYIncrease = 0.5f;
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
        // Uncomment the following line if you want to draw bounds
        // drawBounds(graphicsHandler, new Color(255, 0, 0, 170));
    }

    @Override
    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {{
            put("STAND_RIGHT", new Frame[]{
                new FrameBuilder(spriteSheet.getSprite(0, 0))
                    .withScale(3)
                    .withBounds(8, 9, 8, 9)
                    .build()
            });

            put("STAND_LEFT", new Frame[]{
                new FrameBuilder(spriteSheet.getSprite(0, 0))
                    .withScale(3)
                    .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                    .withBounds(8, 9, 8, 9)
                    .build()
            });

            put("WALK_RIGHT", new Frame[]{
                new FrameBuilder(spriteSheet.getSprite(1, 0), 14)
                    .withScale(3)
                    .withBounds(8, 9, 8, 9)
                    .build(),
                new FrameBuilder(spriteSheet.getSprite(1, 1), 14)
                    .withScale(3)
                    .withBounds(8, 9, 8, 9)
                    .build(),
                new FrameBuilder(spriteSheet.getSprite(1, 2), 14)
                    .withScale(3)
                    .withBounds(8, 9, 8, 9)
                    .build(),
                new FrameBuilder(spriteSheet.getSprite(1, 3), 14)
                    .withScale(3)
                    .withBounds(8, 9, 8, 9)
                    .build()
            });

            put("WALK_LEFT", new Frame[]{
                new FrameBuilder(spriteSheet.getSprite(1, 0), 14)
                    .withScale(3)
                    .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                    .withBounds(8, 9, 8, 9)
                    .build(),
                new FrameBuilder(spriteSheet.getSprite(1, 1), 14)
                    .withScale(3)
                    .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                    .withBounds(8, 9, 8, 9)
                    .build(),
                new FrameBuilder(spriteSheet.getSprite(1, 2), 14)
                    .withScale(3)
                    .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                    .withBounds(8, 9, 8, 9)
                    .build(),
                new FrameBuilder(spriteSheet.getSprite(1, 3), 14)
                    .withScale(3)
                    .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                    .withBounds(8, 9, 8, 9)
                    .build()
            });

            put("JUMP_RIGHT", new Frame[]{
                new FrameBuilder(spriteSheet.getSprite(2, 0))
                    .withScale(3)
                    .withBounds(8, 9, 8, 9)
                    .build()
            });

            put("JUMP_LEFT", new Frame[]{
                new FrameBuilder(spriteSheet.getSprite(2, 0))
                    .withScale(3)
                    .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                    .withBounds(8, 9, 8, 9)
                    .build()
            });

            put("FALL_RIGHT", new Frame[]{
                new FrameBuilder(spriteSheet.getSprite(3, 0))
                    .withScale(3)
                    .withBounds(8, 9, 8, 9)
                    .build()
            });

            put("FALL_LEFT", new Frame[]{
                new FrameBuilder(spriteSheet.getSprite(3, 0))
                    .withScale(3)
                    .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                    .withBounds(8, 9, 8, 9)
                    .build()
            });

            put("CROUCH_RIGHT", new Frame[]{
                new FrameBuilder(spriteSheet.getSprite(4, 0))
                    .withScale(3)
                    .withBounds(8, 12, 8, 6)
                    .build()
            });

            put("CROUCH_LEFT", new Frame[]{
                new FrameBuilder(spriteSheet.getSprite(4, 0))
                    .withScale(3)
                    .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                    .withBounds(8, 12, 8, 6)
                    .build()
            });

            put("DEATH_RIGHT", new Frame[]{
                new FrameBuilder(spriteSheet.getSprite(5, 0), 8)
                    .withScale(3)
                    .build(),
                new FrameBuilder(spriteSheet.getSprite(5, 1), 8)
                    .withScale(3)
                    .build(),
                new FrameBuilder(spriteSheet.getSprite(5, 2), -1)
                    .withScale(3)
                    .build()
            });

            put("DEATH_LEFT", new Frame[]{
                new FrameBuilder(spriteSheet.getSprite(5, 0), 8)
                    .withScale(3)
                    .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                    .build(),
                new FrameBuilder(spriteSheet.getSprite(5, 1), 8)
                    .withScale(3)
                    .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                    .build(),
                new FrameBuilder(spriteSheet.getSprite(5, 2), -1)
                    .withScale(3)
                    .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                    .build()
            });

            put("SWIM_STAND_RIGHT", new Frame[]{
                new FrameBuilder(spriteSheet.getSprite(6, 0))
                    .withScale(3)
                    .withBounds(8, 9, 8, 9)
                    .build()
            });

            put("SWIM_STAND_LEFT", new Frame[]{
                new FrameBuilder(spriteSheet.getSprite(6, 0))
                    .withScale(3)
                    .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                    .withBounds(8, 9, 8, 9)
                    .build()
            });
        }};
    }
}
