package Enemies;

import Builders.FrameBuilder;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.ImageEffect;
import GameObject.SpriteSheet;
import Level.Enemy;
import Level.Player;
import Utils.Point;

import java.util.HashMap;

// This class is for the volcano enemy that shoots fireballs upward every few seconds
public class VolcanoEnemy extends Enemy {

    // Timer used to determine when to shoot fireballs
    private int shootWaitTimer;
    private int shootTimer;
    private VolcanoState volcanoState;
    private VolcanoState previousVolcanoState;

    public VolcanoEnemy(Point location) {
        // Use the volcano sprite sheet
        super(location.x, location.y, new SpriteSheet(ImageLoader.load("volcano.png"), 14, 17), "IDLE");
        initialize();
    }

    @Override
    public void initialize() {
        super.initialize();
        volcanoState = VolcanoState.IDLE;
        previousVolcanoState = volcanoState;

        // Set the initial shoot wait timer
        shootWaitTimer = 35;
    }

    @Override
    public void update(Player player) {
        // Reduce the shoot wait timer each frame
        if (shootWaitTimer == 0 && volcanoState != VolcanoState.SHOOT_WAIT) {
            volcanoState = VolcanoState.SHOOT_WAIT;
        } else {
            shootWaitTimer--;
        }

        // Volcano is idle before shooting
        if (volcanoState == VolcanoState.IDLE) {
            currentAnimationName = "IDLE";
        }

        // After shoot wait time, volcano enters shoot wait state, preparing to shoot fireballs
        if (volcanoState == VolcanoState.SHOOT_WAIT) {
            if (previousVolcanoState == VolcanoState.IDLE) {
                shootTimer = 45;
                currentAnimationName = "SHOOT";
            } else if (shootTimer == 0) {
                volcanoState = VolcanoState.SHOOT;
            } else {
                shootTimer--;
            }
        }

        // Shoot fireballs upwards when in the SHOOT state
        if (volcanoState == VolcanoState.SHOOT) {
            shootFireballs();
            volcanoState = VolcanoState.IDLE;
            shootWaitTimer = 75;
        }

        super.update(player);
        previousVolcanoState = volcanoState;
    }

    // Method to handle shooting fireballs
    private void shootFireballs() {
        // The volcano will shoot 5 fireballs upward
        for (int i = 0; i < 5; i++) {
            int fireballX = Math.round(getX()) + getWidth() / 2;
            int fireballY = Math.round(getY()) - (i * 20);  // Spread fireballs along Y axis
            Fireball fireball = new Fireball(new Point(fireballX, fireballY), 3.5f, 60, true);  // Movement speed and moving upwards
            map.addEnemy(fireball);  // Add the fireball to the map
        }
    }

    @Override
    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        // Load animations for idle and shoot states
        return new HashMap<String, Frame[]>() {{
            put("IDLE", new Frame[]{
                new FrameBuilder(spriteSheet.getSprite(0, 0), 0)
                    .withScale(3)
                    .withBounds(4, 2, 5, 13)
                    .build()
            });

            put("SHOOT", new Frame[]{
                new FrameBuilder(spriteSheet.getSprite(1, 0), 0)
                    .withScale(3)
                    .withBounds(4, 2, 5, 13)
                    .build(),
            });
        }};
    }

    // Enum for volcano states
    public enum VolcanoState {
        IDLE, SHOOT_WAIT, SHOOT
    }
}
