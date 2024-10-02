package Screens;

import Engine.GraphicsHandler;
import Engine.Screen;
import Engine.ScreenManager;
import SpriteFont.SpriteFont;

import java.awt.*;

// This class is for the level cleared screen
public class LevelClearedScreen extends Screen {
    protected SpriteFont winMessage;
    protected SpriteFont RecordTime;
    protected SpriteFont CurrentTime;

    public LevelClearedScreen() {
        initialize();
    }

    @Override
    public void initialize() {
        winMessage = new SpriteFont("Level Cleared", 300, 179, "Arial", 30, Color.white);
        RecordTime = new SpriteFont("Current Record: " + 1, 300, 229, "Arial", 30, Color.green);
        CurrentTime = new SpriteFont("Current Time: " + 1, 300, 279, "Arial", 30, Color.red);
    }

    @Override
    public void update() {

    }

    public void draw(GraphicsHandler graphicsHandler) {
        // paint entire screen black and dislpay level cleared text
        graphicsHandler.drawFilledRectangle(0, 0, ScreenManager.getScreenWidth(), ScreenManager.getScreenHeight(), Color.black);
        winMessage.draw(graphicsHandler);
        RecordTime.draw(graphicsHandler);
        CurrentTime.draw(graphicsHandler);
    }
}
