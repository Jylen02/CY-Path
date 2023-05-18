package Presentation;

import Abstraction.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Main extends Application {
	private static final int BOARD_SIZE = 19;
	private static final int CELL_SIZE = 60;
	private static final Color PLAYER1_COLOR = Color.RED;
	private static final Color PLAYER2_COLOR = Color.BLUE;

	private Board board;
	private Player currentPlayer;
	private Rectangle[][] cells;
	private Stage primaryStage;
	private Rectangle cell;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Quoridor");
		this.primaryStage.setWidth(800);
		this.primaryStage.setHeight(700);
		this.primaryStage.setResizable(false);

		VBox box = new VBox(20);

		Label title = new Label("Quoridor");
		title.setStyle("-fx-font-size: 140px;");

		Button play = createButton("Play");
		play.setOnAction(e -> chooseNumberOfPlayer());

		Button rules = createButton("Rules");
		rules.setOnAction(e -> showRules());

		Button exit = createButton("Exit");
		exit.setOnAction(e -> primaryStage.close());

		box.getChildren().addAll(title, play, rules, exit);
		box.setAlignment(Pos.CENTER);

		Scene scene = new Scene(box);

		this.primaryStage.setScene(scene);
		this.primaryStage.show();
		/*
		 * // Create the board and players board = new Board(BOARD_SIZE); Player player1
		 * = new Player("Player 1", new Pawn(board, new Position(1,8), Case.PLAYER1));
		 * Player player2 = new Player("Player 2", new Pawn(board, new Position(9,8),
		 * Case.PLAYER2)); currentPlayer = player1;
		 * 
		 * // Create the grid pane for the game board GridPane gridPane =
		 * createGridPane(); cells = new Rectangle[BOARD_SIZE][BOARD_SIZE];
		 * 
		 * // Create the rectangles representing the cells on the game board for (int i
		 * = 0; i < BOARD_SIZE; i++) { for (int j = 0; j < BOARD_SIZE; j++) { Rectangle
		 * cell = createCell(i, j); cells[i][j] = cell; gridPane.add(cell, j, i); } }
		 * 
		 * // Create the buttons for player actions Button moveButton =
		 * createButton("Move"); Button placeWallButton = createButton("Place Wall");
		 * Button resetButton = createButton("Reset");
		 * 
		 * // Create the horizontal box for the buttons HBox buttonsBox = new HBox(10,
		 * moveButton, placeWallButton, resetButton);
		 * buttonsBox.setAlignment(Pos.CENTER);
		 * 
		 * // Create the main layout for the scene GridPane mainLayout = new GridPane();
		 * mainLayout.setAlignment(Pos.CENTER); mainLayout.add(gridPane, 0, 0);
		 * mainLayout.add(buttonsBox, 0, 1); mainLayout.setPadding(new Insets(20));
		 * 
		 * // Create the scene and set it to the primary stage Scene scene = new
		 * Scene(mainLayout); primaryStage.setScene(scene);
		 * primaryStage.setTitle("Quoridor Game"); primaryStage.show();
		 */
	}

	private void chooseNumberOfPlayer() {
		VBox box = new VBox(10);
		Button back = new Button("Back");
		back.setOnAction(e -> start(primaryStage));
		Label title = new Label("Quoridor");
		Label label = new Label("Choose the number of players");
		RadioButton twoPlayer = new RadioButton("2 Players");
		twoPlayer.setOnAction(e -> {
			createPlayers(2);
			this.board = new Board(2);
			});
		RadioButton fourPlayer = new RadioButton("4 Players");
		fourPlayer.setOnAction(e -> {
			createPlayers(4);
			this.board = new Board(4);
			});
		box.getChildren().addAll(title, label, twoPlayer, fourPlayer, back);
		Scene scene = new Scene(box);
		this.primaryStage.setScene(scene);
		this.primaryStage.show();
	}

	private void createPlayers(int number) {
		// TODO Auto-generated method stub
		Button back = new Button("Back");
		back.setOnAction(e -> chooseNumberOfPlayer());
		Label title = new Label("Quoridor");
		Label label = new Label("Choose the name of each players");
		VBox box = new VBox(title, label);
		for (int i = 1; i < number + 1; i++) {
			TextField player = new TextField("Player" + i);
			box.getChildren().add(player);
		}
		box.getChildren().add(back);
		Button start = new Button("Start");
		start.setOnAction(e -> playBoard());
		box.getChildren().add(start);
		Scene scene = new Scene(box);
		this.primaryStage.setScene(scene);
		this.primaryStage.show();

	}

	public void playBoard() {
		// TODO Auto-generated method stub
		GridPane gridPane = new GridPane();
		for (int row = 0; row < BOARD_SIZE; row++) {
			for (int col = 0; col < BOARD_SIZE; col++) {
				if (board.getBoard()[row][col] == Case.BORDER || board.getBoard()[row][col] == Case.POTENTIALWALL) {
					if (row % 2 == 0) {
						this.cell = new Rectangle(5, 30);
					} else {
						this.cell = new Rectangle(30, 5);
					}
					this.cell.setFill(Color.LIGHTGRAY);
				} else if (board.getBoard()[row][col] == Case.NULL){
					this.cell = new Rectangle(5, 5);
					this.cell.setFill(Color.LIGHTGRAY);
				} else {
					cell = new Rectangle(30, 30);
					this.cell.setFill(Color.WHITE);
				}
				this.cell.setStroke(null);
				gridPane.add(cell, row, col);
				
			}
			

		}
		
		Scene scene = new Scene(gridPane);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Tableau de rectangles");
		primaryStage.show();
	}

	private void showRules() {

		Label title = new Label("Rules");
		title.setStyle("-fx-font-size: 140px; -fx-justify-content: center;");

		ListView<String> listOfRules = new ListView<>();
		listOfRules.getItems().addAll(
        		"Goal : To be the first to reach the line opposite to one’s base line.",
        		"",
                "Rules 1 : When the game starts the wall are placed in their storage area.",
                "Rules 2 : Each player in turn, chooses to move his pawn or to put up one of his wall.",
                "Rules 3 : When the maximum amount of wall is reached, the player must move his pawn.",
                "Rules 4 : The pawns are moved one square at a time, horizontally or vertically",
                "Rules 5 : The fences must be placed between 2 sets of 2 squares",
                "Rules 6 : When two pawns face each other on neighbouring squares which are not separated by a fence,\n\t\tthe player whose turn it is can jump the opponent’s pawn (and place himself behind him), thus advancing an extra square",
                "Rules 7 : If there is a fence behind the said pawn, the player can place his pawn to the side of the other pawn",
                "Rules 8 : The first player who reaches one of the 9 squares opposite his base line is the winner",
                "Rules 9 : It is forbidden to jump more than one pawn"
        );
        
        Button back = new Button("back");
        back.setOnAction(e -> start(primaryStage));
        
        VBox box = new VBox(title, listOfRules, back);
        
		Scene scene = new Scene(box);
		
		this.primaryStage.setScene(scene);
		this.primaryStage.show();
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
		button.setPrefSize(300, 100);
		button.setStyle("-fx-font-size: 50px;");
		return button;
	}

	private void handleCellClick(int row, int col) {
		Position currentPosition = currentPlayer.getPawn().getPos();
		Position clickedPosition = new Position(row, col);

		// Check if it's the current player's turn and if the clicked cell is
		// if (currentPlayer.getPawn().canMove(clickedPosition) &&
		// board.isPathFree(currentPosition, clickedPosition)) {
		// currentPlayer.getPawn().moveTo(clickedPosition);
		// cells[currentPosition.getX()][currentPosition.getY()].setFill(Color.WHITE);
		// cells[row][col].setFill(getPlayerColor(currentPlayer));
		// currentPlayer = (currentPlayer == player1) ? player2 : player1;
		// }
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