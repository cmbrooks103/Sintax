package Level;

import Engine.GraphicsHandler;
import GameObject.Frame;
import GameObject.SpriteSheet;
import Screens.PlayLevelScreen;

import java.awt.Color;
import java.util.HashMap;

// This class is a base class for all enemies in the game
public class Enemy extends MapEntity {
    public int Health;
    protected int damageTimer;
    protected int maxHealth;  // To keep track of the maximum health

    public Enemy(float x, float y, SpriteSheet spriteSheet, String startingAnimation) {
        super(x, y, spriteSheet, startingAnimation);
        this.maxHealth = 3;  // Default max health
    }

    public Enemy(float x, float y, HashMap<String, Frame[]> animations, String startingAnimation) {
        super(x, y, animations, startingAnimation);
        this.maxHealth = 3;  // Default max health
    }

    public Enemy(float x, float y, Frame[] frames) {
        super(x, y, frames);
        this.maxHealth = 3;  // Default max health
    }

    public Enemy(float x, float y, Frame frame) {
        super(x, y, frame);
        this.maxHealth = 3;  // Default max health
    }

    public Enemy(float x, float y) {
        super(x, y);
        this.maxHealth = 3;  // Default max health
    }

    @Override
    public void initialize() {
        super.initialize();
        Health = maxHealth;  // Initialize health to max health
        damageTimer = 0;
    }

    public void update(Player player) {
        super.update();
        if (damageTimer > 0) {
            damageTimer--;
        }
        if (intersects(player)) {
            touchedPlayer(player);
        }
    }

    public void touchedPlayer(Player player) {
        player.hurtPlayer(this);
    }

    public void hurtEnemy() {
        if (damageTimer == 0) {
            damageTimer = 1;
            if (Health > 0) {
                Health--;
                if (Health <= 0) {
                    PlayLevelScreen.playEnemyDefeatSound();
                    this.mapEntityStatus = mapEntityStatus.REMOVED;  // Remove the enemy when dead
                }
            }
        }
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
        drawHealthBar(graphicsHandler);  // Draw the health bar
    }

    public void drawHealthBar(GraphicsHandler graphicsHandler) {
        if (Health > 0) {
            int barWidth = 1000;  // Width of the health bar
            int healthBarWidth = (int) (((float) Health / maxHealth) * barWidth);

            // Draw filled health bar
            graphicsHandler.drawFilledRectangle(getX(), getY() - 15, healthBarWidth, 100, Color.RED);
            // Draw outline of health bar
            graphicsHandler.drawRectangle(getX(), getY() - 15, barWidth, 100, Color.BLACK);
        }
    }
}
