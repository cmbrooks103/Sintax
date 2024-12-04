package Level;

import Builders.FrameBuilder;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.SpriteSheet;
import Utils.Point;

import java.util.HashMap;

public class Explosion extends MapEntity {
    private int lifespan; // Number of frames the explosion will exist

    public Explosion(Point location, int lifespan) {
        super(location.x, location.y, new SpriteSheet(ImageLoader.load("Resources/Explosion.png"), 16, 16), "DEFAULT");
        this.lifespan = lifespan;
    }

    @Override
    public void update() {
        super.update(); // Call the base class update

        // Reduce lifespan and remove if it reaches zero
        lifespan--;
        if (lifespan <= 0) {
            this.mapEntityStatus = MapEntityStatus.REMOVED;
        }

        // Check if the current animation frame index has reached the last frame and stop it from looping
        if (currentAnimationName.equals("DEFAULT") && currentFrameIndex == getCurrentAnimation().length - 1) {
            this.mapEntityStatus = MapEntityStatus.REMOVED; // Remove the entity when animation finishes
        }
    }

    @Override
    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {{
            put("DEFAULT", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(0, 0), 5) // Set delay to 5
                    .withScale(3)
                    .withBounds(1, 1, 14, 14)
                    .build(),
                new FrameBuilder(spriteSheet.getSprite(0, 1), 5) // Set delay to 5
                    .withScale(3)
                    .withBounds(1, 1, 14, 14)
                    .build(),
                new FrameBuilder(spriteSheet.getSprite(0, 2), 5) // Set delay to 5
                    .withScale(3)
                    .withBounds(1, 1, 14, 14)
                    .build()
            });
        }};
    }
}
