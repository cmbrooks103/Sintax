package Level;

import Builders.FrameBuilder;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.SpriteSheet;
import Utils.Point;

import java.util.HashMap;

public class PlayerFireball extends MapEntity {
    private float speed;
    private int damage;
    private int lifespan; // Number of frames the fireball will exist
    private boolean movingRight;

    public PlayerFireball(Point location, float speed, int damage, int lifespan, boolean movingRight) {
        super(location.x, location.y, new SpriteSheet(ImageLoader.load("Fireball.png"), 7, 7), "DEFAULT");
        this.speed = speed;
        this.damage = damage;
        this.lifespan = lifespan;
        this.movingRight = movingRight;
    }

    @Override
    public void update() {
        super.update(); // Call the base class update

        // Move the fireball in the appropriate direction
        moveXHandleCollision(movingRight ? speed : -speed);

        // Check for collision with each enemy to ensure immediate damage registration
        for (Enemy enemy : map.getEnemies()) {
            if (this.getBounds().intersects(enemy.getBounds())) {
                enemy.hurtEnemy(); // Apply damage

                // Create an explosion at the intersection point
                Explosion explosion = new Explosion(new Point((int) this.x, (int) this.y), 60);
                map.addMapEntity(explosion);

                // Remove the fireball after collision
                this.mapEntityStatus = MapEntityStatus.REMOVED;
                break; // Exit the loop as the fireball should only damage one enemy at a time
            }
        }

        // Reduce lifespan and remove if it reaches zero
        lifespan--;
        if (lifespan <= 0) {
            // Create an explosion when the fireball vanishes
            Explosion explosion = new Explosion(new Point((int) this.x, (int) this.y), 60);
            map.addMapEntity(explosion);

            this.mapEntityStatus = MapEntityStatus.REMOVED;
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
