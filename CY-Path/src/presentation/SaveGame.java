package presentation;

import java.io.IOException;

import abstraction.Board;
import abstraction.SaveLoadGame;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * 
 * The SaveGame class represents a JavaFX application for saving a game.
 */
public class SaveGame extends Application {

	private StackPane backgroundPane;
	private Board board;

	/**
	 * Constructs a new SaveGame instance with the specified board and background
	 * pane.
	 *
	 * @param board          The game board to be saved.
	 * @param backgroundPane The background pane to be used in the UI.
	 */
	public SaveGame(Board board, StackPane backgroundPane) {
		this.board = board;
		this.backgroundPane = backgroundPane;
	}

	/**
	 * Starts the SaveGame application and sets up the UI components.
	 *
	 * @param primaryStage The primary stage for the application.
	 * @throws Exception If an error occurs during the application startup.
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		Button back = Menu.createButton("Back", 130, 50, 20);
		back.setOnAction(e -> {
			GameTurn gameTurnInstance = new GameTurn(board, backgroundPane, primaryStage);
			Menu.launchVerification(gameTurnInstance, primaryStage);
		});

		Label title = Menu.createLabel("Save a Game", 100);

		Button save1 = Menu.createButton("Save 1", 130, 50, 20);
		save1.setOnAction(e -> SaveVerification("save1.svg", primaryStage));

		Button save2 = Menu.createButton("Save 2", 130, 50, 20);
		save2.setOnAction(e -> SaveVerification("save2.svg", primaryStage));

		Button save3 = Menu.createButton("Save 3", 130, 50, 20);
		save3.setOnAction(e -> SaveVerification("save3.svg", primaryStage));

		VBox box = Menu.createVBox(10, title, save1, save2, save3, back);

		Scene scene = Menu.createScene(backgroundPane, box);

		primaryStage.setScene(scene);
		primaryStage.sizeToScene();
		primaryStage.show();
	}

	private void SaveVerification(String save, Stage primaryStage) {
		try {
			SaveLoadGame.save(board, save);

			GameTurn gameTurnInstance = new GameTurn(board, backgroundPane, primaryStage);
			Menu.launchVerification(gameTurnInstance, primaryStage);
		} catch (IOException e) {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Error : Can't save.");
			alert.setHeaderText("You can't save at this emplacement, please select another emplacement.");
			alert.showAndWait();
			Menu.launchVerification(this, primaryStage);
		}
	}
}
