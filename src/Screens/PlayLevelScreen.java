package Screens;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import Engine.GraphicsHandler;
import Engine.ImageLoader;
import Engine.Screen;
import Game.GameState;
import Game.ScreenCoordinator;
import Level.Map;
import Level.Player;
import Level.PlayerListener;
import Maps.TestMap;
import Players.PlayerSix;
import Players.PlayerFive;
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
    private int cutsceneTimer; // Timer to track when to change images
    private int cutsceneIndex; // Index of the current image in the cutscene
    private static final int CUTSCENE_IMAGE_DURATION = 240; // Duration per image (in frames)
    private List<String> cutsceneImages; // List of cutscene image file paths
    private ImageLoader imageLoader;

    // Timer variables
    protected int levelTimer; // Timer counts in frames
    protected int framesPerSecond = 60; // Assuming game runs at 60 FPS

    // Audio variables
    private static Clip backgroundClip;

    // Final area variables
    private int finalAreaStartX = 800;  // Starting X coordinate of the final area
    private int finalAreaEndX = 1000;   // Ending X coordinate of the final area
    private int finalAreaStartY = 0;    // z Y coordinate of the final area
    private int finalAreaEndY = 600;    // Ending Y coordinate of the final area
    private boolean inFinalArea = false;  // Flag to track if the player is in the final area

    public PlayLevelScreen(ScreenCoordinator screenCoordinator) {
        this.screenCoordinator = screenCoordinator;
    }

    public PlayLevelScreen(ScreenCoordinator screenCoordinator2, PlayerType selectedPlayer) {
        //TODO Auto-generated constructor stub
    }

    public void initialize() {

         // Initialize cutscene
        cutsceneImages = Arrays.asList(
        "CutsceneImage1.png",
            "CutsceneImage2.png",
            "CutsceneImage3.png",
            "CutsceneImage4.png"
        );
        cutsceneTimer = 0;
        cutsceneIndex = 0;
        playLevelScreenState = PlayLevelScreenState.CUTSCENE;
        // Play background music for the level only if it's not already playing
        if (backgroundClip == null || !backgroundClip.isRunning()) {
            playBackgroundMusic("src/Sounds/Super Mario Bros. 3 - World Map 8_ Dark Land Theme (online-audio-converter.com).wav");
        }

        // Define/setup map
        this.map = new TestMap();

        // Setup player based on selected player type
        PlayerType selectedPlayerType = screenCoordinator.getSelectedPlayer();

        if (selectedPlayerType == PlayerType.PROF) {
            this.player = new prof(map.getPlayerStartPosition().x, map.getPlayerStartPosition().y, map);
        } else if (selectedPlayerType == PlayerType.PLAYER_TWO) {
            this.player = new PlayerTwo(map.getPlayerStartPosition().x, map.getPlayerStartPosition().y, map);
        } else if (selectedPlayerType == PlayerType.PLAYER_THREE) { // Added for PlayerThree
            this.player = new PlayerThree(map.getPlayerStartPosition().x, map.getPlayerStartPosition().y, map);
        } else if (selectedPlayerType == PlayerType.PLAYER_FOUR) { // Added for PlayerFour
            this.player = new PlayerFour(map.getPlayerStartPosition().x, map.getPlayerStartPosition().y, map);
        }else if (selectedPlayerType == PlayerType.PLAYER_FIVE) { // Added for PlayerFive
            this.player = new PlayerFive(map.getPlayerStartPosition().x, map.getPlayerStartPosition().y, map);
        }else if (selectedPlayerType == PlayerType.PLAYER_SIX) { // Added for PlayerSix
            this.player = new PlayerSix(map.getPlayerStartPosition().x, map.getPlayerStartPosition().y, map);
       }
        // Common setup for player
        if (this.player != null) {
            this.player.setMap(map);
            this.player.addListener(this);
        }

        levelClearedScreen = new LevelClearedScreen();
        levelLoseScreen = new LevelLoseScreen(this);


        // Initialize timer
        this.levelTimer = 0;
    }

    public void update() {
        // Based on screen state, perform specific actions
        switch (playLevelScreenState) {
            case CUTSCENE:
                cutsceneTimer++;
                if (cutsceneTimer >= CUTSCENE_IMAGE_DURATION) {
                    cutsceneTimer = 0;
                    cutsceneIndex++;
                    if (cutsceneIndex >= cutsceneImages.size()) {
                        playLevelScreenState = PlayLevelScreenState.RUNNING;
                    }
                }
                break;

            case RUNNING:
                if (player != null) {
                    player.update();
                    map.update(player);

                    // Check if player has entered the final area
                    if (!inFinalArea && isPlayerInFinalArea()) {
                        inFinalArea = true; // Mark that we are in the final area
                        changeBackgroundMusic("src/Sounds/Super Metroid Music - Ridley Draygon Boss Theme (online-audio-converter.com).wav"); // Change to the final area music
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
            case CUTSCENE:
            if (cutsceneIndex < cutsceneImages.size()) {
                String imagePath = cutsceneImages.get(cutsceneIndex);
                graphicsHandler.drawImage(imageLoader.load(imagePath), 0, 0, 800, 600); // Adjust dimensions as needed
            }
            break;

            case RUNNING:
                map.draw(graphicsHandler);
                if (player != null) {
                    player.draw(graphicsHandler);
                }

                // Draw the timer on the screen
                drawLevelTimer(graphicsHandler);
                drawHealth(graphicsHandler);
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
        Font timerFont = new Font("Georgia", Font.PLAIN, 24);
        Color timerColor = Color.WHITE;

        graphicsHandler.drawString("Time: " + timeString, 650, 50, timerFont, timerColor);
    }
    
     // Method to draw the health on the screen
     private void drawHealth(GraphicsHandler graphicsHandler) {
        int barWidth = 130;
        int healthBarWidth = (int) (((float) this.player.getPlayerHealth()/ 3) * barWidth);

            // Draw filled health bar
            graphicsHandler.drawFilledRectangle(650, 70, healthBarWidth, 10, Color.RED);
            // Draw outline of health bar
            graphicsHandler.drawRectangle(650, 70, barWidth, 10, Color.YELLOW);
    }


    public PlayLevelScreenState getPlayLevelScreenState() {
        return playLevelScreenState;
    }

    @Override
    public void onLevelCompleted() {
        if (playLevelScreenState != PlayLevelScreenState.LEVEL_COMPLETED) {
            playVictorySound();       // Play the victory sound
            playLevelScreenState = PlayLevelScreenState.LEVEL_COMPLETED;
            levelCompletedStateChangeStart = true;
            stopBackgroundMusic();    // Stop the background music when level is completed
        }
    }
    

    @Override
    public void onDeath() {
        if (playLevelScreenState != PlayLevelScreenState.LEVEL_LOSE) {
            playDeathSound();  // Play the death sound
            playLevelScreenState = PlayLevelScreenState.LEVEL_LOSE;
            stopBackgroundMusic(); // Stop the music when the player dies
        }
    }


    public static void playDoomSound() {
    try {
        File doomSoundPath = new File("src/Sounds/doom.wav"); // Update with the correct file path
        if (doomSoundPath.exists()) {
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(doomSoundPath);
            Clip doomClip = AudioSystem.getClip();
            doomClip.open(audioInput);
            doomClip.start(); // Play the doom sound once
        } else {
            System.out.println("WAV file not found: " + doomSoundPath);
        }
    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
        e.printStackTrace();
    }
}



    public static void playFireballShootSound() {
        try {
            File fireballShootSoundPath = new File("src/Sounds/Fireball_Shoot_Sound_Effect.wav"); // Update with the correct file path
            if (fireballShootSoundPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(fireballShootSoundPath);
                Clip fireballShootClip = AudioSystem.getClip();
                fireballShootClip.open(audioInput);
                fireballShootClip.start(); // Play the fireball shoot sound once
            } else {
                System.out.println("WAV file not found: " + fireballShootSoundPath);
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    
    public static void playEnemyDefeatSound() {
        try {
            File enemyDefeatSoundPath = new File("src/Sounds/Enemy_Defeat_Sound_Effect.wav"); // Update with the correct file path
            if (enemyDefeatSoundPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(enemyDefeatSoundPath);
                Clip enemyDefeatClip = AudioSystem.getClip();
                enemyDefeatClip.open(audioInput);
                enemyDefeatClip.start(); // Play the enemy defeat sound once
            } else {
                System.out.println("WAV file not found: " + enemyDefeatSoundPath);
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    

    public static void playDamageSound() {
        try {
            File damageSoundPath = new File("src/Sounds/Damage_Sound_Effect.wav"); // Update with the correct file path
            if (damageSoundPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(damageSoundPath);
                Clip damageClip = AudioSystem.getClip();
                damageClip.open(audioInput);
                damageClip.start(); // Play the damage sound once
            } else {
                System.out.println("WAV file not found: " + damageSoundPath);
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    

    public void playExplosionSound() {
        try {
            File explosionSoundPath = new File("src/Sounds/Explosion_Sound_Effect.wav"); // Update with the correct file path
            if (explosionSoundPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(explosionSoundPath);
                Clip explosionClip = AudioSystem.getClip();
                explosionClip.open(audioInput);
                explosionClip.start(); // Play the explosion sound once
            } else {
                System.out.println("WAV file not found: " + explosionSoundPath);
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    
    // Method to play the death sound
    private void playDeathSound() {
        try {
            File deathSoundPath = new File("src/Sounds/Video Game Death - Sound Effect (HD) (online-audio-converter.com).wav");
            if (deathSoundPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(deathSoundPath);
                Clip deathClip = AudioSystem.getClip();
                deathClip.open(audioInput);
                deathClip.start(); // Play the death sound once
            } else {
                System.out.println("WAV file not found: " + deathSoundPath);
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
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

    
    private void playVictorySound() {
        try {
            File victorySoundFile = new File("src/Sounds/Stage Win (Super Mario) - Sound Effect HD (1) (online-audio-converter.com).wav");
            if (victorySoundFile.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(victorySoundFile);
                Clip victoryClip = AudioSystem.getClip();
                victoryClip.open(audioInput);
                victoryClip.start();
            } else {
                System.out.println("Victory sound file not found.");
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

    // This enum represents the different states this screen can be in
    private enum PlayLevelScreenState {
        CUTSCENE, RUNNING, LEVEL_COMPLETED, LEVEL_LOSE
    }
}
