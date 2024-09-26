package Screens;

import Engine.*;
import Game.GameState;
import Game.ScreenCoordinator;
import Level.Map;
import Maps.TitleScreenMap;
import SpriteFont.SpriteFont;
import java.awt.*;

// This class is for the tutorial screen
public class TutorialScreen extends Screen {
    protected ScreenCoordinator screenCoordinator;
    protected Map background;
    protected KeyLocker keyLocker = new KeyLocker();
    protected SpriteFont tutorialText; // Display tutorial instructions
    protected String[] tutorialLines;
    protected int currentLine = 0;

    public TutorialScreen(ScreenCoordinator screenCoordinator) {
        this.screenCoordinator = screenCoordinator;
    }

    @Override
    public void initialize() {
        // Setup graphics on screen (background map, sprite font text)
        background = new TitleScreenMap();
        background.setAdjustCamera(false);

        // Initialize tutorial lines
        tutorialLines = new String[] {
            "Welcome to the game!",
            "Use arrow keys to move.",
            "Press SPACE to jump.",
            "Avoid enemies and obstacles.",
            "Collect power-ups to enhance your abilities.",
            "Good luck!"
        };

        // Set the first line of the tutorial
        tutorialText = new SpriteFont(tutorialLines[currentLine], 50, 100, "Arial", 24, Color.WHITE);
        keyLocker.lockKey(Key.SPACE);
    }

    public void update() {
        background.update(null);

        if (Keyboard.isKeyUp(Key.SPACE)) {
            keyLocker.unlockKey(Key.SPACE);
        }

        // If SPACE is pressed, go to the next line or return to the menu
        if (!keyLocker.isKeyLocked(Key.SPACE) && Keyboard.isKeyDown(Key.SPACE)) {
            currentLine++;
            if (currentLine >= tutorialLines.length) {
                screenCoordinator.setGameState(GameState.MENU); // Return to the menu after the last line
            } else {
                tutorialText.setText(tutorialLines[currentLine]); // Update to the next tutorial line
                keyLocker.lockKey(Key.SPACE); // Lock the key again
            }
        }
    }

    public void draw(GraphicsHandler graphicsHandler) {
        background.draw(graphicsHandler);
        tutorialText.draw(graphicsHandler);
        graphicsHandler.drawString("Press SPACE to continue...", 50, 400, null, Color.WHITE);
    }
}
