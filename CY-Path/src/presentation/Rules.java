package presentation;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * The Rules class represents the rules menu of the Quoridor game application.
 * It extends the Application class and provides functionality for displaying
 * the game rules and allowing the user to go back to the main menu.
 */
public class Rules extends Application {
	private StackPane backgroundPane;

	/**
	 * Constructs a new Rules instance with the specified background pane.
	 *
	 * @param backgroundPane the background pane to be used in the rules menu
	 */
	public Rules(StackPane backgroundPane) {
		this.backgroundPane = backgroundPane;
	}

	/**
	 * The start method is the entry point of the JavaFX application. It initializes
	 * and configures the primary stage and sets up the rules menu scene with the
	 * rules content and a back button.
	 *
	 * @param primaryStage the primary stage of the JavaFX application
	 * @throws Exception if an exception occurs during the start process
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		Label title = Menu.createLabel("Rules", 140);
		title.getStyleClass().add("label-title");

		ListView<String> listOfRules = new ListView<>();
		listOfRules.getItems().addAll("Goal : To be the first to reach the line opposite to one’s base line.", "",
				"Rules 1 : Each player has a initial amount of walls (10 for 2 players, 5 for 4 players.",
				"Rules 2 : Each player in turn, chooses to move his pawn or to put one of his wall.",
				"Rules 3 : When the maximum amount of wall is reached, the player must move his pawn.",
				"Rules 4 : The pawns are moved one square at a time, horizontally or vertically",
				"Rules 5 : When two pawns face each other on neighbouring squares which are not separated by a fence,\n\t\tthe player, whose turn it is, can jump the opponent’s pawn (and place himself behind him), thus advancing an extra square",
				"Rules 6 : If there is a fence behind the said pawn, the player can place his pawn to the side of the other pawn",
				"Rules 7 : It is forbidden to jump more than one pawn",
				"Rules 8 : If a pawn is blocked and can't move, his turn is skipped",
				"Rules 9 : If a pawn is blocked, no player can place a wall",
				"Rules 10 : The fences must be placed between 2 sets of 2 squares",
				"Rules 11 : The first player who reaches one of the 9 squares opposite his base line is the winner");
		listOfRules.getStyleClass().add("list-view-rules");

		Button back = Menu.createButton("Back", 100, 50, 20);
		back.setOnAction(e -> {
			Menu menuInstance = new Menu();
			Menu.launchVerification(menuInstance, primaryStage);
		});

		VBox box = new VBox(title, listOfRules, back);
		box.setAlignment(Pos.CENTER);

		StackPane sceneContent = new StackPane();
		sceneContent.getChildren().addAll(backgroundPane, box);

		Scene scene = new Scene(sceneContent, 800, 700);
		scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
		
		primaryStage.setScene(scene);
		primaryStage.sizeToScene();
		primaryStage.show();
	}

}
