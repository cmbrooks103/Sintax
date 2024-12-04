package Screens;

import Engine.*;
import Game.GameState;
import Game.ScreenCoordinator;
import Level.Map;
import Maps.TitleScreenMap;
import SpriteFont.SpriteFont;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

public class MenuScreen extends Screen {
    protected ScreenCoordinator screenCoordinator;
    protected int currentMenuItemHovered = 0; // current menu item being "hovered" over
    protected int menuItemSelected = -1;
    protected SpriteFont playGame;
    protected SpriteFont credits;
    protected SpriteFont playerSelect; // New Player Select button
    protected SpriteFont keybinds; // New Keybinds button
    protected Map background;
    protected int keyPressTimer;
    protected int pointerLocationX, pointerLocationY;
    protected KeyLocker keyLocker = new KeyLocker();
    protected BufferedImage overlayImage; // This will hold the PNG image
    protected boolean showImage = true; // Used to control when the image is visible
    private Clip backgroundMusicClip; // To hold the music clip

    public MenuScreen(ScreenCoordinator screenCoordinator) {
        this.screenCoordinator = screenCoordinator;
    }

    @Override
    public void initialize() {
        playGame = new SpriteFont("PLAY", 100, 310, "Arial", 35, new Color(198, 49, 17));
        playGame.setOutlineColor(Color.black);
        playGame.setOutlineThickness(3);
        credits = new SpriteFont("HOW TO PLAY", 100, 373, "Arial", 35, new Color(198, 49, 17));
        credits.setOutlineColor(Color.black);
        credits.setOutlineThickness(3);
        playerSelect = new SpriteFont("PROFESSOR SELECT", 100, 453, "Arial", 35, new Color(198, 49, 17));
        playerSelect.setOutlineColor(Color.black);
        playerSelect.setOutlineThickness(3);
        keybinds = new SpriteFont("KEYBINDS", 100, 522, "Arial", 35, new Color(198, 49, 17));
        keybinds.setOutlineColor(Color.black);
        keybinds.setOutlineThickness(3);

        background = new TitleScreenMap();
        background.setAdjustCamera(false);
        keyPressTimer = 0;
        menuItemSelected = -1;
        keyLocker.lockKey(Key.SPACE);

        // Load the image from the resources folder using ImageLoader
        overlayImage = ImageLoader.load("Resources/characterselect.png");
        if (overlayImage == null) {
            System.out.println("Image not loaded correctly.");
        }

        // Load and play the background music
        loadBackgroundMusic("src/Sounds/Don Toliver - Crossfaded (Instrumental) (online-audio-converter.com).wav");
        playBackgroundMusic();

        // Set keybinds for menu navigation (Move Up/Down)
        Key.setKeybinds(Key.W, Key.S, Key.UP, Key.DOWN); // WASD and Arrow keys for movement
    }

