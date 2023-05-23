package presentation;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

/**
 * The Menu class represents the main menu of the Quoridor game application. It
 * extends the Application class and provides functionality for displaying the
 * menu options and handling user interactions.
 */
public class Menu extends Application {

	/**
	 * The start method is the entry point of the JavaFX application. It initializes
	 * and configures the primary stage and sets up the menu scene with various UI
	 * elements.
	 *
	 * @param primaryStage the primary stage of the JavaFX application
	 */
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Quoridor");
		primaryStage.setResizable(false);

		primaryStage.getIcons().add(new Image(getClass().getResource("/image/dikdik.png").toExternalForm()));

		Image backgroundImage = new Image(getClass().getResource("/image/background.png").toExternalForm());
		BackgroundSize backgroundSize = new BackgroundSize(800, 700, true, true, true, true);
		BackgroundImage backgroundImg = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
		Background background = new Background(backgroundImg);

		StackPane backgroundPane = new StackPane();
		backgroundPane.setBackground(background);

		Label volumeLabel = createLabel("Volume", 40);

		MediaPlayer mediaPlayerMusic = BackgroundMusic.getInstance().getMusicPlayer();
		Slider volumeSlider = BackgroundMusic.getInstance().getVolumeSlider();
		mediaPlayerMusic.volumeProperty().bindBidirectional(volumeSlider.valueProperty());
		mediaPlayerMusic.setCycleCount(MediaPlayer.INDEFINITE); // Infinite restart
		mediaPlayerMusic.play(); // background music start with the launch of the app

		HBox sliderContainer = new HBox(10);
		sliderContainer.setAlignment(Pos.CENTER);
		sliderContainer.getChildren().addAll(volumeLabel, volumeSlider);

		Label title = createLabel("Quoridor", 140);

		Button newGame = createButton("New Game", 300, 100, 50);
		newGame.setOnAction(e -> {
			ChooseNumberOfPlayers chooseNumberOfPlayersInstance = new ChooseNumberOfPlayers(backgroundPane);
			launchVerification(chooseNumberOfPlayersInstance, primaryStage);
		});

		Button loadGame = createButton("Load Game", 300, 100, 50);
		loadGame.setOnAction(e -> {
			LoadGame loadGameInstance = new LoadGame(backgroundPane);
			launchVerification(loadGameInstance, primaryStage);
		});

		Button rules = createButton("Rules", 300, 100, 50);
		rules.setOnAction(e -> {
			Rules rulesInstance = new Rules(backgroundPane);
			launchVerification(rulesInstance, primaryStage);
		});

		Button exit = createButton("Exit", 300, 100, 50);
		exit.setOnAction(e -> primaryStage.close());

		
		HBox play = createHBox(50, newGame, loadGame);
		HBox info = createHBox(50, rules, exit);

		VBox box = createVBox(50, title, play, info, sliderContainer);

		Scene scene = createScene(backgroundPane, box);


		primaryStage.setScene(scene);
		primaryStage.sizeToScene();
		primaryStage.show();
	}

	/**
	 * Creates a button with the given text and size.
	 *
	 * @param text   the text to be displayed on the button
	 * @param width  the width of the button
	 * @param height the height of the button
	 * @param pixel  the font size of the button text
	 * @return the created button
	 */
	protected static Button createButton(String text, int i, int j, int pixel) {
		Button button = new Button(text);
		button.setPrefSize(i, j);
		button.setStyle("-fx-font-size: " + pixel + "px;");
		return button;
	}

	/**
	 * Creates a label with the given text and font size.
	 *
	 * @param text  the text to be displayed on the label
	 * @param pixel the font size of the label text
	 * @return the created label
	 */
	protected static Label createLabel(String text, int pixel) {
		Label label = new Label(text);
		label.setStyle("-fx-font-size: " + pixel + "px; -fx-text-fill: white;");
		return label;
	}
	
	protected static VBox createVBox(int spacing, Node... nodes) {
	    VBox box = new VBox(spacing);
	    box.getChildren().addAll(nodes);
	    box.setAlignment(Pos.CENTER);
	    return box;
	}

	protected static HBox createHBox(int spacing, Node... nodes) {
	    HBox box = new HBox(spacing);
	    box.getChildren().addAll(nodes);
	    box.setAlignment(Pos.CENTER);
	    return box;
	}

	protected static Scene createScene(Node... nodes) {
		StackPane sceneContent = new StackPane();
		for (int i = 0; i < nodes.length; i++) {
			sceneContent.getChildren().add(nodes[i]);
		}

		Scene scene = new Scene(sceneContent, 800, 700);
		scene.getStylesheets().add(Menu.class.getResource("style.css").toExternalForm());
		return scene;
	}
	
	/**
	 * Launches the specified JavaFX application with the given primary stage and
	 * handles any exceptions that occur during the launch.
	 * 
	 * @param name         The JavaFX application to launch.
	 * @param primaryStage The primary stage for the application.
	 */
	protected static void launchVerification(Application name, Stage primaryStage) {
		try {
			name.start(primaryStage);
		} catch (Exception e) {
			primaryStage.close();
		}
	}

	/**
	 * The main method is the entry point of the Java application. It launches the
	 * JavaFX application by calling the launch method.
	 *
	 * @param args the command-line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}
}