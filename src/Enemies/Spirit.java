package Enemies;

import Builders.FrameBuilder;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.SpriteSheet;
import Level.Enemy;
import Level.Player;
import Utils.Point;

import java.util.HashMap;

// This class is for a stationary enemy named Spirit that renders animations
public class Spirit extends Enemy {

    private SpiritState spiritState;
    private SpiritState previousSpiritState;

    public Spirit(Point location) {
        // Use a sprite sheet for the Spirit enemy
        super(location.x, location.y, new SpriteSheet(ImageLoader.load("spirit.png"), 14, 17), "IDLE");
        initialize();
    }

    @Override
    public void initialize() {
        super.initialize();
        spiritState = SpiritState.IDLE; // Set the initial state
        previousSpiritState = spiritState;
    }

    @Override
    public void update(Player player) {
        // Update animations based on the Spirit's state
        switch (spiritState) {
            case IDLE:
                currentAnimationName = "IDLE"; // Set the animation to idle
                break;
            case ATTACK:
                currentAnimationName = "ATTACK"; // Set the animation to attack
                // Optionally, you could add logic here to return to IDLE after the attack
                break;
            // Add more cases for other states as needed
        }

        // Call the parent update method to handle animation updates
        super.update(player);
        previousSpiritState = spiritState; // Update previous state for the next update
    }

    @Override
    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        // Load animations for idle and attack states
        return new HashMap<String, Frame[]>() {{
            put("IDLE", new Frame[]{
                new FrameBuilder(spriteSheet.getSprite(0, 0), 40)
                    .withScale(3)
                    .withBounds(4, 2, 5, 13)
                    .build(),
                new FrameBuilder(spriteSheet.getSprite(0, 1), 40)
                    .withScale(3)
                    .withBounds(4, 2, 5, 13)
                    .build()
            });
            put("ATTACK", new Frame[]{
                new FrameBuilder(spriteSheet.getSprite(1, 0), 40)
                    .withScale(3)
                    .withBounds(4, 2, 5, 13)
                    .build(),
                new FrameBuilder(spriteSheet.getSprite(1, 1), 40)
                    .withScale(3)
                    .withBounds(4, 2, 5, 13)
                    .build()
            });
        }};
    }

    // Enum for Spirit states
    public enum SpiritState {
        IDLE, ATTACK
    }
}
