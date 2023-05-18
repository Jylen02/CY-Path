package Presentation;

import Abstraction.*;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Main extends Application {

	private Board board;
	private Stage primaryStage;
	private Rectangle cell;
	TextField[] player;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Quoridor");
		this.primaryStage.setWidth(800);
		this.primaryStage.setHeight(700);
		//this.primaryStage.setResizable(false);

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
		
		Label title = new Label("Quoridor");
		
		Label label = new Label("Choose the name of each players");
		
		VBox box = new VBox(title, label);
		
		Button back = new Button("Back");
		back.setOnAction(e -> chooseNumberOfPlayer());
		
		Button start = new Button("Start");
		start.setOnAction(e -> playBoard(0));
		player = new TextField[number];
		for (int i = 1; i < number + 1; i++) {
			player[i-1] = new TextField("Player " + i);
			box.getChildren().add(player[i-1]);
		}
		
		box.getChildren().addAll(back, start);
		
		Scene scene = new Scene(box);
		this.primaryStage.setScene(scene);
		this.primaryStage.show();

	}

	private void playBoard(int turn) {
		Label playerTurn = new Label(player[turn].getText() + "'s turn");
		playerTurn.setStyle("-fx-font-size: 50px;");
		
		GridPane grid = updateBoard();
		
		VBox action = actionList(turn);
		
		BorderPane pane = new BorderPane();
		pane.setTop(playerTurn);
		pane.setLeft(grid);
		pane.setCenter(action);
		
		Scene scene = new Scene(pane);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private VBox actionList(int turn) {
		Rectangle wall = new Rectangle (5,30);
		wall.setFill(Color.BLACK);
		wall.setStroke(null);
		wall.setOnDragDetected(e -> handlePlaceWall());
		
		Rectangle wall1 = new Rectangle (30,5);
		wall1.setFill(Color.BLACK);
		wall1.setStroke(null);
		wall1.setOnDragDetected(e -> handlePlaceWall());
		
		HBox walls = new HBox(20);
		walls.getChildren().addAll(wall, wall1);
		
		Button restart = new Button("Restart");
		
		Button exit = new Button("Exit");
		
		Button cancel = new Button("Cancel");
		cancel.setOnAction(e -> {
			
		});
		
		Button confirm = new Button("Confirm");
		confirm.setOnAction(e -> {
			playBoard((turn+1)%4);
		});
		
		HBox confirms = new HBox(20);
		confirms.getChildren().addAll(cancel, confirm);
		return new VBox(exit, restart, walls, confirms); 
	}
	
	private GridPane updateBoard() {
		GridPane grid = new GridPane();
		
		for (int row = 0; row < Board.SIZE; row++) {
			for (int col = 0; col < Board.SIZE; col++) {
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
				grid.add(cell, row, col);
			}
		}
		return grid;
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

	private Button createButton(String text) {
		Button button = new Button(text);
		button.setPrefSize(300, 100);
		button.setStyle("-fx-font-size: 50px;");
		return button;
	}

	private void handleMove() {
		// TODO: Implement the logic for handling the Move  click
	}

	private void handlePlaceWall() {
		// TODO: Implement the logic for handling the Place Wall button click
	}

	private void handleResetButton() {
		// Reset the game state
		board.initializeBoard();
		playBoard(0);
	}

	public static void main(String[] args) {
		launch(args);
	}
}