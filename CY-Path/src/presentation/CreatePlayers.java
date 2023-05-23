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
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * The CreatePlayers class represents the menu for creating players in the
 * Quoridor game application. It extends the Application class and provides
 * functionality for choosing the names of each player and starting the game
 * with the selected players.
 */
public class CreatePlayers extends Application {

	private StackPane backgroundPane;

	private Board board;

	/**
	 * Constructs a new CreatePlayers instance with the specified board and
	 * background pane.
	 *
	 * @param board          the game board for the Quoridor game
	 * @param backgroundPane the background pane to be used in the create players
	 *                       menu
	 */
	public CreatePlayers(Board board, StackPane backgroundPane) {
		this.board = board;
		this.backgroundPane = backgroundPane;
	}

	/**
	 * The start method is the entry point of the JavaFX application. It initializes
	 * and configures the primary stage and sets up the create players menu scene
	 * with text fields for entering player names, a back button, and a start
	 * button.
	 *
	 * @param primaryStage the primary stage of the JavaFX application
	 * @throws Exception if an exception occurs during the start process
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		Label title = Menu.createLabel("Quoridor", 100);

		Label label = Menu.createLabel("Choose the name of each players", 50);

		VBox box = new VBox(title, label);
		box.setSpacing(10);
		box.setAlignment(Pos.CENTER);

		// Choose each player's name
		TextField[] name = new TextField[board.getPlayerNumber()];
		for (int i = 0; i < this.board.getPlayerNumber(); i++) {
			name[i] = new TextField("Player " + (i + 1));
			name[i].getStyleClass().add("text-field");
			name[i].setMaxWidth(400);
			name[i].setMaxHeight(40);
			box.getChildren().add(name[i]);
		}

		Button back = Menu.createButton("Back", 100, 50, 20);
		back.setOnAction(e -> {
			ChooseNumberOfPlayers chooseNumberOfPlayersInstance = new ChooseNumberOfPlayers(backgroundPane);
			Menu.launchVerification(chooseNumberOfPlayersInstance, primaryStage);
		});

		Button start = Menu.createButton("Start", 100, 50, 20);
		start.setOnAction(e -> {
			// Get each player's name
			for (int i = 0; i < this.board.getPlayerNumber(); i++) {
				String playerName = name[i].getText();
				for (Case value : Case.values()) {
					if (value.getValue() == i + 1) {
						board.getPlayers()[i] = new Player(playerName,
								new Pawn(board,
										new Position(Board.STARTINGPOSITIONPLAYERS[i].getX(),
												Board.STARTINGPOSITIONPLAYERS[i].getY()),
										value),
								Board.MAXWALLCOUNT / this.board.getPlayerNumber());
					}
				}
			}
			GameTurn gameTurnInstance = new GameTurn(board, backgroundPane, primaryStage);
			Menu.launchVerification(gameTurnInstance, primaryStage);
		});
		box.getChildren().addAll(back, start);

		Scene scene = Menu.createScene(backgroundPane, box);
		/*
		StackPane sceneContent = new StackPane();
		sceneContent.getChildren().addAll(backgroundPane, box);

		Scene scene = new Scene(sceneContent, 800, 700);
		scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());*/

		primaryStage.setScene(scene);
		primaryStage.sizeToScene();
		primaryStage.show();

	}

}
