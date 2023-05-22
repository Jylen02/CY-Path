package presentation;

import abstraction.Board;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
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
		VBox box = new VBox(10);

		Button back = Menu.createButton("Back", 100, 50, 20);
		back.setOnAction(e -> {
			Menu menuInstance = new Menu();
			menuInstance.start(primaryStage);
		});

		Label title = Menu.createLabel("Quoridor", 100);

		Label label = Menu.createLabel("Choose the number of players", 50);

		RadioButton twoPlayer = new RadioButton("2 Players");
		twoPlayer.getStyleClass().add("radio-button");
		twoPlayer.setOnAction(e -> {
			CreatePlayers createPlayersInstance = new CreatePlayers(new Board(2), backgroundPane);
			try {
				createPlayersInstance.start(primaryStage);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});

		RadioButton fourPlayer = new RadioButton("4 Players");
		fourPlayer.getStyleClass().add("radio-button");
		fourPlayer.setOnAction(e -> {
			CreatePlayers createPlayersInstance = new CreatePlayers(new Board(4), backgroundPane);
			try {
				createPlayersInstance.start(primaryStage);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});

		box.getChildren().addAll(title, label, twoPlayer, fourPlayer, back);
		box.setAlignment(Pos.CENTER);

		StackPane sceneContent = new StackPane();
		sceneContent.getChildren().addAll(backgroundPane, box);

		Scene scene = new Scene(sceneContent, 800, 700);
		
		String  style= getClass().getResource("style.css").toExternalForm();
		scene.getStylesheets().add(style);

		primaryStage.setScene(scene);
		primaryStage.sizeToScene();
		primaryStage.show();
	}

}
