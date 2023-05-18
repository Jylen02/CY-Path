package Presentation;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class VisualMain extends Application {

    private Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        primaryStage.setTitle("Menu du jeu");

        Button jouerButton = new Button("Jouer");
        jouerButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                afficherChoixNombreJoueurs();
            }
        });

        Button exitButton = new Button("Exit");
        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.close();
            }
        });

        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(jouerButton, exitButton);

        Scene scene = new Scene(vbox, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void afficherChoixNombreJoueurs() {
        Button deuxJoueursButton = new Button("2 Joueurs");
        deuxJoueursButton.setOnAction(e -> afficherPlateau(2));

        Button quatreJoueursButton = new Button("4 Joueurs");
        quatreJoueursButton.setOnAction(e -> afficherPlateau(4));

        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(deuxJoueursButton, quatreJoueursButton);

        Scene scene = new Scene(vbox, 400, 300);
        primaryStage.setScene(scene);
    }

    private void afficherPlateau(int nombreJoueurs) {
        primaryStage.setTitle("Plateau de jeu");

        Button retourButton = new Button("Retour");
        retourButton.setOnAction(e -> start(primaryStage));

        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(retourButton);

        Scene scene = new Scene(vbox, 400, 300);
        primaryStage.setScene(scene);
    }
}

