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

public class Rules extends Application {
	private StackPane backgroundPane;
	
	public Rules(StackPane backgroundPane) {
		this.backgroundPane = backgroundPane;
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		Label title = Menu.createLabel("Rules", 140);

		ListView<String> listOfRules = new ListView<>();
		listOfRules.getItems().addAll("Goal : To be the first to reach the line opposite to one’s base line.", "",
				"Rules 1 : When the game starts the wall are placed in their storage area.",
				"Rules 2 : Each player in turn, chooses to move his pawn or to put up one of his wall.",
				"Rules 3 : When the maximum amount of wall is reached, the player must move his pawn.",
				"Rules 4 : The pawns are moved one square at a time, horizontally or vertically",
				"Rules 5 : The fences must be placed between 2 sets of 2 squares",
				"Rules 6 : When two pawns face each other on neighbouring squares which are not separated by a fence,\n\t\tthe player whose turn it is can jump the opponent’s pawn (and place himself behind him), thus advancing an extra square",
				"Rules 7 : If there is a fence behind the said pawn, the player can place his pawn to the side of the other pawn",
				"Rules 8 : The first player who reaches one of the 9 squares opposite his base line is the winner",
				"Rules 9 : It is forbidden to jump more than one pawn");

		Button back = Menu.createButton("Back", 100, 50, 20);
		back.setOnAction(e -> {
			Menu menuInstance = new Menu();
			menuInstance.start(primaryStage);
		});

		VBox box = new VBox(title, listOfRules, back);
		box.setAlignment(Pos.CENTER);

		StackPane sceneContent = new StackPane();
        sceneContent.getChildren().addAll(backgroundPane, box);
        
		Scene scene = new Scene(sceneContent, 800, 700);

		primaryStage.setScene(scene);
		primaryStage.sizeToScene();
		primaryStage.show();
	}

	
}
