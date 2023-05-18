package Presentation;

import Abstraction.Board;
import Abstraction.Case;
import Abstraction.Position;
import Abstraction.Pawn;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class VisualBoard extends Application {

    private Board board;

    public VisualBoard(Board board) {
        this.board = board;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Plateau de jeu");

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(5);
        gridPane.setVgap(5);

        Case[][] boardLayout = board.getBoard();
        int boardSize = boardLayout.length;

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                Label label = new Label();
                Case cell = boardLayout[i][j];
                label.setText(cellToSymbol(cell));

                if (cell == Case.PLAYER1 || cell == Case.PLAYER2 || cell == Case.PLAYER3 || cell == Case.PLAYER4) {
                    label.getStyleClass().add("player-cell");
                } else if (cell == Case.WALL) {
                    label.getStyleClass().add("wall-cell");
                } else if (cell == Case.BORDER || cell == Case.POTENTIALWALL || cell == Case.NULL) {
                    label.getStyleClass().add("border-cell");
                } else {
                    label.getStyleClass().add("empty-cell");
                }

                gridPane.add(label, j, i);
            }
        }

        Scene scene = new Scene(gridPane, 400, 400);
        scene.getStylesheets().add("styles.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private String cellToSymbol(Case cell) {
        switch (cell) {
            case BORDER:
            case POTENTIALWALL:
            case NULL:
                return "";
            case WALL:
                return "/";
            case EMPTY:
                return " ";
            default:
                return cell.getValue() + "";
        }
    }
}
