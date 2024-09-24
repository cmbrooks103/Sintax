package Screens;

import Engine.*;
import Game.GameState;
import Game.ScreenCoordinator;
import Level.Map;
import Maps.TitleScreenMap;
import SpriteFont.SpriteFont;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

// This is the class for the main menu screen
public class MenuScreen extends Screen {
    protected ScreenCoordinator screenCoordinator;
    protected int currentMenuItemHovered = 0; // current menu item being "hovered" over
    protected int menuItemSelected = -1;
    protected SpriteFont playGame;
    protected SpriteFont credits;
    protected Map background;
    protected int keyPressTimer;
    protected int pointerLocationX, pointerLocationY;
    protected KeyLocker keyLocker = new KeyLocker();
    protected BufferedImage overlayImage; // This will hold the PNG image
    protected boolean showImage = true; // Used to control when the image is visible

    public MenuScreen(ScreenCoordinator screenCoordinator) {
        this.screenCoordinator = screenCoordinator;
    }

    @Override
    public void initialize() {
        playGame = new SpriteFont("ESCAPE FROM HELL!", 200, 123, "Arial", 35, new Color(198, 49, 17));
        playGame.setOutlineColor(Color.black);
        playGame.setOutlineThickness(3);
        credits = new SpriteFont("CREDITS", 200, 223, "Arial", 35, new Color(198, 49, 17));
        credits.setOutlineColor(Color.black);
        credits.setOutlineThickness(3);
        background = new TitleScreenMap();
        background.setAdjustCamera(false);
        keyPressTimer = 0;
        menuItemSelected = -1;
        keyLocker.lockKey(Key.SPACE);

        // Load the image (PNG) file for the menu screen
        try {
            overlayImage = ImageIO.read(new File("C:\\Users\\cmbrookschapman\\Downloads\\SERCLASS\\SER-225-Game-Platformer\\Resources\\hellmenu.png "));
            if (overlayImage == null) {
                System.out.println("Image not loaded correctly.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        // Update background map (to play tile animations)
        background.update(null);

        // Change menu item "hovered" over if UP/DOWN key is pressed
        if (Keyboard.isKeyDown(Key.DOWN) && keyPressTimer == 0) {
            keyPressTimer = 14;
            currentMenuItemHovered++;
        } else if (Keyboard.isKeyDown(Key.UP) && keyPressTimer == 0) {
            keyPressTimer = 14;
            currentMenuItemHovered--;
        } else {
            if (keyPressTimer > 0) {
                keyPressTimer--;
            }
        }

        // Loop the selection back around if necessary
        if (currentMenuItemHovered > 1) {
            currentMenuItemHovered = 0;
        } else if (currentMenuItemHovered < 0) {
            currentMenuItemHovered = 1;
        }

        // Set color for selected menu item and update pointer location
        if (currentMenuItemHovered == 0) {
            playGame.setColor(new Color(225, 136, 67));
            credits.setColor(new Color(198, 49, 17));
            pointerLocationX = 170;
            pointerLocationY = 130;
        } else if (currentMenuItemHovered == 1) {
            playGame.setColor(new Color(198, 49, 17));
            credits.setColor(new Color(225, 136, 67));
            pointerLocationX = 170;
            pointerLocationY = 230;
        }

        // If SPACE is pressed, select the menu item
        if (Keyboard.isKeyUp(Key.SPACE)) {
            keyLocker.unlockKey(Key.SPACE);
        }
        if (!keyLocker.isKeyLocked(Key.SPACE) && Keyboard.isKeyDown(Key.SPACE)) {
            menuItemSelected = currentMenuItemHovered;
            showImage = false; // Hide the image once the user selects an option
            if (menuItemSelected == 0) {
                screenCoordinator.setGameState(GameState.LEVEL);
            } else if (menuItemSelected == 1) {
                screenCoordinator.setGameState(GameState.CREDITS);
            }
        }
    }

    public void draw(GraphicsHandler graphicsHandler) {
        // Draw the background first
        background.draw(graphicsHandler);

        // Draw the overlay image (if it should be shown)
        if (showImage && overlayImage != null) {
            graphicsHandler.drawImage(overlayImage, 0, 0); // Adjust position to where you want the image displayed
        }

        // Draw the menu text and UI elements
        playGame.draw(graphicsHandler);
        credits.draw(graphicsHandler);
        graphicsHandler.drawFilledRectangleWithBorder(pointerLocationX, pointerLocationY, 20, 20, new Color(255, 0, 0), Color.black, 2);
    }
}
