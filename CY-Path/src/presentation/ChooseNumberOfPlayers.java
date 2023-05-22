package presentation;

import abstraction.Board;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class ChooseNumberOfPlayers extends Application {
	
	private MediaPlayer mediaPlayerPawnMove;
	private MediaPlayer mediaPlayerMusic;
	private Slider volumeSlider;
	
	public ChooseNumberOfPlayers(MediaPlayer mediaPlayerPawnMove, MediaPlayer mediaPlayerMusic, Slider volumeSlider) {
		this.mediaPlayerPawnMove = mediaPlayerPawnMove;
		this.mediaPlayerMusic = mediaPlayerMusic;
		this.volumeSlider = volumeSlider;
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		VBox box = new VBox(10);

		Button back = Menu.createButton("Back", 100, 50, 20);
		back.setOnAction(e -> {
			Menu menuInstance = new Menu();
			menuInstance.start(primaryStage);
		});

		Label title = Menu.createLabel("Quoridor", 100);

		Label label = Menu.createLabel("Choose the number of players", 50);

		RadioButton twoPlayer = new RadioButton("2 Players");
		twoPlayer.setOnAction(e -> {
			CreatePlayers createPlayersInstance = new CreatePlayers(new Board(2), mediaPlayerPawnMove, mediaPlayerMusic, volumeSlider);
			try {
				createPlayersInstance.start(primaryStage);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});

		RadioButton fourPlayer = new RadioButton("4 Players");
		fourPlayer.setOnAction(e -> {
			CreatePlayers createPlayersInstance = new CreatePlayers(new Board(4), mediaPlayerPawnMove, mediaPlayerMusic, volumeSlider);
			try {
				createPlayersInstance.start(primaryStage);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});

		box.getChildren().addAll(title, label, twoPlayer, fourPlayer, back);
		box.setAlignment(Pos.CENTER);
		/*
		 * rootPane = new StackPane(); rootPane.setBackground(background);
		 * rootPane.getChildren().add(box);
		 */
		Scene scene = new Scene(box, 800, 700);

		primaryStage.setScene(scene);
		primaryStage.sizeToScene();
		primaryStage.show();
	}
	
}
