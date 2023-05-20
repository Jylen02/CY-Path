package presentation;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class test extends Application {
	 private Rectangle rectangle;
	    private boolean isRectangleVisible = false;

	    public static void main(String[] args) {
	        launch(args);
	    }

	    @Override
	    public void start(Stage primaryStage) {
	        Pane root = new Pane();
	        Scene scene = new Scene(root, 400, 400);
	        primaryStage.setScene(scene);
	        primaryStage.show();

	        rectangle = new Rectangle(65, 5, Color.BLUE);
	        rectangle.setVisible(false);

	        root.getChildren().add(rectangle);

	        Button button = new Button("Afficher/Disparaître");
	        button.setTranslateX(150);
	        button.setTranslateY(20);
	        root.getChildren().add(button);

	        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent event) {
	                if (isRectangleVisible) {
	                    rectangle.setVisible(false);
	                    isRectangleVisible = false;
	                } else {
	                    rectangle.setVisible(true);
	                    isRectangleVisible = true;
	                }
	            }
	        });

	        scene.setOnMouseMoved(new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent event) {
	                if (isRectangleVisible) {
	                    double rectangleX = event.getX() - rectangle.getWidth() / 2;
	                    double rectangleY = event.getY() - rectangle.getHeight() / 2;
	                    rectangle.setTranslateX(rectangleX);
	                    rectangle.setTranslateY(rectangleY);
	                }
	            }
	        });
	    }
}