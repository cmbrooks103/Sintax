package Screens;

import Engine.*;
import Game.GameState;
import Game.ScreenCoordinator;
import SpriteFont.SpriteFont;
import Players.prof;
import Players.PlayerTwo;
import Level.Player; // Ensure Player is imported here
import Players.PlayerType; // Import PlayerType for selecting character type

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
    private PlayerType selectedPlayerType; // Use PlayerType to determine selected player

    // New variables for image size and position
    private int imageWidth = 1200; // Default width of the image
    private int imageHeight = 1200; // Default height of the image
    private int imageX = 0; // Default X position of the image
    private int imageY = 0; // Default Y position of the image

    public CharacterSelectScreen(ScreenCoordinator screenCoordinator) {
        this.screenCoordinator = screenCoordinator;
        this.selectedPlayerType = PlayerType.PROF; // Assign PROF by default
    }

    @Override
    public void initialize() {
        character1 = new SpriteFont("Professor Oneil: Run Faster!", 100, 50, "Arial", 35, new Color(198, 49, 17));
        character1.setOutlineColor(Color.black);
        character1.setOutlineThickness(3);

        character2 = new SpriteFont("Professor Alex: Jump Higher!", 100, 150, "Arial", 35, new Color(198, 49, 17));
        character2.setOutlineColor(Color.black);
        character2.setOutlineThickness(3);

        pointerLocationX = 60;
        pointerLocationY = 60;

        // Load the character select image
        characterSelectImage = ImageLoader.load("hellish.png"); // Ensure the image path is correct
        if (characterSelectImage == null) {
            System.out.println("Character select image not loaded correctly.");
        }

        keyLocker.lockKey(Key.SPACE);
    }

    public void update() {
        if (Keyboard.isKeyDown(Key.DOWN) && keyPressTimer == 0) {
            keyPressTimer = 14;
            currentOptionHovered = 1;
            pointerLocationY = 160;
        } else if (Keyboard.isKeyDown(Key.UP) && keyPressTimer == 0) {
            keyPressTimer = 14;
            currentOptionHovered = 0;
            pointerLocationY = 60;
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
                // Select Character 1 (prof)
                selectedPlayerType = PlayerType.PROF; // Set to PROF
            } else if (currentOptionHovered == 1) {
                // Select Character 2 (PlayerTwo)
                selectedPlayerType = PlayerType.PLAYER_TWO; // Set to PLAYER_TWO
            }
            screenCoordinator.setSelectedPlayer(selectedPlayerType); // Set the selected player in ScreenCoordinator
            screenCoordinator.setGameState(GameState.LEVEL); // Proceed to the level
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

    public PlayerType getSelectedPlayerType() {
        return selectedPlayerType; // Return the selected player type
    }

    // New methods to set image size and position
    public void setImagePosition(int x, int y) {
        this.imageX = x; // Set X position
        this.imageY = y; // Set Y position
    }

    public void setImageSize(int width, int height) {
        this.imageWidth = width; // Set width
        this.imageHeight = height; // Set height
    }
}
