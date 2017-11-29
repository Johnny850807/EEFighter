package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * @author Waterball
 * The singleton sound player playing the sound or the looping background music. (Notice, only when any UI component such
 * as JFrame created and showing cause this work.)
 */
public class SoundPlayer {
	private static SoundPlayer instance = new SoundPlayer();
	private Clip backgroundMusic;  //背景音樂 一次只能撥一首 
	
	public static SoundPlayer getInstance(){
		return instance;
	}
	
	/**
	 * Play once the sound.
	 * @param path sound's path.
	 */
	public void playSound(String path){
		try {   
			Clip soundClip = AudioSystem.getClip();
			soundClip.open(AudioSystem.getAudioInputStream(new File(path).toURL()));
			soundClip.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Play the background music, the background music can only exits one at the same time, as well as the
	 * background music will be looping over and over again. Invoke stopBackgroundMusicIfLooping() if you want to stop the background music.
	 */
	public void playLoopMusic(String path){
		try {
			stopBackgroundMusicIfLooping();
			backgroundMusic = AudioSystem.getClip();
			backgroundMusic.open(AudioSystem.getAudioInputStream(new File(path).toURL()));
			backgroundMusic.loop(-1);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	/**
	 * Stop the playing background music.
	 */
	public void stopBackgroundMusicIfLooping(){
		if ( backgroundMusic != null && backgroundMusic.isOpen() )
			backgroundMusic.close();
	}
}
