package Engine;

import GameObject.Rectangle;
import SpriteFont.SpriteFont;
import Utils.Colors;

import javax.swing.*;
import java.awt.*;

/*
 * This is where the game loop process and render back buffer is set up.
 */
public class GamePanel extends JPanel {
    private ScreenManager screenManager;
    private GraphicsHandler graphicsHandler;

    private boolean isGamePaused = false;
    private SpriteFont pauseLabel;
    private KeyLocker keyLocker = new KeyLocker();
    private final Key pauseKey = Key.P;
    private Thread gameLoopProcess;

    private Key showFPSKey = Key.G;
    private SpriteFont fpsDisplayLabel;
    private boolean showFPS = false;
    private int currentFPS;
    private boolean doPaint;

    // Viewport dimensions
    private static final int VIEWPORT_WIDTH = 800;
    private static final int VIEWPORT_HEIGHT = 605;

    public GamePanel() {
        super();
        this.setDoubleBuffered(true);
        this.addKeyListener(Keyboard.getKeyListener());

        graphicsHandler = new GraphicsHandler();
        screenManager = new ScreenManager();

        pauseLabel = new SpriteFont("PAUSE", 365, 280, "Arial", 24, Color.white);
        pauseLabel.setOutlineColor(Color.black);
        pauseLabel.setOutlineThickness(2.0f);

        fpsDisplayLabel = new SpriteFont("FPS", 4, 3, "Arial", 12, Color.black);
        currentFPS = Config.TARGET_FPS;

        GameLoop gameLoop = new GameLoop(this);
        gameLoopProcess = new Thread(gameLoop.getGameLoopProcess());
    }

    public void setupGame() {
        setBackground(Colors.CORNFLOWER_BLUE);
        screenManager.initialize(new Rectangle(0, 0, VIEWPORT_WIDTH, VIEWPORT_HEIGHT));
    }

    public void startGame() {
        gameLoopProcess.start();
    }

    public ScreenManager getScreenManager() {
        return screenManager;
    }

    public void setCurrentFPS(int currentFPS) {
        this.currentFPS = currentFPS;
    }

    public void setDoPaint(boolean doPaint) {
        this.doPaint = doPaint;
    }

    public void update() {
        updatePauseState();
        updateShowFPSState();

        if (!isGamePaused) {
            screenManager.update();
        }
    }

    private void updatePauseState() {
        if (Keyboard.isKeyDown(pauseKey) && !keyLocker.isKeyLocked(pauseKey)) {
            isGamePaused = !isGamePaused;
            keyLocker.lockKey(pauseKey);
        }

        if (Keyboard.isKeyUp(pauseKey)) {
            keyLocker.unlockKey(pauseKey);
        }
    }

    private void updateShowFPSState() {
        if (Keyboard.isKeyDown(showFPSKey) && !keyLocker.isKeyLocked(showFPSKey)) {
            showFPS = !showFPS;
            keyLocker.lockKey(showFPSKey);
        }

        if (Keyboard.isKeyUp(showFPSKey)) {
            keyLocker.unlockKey(showFPSKey);
        }

        fpsDisplayLabel.setText("FPS: " + currentFPS);
    }

    public void draw() {
        screenManager.draw(graphicsHandler);

        if (isGamePaused) {
            pauseLabel.draw(graphicsHandler);
            graphicsHandler.drawFilledRectangle(0, 0, VIEWPORT_WIDTH, VIEWPORT_HEIGHT, new Color(0, 0, 0, 100));
        }

        if (showFPS) {
            fpsDisplayLabel.draw(graphicsHandler);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (doPaint) {
            Graphics2D g2d = (Graphics2D) g;

            // Set the viewport to only 800x605
            g2d.setClip(0, 0, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);

            // Clear the area before drawing
            g2d.clearRect(0, 0, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);

            graphicsHandler.setGraphics(g2d);
            draw();
        }
    }
}
