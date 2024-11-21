package Enemies;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class MusicHandler {
    private static final HashMap<String, Clip> musicClips = new HashMap<>();
    private static String currentPlayingMusic = null;

    /**
     * Plays a music file. If it's already playing, it will restart.
     *
     * @param filePath Path to the music file.
     */
    public static void play(String filePath) {
        // Stop the currently playing music if it's different
        if (currentPlayingMusic != null && !currentPlayingMusic.equals(filePath)) {
            stop(currentPlayingMusic);
        }

        try {
            if (!musicClips.containsKey(filePath)) {
                File musicFile = new File(filePath);
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(musicFile);
                Clip clip = AudioSystem.getClip();
                clip.open(audioStream);
                musicClips.put(filePath, clip);
            }

            Clip clip = musicClips.get(filePath);
            if (clip != null) {
                clip.setFramePosition(0); // Restart the music
                clip.start();
                clip.loop(Clip.LOOP_CONTINUOUSLY); // Loop the music continuously
                currentPlayingMusic = filePath;
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
            System.out.println("Error playing music: " + filePath);
        }
    }

    /**
     * Pauses the music file if it's currently playing.
     *
     * @param filePath Path to the music file.
     */
    public static void pause(String filePath) {
        Clip clip = musicClips.get(filePath);
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    /**
     * Stops the music file and removes it from memory.
     *
     * @param filePath Path to the music file.
     */
    public static void stop(String filePath) {
        Clip clip = musicClips.get(filePath);
        if (clip != null) {
            clip.stop();
            clip.close();
            musicClips.remove(filePath);

            if (filePath.equals(currentPlayingMusic)) {
                currentPlayingMusic = null;
            }
        }
    }

    /**
     * Resumes the music file if it was paused.
     *
     * @param filePath Path to the music file.
     */
    public static void resume(String filePath) {
        Clip clip = musicClips.get(filePath);
        if (clip != null && !clip.isRunning()) {
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY); // Resume looping
            currentPlayingMusic = filePath;
        }
    }

    /**
     * Stops all music currently playing.
     */
    public static void stopAll() {
        for (String key : musicClips.keySet()) {
            stop(key);
        }
    }
}
