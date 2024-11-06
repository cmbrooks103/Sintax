package Enemies;

import Builders.FrameBuilder;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.SpriteSheet;
import Level.Enemy;
import Level.MapEntity;
import Level.MapEntityStatus;
import Level.Player;
import Utils.Direction;
import Utils.Point;

import java.util.HashMap;

// Modified Fireball class to handle upward movement
public class Fireball extends Enemy {
    private float movementSpeed;
    private int existenceFrames;
    private boolean moveUpwards;  // Added to determine direction

    public Fireball(Point location, float movementSpeed, int existenceFrames, boolean moveUpwards) {
        super(location.x, location.y, new SpriteSheet(ImageLoader.load("Fireball.png"), 7, 7), "DEFAULT");
        this.movementSpeed = movementSpeed;
        this.existenceFrames = existenceFrames;
        this.moveUpwards = moveUpwards;  // Initialize direction
        isProjectile = true;
        initialize();
    }

    @Override
    public void update(Player player) {
        // Remove fireball if timer is up
        if (existenceFrames == 0) {
            this.mapEntityStatus = MapEntityStatus.REMOVED;
        } else {
            // Move fireball upward or forward based on the direction
            if (moveUpwards) {
                moveYHandleCollision(-movementSpeed);  // Move upwards (negative Y direction)
            } else {
                moveXHandleCollision(movementSpeed);   // Horizontal movement
            }
            super.update(player);
        }
        existenceFrames--;
    }

    @Override
    public void onEndCollisionCheckX(boolean hasCollided, Direction direction, MapEntity entityCollidedWith) {
        if (hasCollided) {
            this.mapEntityStatus = MapEntityStatus.REMOVED;
        }
    }

    @Override
    public void touchedPlayer(Player player) {
        super.touchedPlayer(player);
        this.mapEntityStatus = MapEntityStatus.REMOVED;
    }

    @Override
    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {{
            put("DEFAULT", new Frame[]{
                    new FrameBuilder(spriteSheet.getSprite(0, 0))
                            .withScale(3)
                            .withBounds(1, 1, 5, 5)
                            .build()
            });
        }};
    }
}
