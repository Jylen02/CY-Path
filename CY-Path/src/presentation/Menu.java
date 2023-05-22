package presentation;

import javafx.application.Application;
import javafx.geometry.Pos;
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

public class Menu extends Application {

	private MediaPlayer mediaPlayerMusic;
	private Slider volumeSlider;

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
		
		mediaPlayerMusic = BackgroundMusic.getInstance().getMusicPlayer();
		volumeSlider = BackgroundMusic.getInstance().getVolumeSlider();
		mediaPlayerMusic.volumeProperty().bindBidirectional(volumeSlider.valueProperty());
		mediaPlayerMusic.setCycleCount(MediaPlayer.INDEFINITE); // Infinite restart
		mediaPlayerMusic.play(); // background music start with the launch of the app

		HBox sliderContainer = new HBox(10);
		sliderContainer.setAlignment(Pos.CENTER);
		sliderContainer.getChildren().addAll(volumeLabel, volumeSlider);

		Label title = createLabel("Quoridor", 140);

		Button play = createButton("Play", 300, 100, 50);
		play.setOnAction(e -> {
			ChooseNumberOfPlayers chooseNumberOfPlayersInstance = new ChooseNumberOfPlayers(backgroundPane);
			try {
				chooseNumberOfPlayersInstance.start(primaryStage);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});

		Button rules = createButton("Rules", 300, 100, 50);
		rules.setOnAction(e -> {
			Rules rulesInstance = new Rules(backgroundPane);
			try {
				rulesInstance.start(primaryStage);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
	
		
		Button exit = createButton("Exit", 300, 100, 50);
		exit.setOnAction(e -> primaryStage.close());

		VBox box = new VBox(20);
		box.getChildren().addAll(title, play, rules, exit, sliderContainer);
		box.setAlignment(Pos.CENTER);

		StackPane sceneContent = new StackPane();
		sceneContent.getChildren().addAll(backgroundPane, box);

		Scene scene = new Scene(sceneContent, 800, 700);

		primaryStage.setScene(scene);
		primaryStage.sizeToScene();
		primaryStage.show();
	}

	protected static Button createButton(String text, int i, int j, int pixel) {
		Button button = new Button(text);
		button.setPrefSize(i, j);
		button.setStyle("-fx-font-size: " + pixel + "px;");
		return button;
	}

	protected static Label createLabel(String text, int pixel) {
		Label label = new Label(text);
		label.setStyle("-fx-font-size: " + pixel + "px; -fx-text-fill: white;");
		return label;
	}

	public static void main(String[] args) {
		launch(args);
	}
}