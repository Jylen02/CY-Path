package presentation;

import abstraction.Board;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * The ChooseNumberOfPlayers class represents the menu for choosing the number
 * of players in the Quoridor game application. It extends the Application class
 * and provides functionality for selecting the number of players and navigating
 * back to the main menu.
 */
public class ChooseNumberOfPlayers extends Application {

	private StackPane backgroundPane;

	/**
	 * Constructs a new ChooseNumberOfPlayers instance with the specified background
	 * pane.
	 *
	 * @param backgroundPane the background pane to be used in the choose number of
	 *                       players menu
	 */
	public ChooseNumberOfPlayers(StackPane backgroundPane) {
		this.backgroundPane = backgroundPane;
	}

	/**
	 * The start method is the entry point of the JavaFX application. It initializes
	 * and configures the primary stage and sets up the choose number of players
	 * menu scene with the player selection options and a back button.
	 *
	 * @param primaryStage the primary stage of the JavaFX application
	 * @throws Exception if an exception occurs during the start process
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		Button back = Menu.createButton("Back", 130, 50, 20);
		back.setOnAction(e -> {
			Menu menuInstance = new Menu();
			Menu.launchVerification(menuInstance, primaryStage);
		});

		Label title = Menu.createLabel("Quoridor", 100);

		Label label = Menu.createLabel("Choose the number of players", 50);

		Button twoPlayer = Menu.createButton("2 Players", 130, 50, 20);
		twoPlayer.setOnAction(e -> {
			CreatePlayers createPlayersInstance = new CreatePlayers(new Board(2), backgroundPane);
			Menu.launchVerification(createPlayersInstance, primaryStage);
		});

		Button fourPlayer = Menu.createButton("4 Players", 130, 50, 20);
		fourPlayer.setOnAction(e -> {
			CreatePlayers createPlayersInstance = new CreatePlayers(new Board(4), backgroundPane);
			Menu.launchVerification(createPlayersInstance, primaryStage);
		});

		VBox box = Menu.createVBox(10, title, label, twoPlayer, fourPlayer, back);

		Scene scene = Menu.createScene(backgroundPane, box);

		primaryStage.setScene(scene);
		primaryStage.sizeToScene();
		primaryStage.show();
	}
}
