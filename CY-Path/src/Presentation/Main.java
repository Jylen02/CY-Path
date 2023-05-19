package Presentation;

import Abstraction.*;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Main extends Application {

	private Stage primaryStage;
	// Board information
	private Board board; // numberOfPlayers in this class
	private GridPane grid;
	private Rectangle cell; // For the construction of the grid
	private Player[] players;
	private int currentTurn = 0;

	// PlaceWall information
	private Rectangle wallPreview;
	private boolean isPlacingWall;
	private int mouseColumn;
	private int mouseRow;
	private Wall wall;

	// Getters & Setters
	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public GridPane getGrid() {
		return grid;
	}

	public Player[] getPlayers() {
		return players;
	}

	public void setPlayers(Player[] players) {
		this.players = players;
	}

	public int getCurrentTurn() {
		return currentTurn;
	}

	public void setCurrentTurn(int currentTurn) {
		this.currentTurn = currentTurn;
	}

	public Rectangle getWallPreview() {
		return wallPreview;
	}

	public void setWallPreview(Rectangle wallPreview) {
		this.wallPreview = wallPreview;
	}

	public boolean isPlacingWall() {
		return isPlacingWall;
	}

	public void setPlacingWall(boolean isPlacingWall) {
		this.isPlacingWall = isPlacingWall;
	}

	public Wall getWall() {
		return wall;
	}

	public void setWall(Wall wall) {
		this.wall = wall;
	}

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Quoridor");
		this.primaryStage.setWidth(800);
		this.primaryStage.setHeight(700);
		// this.primaryStage.setResizable(false);

		VBox box = new VBox(20);

		Label title = createLabel("Quoridor", 140);

		Button play = createButton("Play", 300, 100, 50);
		play.setOnAction(e -> chooseNumberOfPlayer());

		Button rules = createButton("Rules", 300, 100, 50);
		rules.setOnAction(e -> showRules());

		Button exit = createButton("Exit", 300, 100, 50);
		exit.setOnAction(e -> primaryStage.close());

		box.getChildren().addAll(title, play, rules, exit);
		box.setAlignment(Pos.CENTER);

		Scene scene = new Scene(box);

		this.primaryStage.setScene(scene);
		this.primaryStage.show();
	}

	private Button createButton(String text, int i, int j, int pixel) {
		Button button = new Button(text);
		button.setPrefSize(i, j);
		button.setStyle("-fx-font-size: " + pixel + "px;");
		return button;
	}

	private Label createLabel(String text, int pixel) {
		Label label = new Label(text);
		label.setStyle("-fx-font-size: " + pixel + "px;");
		return label;
	}

	private void showRules() {

		Label title = createLabel("Rules", 140);

		ListView<String> listOfRules = new ListView<>();
		listOfRules.getItems().addAll("Goal : To be the first to reach the line opposite to one’s base line.", "",
				"Rules 1 : When the game starts the wall are placed in their storage area.",
				"Rules 2 : Each player in turn, chooses to move his pawn or to put up one of his wall.",
				"Rules 3 : When the maximum amount of wall is reached, the player must move his pawn.",
				"Rules 4 : The pawns are moved one square at a time, horizontally or vertically",
				"Rules 5 : The fences must be placed between 2 sets of 2 squares",
				"Rules 6 : When two pawns face each other on neighbouring squares which are not separated by a fence,\n\t\tthe player whose turn it is can jump the opponent’s pawn (and place himself behind him), thus advancing an extra square",
				"Rules 7 : If there is a fence behind the said pawn, the player can place his pawn to the side of the other pawn",
				"Rules 8 : The first player who reaches one of the 9 squares opposite his base line is the winner",
				"Rules 9 : It is forbidden to jump more than one pawn");

		Button back = createButton("Back", 100, 50, 20);
		back.setOnAction(e -> start(primaryStage));

		VBox box = new VBox(title, listOfRules, back);
		// box.setAlignment(Pos.CENTER);

		Scene scene = new Scene(box);

		this.primaryStage.setScene(scene);
		this.primaryStage.show();
	}

	private void chooseNumberOfPlayer() {
		VBox box = new VBox(10);

		Button back = createButton("Back", 100, 50, 20);
		back.setOnAction(e -> start(primaryStage));

		Label title = createLabel("Quoridor", 100);

		Label label = createLabel("Choose the number of players", 50);

		RadioButton twoPlayer = new RadioButton("2 Players");
		twoPlayer.setOnAction(e -> {
			this.setBoard(new Board(2));
			createPlayers();
		});

		RadioButton fourPlayer = new RadioButton("4 Players");
		fourPlayer.setOnAction(e -> {
			this.setBoard(new Board(4));
			createPlayers();
		});

		box.getChildren().addAll(title, label, twoPlayer, fourPlayer, back);
		box.setAlignment(Pos.CENTER);

		Scene scene = new Scene(box);

		this.primaryStage.setScene(scene);
		this.primaryStage.show();
	}

	private void createPlayers() {

		Label title = createLabel("Quoridor", 100);

		Label label = createLabel("Choose the name of each players", 50);

		VBox box = new VBox(title, label);

		// Choose each player's name
		TextField[] name = new TextField[this.getBoard().getPlayerNumber()];
		for (int i = 0; i < this.getBoard().getPlayerNumber(); i++) {
			name[i] = new TextField("Player " + (i + 1));
			box.getChildren().add(name[i]);
		}

		Button back = createButton("Back", 100, 50, 20);
		back.setOnAction(e -> chooseNumberOfPlayer());

		Button start = createButton("Start", 100, 50, 20);
		start.setOnAction(e -> {
			// Get each player's name
			this.setPlayers(new Player[this.getBoard().getPlayerNumber()]);
			for (int i = 0; i < this.getBoard().getPlayerNumber(); i++) {
				String playerName = name[i].getText();
				switch (i) {
				case 0:
					this.getPlayers()[0] = new Player(playerName,
							new Pawn(this.getBoard(), new Position(Board.SIZE - 2, Board.SIZE / 2), Case.PLAYER1));
					break;
				case 1:
					this.getPlayers()[1] = new Player(playerName,
							new Pawn(this.getBoard(), new Position(1, Board.SIZE / 2), Case.PLAYER2));
					break;
				case 2:
					this.getPlayers()[2] = new Player(playerName,
							new Pawn(this.getBoard(), new Position(Board.SIZE / 2, 1), Case.PLAYER3));
					break;
				case 3:
					this.getPlayers()[3] = new Player(playerName,
							new Pawn(this.getBoard(), new Position(Board.SIZE / 2, Board.SIZE - 2), Case.PLAYER4));
					break;
				default:
					break;
				}
			}
			playBoard(true);
		});
		box.setAlignment(Pos.CENTER);
		box.getChildren().addAll(back, start);

		Scene scene = new Scene(box);
		this.primaryStage.setScene(scene);
		this.primaryStage.show();

	}

	private void playBoard(Boolean canDoAction) {
		Label playerTurn = createLabel(this.getPlayers()[this.getCurrentTurn()].getName() + "'s turn", 50);

		grid = updateBoard();
	    Scene scene = new Scene(new BorderPane());

		VBox action = actionList(scene, canDoAction);

		BorderPane pane = new BorderPane();
		pane.setTop(playerTurn);
		pane.setLeft(grid);
		pane.setCenter(action);

	    if (this.isPlacingWall()) {
	    	StackPane wallContainer = new StackPane(this.getWallPreview());
	        // Vérifier si le mur en cours de placement existe déjà dans la grille
	        if (!grid.getChildren().contains(wallContainer)) {
	            // Ajouter le mur en cours de placement à la grille du plateau
	            grid.getChildren().add(wallContainer);
	        }
	    }
		scene.setRoot(pane);
		
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private VBox actionList(Scene scene, Boolean canDoAction) {
		Button exit = createButton("Exit",100,50,20);
		exit.setOnAction(e -> start(primaryStage));
		
		Button restart = createButton("Restart",100,50,20);
		restart.setOnAction(e -> handleRestartButton());
		
		Button wall = createButton("Wall --",100,50,20);
		if (canDoAction) {
			wall.setOnAction(e -> handlePlaceWall(scene));
		}
		else {
			wall.setStyle("-fx-background-color: gray;");
		}
		

		Button cancel = createButton("Cancel",100,50,20);
		cancel.setOnAction(e -> handleCancel());

		Button confirm = createButton("Confirm",100,50,20);
		confirm.setOnAction(e -> handleConfirm());
		
		HBox confirms = new HBox(20);
		confirms.getChildren().addAll(cancel, confirm);
		
		VBox box = new VBox(20);
		box.getChildren().addAll(exit, restart, wall, confirms);
		return box;
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
				} else if (board.getBoard()[row][col] == Case.NULL) {
					this.cell = new Rectangle(5, 5);
					this.cell.setFill(Color.LIGHTGRAY);
				} else if (board.getBoard()[row][col] == Case.WALLINTERSECTION) {
					this.cell = new Rectangle(5, 5);
					this.cell.setFill(Color.BLACK);
				} else if (board.getBoard()[row][col] == Case.WALL) {
					if (row % 2 == 0) {
						this.cell = new Rectangle(5, 30);
					} else {
						this.cell = new Rectangle(30, 5);
					}
					this.cell.setFill(Color.BLACK);
				} else if( board.getBoard()[row][col] == Case.PLAYER1) {
					cell = new Rectangle(30, 30);
					this.cell.setFill(Color.BLUE);
					
				}
				else if( board.getBoard()[row][col] == Case.PLAYER2) {
					cell = new Rectangle(30, 30);
					this.cell.setFill(Color.RED);
					
				}
				else if( board.getBoard()[row][col] == Case.PLAYER3) {
					cell = new Rectangle(30, 30);
					this.cell.setFill(Color.GREEN);
					
				}
				else if( board.getBoard()[row][col] == Case.PLAYER4) {
					cell = new Rectangle(30, 30);
					this.cell.setFill(Color.PURPLE);
					
				}
				else {
					cell = new Rectangle(30, 30);
					this.cell.setFill(Color.WHITE);
				}
				this.cell.setStroke(null);
				grid.add(cell, row, col);
			}
		}
		return grid;
	}

	private void handleMove() {
		// TODO: Implement the logic for handling the Move click
	}

	private void handlePlaceWall(Scene scene) {
		// TODO: Implement the logic for handling the Place Wall button click
		//Création de la prévisualisation du mur
		this.setWallPreview(new Rectangle(65, 5));
		this.getWallPreview().setFill(Color.BLACK);
		this.getWallPreview().setOpacity(0.5);
		this.getWallPreview().setStroke(null);
		
		this.setPlacingWall(true);
		this.setWall(new Wall(Orientation.HORIZONTAL, new Position(0, 0))); // Orientation horizontale par défaut
		
		// Créer un conteneur pour le mur en cours de placement
	    StackPane wallContainer = new StackPane(this.getWallPreview());

	    // Gestion de l'événement de mouvement de la souris pour suivre le curseur
	    scene.setOnMouseMoved(e -> {
	    	mouseRow = (int) e.getX();
	    	mouseColumn = (int) e.getY();
	    	int row = cursorRowToIndex();
	    	int column = cursorColumnToIndex();
	    	if (0 < row && row < 19 && 0 < column && column < 19) {
	    		if (row%2==0 && column%2==0) {
		    		this.getWallPreview().setFill(Color.RED);
		    		this.getWallPreview().setX(row); // Mettre à jour la position X du rectangle
		    		this.getWallPreview().setY(column); // Mettre à jour la position Y du rectangle
	    		}
	    	}else {
	    		this.getWallPreview().setFill(Color.BLACK);
	    	}
	        wallContainer.setTranslateX(mouseRow-31);
	        wallContainer.setTranslateY(mouseColumn-75);
	        System.out.println(mouseColumn + "," + mouseRow + " : " + column + "," + row);
	    });

		// Gestion de l'événement de clic gauche pour placer le mur
		scene.setOnMouseClicked(e -> {
			if (e.getButton() == MouseButton.PRIMARY) {
				// Vérifier si la position du mur est valide (Case.NULL) et le placer
		    	int column = cursorColumnToIndex();
		    	int row = cursorRowToIndex();
		    	if (row%2==0 && column%2==0) {
		    		if (this.getBoard().getBoard()[row][column] == Case.NULL) {
						this.getWall().setPosition(new Position(row, column));
						this.getWall().wallError(this.getBoard(), this.getPlayers(), this.getCurrentTurn());
						// Mettre à jour l'affichage du plateau
						this.setPlacingWall(false);
						// Supprimer le mur en cours de placement de la grille du plateau
						this.setWallPreview(null);
						playBoard(false);
					}
		    	} else {
		    		 Alert alert = new Alert(Alert.AlertType.INFORMATION);
		    		 alert.setContentText("Les coordonnées entrées sont non valide");
		    	}
			} else if (e.getButton() == MouseButton.SECONDARY) {
				// Changer l'orientation du mur avec un clic droit
				if (this.getWall().getOrientation()==Orientation.HORIZONTAL) {
					this.getWall().setOrientation(Orientation.VERTICAL);
				} else {
					this.getWall().setOrientation(Orientation.HORIZONTAL);
				}
				// Mettre à jour l'affichage du mur en cours de placement
				updateWallPreview();
			}
		});
		
		// Mettre à jour la taille et l'orientation du mur en cours de placement
	    updateWallPreview();
	    // Ajouter le mur en cours de placement à la grille du plateau
	    if (!grid.getChildren().contains(wallContainer)) {
	        grid.getChildren().add(wallContainer);
	    }
	}

	private void updateWallPreview() {
		// Mettre à jour la taille et l'orientation du mur en cours de placement
		if (this.getWall().getOrientation()==Orientation.HORIZONTAL) {
			this.getWallPreview().setWidth(65);
			this.getWallPreview().setHeight(5);
		} else {
			this.getWallPreview().setWidth(5);
			this.getWallPreview().setHeight(65);
		}
	}

	private int cursorRowToIndex() {
		//TODO bien convertir le curseur
		return (int) ((mouseRow)/16);
	}
	
	private int cursorColumnToIndex() {
		//TODO bien convertir le curseur
		return (int) ((mouseColumn-76+8)/16);
	}
	
	private void handleCancel() {
		// Si on veut annuler le placement du mur en cours
		if (this.isPlacingWall()) {
			this.setPlacingWall(false);
			// Supprimer le mur en cours de placement de la grille du plateau
			this.setWallPreview(null);
			// Réinitialiser l'affichage du plateau
			playBoard(true);
		}

		//Si on veut annuler un mur posé
		if (this.getWall()!=null) {
			this.getWall().updateWall(board, Case.POTENTIALWALL, -1);

			playBoard(true);
		}
		
		//TODO Si on veut annuler le déplacement d'un pion à implémenter
	}

	private void handleConfirm() {
		this.setPlacingWall(false);
		this.setWallPreview(null);
		this.setWall(null);
		this.setCurrentTurn((currentTurn + 1) % board.getPlayerNumber());
		playBoard(true);
	}
	private void handleRestartButton() {
		// Reset the game state
		this.setWallPreview(null);
		this.setWall(null);
		this.setPlacingWall(false);
		this.setCurrentTurn(0);
		this.getBoard().initializeBoard();
		playBoard(true);
	}

	public static void main(String[] args) {
		launch(args);
	}
}