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

public class ChooseNumberOfPlayers extends Application {

	private StackPane backgroundPane;

	public ChooseNumberOfPlayers(StackPane backgroundPane) {
		this.backgroundPane = backgroundPane;
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
		twoPlayer.setStyle("-fx-text-fill: white;");
		twoPlayer.setOnAction(e -> {
			CreatePlayers createPlayersInstance = new CreatePlayers(new Board(2), backgroundPane);
			try {
				createPlayersInstance.start(primaryStage);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});

		RadioButton fourPlayer = new RadioButton("4 Players");
		fourPlayer.setStyle("-fx-text-fill: white;");
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

		primaryStage.setScene(scene);
		primaryStage.sizeToScene();
		primaryStage.show();
	}

}
