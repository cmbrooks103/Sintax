package Level;

import Builders.FrameBuilder;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.SpriteSheet;
import Utils.Point;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class PlayerSword extends MapEntity {
    private float speed;
    private int damage;
    private int lifespan; // Number of frames the sword slash will exist
    private boolean movingRight;

    public PlayerSword(Point location, float speed, int damage, int lifespan, boolean movingRight) {
        super(location.x, location.y, new SpriteSheet(ImageLoader.load("slash.png"), 7, 7), "DEFAULT");
        this.speed = speed;
        this.damage = damage;
        lifespan = (int) (50 / (1000.0 / 60.0));  // 200 ms lifespan
        this.movingRight = movingRight;
    }

    @Override
    public void update() {
        super.update(); // Call the base class update

        // Move the sword slash in the appropriate direction
        moveXHandleCollision(movingRight ? speed : -speed);

        // Check for collision with each enemy to ensure immediate damage registration
        for (Enemy enemy : map.getEnemies()) {
            if (this.getBounds().intersects(enemy.getBounds())) {
                enemy.hurtEnemy(); // Apply damage

                // Play clash sound effect
                playClashSound();

                // Create a clash at the intersection point
                Clash clash = new Clash(new Point((int) this.x, (int) this.y), 60);
                map.addMapEntity(clash);

                // Remove the sword slash after collision
                this.mapEntityStatus = MapEntityStatus.REMOVED;
                break; // Exit the loop as the sword slash should only damage one enemy at a time
            }
        }

        // Reduce lifespan and remove if it reaches zero
        lifespan--;
        if (lifespan <= 0) {
            // Create a clash when the sword slash vanishes
            Clash clash = new Clash(new Point((int) this.x, (int) this.y), 60);
            map.addMapEntity(clash);

            // Play clash sound effect for when the sword slash disappears
            playClashSound();

            this.mapEntityStatus = MapEntityStatus.REMOVED;
        }
    }

    private void playClashSound() {
        try {
            File clashSoundPath = new File("src/Sounds/Clash_Sound_Effect.wav"); // Update with your clash sound file path
            if (clashSoundPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(clashSoundPath);
                Clip clashClip = AudioSystem.getClip();
                clashClip.open(audioInput);
                clashClip.start(); // Play the clash sound once
            } else {
                System.out.println("WAV file not found: " + clashSoundPath);
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    @Override
    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {{
            put("DEFAULT", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(0, 0))
                    .withScale(3)
                    .withBounds(1, 1, 5, 5)
                    .build()
            });
        }};
    }
}