    // Method to load background music
    private void loadBackgroundMusic(String filepath) {
        try {
            File musicFile = new File(filepath);
            if (musicFile.exists()) {
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(musicFile);
                backgroundMusicClip = AudioSystem.getClip();
                backgroundMusicClip.open(audioStream);
            } else {
                System.out.println("Music file not found.");
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    // Method to play background music
    private void playBackgroundMusic() {
        if (backgroundMusicClip != null) {
            backgroundMusicClip.loop(Clip.LOOP_CONTINUOUSLY); // Play in a loop
        }
    }

    // Method to stop background music
    private void stopBackgroundMusic() {
        if (backgroundMusicClip != null && backgroundMusicClip.isRunning()) {
            backgroundMusicClip.stop();
        }
    }

    public void update() {
        // Update background map (to play tile animations)
        background.update(null);

        // Change menu item "hovered" over using dynamic keybinding (Arrow keys and WASD)
        if ((Key.isActionKeyDown("Move Down") || Keyboard.isKeyDown(Key.DOWN)) && keyPressTimer == 0) {
            keyPressTimer = 14;
            currentMenuItemHovered++;
        } else if ((Key.isActionKeyDown("Move Up") || Keyboard.isKeyDown(Key.UP)) && keyPressTimer == 0) {
            keyPressTimer = 14;
            currentMenuItemHovered--;
        } else {
            if (keyPressTimer > 0) {
                keyPressTimer--;
            }
        }

        // Loop the selection back around if necessary
        if (currentMenuItemHovered > 3) {  // Adjusted for 4 items
            currentMenuItemHovered = 0;
        } else if (currentMenuItemHovered < 0) {
            currentMenuItemHovered = 3;
        }

        // Set color for selected menu item and update pointer location
        if (currentMenuItemHovered == 0) {
            playGame.setColor(new Color(225, 136, 67));
            credits.setColor(new Color(198, 49, 17));
            playerSelect.setColor(new Color(198, 49, 17));
            keybinds.setColor(new Color(198, 49, 17));
            pointerLocationX = 70;
            pointerLocationY = 310;
        } else if (currentMenuItemHovered == 1) {
            playGame.setColor(new Color(198, 49, 17));
            credits.setColor(new Color(225, 136, 67));
            playerSelect.setColor(new Color(198, 49, 17));
            keybinds.setColor(new Color(198, 49, 17));
            pointerLocationX = 70;
            pointerLocationY = 373;
        } else if (currentMenuItemHovered == 2) {
            playGame.setColor(new Color(198, 49, 17));
            credits.setColor(new Color(198, 49, 17));
            playerSelect.setColor(new Color(225, 136, 67));
            keybinds.setColor(new Color(198, 49, 17));
            pointerLocationX = 70;
            pointerLocationY = 453;
        } else if (currentMenuItemHovered == 3) {
            playGame.setColor(new Color(198, 49, 17));
            credits.setColor(new Color(198, 49, 17));
            playerSelect.setColor(new Color(198, 49, 17));
            keybinds.setColor(new Color(225, 136, 67));
            pointerLocationX = 70;
            pointerLocationY = 522;
        }

        // If SPACE is pressed, select the menu item
        if (Keyboard.isKeyUp(Key.SPACE)) {
            keyLocker.unlockKey(Key.SPACE);
        }
        if (!keyLocker.isKeyLocked(Key.SPACE) && Keyboard.isKeyDown(Key.SPACE)) {
            menuItemSelected = currentMenuItemHovered;
            showImage = false; // Hide the image once the user selects an option
            stopBackgroundMusic(); // Stop music when a menu item is selected
            if (menuItemSelected == 0) {
                screenCoordinator.setGameState(GameState.LEVEL);
            } else if (menuItemSelected == 1) {
                screenCoordinator.setGameState(GameState.CREDITS);
            } else if (menuItemSelected == 2) {
                screenCoordinator.setGameState(GameState.PLAYER_SELECT);
            } else if (menuItemSelected == 3) {
                screenCoordinator.setGameState(GameState.KEYBINDS);
            }
        }
    }

    public void draw(GraphicsHandler graphicsHandler) {
        // Draw the background first
        background.draw(graphicsHandler);

        // Draw the overlay image (if it should be shown)
        if (showImage && overlayImage != null) {
            // Adjust the position and size of the overlay image
            int overlayX = 0; // X position
            int overlayY = 0; // Y position
            int overlayWidth = 1200; // Desired width
            int overlayHeight = 800; // Desired height
            graphicsHandler.drawImage(overlayImage, overlayX, overlayY, overlayWidth, overlayHeight);
        }

        // Draw the menu text and UI elements
        playGame.draw(graphicsHandler);
        credits.draw(graphicsHandler);
        playerSelect.draw(graphicsHandler); // Draw Player Select button
        keybinds.draw(graphicsHandler); //
        graphicsHandler.drawFilledRectangleWithBorder(pointerLocationX, pointerLocationY, 20, 20, new Color(255, 0, 0), Color.black, 2);
    }
}
