package presentation;

import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * The BackgroundMusic class manages the background music and sound effects for the game.
 * It provides methods to control the playback and volume of the media players.
 */
public class BackgroundMusic {
	
	/**
	 * The singleton instance of the BackgroundMusic class.
	 */
	private static BackgroundMusic instance;
	
	/**
	 * Media player for background music.
	 */
	private MediaPlayer mediaPlayerMusic;
	
	/**
	 * Media player for pawn move sound effect.
	 */
	private MediaPlayer mediaPlayerPawnMove;
	
	/**
	 * Media player for wall placement sound effect.
	 */
	private MediaPlayer mediaPlayerWallPlaced;
	
	/**
	 * Slider to control the volume of the media players.
	 */
	private Slider volumeSlider;
	
	/**
	 * Private constructor to initialize media players and volume control. 
	 * Background music is set to play indefinitely while the sound effects are set to play once.
	 */
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
	
	/**
	 * Static method to get the singleton instance of the BackgroundMusic class.
	 *
	 * @return The singleton instance of the BackgroundMusic class.
	 */
	public static BackgroundMusic getInstance() {
		if (instance == null) {
			instance = new BackgroundMusic();
		}

		return instance;
	}
	
	/**
	 * Getter method for the media player of the background music.
	 *
	 * @return The media player for the background music.
	 */
	public MediaPlayer getMusicPlayer() {
		return mediaPlayerMusic;
	}
	
	/**
	 * Getter method for the media player of the pawn move sound effect.
	 *
	 * @return The media player for the pawn move sound effect.
	 */
	public MediaPlayer getPawnMovePlayer() {
		return mediaPlayerPawnMove;
	}
	
	/**
	 * Getter method for the media player of the wall placement sound effect.
	 *
	 * @return The media player for the wall placement sound effect.
	 */
	public MediaPlayer getWallPlacedPlayer() {
		return mediaPlayerWallPlaced;
	}
	
	/**
	 * Getter method for the volume slider.
	 *
	 * @return The slider that controls the volume of the media players.
	 */
	public Slider getVolumeSlider() {
		return volumeSlider;
	}
}