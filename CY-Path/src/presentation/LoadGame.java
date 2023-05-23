package presentation;

import java.io.IOException;

import abstraction.Board;
import abstraction.SaveLoadGame;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoadGame extends Application {

	private StackPane backgroundPane;
	
	public LoadGame(StackPane backgroundPane) {
		this.backgroundPane = backgroundPane;
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		Button back = Menu.createButton("Back", 130, 50, 20);
		back.setOnAction(e -> {
			Menu menuInstance = new Menu();
			Menu.launchVerification(menuInstance, primaryStage);
		});

		Label title = Menu.createLabel("Load a Game", 100);

		Button save1 = Menu.createButton("Save 1", 130, 50, 20);
		save1.setOnAction(e -> {
			LoadVerification("save1.svg", primaryStage);
		});

		Button save2 = Menu.createButton("Save 2", 130, 50, 20);
		save2.setOnAction(e -> {
			LoadVerification("save2.svg", primaryStage);
		});
		
		Button save3 = Menu.createButton("Save 3", 130, 50, 20);
		save3.setOnAction(e -> {
			LoadVerification("save3.svg", primaryStage);
		});

		VBox box = new VBox(10);
		box.getChildren().addAll(title, save1, save2, save3, back);
		box.setAlignment(Pos.CENTER);

		StackPane sceneContent = new StackPane();
		sceneContent.getChildren().addAll(backgroundPane, box);

		Scene scene = new Scene(sceneContent, 800, 700);
		scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

		primaryStage.setScene(scene);
		primaryStage.sizeToScene();
		primaryStage.show();
	}
	
	private void LoadVerification(String save, Stage primaryStage) {
		Board board = new Board(0);
		try {
			SaveLoadGame.load(board, save);
		} catch (IOException e) {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Error : No save found.");
			alert.setHeaderText("There is no save at this emplacement, please select another save.");
			alert.showAndWait();
			Menu.launchVerification(this, primaryStage);
		}
		GameTurn gameTurnInstance = new GameTurn(board, backgroundPane, primaryStage);
		Menu.launchVerification(gameTurnInstance, primaryStage);
	}

}