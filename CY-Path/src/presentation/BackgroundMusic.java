package presentation;

import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class BackgroundMusic {
	private static BackgroundMusic instance;
	private Media mediaMusic;
	private MediaPlayer mediaPlayerMusic;
	private Slider volumeSlider;

	private BackgroundMusic() {
		mediaMusic = new Media(getClass().getResource("/sound/tw3.mp3").toString());
		volumeSlider = new Slider(0, 0.1, 0.05);
		mediaPlayerMusic = new MediaPlayer(mediaMusic);
		mediaPlayerMusic.setCycleCount(MediaPlayer.INDEFINITE);
		mediaPlayerMusic.play();
	}

	public MediaPlayer getMediaPlayer() {
		return mediaPlayerMusic;
	}

	public static BackgroundMusic getInstance() {
		if (instance == null) {
			instance = new BackgroundMusic();
		}

		return instance;
	}

	public Slider getVolumeSlider() {
		return volumeSlider;
	}
}
