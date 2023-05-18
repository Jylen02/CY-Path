package Presentation;

import Abstraction.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Main extends Application {
	private static final int BOARD_SIZE = 9;
	private static final int CELL_SIZE = 60;
	private static final Color PLAYER1_COLOR = Color.RED;
	private static final Color PLAYER2_COLOR = Color.BLUE;

	private Board board;
	private Player currentPlayer;
	private Rectangle[][] cells;
	private Stage primaryStage;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Quoridor");
		VBox box = new VBox();
		
		Label title = new Label("Quoridor");
		Button play = new Button("Play");
		play.setOnAction(e -> chooseNumberOfPlayer());
		Button rules = new Button("Rules");
		rules.setOnAction(e -> showRules());
		Button exit = new Button("Exit");
		exit.setOnAction(e -> primaryStage.close());
		
		box.getChildren().addAll(title, play, rules, exit);
		box.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(box);
		this.primaryStage.setScene(scene);
		this.primaryStage.show();
		/*
		// Create the board and players
		board = new Board(BOARD_SIZE);
		Player player1 = new Player("Player 1", new Pawn(board, new Position(1,8), Case.PLAYER1));
		Player player2 = new Player("Player 2", new Pawn(board, new Position(9,8), Case.PLAYER2));
		currentPlayer = player1;

		// Create the grid pane for the game board
		GridPane gridPane = createGridPane();
		cells = new Rectangle[BOARD_SIZE][BOARD_SIZE];

		// Create the rectangles representing the cells on the game board
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				Rectangle cell = createCell(i, j);
				cells[i][j] = cell;
				gridPane.add(cell, j, i);
			}
		}

		// Create the buttons for player actions
		Button moveButton = createButton("Move");
		Button placeWallButton = createButton("Place Wall");
		Button resetButton = createButton("Reset");

		// Create the horizontal box for the buttons
		HBox buttonsBox = new HBox(10, moveButton, placeWallButton, resetButton);
		buttonsBox.setAlignment(Pos.CENTER);

		// Create the main layout for the scene
		GridPane mainLayout = new GridPane();
		mainLayout.setAlignment(Pos.CENTER);
		mainLayout.add(gridPane, 0, 0);
		mainLayout.add(buttonsBox, 0, 1);
		mainLayout.setPadding(new Insets(20));

		// Create the scene and set it to the primary stage
		Scene scene = new Scene(mainLayout);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Quoridor Game");
		primaryStage.show();
		*/
	}
	
	private void chooseNumberOfPlayer() {
		VBox box = new VBox();
		Label title = new Label("Quoridor");
		box.getChildren().addAll(title);
		Scene scene = new Scene(box);
		this.primaryStage.setScene(scene);
		this.primaryStage.show();
	}
	
	private void showRules() {
		
	}
	
	private GridPane createGridPane() {
		GridPane gridPane = new GridPane();
		gridPane.setHgap(1);
		gridPane.setVgap(1);
		gridPane.setGridLinesVisible(true);
		return gridPane;
	}

	private Rectangle createCell(int row, int col) {
		Rectangle cell = new Rectangle(CELL_SIZE, CELL_SIZE);
		cell.setFill(Color.WHITE);
		cell.setStroke(Color.BLACK);
		cell.setOnMouseClicked(e -> handleCellClick(row, col));
		return cell;
	}

	private Button createButton(String text) {
		Button button = new Button(text);
		button.setMinWidth(100);
		return button;
	}

	private void handleCellClick(int row, int col) {
		Position currentPosition = currentPlayer.getPawn().getPos();
		Position clickedPosition = new Position(row, col);

		//Check if it's the current player's turn and if the clicked cell is
		//if (currentPlayer.getPawn().canMove(clickedPosition) && board.isPathFree(currentPosition, clickedPosition)) {
            //currentPlayer.getPawn().moveTo(clickedPosition);
            //cells[currentPosition.getX()][currentPosition.getY()].setFill(Color.WHITE);
            //cells[row][col].setFill(getPlayerColor(currentPlayer));
            //currentPlayer = (currentPlayer == player1) ? player2 : player1;
        //}
    }

    private void handleMoveButton() {
        // TODO: Implement the logic for handling the Move button click
    }

    private void handlePlaceWallButton() {
        // TODO: Implement the logic for handling the Place Wall button click
    }

    private void handleResetButton(Player player) {
        // Reset the game state
        board.initializeBoard();
        currentPlayer = player;

        // Clear the cell colors
        for (Rectangle[] row : cells) {
            for (Rectangle cell : row) {
                cell.setFill(Color.WHITE);
            }
        }
    }
    

	public static void main(String[] args) {
		launch(args);
	}
}