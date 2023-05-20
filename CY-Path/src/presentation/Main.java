package presentation;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.io.File;
import java.util.Set;

import abstraction.*;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
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
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Main extends Application {

	private Stage primaryStage;
	// Board information
	private Board board; // numberOfPlayers in this class
	private GridPane grid;
	private GridPane invisible;
	private Rectangle cell; // For the construction of the grid

	// Main of quoridor
	private Player[] players;
	private int currentTurn = 0;

	// private Set<Position> possibleCell;
	private LinkedHashMap<Position, Rectangle> possibleCellMap = new LinkedHashMap<Position, Rectangle>();
	private Set<Position> positionWall = new LinkedHashSet<Position>();
	private LinkedHashMap<Position,Rectangle> cellWallMap = new LinkedHashMap<Position,Rectangle>();

	// A Supprimer
	private Wall wall;

	// PlaceWall information
	private VBox box;
	private StackPane wallContainer;
	private Rectangle wallPreview;
	private boolean isPlacingWall;
	private boolean hasPlacedWall;
	private int mouseColumn;
	private int mouseRow;

	// Background A voir si on garde en attribut
	private StackPane rootPane;
	private Background background;

	private Image wolf = new Image("image/wolfR.png");
	private Image gibbon = new Image("image/gibbonG.png");
	private Image penguin = new Image("image/penguinB.png");
	private Image seagull = new Image("image/seagullY.png");

	private Media mediaPawnMove = new Media(new File("src/sound/move.mp3").toURI().toString());
	private MediaPlayer mediaPlayerPawnMove = new MediaPlayer(mediaPawnMove);
	private Media mediaMusic = new Media(new File("src/sound/tw3LOW.mp3").toURI().toString());
	private MediaPlayer mediaPlayerMusic = new MediaPlayer(mediaMusic);

	// Slider volume lié entre pages
	private Label volumeLabel = createLabel("Volume", 40);
	private Slider volumeSlider = new Slider(0, 0.1, 0.05);

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

		this.primaryStage.getIcons().add(new Image("image/dikdik.png"));

		mediaPlayerPawnMove.volumeProperty().bindBidirectional(volumeSlider.valueProperty());
		mediaPlayerPawnMove.setCycleCount(1); // To repeat the sound 1 time

		mediaPlayerMusic.volumeProperty().bindBidirectional(volumeSlider.valueProperty());
		mediaPlayerMusic.setCycleCount(MediaPlayer.INDEFINITE); // Infinite restart
		mediaPlayerMusic.play(); // background music start with the launch of the app

		HBox sliderContainer = new HBox(10);
		sliderContainer.setAlignment(Pos.CENTER);
		sliderContainer.getChildren().addAll(volumeLabel, volumeSlider);

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
		box.getChildren().add(sliderContainer);

		Image backgroundImage = new Image("image/background.png");
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

		/*
		 * rootPane = new StackPane(); rootPane.setBackground(background);
		 * rootPane.getChildren().add(box);
		 */
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
		 * rootPane = new StackPane(); rootPane.setBackground(background);
		 * rootPane.getChildren().add(box);
		 */
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
			this.setCurrentTurn(0);
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
		Label uselessPlayerTurn = createLabel(this.getPlayers()[this.getCurrentTurn()].getName() + "'s turn", 50);
		
		Pawn p = players[this.getCurrentTurn()].getPawn();
		p.setPossibleMove(p.possibleMove(this.board, p.getPos()));

		grid = updateBoard(false);
		grid.setAlignment(Pos.CENTER);

		invisible = updateBoard(true);
		invisible.setAlignment(Pos.CENTER);
		
		wallPreview = new Rectangle(65, 5, Color.RED);
		wallPreview.setOpacity(1);
		wallPreview.setStroke(null);
		wallPreview.setVisible(false);
		
		wallContainer = new StackPane();
		
		Scene scene = new Scene(new StackPane(), 800, 700);

		HBox action = actionList(scene, canDoAction);
		HBox uselessAction = actionList(scene, canDoAction);

		Label volumeLabel = createLabel("Volume", 40);

		mediaPlayerMusic.volumeProperty().bindBidirectional(volumeSlider.valueProperty());

		HBox sliderContainer = new HBox(10);
		sliderContainer.getChildren().addAll(volumeLabel, volumeSlider);
		sliderContainer.setAlignment(Pos.CENTER);
		
		HBox uselessSliderContainer = new HBox(10);
		uselessSliderContainer.getChildren().addAll(createLabel("Volume", 40), new Slider(0, 0.1, 0.05));
		uselessSliderContainer.setAlignment(Pos.CENTER);
		
		VBox uselessBox = new VBox(50);
		uselessBox.getChildren().addAll(uselessPlayerTurn, grid, uselessAction, uselessSliderContainer);
		uselessBox.setAlignment(Pos.CENTER);
		
		box = new VBox(50);
		box.getChildren().addAll(playerTurn, invisible, action, sliderContainer);
		box.setAlignment(Pos.CENTER);
		
		if (canDoAction) {
			handleMove(scene, players[this.getCurrentTurn()]);
		}

		wallContainer.getChildren().addAll(uselessBox, wallPreview, box);
		scene.setRoot(wallContainer);

		scene.setOnMouseMoved(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (isPlacingWall) {
					mouseColumn =  (int) (event.getX() - wallPreview.getWidth() / 2);
					mouseRow = (int) (event.getY() - wallPreview.getHeight() / 2);
					if (wallPreview.getWidth() > wallPreview.getHeight()) {
						wallPreview.setTranslateX(mouseColumn-367);
						wallPreview.setTranslateY(mouseRow-346);
					} else {
						wallPreview.setTranslateX(mouseColumn-365-32);
						wallPreview.setTranslateY(mouseRow-347+30);
					}
				}
			}
		});

		primaryStage.setScene(scene);
		primaryStage.sizeToScene();
		primaryStage.show();
	}

	private HBox actionList(Scene scene, boolean canDoAction) {
		Button exit = createButton("Exit", 100, 50, 20);
		exit.setOnAction(e -> start(primaryStage));

		Button restart = createButton("Restart", 100, 50, 20);
		restart.setOnAction(e -> handleRestartButton());

		Button wall = createButton("Wall --", 100, 50, 20);
		wall.setOnAction(e -> {
			handlePlaceWall(scene, wall);
			wallPreview.setVisible(true);
			/*if (isPlacingWall) {
				wallPreview.setVisible(true);
				isPlacingWall = false;
			} else {
				wallPreview.setVisible(false);
				isPlacingWall = true;
			}*/
		});


		if (!canDoAction) {
			wall.setDisable(true);
		}

		Button cancel = createButton("Cancel", 100, 50, 20);
		cancel.setOnAction(e -> handleCancel());

		Button confirm = createButton("Confirm", 100, 50, 20);
		confirm.setOnAction(e -> handleConfirm());

		HBox box = new HBox(20);
		box.getChildren().addAll(exit, restart, wall, cancel, confirm);
		box.setAlignment(Pos.TOP_CENTER);

		return box;
	}

	private GridPane updateBoard(boolean invisible) {
		GridPane grid = new GridPane();
		possibleCellMap.clear();
		cellWallMap.clear();
		positionWall.clear();
		for (int row = 0; row < Board.SIZE; row++) {
			for (int col = 0; col < Board.SIZE; col++) {
				Position pos = new Position(row, col);
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
					positionWall.add(pos);
					cellWallMap.put(pos, this.cell);
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
					// possibleCellMap.put(pos,this.cell);
					this.cell.setFill(new ImagePattern(penguin));
					// this.cell.setFill(Color.BLUE);
				} else if (board.getBoard()[row][col] == Case.PLAYER2) {
					cell = new Rectangle(30, 30);
					// possibleCellMap.put(pos,this.cell);
					this.cell.setFill(new ImagePattern(wolf));
					// this.cell.setFill(Color.RED);
				} else if (board.getBoard()[row][col] == Case.PLAYER3) {
					cell = new Rectangle(30, 30);
					// possibleCellMap.put(pos,this.cell);
					// this.cell.setFill(Color.GREEN);
					this.cell.setFill(new ImagePattern(gibbon));
				} else if (board.getBoard()[row][col] == Case.PLAYER4) {
					cell = new Rectangle(30, 30);
					// possibleCellMap.put(pos,this.cell);
					this.cell.setFill(new ImagePattern(seagull));
					// this.cell.setFill(Color.YELLOW);
				} else {
					cell = new Rectangle(30, 30);

					if (players[currentTurn].getPawn().getPossibleMove().contains(pos)) {

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
				if (invisible) {
					this.cell.setOpacity(0);
				}
				this.cell.setStroke(null);
				possibleCellMap.put(pos, this.cell);
				grid.add(cell, col, row);
			}
		}
		return grid;
	}

	private void handleMove(Scene scene, Player p) {
		for (Position position : p.getPawn().getPossibleMove()) {
			possibleCellMap.get(position).setOnMouseClicked(e -> pawnMove(p, position));
		}
	}

	private void pawnMove(Player p, Position pos) {
		if (p.getPawn().move(this.board, pos)) {
			mediaPlayerPawnMove.stop();
			mediaPlayerPawnMove.play();
			invisible = updateBoard(true);
			grid = updateBoard(false);
			playBoard(false);
			// mediaPlayerPawnMove.play();
			if (p.getPawn().isWinner()) {
				/*
				 * Set<Position> poz=p.getPawn().getFinishLine(); for (Position position : poz)
				 * { possibleCellMap.get(position).setFill(Color.GOLD); }
				 */
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("winner");
				alert.setHeaderText("The winner is  " + p.getName());
				alert.showAndWait();
				// Finir la partie
				start(primaryStage);
			}
		}
	}

	/*private void wallPreview(Scene scene) {
		this.setWallPreview(new Rectangle(65, 5));
		this.getWallPreview().setFill(Color.RED);
		this.getWallPreview().setOpacity(0.5);
		this.getWallPreview().setStroke(null);
		this.setPlacingWall(true);

	}*/

	private void handlePlaceWall(Scene scene, Button button) {
		button.setDisable(true);
		//wallPreview(scene);
		this.setPlacingWall(true);
		this.setWall(new Wall(Orientation.HORIZONTAL, new Position(0, 0)));

		scene.setOnMouseClicked(e -> {
			//Collections.reverse(wallContainer.getChildren());
			if (e.getButton() == MouseButton.SECONDARY) {
				// Changer l'orientation du mur avec un clic droit
				updateWallOrientation();
			}
		});
		for (Position position : positionWall) {
			cellWallMap.get(position).setOnMouseEntered(e -> wallPreview.setFill(Color.BLACK));
			cellWallMap.get(position).setOnMouseExited(e -> wallPreview.setFill(Color.RED));
			possibleCellMap.get(position).setOnMouseClicked(e -> {
				if (e.getButton() == MouseButton.PRIMARY) {
					
					// Vérifier si la position du mur est valide (Case.NULL) et le placer
					// int column = cursorColumnToIndex();
					// int row = cursorRowToIndex();
					this.getWall().setPosition(position);
					if (Wall.createWall(this.getBoard(), this.getPlayers(), this.getCurrentTurn(),
							this.getWall().getOrientation(), this.getWall().getPosition())) {
						// Mettre à jour l'affichage du plateau
						this.setPlacingWall(false);
						this.setHasPlacedWall(true);
						// Supprimer le mur en cours de placement de la grille du plateau
						this.setWallPreview(null);
						playBoard(false);
					}
				}
				//Collections.reverse(wallContainer.getChildren());
			});
		}
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
		return (int) ((mouseRow - 146) / 17);
	}

	private int cursorColumnToIndex() {
		// TODO bien convertir le curseur
		return (int) ((mouseColumn - 240) / 17);
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
		// Reset Wall preview
		this.setPlacingWall(false);
		this.setHasPlacedWall(false);
		this.setWallPreview(null);
		this.setWall(null);

		// Change turn
		this.setCurrentTurn((currentTurn + 1) % board.getPlayerNumber());
		playBoard(true);
	}

	private void handleRestartButton() {
		// Reset Wall preview
		this.setWallPreview(null);
		this.setWall(null);
		this.setPlacingWall(false);
		this.setHasPlacedWall(false);

		// Reset the game state
		this.setCurrentTurn(0);
		this.getBoard().initializeBoard();
		playBoard(true);
	}

	public static void main(String[] args) {
		launch(args);
	}
}