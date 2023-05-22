package presentation;

import abstraction.Board;
import abstraction.Case;
import abstraction.Pawn;
import abstraction.Player;
import abstraction.Position;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class CreatePlayers extends Application {

	private StackPane backgroundPane;

	private Board board;

	private MediaPlayer mediaPlayerPawnMove;
	private MediaPlayer mediaPlayerMusic;
	private Slider volumeSlider;

	public CreatePlayers(Board board, MediaPlayer mediaPlayerPawnMove, MediaPlayer mediaPlayerMusic,
			Slider volumeSlider, StackPane backgroundPane) {
		this.board = board;
		this.mediaPlayerPawnMove = mediaPlayerPawnMove;
		this.mediaPlayerMusic = mediaPlayerMusic;
		this.volumeSlider = volumeSlider;
		this.backgroundPane = backgroundPane;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Label title = Menu.createLabel("Quoridor", 100);

		Label label = Menu.createLabel("Choose the name of each players", 50);

		VBox box = new VBox(title, label);

		// Choose each player's name
		TextField[] name = new TextField[board.getPlayerNumber()];
		for (int i = 0; i < this.board.getPlayerNumber(); i++) {
			name[i] = new TextField("Player " + (i + 1));
			box.getChildren().add(name[i]);
		}

		Button back = Menu.createButton("Back", 100, 50, 20);
		back.setOnAction(e -> {
			ChooseNumberOfPlayers chooseNumberOfPlayersInstance = new ChooseNumberOfPlayers(mediaPlayerPawnMove,
					mediaPlayerMusic, volumeSlider, backgroundPane);
			try {
				chooseNumberOfPlayersInstance.start(primaryStage);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});

		Button start = Menu.createButton("Start", 100, 50, 20);
		start.setOnAction(e -> {
			// Get each player's name
			Player[] players = new Player[this.board.getPlayerNumber()];
			for (int i = 0; i < this.board.getPlayerNumber(); i++) {
				String playerName = name[i].getText();
				for (Case value : Case.values()) {
					if (value.getValue() == i + 1) {
						players[i] = new Player(playerName,
								new Pawn(board,
										new Position(Board.STARTINGPOSITIONPLAYERS[i].getX(),
												Board.STARTINGPOSITIONPLAYERS[i].getY()),
										value),
								Board.MAXWALLCOUNT / this.board.getPlayerNumber());
					}
				}
			}
			GameTurn gameTurnInstance = new GameTurn(board, players, mediaPlayerPawnMove, mediaPlayerMusic,
					volumeSlider, backgroundPane, primaryStage);
			try {
				gameTurnInstance.start(primaryStage);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		box.setAlignment(Pos.CENTER);
		box.getChildren().addAll(back, start);

		StackPane sceneContent = new StackPane();
		sceneContent.getChildren().addAll(backgroundPane, box);

		Scene scene = new Scene(sceneContent, 800, 700);

		primaryStage.setScene(scene);
		primaryStage.sizeToScene();
		primaryStage.show();

	}

}
