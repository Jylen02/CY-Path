package Presentation;

import java.awt.Button;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
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
        jouerButton.setOnAction(e -> afficherChoixNombreJoueurs());

        Button exitButton = new Button("Exit");
        exitButton.setOnAction(e -> primaryStage.close());

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
        // Afficher le plateau 9x9 avec des lignes pour poser les murs entre chaque case
        // Code pour afficher le plateau ici...

        primaryStage.setTitle("Plateau de jeu");

        Button retourButton = new Button("Retour");
        retourButton.setOnAction(e -> start(primaryStage));

        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(/* Ajouter les éléments du plateau ici */, retourButton);

        Scene scene = new Scene(vbox, 400, 300);
        primaryStage.setScene(scene);
    }
}