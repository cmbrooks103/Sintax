package Screens;

import Engine.GraphicsHandler;
import Engine.Screen;
import Game.GameState;
import Game.ScreenCoordinator;
import Level.Map;
import Level.Player;
import Level.PlayerListener;
import Maps.TestMap;
import Players.prof;
import Players.PlayerTwo;
import Players.PlayerThree; // Import PlayerThree class
import Players.PlayerFour; // Import PlayerFour class
import Players.PlayerType;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.IOException;

// This class is for when the platformer game is actually being played
public class PlayLevelScreen extends Screen implements PlayerListener {
    protected ScreenCoordinator screenCoordinator;
    protected Map map;
    protected Player player;
    protected PlayLevelScreenState playLevelScreenState;
    protected int screenTimer;
    protected LevelClearedScreen levelClearedScreen;
    protected LevelLoseScreen levelLoseScreen;
    protected boolean levelCompletedStateChangeStart;

    // Timer variables
    protected int levelTimer; // Timer counts in frames
    protected int framesPerSecond = 60; // Assuming game runs at 60 FPS

    // Audio variables
    private static Clip backgroundClip;

    public PlayLevelScreen(ScreenCoordinator screenCoordinator) {
        this.screenCoordinator = screenCoordinator;
    }

    public PlayLevelScreen(ScreenCoordinator screenCoordinator2, PlayerType selectedPlayer) {
        //TODO Auto-generated constructor stub
    }

    public void initialize() {
        // Play background music for the level only if it's not already playing
        if (backgroundClip == null || !backgroundClip.isRunning()) {
            playBackgroundMusic("src/Sounds/Super Mario Bros. 3 - World Map 8_ Dark Land Theme (online-audio-converter.com).wav");
        }

        // Define/setup map
        this.map = new TestMap();

        // Setup player based on selected player type
        PlayerType selectedPlayerType = screenCoordinator.getSelectedPlayer();

        if (selectedPlayerType == PlayerType.PROF) {
            this.player = new prof(map.getPlayerStartPosition().x, map.getPlayerStartPosition().y);
        } else if (selectedPlayerType == PlayerType.PLAYER_TWO) {
            this.player = new PlayerTwo(map.getPlayerStartPosition().x, map.getPlayerStartPosition().y);
        } else if (selectedPlayerType == PlayerType.PLAYER_THREE) { // Added for PlayerThree
            this.player = new PlayerThree(map.getPlayerStartPosition().x, map.getPlayerStartPosition().y);
        } else if (selectedPlayerType == PlayerType.PLAYER_FOUR) { // Added for PlayerFour
            this.player = new PlayerFour(map.getPlayerStartPosition().x, map.getPlayerStartPosition().y);
        }

        // Common setup for player
        if (this.player != null) {
            this.player.setMap(map);
            this.player.addListener(this);
        }

        levelClearedScreen = new LevelClearedScreen();
        levelLoseScreen = new LevelLoseScreen(this);

        this.playLevelScreenState = PlayLevelScreenState.RUNNING;

        // Initialize timer
        this.levelTimer = 0;
    }

    public void update() {
        // Based on screen state, perform specific actions
        switch (playLevelScreenState) {
            case RUNNING:
                if (player != null) {
                    player.update();
                    map.update(player);
                }

                // Increment the level timer (counts in frames)
                levelTimer++;
                break;
            case LEVEL_COMPLETED:
                if (levelCompletedStateChangeStart) {
                    screenTimer = 200;
                    levelCompletedStateChangeStart = false;
                } else {
                    levelClearedScreen.update();
                    screenTimer--;
                    if (screenTimer == 0) {
                        goBackToMenu();
                    }
                }
                break;
            case LEVEL_LOSE:
                levelLoseScreen.update();
                break;
        }
    }

    public void draw(GraphicsHandler graphicsHandler) {
        switch (playLevelScreenState) {
            case RUNNING:
                map.draw(graphicsHandler);
                if (player != null) {
                    player.draw(graphicsHandler);
                }

                // Draw the timer on the screen
                drawLevelTimer(graphicsHandler);
                break;
            case LEVEL_COMPLETED:
                // Tell the LevelClearedScreen how much time the player took 
                levelClearedScreen.setTime(levelTimer);
                levelClearedScreen.draw(graphicsHandler);
                break;
            case LEVEL_LOSE:
                levelLoseScreen.draw(graphicsHandler);
                break;
        }
    }

    // Method to draw the level timer on the screen
    private void drawLevelTimer(GraphicsHandler graphicsHandler) {
        int secondsElapsed = levelTimer / framesPerSecond;
        int minutes = secondsElapsed / 60;
        int seconds = secondsElapsed % 60;

        String timeString = String.format("%02d:%02d", minutes, seconds);
        Font timerFont = new Font("Arial", Font.PLAIN, 24);
        Color timerColor = Color.WHITE;

        graphicsHandler.drawString("Time: " + timeString, 650, 50, timerFont, timerColor);
    }

    public PlayLevelScreenState getPlayLevelScreenState() {
        return playLevelScreenState;
    }

    @Override
    public void onLevelCompleted() {
        if (playLevelScreenState != PlayLevelScreenState.LEVEL_COMPLETED) {
            playLevelScreenState = PlayLevelScreenState.LEVEL_COMPLETED;
            levelCompletedStateChangeStart = true;
            stopBackgroundMusic(); // Stop the music when level is completed
        }
    }

    @Override
    public void onDeath() {
        if (playLevelScreenState != PlayLevelScreenState.LEVEL_LOSE) {
            playLevelScreenState = PlayLevelScreenState.LEVEL_LOSE;
            stopBackgroundMusic(); // Stop the music when the player dies
        }
    }

    public void resetLevel() {
        initialize();
    }

    public void goBackToMenu() {
        screenCoordinator.setGameState(GameState.MENU);
    }

    // Method to load and play the background music
    private void playBackgroundMusic(String filepath) {
        try {
            File musicPath = new File(filepath);
            if (musicPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                backgroundClip = AudioSystem.getClip();
                backgroundClip.open(audioInput);
                backgroundClip.start(); // Play music once
                backgroundClip.loop(Clip.LOOP_CONTINUOUSLY); // Loop the music
            } else {
                System.out.println("WAV file not found: " + filepath);
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    // Method to stop the background music
    private void stopBackgroundMusic() {
        if (backgroundClip != null && backgroundClip.isRunning()) {
            backgroundClip.stop();
        }
    }

    // This enum represents the different states this screen can be in
    private enum PlayLevelScreenState {
        RUNNING, LEVEL_COMPLETED, LEVEL_LOSE
    }
}
