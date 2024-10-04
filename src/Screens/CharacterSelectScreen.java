package Screens;

import Engine.*;
import Game.GameState;
import Game.ScreenCoordinator;
import SpriteFont.SpriteFont;

import java.awt.*;
import java.awt.image.BufferedImage;

public class CharacterSelectScreen extends Screen {
    private ScreenCoordinator screenCoordinator;
    private SpriteFont character1;
    private SpriteFont character2;
    private int currentOptionHovered = 0;
    private int pointerLocationX, pointerLocationY;
    private KeyLocker keyLocker = new KeyLocker();
    private int keyPressTimer = 0;
    private BufferedImage characterSelectImage; // To hold the character select image

    // New variables for image size and position
    private int imageWidth = 1200; // Default width of the image
    private int imageHeight = 1200; // Default height of the image
    private int imageX = 0; // Default X position of the image
    private int imageY = 0; // Default Y position of the image

    public CharacterSelectScreen(ScreenCoordinator screenCoordinator) {
        this.screenCoordinator = screenCoordinator;
    }

    @Override
    public void initialize() {
        character1 = new SpriteFont("Character 1", 200, 200, "Arial", 35, new Color(198, 49, 17));
        character1.setOutlineColor(Color.black);
        character1.setOutlineThickness(3);

        character2 = new SpriteFont("Character 2", 200, 300, "Arial", 35, new Color(198, 49, 17));
        character2.setOutlineColor(Color.black);
        character2.setOutlineThickness(3);

        pointerLocationX = 170;
        pointerLocationY = 210;

        // Load the character select image
        characterSelectImage = ImageLoader.load("characterselect.png"); // Ensure the image path is correct
        if (characterSelectImage == null) {
            System.out.println("Character select image not loaded correctly.");
        }

        keyLocker.lockKey(Key.SPACE);
    }

    public void update() {
        if (Keyboard.isKeyDown(Key.DOWN) && keyPressTimer == 0) {
            keyPressTimer = 14;
            currentOptionHovered = 1;
            pointerLocationY = 310;
        } else if (Keyboard.isKeyDown(Key.UP) && keyPressTimer == 0) {
            keyPressTimer = 14;
            currentOptionHovered = 0;
            pointerLocationY = 210;
        } else {
            if (keyPressTimer > 0) {
                keyPressTimer--;
            }
        }

        // Update color based on hover
        if (currentOptionHovered == 0) {
            character1.setColor(new Color(225, 136, 67));
            character2.setColor(new Color(198, 49, 17));
        } else {
            character1.setColor(new Color(198, 49, 17));
            character2.setColor(new Color(225, 136, 67));
        }

        if (Keyboard.isKeyUp(Key.SPACE)) {
            keyLocker.unlockKey(Key.SPACE);
        }

        if (!keyLocker.isKeyLocked(Key.SPACE) && Keyboard.isKeyDown(Key.SPACE)) {
            if (currentOptionHovered == 0) {
                // Logic to select Character 1
            } else if (currentOptionHovered == 1) {
                // Logic to select Character 2
            }
            screenCoordinator.setGameState(GameState.MENU);
        }
    }

    public void draw(GraphicsHandler graphicsHandler) {
        // Draw the character select image with adjustable size and position
        if (characterSelectImage != null) {
            graphicsHandler.drawImage(characterSelectImage, imageX, imageY, imageWidth, imageHeight); // Adjusting size and position
        }

        // Draw the character options
        character1.draw(graphicsHandler);
        character2.draw(graphicsHandler);
        graphicsHandler.drawFilledRectangleWithBorder(pointerLocationX, pointerLocationY, 20, 20, new Color(255, 0, 0), Color.black, 2);
    }

    // New methods to set image size and position
    public void setImagePosition(int x, int y) {
        this.imageX = 0;
        this.imageY = -100;
    }

    public void setImageSize(int width, int height) {
        this.imageWidth = 1200;
        this.imageHeight = 1200;
    }
}
