package Screens;

import Engine.*;
import Game.GameState;
import Game.ScreenCoordinator;
import SpriteFont.SpriteFont;
import Players.PlayerThree;
import Players.PlayerTwo;
import Level.Player;
import Players.PlayerType;

import java.awt.*;
import java.awt.image.BufferedImage;

public class CharacterSelectScreen extends Screen {
    private ScreenCoordinator screenCoordinator;
    private SpriteFont character1;
    private SpriteFont character2;
    private SpriteFont character3; // New option for PlayerThree
    private int currentOptionHovered = 0;
    private int pointerLocationX, pointerLocationY;
    private KeyLocker keyLocker = new KeyLocker();
    private int keyPressTimer = 0;
    private BufferedImage characterSelectImage;
    private PlayerType selectedPlayerType;

    private int imageWidth = 1200;
    private int imageHeight = 1200;
    private int imageX = 0;
    private int imageY = 0;

    public CharacterSelectScreen(ScreenCoordinator screenCoordinator) {
        this.screenCoordinator = screenCoordinator;
        this.selectedPlayerType = PlayerType.PROF; // Default to PROF
    }

    @Override
    public void initialize() {
        character1 = new SpriteFont("Professor Oneil: Run Faster!", 100, 50, "Arial", 35, new Color(198, 49, 17));
        character1.setOutlineColor(Color.black);
        character1.setOutlineThickness(3);

        character2 = new SpriteFont("Professor Alex: Jump Higher!", 100, 150, "Arial", 35, new Color(198, 49, 17));
        character2.setOutlineColor(Color.black);
        character2.setOutlineThickness(3);
        
        character3 = new SpriteFont("Professor Oneil Variant: Jack O' Lantern!", 100, 250, "Arial", 35, new Color(198, 49, 17)); // New third option
        character3.setOutlineColor(Color.black);
        character3.setOutlineThickness(3);

        pointerLocationX = 60;
        pointerLocationY = 60;

        characterSelectImage = ImageLoader.load("hellish.png");
        keyLocker.lockKey(Key.SPACE);
    }

    public void update() {
        if (Keyboard.isKeyDown(Key.DOWN) && keyPressTimer == 0) {
            keyPressTimer = 14;
            if (currentOptionHovered < 2) {
                currentOptionHovered++;
                pointerLocationY += 100; // Adjusted to move to the third option
            }
        } else if (Keyboard.isKeyDown(Key.UP) && keyPressTimer == 0) {
            keyPressTimer = 14;
            if (currentOptionHovered > 0) {
                currentOptionHovered--;
                pointerLocationY -= 100; // Adjusted for upward movement
            }
        }

        if (keyPressTimer > 0) {
            keyPressTimer--;
        }

        // Update colors based on the current option hovered
        character1.setColor(currentOptionHovered == 0 ? new Color(225, 136, 67) : new Color(198, 49, 17));
        character2.setColor(currentOptionHovered == 1 ? new Color(225, 136, 67) : new Color(198, 49, 17));
        character3.setColor(currentOptionHovered == 2 ? new Color(225, 136, 67) : new Color(198, 49, 17)); // Color update for third option

        if (Keyboard.isKeyUp(Key.SPACE)) {
            keyLocker.unlockKey(Key.SPACE);
        }

        if (!keyLocker.isKeyLocked(Key.SPACE) && Keyboard.isKeyDown(Key.SPACE)) {
            switch (currentOptionHovered) {
                case 0:
                    selectedPlayerType = PlayerType.PROF;
                    break;
                case 1:
                    selectedPlayerType = PlayerType.PLAYER_TWO;
                    break;
                case 2:
                    selectedPlayerType = PlayerType.PLAYER_THREE; // Handle the third option
                    break;
            }
            screenCoordinator.setSelectedPlayer(selectedPlayerType);
            screenCoordinator.setGameState(GameState.LEVEL);
        }
    }

    public void draw(GraphicsHandler graphicsHandler) {
        if (characterSelectImage != null) {
            graphicsHandler.drawImage(characterSelectImage, imageX, imageY, imageWidth, imageHeight);
        }
        character1.draw(graphicsHandler);
        character2.draw(graphicsHandler);
        character3.draw(graphicsHandler); // Draw third option
        graphicsHandler.drawFilledRectangleWithBorder(pointerLocationX, pointerLocationY, 20, 20, new Color(255, 0, 0), Color.black, 2);
    }
}
