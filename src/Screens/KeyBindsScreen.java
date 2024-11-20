package Screens;

import java.awt.Color;
import java.awt.image.BufferedImage;

import Engine.GraphicsHandler;
import Engine.ImageLoader;
import Engine.Key;
import Engine.KeyLocker;
import Engine.Keyboard;
import Engine.Screen;
import Game.ScreenCoordinator;
import Game.GameState;
import SpriteFont.SpriteFont;

public class KeyBindsScreen extends Screen {
    private SpriteFont title;
    private SpriteFont[] options;
    private int currentOptionHovered = 0;
    private int pointerLocationX, pointerLocationY;
    private KeyLocker keyLocker = new KeyLocker();
    private int keyPressTimer = 0;
    private BufferedImage keyBindsImage;
    private ScreenCoordinator screenCoordinator;

    public KeyBindsScreen(ScreenCoordinator screenCoordinator) {
        this.screenCoordinator = screenCoordinator;
    }

    @Override
    public void initialize() {
        title = new SpriteFont("Key Bindings Configuration", 100, 50, "Arial", 30, Color.WHITE);
        title.setOutlineColor(Color.BLACK);
        title.setOutlineThickness(3);

        // Initialize options for WASD and Arrow Keys
        options = new SpriteFont[2];
        options[0] = new SpriteFont("WASD Controls", 100, 150, "Arial", 25, Color.YELLOW);
        options[1] = new SpriteFont("Arrow Key Controls", 100, 200, "Arial", 25, Color.YELLOW);

        // Add outline to options
        for (SpriteFont option : options) {
            option.setOutlineColor(Color.BLACK);
            option.setOutlineThickness(3);
        }

        // Initialize pointer position
        pointerLocationX = 60;
        pointerLocationY = 155;

        // Load background image for the screen
        keyBindsImage = ImageLoader.load("KeyBinds.png");
        keyLocker.lockKey(Key.SPACE);
    }

    @Override
    public void update() {
        // Navigate options with UP and DOWN keys
        if ((Keyboard.isKeyDown(Key.DOWN) || Keyboard.isKeyDown(Key.S)) && keyPressTimer == 0) {
            keyPressTimer = 14;
            if (currentOptionHovered < options.length - 1) {
                currentOptionHovered++;
                pointerLocationY += 50;
            }
        } else if ((Keyboard.isKeyDown(Key.UP) || Keyboard.isKeyDown(Key.W)) && keyPressTimer == 0) {
            keyPressTimer = 14;
            if (currentOptionHovered > 0) {
                currentOptionHovered--;
                pointerLocationY -= 50;
            }
        }

        // Handle key press timer
        if (keyPressTimer > 0) {
            keyPressTimer--;
        }

        // Escape key to return to main menu
        if (Keyboard.isKeyDown(Key.ESC)) {
            screenCoordinator.setGameState(GameState.MENU);
        }

        // Space key to select the current option
        if (Keyboard.isKeyUp(Key.SPACE)) {
            keyLocker.unlockKey(Key.SPACE);
        }

        if (!keyLocker.isKeyLocked(Key.SPACE) && Keyboard.isKeyDown(Key.SPACE)) {
            if (currentOptionHovered == 0) {
                setWASDKeybinds(); // Set WASD keybinds
            } else if (currentOptionHovered == 1) {
                setArrowKeyKeybinds(); // Set Arrow Key keybinds
            }

            // Return to main menu after setting keybinds
            screenCoordinator.setGameState(GameState.MENU);
            keyLocker.lockKey(Key.SPACE);
        }
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        // Draw background image
        if (keyBindsImage != null) {
            graphicsHandler.drawImage(keyBindsImage, 0, 0, 1200, 800);
        }

        // Draw title and options
        title.draw(graphicsHandler);
        for (SpriteFont option : options) {
            option.draw(graphicsHandler);
        }

        // Draw pointer
        graphicsHandler.drawFilledRectangleWithBorder(pointerLocationX, pointerLocationY, 20, 20, new Color(255, 0, 0), Color.BLACK, 2);
    }

    private void setWASDKeybinds() {
        // Set WASD keybinds globally
        Key.setKeybinds(Key.W, Key.S, Key.A, Key.D);
        System.out.println("Switched to WASD Controls");
        Key.printKeybinds();
    }

    private void setArrowKeyKeybinds() {
        // Set Arrow Key keybinds globally
        Key.setKeybinds(Key.UP, Key.DOWN, Key.LEFT, Key.RIGHT);
        System.out.println("Switched to Arrow Key Controls");
        Key.printKeybinds();
    }
}
