package Screens;

import Engine.*;
import Game.GameState;
import Game.ScreenCoordinator;
import Level.Map;
import Maps.TitleScreenMap;
import SpriteFont.SpriteFont;
import java.awt.*;

// This class is for the credits screen
public class CreditsScreen extends Screen {
    protected ScreenCoordinator screenCoordinator;
    protected Map background;
    protected KeyLocker keyLocker = new KeyLocker();
    protected SpriteFont creditsLabel;
    protected SpriteFont createdByLabel;
    protected SpriteFont returnInstructionsLabel;
    protected SpriteFont additionalTutorialLabel;  // New tutorial text
    protected SpriteFont newTutorialLabel;         // Another new tutorial text

    public CreditsScreen(ScreenCoordinator screenCoordinator) {
        this.screenCoordinator = screenCoordinator;
    }

    @Override
    public void initialize() {
        // setup graphics on screen (background map, spritefont text)
        background = new TitleScreenMap();
        background.setAdjustCamera(false);
        creditsLabel = new SpriteFont("Tutorial", 15, 7, "Times New Roman", 30, Color.red);
        createdByLabel = new SpriteFont("Use < > keys to move left and right. Use ^ key to jump.", 130, 121, "Times New Roman", 20, Color.red);
        returnInstructionsLabel = new SpriteFont("Press Space to return to the menu", 20, 532, "Times New Roman", 30, Color.red);
        additionalTutorialLabel = new SpriteFont("Lava and fire will kill you! Run through fire without stopping to negate damage and press space to interact with certain objects in the world.", 130, 160, "Times New Roman", 20, Color.red);  // New label
        newTutorialLabel = new SpriteFont("Avoid all enemies!!!", 130, 200, "Times New Roman", 20, Color.red);  // Another new label

        keyLocker.lockKey(Key.SPACE);
    }

    public void update() {
        background.update(null);

        if (Keyboard.isKeyUp(Key.SPACE)) {
            keyLocker.unlockKey(Key.SPACE);
        }

        // if space is pressed, go back to main menu
        if (!keyLocker.isKeyLocked(Key.SPACE) && Keyboard.isKeyDown(Key.SPACE)) {
            screenCoordinator.setGameState(GameState.MENU);
        }
    }

    public void draw(GraphicsHandler graphicsHandler) {
        background.draw(graphicsHandler);
        creditsLabel.draw(graphicsHandler);
        createdByLabel.draw(graphicsHandler);
        returnInstructionsLabel.draw(graphicsHandler);
        additionalTutorialLabel.draw(graphicsHandler);  // Draw new label
        newTutorialLabel.draw(graphicsHandler);         // Draw another new label
    }
}
