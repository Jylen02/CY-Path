package presentation;

import java.util.LinkedHashMap;
import java.util.Map;
import java.io.File;
import java.util.Set;

import abstraction.*;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
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

	// A enlever (récupérer dans players[i].getPawn())
	private Set<Position> possibleMove;
	private Position pos;
	//private Set<Position> possibleCell;
	private LinkedHashMap<Position,Rectangle> possibleCellMap = new LinkedHashMap<Position,Rectangle>();

	// PlaceWall information
	private Wall wall;
	private Rectangle wallPreview;
	private boolean isPlacingWall;
	private boolean hasPlacedWall;
	private int mouseColumn;
	private int mouseRow;
    private StackPane rootPane;
	private Background background;

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

	public boolean hasPlacedWall() {
		return hasPlacedWall;
	}

	public void setHasPlacedWall(boolean hasPlacedWall) {
		this.hasPlacedWall = hasPlacedWall;
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
		this.primaryStage.setResizable(false);

		Image icon = new Image("image/dikdik.png"); // Icon of the application
		this.primaryStage.getIcons().add(icon);
		
		/*	Deplacement des pions
		Media mediaPawnMove = new Media(new File("src/sound/move.mp3").toURI().toString());
		MediaPlayer mediaPlayerPawnMove = new MediaPlayer(mediaPawnMove);
		mediaPlayerPawnMove.setVolume(0.5); // Set volume at 50%
		mediaPlayerPawnMove.setCycleCount(1); // To repeat the sound 1 time
		mediaPlayerPawnMove.play(); //A mettre dans la methode move pour jouer le son
		*/
		Media mediaMusic = new Media(new File("src/sound/tw3.mp3").toURI().toString());
		MediaPlayer mediaPlayerMusic = new MediaPlayer(mediaMusic);
		mediaPlayerMusic.setVolume(0.03); // Set volume at 3%
		mediaPlayerMusic.setCycleCount(MediaPlayer.INDEFINITE); // Repetition à l'infini
		mediaPlayerMusic.play(); //A mettre dans la methode move pour jouer le son
		

  
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
		
		Image backgroundImage = new Image("image/wallpaper.jpg");
        BackgroundSize backgroundSize = new BackgroundSize(800, 700, true, true, true, true);
        BackgroundImage backgroundImg = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        background = new Background(backgroundImg);

        rootPane = new StackPane();
        rootPane.setBackground(background);
		
		rootPane.getChildren().add(box);

		Scene scene = new Scene(rootPane, 800, 700);  

		this.primaryStage.setScene(scene);
		this.primaryStage.sizeToScene();
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
		box.setAlignment(Pos.CENTER);
		
		/*rootPane = new StackPane();
        rootPane.setBackground(background);
		rootPane.getChildren().add(box);*/
		Scene scene = new Scene(box, 800, 700);

		this.primaryStage.setScene(scene);
		this.primaryStage.sizeToScene();
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
		/*
		rootPane = new StackPane();
        rootPane.setBackground(background);
		rootPane.getChildren().add(box); */
		Scene scene = new Scene(box, 800, 700);

		this.primaryStage.setScene(scene);
		this.primaryStage.sizeToScene();
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
					players[0] = new Player(playerName,
							new Pawn(board, new Position(Board.SIZE - 2, Board.SIZE / 2), Case.PLAYER1),
							Board.MAXWALLCOUNT / this.getBoard().getPlayerNumber());
					break;
				case 1:
					players[1] = new Player(playerName, new Pawn(board, new Position(1, Board.SIZE / 2), Case.PLAYER2),
							Board.MAXWALLCOUNT / this.getBoard().getPlayerNumber());
					break;
				case 2:
					players[2] = new Player(playerName, new Pawn(board, new Position(Board.SIZE / 2, 1), Case.PLAYER3),
							Board.MAXWALLCOUNT / this.getBoard().getPlayerNumber());
					break;
				case 3:

					players[3] = new Player(playerName,
							new Pawn(board, new Position(Board.SIZE / 2, Board.SIZE - 2), Case.PLAYER4),
							Board.MAXWALLCOUNT / this.getBoard().getPlayerNumber());
					break;
				default:
					break;
				}
			}
			playBoard(true);
		});
		box.setAlignment(Pos.CENTER);
		box.getChildren().addAll(back, start);

		Scene scene = new Scene(box, 800, 700);

		this.primaryStage.setScene(scene);
		this.primaryStage.sizeToScene();
		this.primaryStage.show();

	}

	private void playBoard(boolean canDoAction) {
		Label playerTurn = createLabel(this.getPlayers()[this.getCurrentTurn()].getName() + "'s turn", 50);
		// playerTurn.setStyle("-fx-text-fill: red;");
		possibleMove = players[this.getCurrentTurn()].getPawn().possibleMove(this.board, players[this.getCurrentTurn()].getPawn().getPos());
		
		grid = updateBoard();
		grid.setAlignment(Pos.CENTER);
		handleMove(players[this.getCurrentTurn()],possibleMove);
		Scene scene = new Scene(new BorderPane(), 800, 700);
		
		VBox action = actionList(scene, canDoAction);

		BorderPane pane = new BorderPane();
		pane.setTop(playerTurn);
		BorderPane.setAlignment(playerTurn, Pos.CENTER);
		pane.setCenter(grid);
		pane.setRight(action);
		//pane.setBottom(wallContainers);


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
		primaryStage.sizeToScene();
		primaryStage.show();
	}

	private VBox actionList(Scene scene, boolean canDoAction) {
		Button exit = createButton("Exit", 100, 50, 20);
		exit.setOnAction(e -> start(primaryStage));

		Button restart = createButton("Restart", 100, 50, 20);
		restart.setOnAction(e -> handleRestartButton());
		
		Button wall = createButton("Wall --", 100, 50, 20);
		wall.setOnAction(e -> handlePlaceWall(scene, wall));
		if (!canDoAction) {
			wall.setDisable(true);
		}

		Button cancel = createButton("Cancel", 100, 50, 20);
		cancel.setOnAction(e -> handleCancel());

		Button confirm = createButton("Confirm", 100, 50, 20);
		confirm.setOnAction(e -> handleConfirm());

		HBox confirms = new HBox(20);
		confirms.getChildren().addAll(cancel, confirm);
		confirms.setAlignment(Pos.CENTER);

		VBox box = new VBox(20);
		box.getChildren().addAll(exit, restart, wall, confirms);
		box.setAlignment(Pos.CENTER);

		return box;
	}

	private GridPane updateBoard() {
		GridPane grid = new GridPane();
		possibleCellMap.clear();
		for (int row = 0; row < Board.SIZE; row++) {
			for (int col = 0; col < Board.SIZE; col++) {
				pos = new Position(row, col);
				if (board.getBoard()[row][col] == Case.BORDER || board.getBoard()[row][col] == Case.POTENTIALWALL) {
					if (row % 2 == 1) {
						this.cell = new Rectangle(5, 30);
					} else {
						this.cell = new Rectangle(30, 5);
					}
					this.cell.setFill(Color.LIGHTGRAY);
				} else if (board.getBoard()[row][col] == Case.NULL) {
					this.cell = new Rectangle(5, 5);
					this.cell.setFill(Color.LIGHTGRAY);
				} else if (board.getBoard()[row][col] == Case.WALL) {
					// Wall Intersection
					if ((row + col) % 2 == 0) {
						this.cell = new Rectangle(5, 5);
						this.cell.setFill(Color.RED);
					} else if (row % 2 == 1) {
						this.cell = new Rectangle(5, 30);
					} else {
						this.cell = new Rectangle(30, 5);
					}
					this.cell.setFill(Color.BLACK);
				} else if (board.getBoard()[row][col] == Case.PLAYER1) {
					cell = new Rectangle(30, 30);
					this.cell.setFill(Color.BLUE);
				} else if (board.getBoard()[row][col] == Case.PLAYER2) {
					cell = new Rectangle(30, 30);
					this.cell.setFill(Color.RED);
				} else if (board.getBoard()[row][col] == Case.PLAYER3) {
					cell = new Rectangle(30, 30);
					this.cell.setFill(Color.GREEN);
				} else if (board.getBoard()[row][col] == Case.PLAYER4) {
					cell = new Rectangle(30, 30);
					this.cell.setFill(Color.YELLOW);
				} else {
					cell = new Rectangle(30, 30);
					if (possibleMove.contains(pos)) {
						possibleCellMap.put(pos,this.cell);
						switch (this.getCurrentTurn()) {
						case 0:
							this.cell.setFill(Color.LIGHTBLUE);
							break;
						case 1:
							this.cell.setFill(Color.LIGHTSALMON);
							break;
						case 2:
							this.cell.setFill(Color.LIGHTGREEN);
							break;
						case 3:
							this.cell.setFill(Color.LIGHTGOLDENRODYELLOW);
							break;
						}
						
						
					} else {
						this.cell.setFill(Color.WHITE);
					}
				}
				this.cell.setStroke(null);
				grid.add(cell, col, row);
			}
		}
		return grid;
	}

	private void handleMove(Player p, Set<Position> possibleMove) {
		boolean verif =false;
		for (Position element : possibleMove) {
		    if(verif ==false) {
		    	possibleCellMap.get(element).setOnMouseClicked(event -> pawnMove(p,element));
		    	verif=true;
		    	
		    }
		}
		
		
		}
	private void pawnMove(Player p, Position ppp) {
		p.getPawn().move(this.board, ppp);
		grid = updateBoard();
	}

	private void handlePlaceWall(Scene scene, Button button) {
		button.setDisable(true);
		// Création de la prévisualisation du mur
		this.setWallPreview(new Rectangle(65, 5));
		this.getWallPreview().setFill(Color.RED);
		this.getWallPreview().setOpacity(0.5);
		this.getWallPreview().setStroke(null);

		this.setPlacingWall(true);
		this.setWall(new Wall(Orientation.HORIZONTAL, new Position(0, 0))); // Orientation horizontale par défaut

		// Créer un conteneur pour voir le mur en cours de placement
		GridPane wallContainer = new GridPane();
		wallContainer.getChildren().add(this.getWallPreview());

		// Gestion de l'événement de mouvement de la souris pour suivre le curseur
		scene.setOnMouseMoved(e -> {
			mouseColumn = (int) e.getX(); // X : abscisse
			mouseRow = (int) e.getY(); // Y : ordonnée
			int row = cursorRowToIndex();
			int column = cursorColumnToIndex();
			if (0 < row && row < 18 && 0 < column && column < 18) {
				if (row % 2 == 0 && column % 2 == 0) {
					this.getWallPreview().setFill(Color.BLACK);
					this.getWallPreview().setX(row); // Mettre à jour la position X du rectangle
					this.getWallPreview().setY(column); // Mettre à jour la position Y du rectangle
				} else {
					this.getWallPreview().setFill(Color.RED);
				}
			}
			if (this.getWall().getOrientation() == Orientation.HORIZONTAL) {
				wallContainer.setTranslateX(mouseColumn - 360);
				wallContainer.setTranslateY(mouseRow - 405);
			} else {
				wallContainer.setTranslateX(mouseColumn - 332);
				wallContainer.setTranslateY(mouseRow - 435);
			}

			System.out.println(mouseRow + "," + mouseColumn + " : " + row + "," + column);
		});

		// Gestion de l'événement de clic gauche pour placer le mur
		scene.setOnMouseClicked(e -> {
			if (e.getButton() == MouseButton.PRIMARY) {
				// Vérifier si la position du mur est valide (Case.NULL) et le placer
				int column = cursorColumnToIndex();
				int row = cursorRowToIndex();
				if (row % 2 == 0 && column % 2 == 0) {
					if (this.getBoard().getBoard()[row][column] == Case.NULL) {
						this.getWall().setPosition(new Position(row, column));
						this.getWall().wallError(this.getBoard(), this.getPlayers(), this.getCurrentTurn());
						// Mettre à jour l'affichage du plateau
						this.setPlacingWall(false);
						this.setHasPlacedWall(true);
						// Supprimer le mur en cours de placement de la grille du plateau
						this.setWallPreview(null);
						playBoard(false);
					}
				} else {
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setTitle("Error");
					alert.setHeaderText("Invalid coordinates");
					alert.setContentText("You can't place a wall here");
					alert.showAndWait();
				}
			} else if (e.getButton() == MouseButton.SECONDARY) {
				// Changer l'orientation du mur avec un clic droit
				updateWallOrientation();
			}
		});
		// Ajouter le mur en cours de placement à la grille du plateau
		scene.setRoot(wallContainer);
	}

	private void updateWallOrientation() {
		// Mettre à jour la taille et l'orientation du mur en cours de placement
		if (this.getWall().getOrientation() == Orientation.HORIZONTAL) {
			this.getWallPreview().setWidth(5);
			this.getWallPreview().setHeight(65);
			this.getWall().setOrientation(Orientation.VERTICAL);
		} else {
			this.getWallPreview().setWidth(65);
			this.getWallPreview().setHeight(5);
			this.getWall().setOrientation(Orientation.HORIZONTAL);
		}
	}

	private int cursorRowToIndex() {
		// TODO bien convertir le curseur
		return (int) ((mouseRow) / 16.5) - 4;
	}

	private int cursorColumnToIndex() {
		// TODO bien convertir le curseur
		return (int) ((mouseColumn - 76 + 8) / 16.5) + 4;
	}

	private void handleCancel() {
		// Si on veut annuler le placement du mur en cours
		// TODO Control
		if (this.isPlacingWall()) {
			this.setPlacingWall(false);
			// Supprimer le mur en cours de placement de la grille du plateau
			this.setWallPreview(null);
			// Réinitialiser l'affichage du plateau
			playBoard(true);
		}

		// Si on veut annuler un mur posé
		if (this.getWall() != null) {
			// Détecter si j'ai posé un mur sinon erreur
			if (this.hasPlacedWall()) {
				this.getWall().updateWall(board, Case.POTENTIALWALL);
				this.setHasPlacedWall(false);
			}
			playBoard(true);
		}

		// TODO Si on veut annuler le déplacement d'un pion à implémenter
	}

	private void handleConfirm() {
		this.setPlacingWall(false);
		this.setHasPlacedWall(false);
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
		this.setHasPlacedWall(false);
		this.setCurrentTurn(0);
		this.getBoard().initializeBoard();
		playBoard(true);
	}
	

	public static void main(String[] args) {
		launch(args);
	}
}