package presentation;

import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class BackgroundMusic {
	private static BackgroundMusic instance;
	private MediaPlayer mediaPlayerMusic;
	private MediaPlayer mediaPlayerPawnMove;
	private MediaPlayer mediaPlayerWallPlaced;
	private Slider volumeSlider;

	private BackgroundMusic() {
		Media mediaMusic = new Media(getClass().getResource("/sound/tw3.mp3").toString());
		Media mediaPawnMove = new Media(getClass().getResource("/sound/move.mp3").toString());
		Media mediaWallPlaced = new Media(getClass().getResource("/sound/wall.mp3").toString());

		mediaPlayerMusic = new MediaPlayer(mediaMusic);
		mediaPlayerMusic.setCycleCount(MediaPlayer.INDEFINITE);

		mediaPlayerPawnMove = new MediaPlayer(mediaPawnMove);
		mediaPlayerPawnMove.setCycleCount(1); // To repeat the sound 1 time

		mediaPlayerWallPlaced = new MediaPlayer(mediaWallPlaced);
		mediaPlayerWallPlaced.setCycleCount(1); // To repeat the sound 1 time

		volumeSlider = new Slider(0, 0.1, 0.05);
		mediaPlayerMusic.volumeProperty().bindBidirectional(volumeSlider.valueProperty());
		mediaPlayerPawnMove.volumeProperty().bindBidirectional(volumeSlider.valueProperty());
		mediaPlayerWallPlaced.volumeProperty().bindBidirectional(volumeSlider.valueProperty());
	}

	public static BackgroundMusic getInstance() {
		if (instance == null) {
			instance = new BackgroundMusic();
		}

		return instance;
	}

	public MediaPlayer getMusicPlayer() {
		return mediaPlayerMusic;
	}

	public MediaPlayer getPawnMovePlayer() {
		return mediaPlayerPawnMove;
	}

	public MediaPlayer getWallPlacedPlayer() {
		return mediaPlayerWallPlaced;
	}

	public Slider getVolumeSlider() {
		return volumeSlider;
	}
}