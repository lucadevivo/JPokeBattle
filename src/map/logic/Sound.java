package map.logic;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {

	Clip clip;
	URL soundURL[] = new URL[30];
	
	public Sound() {
		
		soundURL[0] = getClass().getResource("/soundeffects/titleSong.wav");
		soundURL[1] = getClass().getResource("/soundeffects/palletTown.wav");
		soundURL[2] = getClass().getResource("/soundeffects/trainerBattle.wav");
		soundURL[3] = getClass().getResource("/soundeffects/wonBattle.wav");
		soundURL[4] = getClass().getResource("/soundeffects/lostBattle.wav");
	}
	public void setFile(int i) {
		
		try {
			
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
			clip = AudioSystem.getClip();
			clip.open(ais);
			
		}catch(Exception e) {
			
		}
	}
	public void play() {
		
		clip.start();
	}

	public void loop() {
		
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	public void stop() {
		
		if(clip != null) {
			
			clip.stop();
		}
	}
}