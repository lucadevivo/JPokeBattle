package map.logic;

import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

// Manages sound effects and music playback
public class Sound {

    private Clip clip; // Clip used to play the audio
    private URL[] soundURL = new URL[30]; // Array to hold URLs for different sound files

    // Constructor initializes the soundURLs array with paths to audio files
    public Sound() {
        soundURL[0] = getClass().getResource("/soundeffects/titleSong.wav"); // Title screen music
        soundURL[1] = getClass().getResource("/soundeffects/palletTown.wav"); // Pallet Town music
        soundURL[2] = getClass().getResource("/soundeffects/trainerBattle.wav"); // Trainer battle music
        soundURL[3] = getClass().getResource("/soundeffects/wonBattle.wav"); // Victory sound
        soundURL[4] = getClass().getResource("/soundeffects/lostBattle.wav"); // Defeat sound
    }

    // Loads the audio file at the specified index into the clip
    public void setFile(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]); // Get audio input stream
            clip = AudioSystem.getClip(); // Get a clip resource
            clip.open(ais); // Open the clip with the audio input stream
        } catch (Exception e) {
            e.printStackTrace(); // Print any errors that occur
        }
    }

    // Starts playing the audio clip
    public void play() {
        if (clip != null) {
            clip.start(); // Start the clip
        }
    }

    // Loops the audio clip continuously
    public void loop() {
        if (clip != null) {
            clip.loop(Clip.LOOP_CONTINUOUSLY); // Loop the clip indefinitely
        }
    }

    // Stops playing the audio clip
    public void stop() {
        if (clip != null) {
            clip.stop(); // Stop the clip if it's currently playing
        }
    }
}