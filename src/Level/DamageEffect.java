package Level;

import Builders.FrameBuilder;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.SpriteSheet;
import Utils.Point;

import java.util.HashMap;

public class DamageEffect extends MapEntity {
    private int lifespan; // Number of frames the effect will exist

    public DamageEffect(Point location, int lifespan) {
        super(location.x, location.y, new SpriteSheet(ImageLoader.load("DamageEffect.png"), 16, 16), "DEFAULT");
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

        // Check if the animation reached the last frame and remove it from the map
        if (currentAnimationName.equals("DEFAULT") && currentFrameIndex == getCurrentAnimation().length - 1) {
            this.mapEntityStatus = MapEntityStatus.REMOVED;
        }
    }

    @Override
    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {{
            put("DEFAULT", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(0, 0), 20) // Set frame delay to 5
                    .withScale(3)
                    .withBounds(1, 1, 14, 14)
                    .build(),
                new FrameBuilder(spriteSheet.getSprite(0, 1), 20)
                    .withScale(3)
                    .withBounds(1, 1, 14, 14)
                    .build(),
                new FrameBuilder(spriteSheet.getSprite(0, 2), 20)
                    .withScale(3)
                    .withBounds(1, 1, 14, 14)
                    .build()
            });
        }};
    }
}
