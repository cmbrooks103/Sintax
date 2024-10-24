package Screens;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import Engine.GraphicsHandler;
import Engine.Screen;
import Game.GameState;
import Game.ScreenCoordinator;
import Level.Map;
import Level.Player;
import Level.PlayerListener;
import Maps.TestMap;
import Players.PlayerFour;
import Players.PlayerThree;
import Players.PlayerTwo;
import Players.PlayerType;
import Players.prof;

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
    private String originalMusicFilepath = "src/Sounds/Super Mario Bros. 3 - World Map 8_ Dark Land Theme (online-audio-converter.com).wav";
    private String specialAreaMusicFilepath = "src/Sounds/SpecialAreaMusic.wav"; // Path to the specific audio file
    private boolean inSpecialArea = false; // Track if the player is inside the special area

    // Final area variables
    private int finalAreaStartX = 800;  // Starting X coordinate of the final area
    private int finalAreaEndX = 1000;   // Ending X coordinate of the final area
    private int finalAreaStartY = 0;    // Starting Y coordinate of the final area
    private int finalAreaEndY = 600;    // Ending Y coordinate of the final area
    private boolean inFinalArea = false;  // Flag to track if the player is in the final area

    // Special area coordinates
    private int specialAreaStartX = 1057;
    private int specialAreaEndX = 1070;
    private int specialAreaY = 11;

    public PlayLevelScreen(ScreenCoordinator screenCoordinator) {
        this.screenCoordinator = screenCoordinator;
    }

    public PlayLevelScreen(ScreenCoordinator screenCoordinator2, PlayerType selectedPlayer) {
        //TODO Auto-generated constructor stub
    }

    public void initialize() {
        // Play background music for the level only if it's not already playing
        if (backgroundClip == null || !backgroundClip.isRunning()) {
            playBackgroundMusic(originalMusicFilepath);
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

                    // Check if player has entered the final area
                    if (!inFinalArea && isPlayerInFinalArea()) {
                        inFinalArea = true; // Mark that we are in the final area
                        changeBackgroundMusic("src/Sounds/Super Metroid Music - Ridley Draygon Boss Theme (online-audio-converter.com).wav"); // Change to the final area music
                    }

                    // Check if the player is in the special area
                    if (isPlayerInSpecialArea() && !inSpecialArea) {
                        inSpecialArea = true;
                        changeBackgroundMusic(specialAreaMusicFilepath); // Change to the special area music
                    } else if (!isPlayerInSpecialArea() && inSpecialArea) {
                        inSpecialArea = false;
                        changeBackgroundMusic(originalMusicFilepath); // Return to original music if player leaves special area
                    }
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

    // Method to load and play the background music with looping
    private void playBackgroundMusic(String filepath) {
        try {
            File musicPath = new File(filepath);
            if (musicPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                backgroundClip = AudioSystem.getClip();
                backgroundClip.open(audioInput);
                backgroundClip.start(); // Play music once
                backgroundClip.loop(Clip.LOOP_CONTINUOUSLY); // Loop the music continuously
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

    // Method to change background music and ensure looping
    private void changeBackgroundMusic(String filepath) {
        stopBackgroundMusic(); // Stop the current background music
        playBackgroundMusic(filepath); // Play new background music and ensure looping
    }

    // Method to check if player is in the final area
    private boolean isPlayerInFinalArea() {
        int playerX = (int) player.getX();
        int playerY = (int) player.getY();

        return playerX >= finalAreaStartX && playerX <= finalAreaEndX &&
        playerY >= finalAreaStartY && playerY <= finalAreaEndY;
    }

    // Method to check if the player is in the special area
    private boolean isPlayerInSpecialArea() {
        int playerX = (int) player.getX();
        int playerY = (int) player.getY();

        return playerX >= specialAreaStartX && playerX <= specialAreaEndX &&
               playerY == specialAreaY;
    }

    // This enum represents the different states this screen can be in
    private enum PlayLevelScreenState {
        RUNNING, LEVEL_COMPLETED, LEVEL_LOSE
    }
}
