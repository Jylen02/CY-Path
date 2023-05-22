package presentation;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;

import abstraction.Board;
import abstraction.Case;
import abstraction.Pawn;
import abstraction.Player;
import abstraction.Position;
import abstraction.Wall;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class GameTurn extends Application {

	protected Stage primaryStage;
	private StackPane backgroundPane;

	// Board information
	protected Board board;
	protected GridPane grid;
	protected GridPane invisibleGrid;
	private Rectangle cell; // For the construction of the grid

	// Players information
	protected Player[] players;
	protected int currentTurn;
	protected boolean canDoAction;

	private Image wolf = new Image(getClass().getResource("/image/wolfR.png").toExternalForm());
	private Image gibbon = new Image(getClass().getResource("/image/gibbonG.png").toExternalForm());
	private Image penguin = new Image(getClass().getResource("/image/penguinB.png").toExternalForm());
	private Image seagull = new Image(getClass().getResource("/image/seagullY.png").toExternalForm());
	
	protected MediaPlayer mediaPlayerPawnMove = BackgroundMusic.getInstance().getPawnMovePlayer();
	protected MediaPlayer mediaPlayerWallPlaced = BackgroundMusic.getInstance().getWallPlacedPlayer();
	private Slider volumeSlider = BackgroundMusic.getInstance().getVolumeSlider();
	
	protected LinkedHashMap<Position, Rectangle> possibleCellMap = new LinkedHashMap<Position, Rectangle>();
	protected Set<Position> positionWall = new LinkedHashSet<Position>();
	protected LinkedHashMap<Position, Rectangle> cellWallMap = new LinkedHashMap<Position, Rectangle>();

	protected Rectangle wallPreview;

	// Action information
	protected boolean isPlacingWall;
	protected boolean hasPlacedWall;
	protected boolean hasMoved;

	private int mouseColumn;
	private int mouseRow;

	public GameTurn(Board board, Player[] players, StackPane backgroundPane, Stage primaryStage) {
		this.board = board;
		this.players = players;
		this.currentTurn = 0;
		this.canDoAction = true;
		this.backgroundPane = backgroundPane;
		this.primaryStage = primaryStage;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Label playerTurn = Menu.createLabel(this.players[this.currentTurn].getName() + "'s turn", 50);
		Label uselessPlayerTurn = Menu.createLabel(this.players[this.currentTurn].getName() + "'s turn", 50);

		Pawn p = players[this.currentTurn].getPawn();
		p.setPossibleMove(p.possibleMove(this.board, p.getPos()));
		if (p.getPossibleMove()==null /*Set vide : new Set<Position>[]*/) {
			//Skip turn + affiche alert
			handleConfirm();
			//Alerte à faire : peux pas bouger
			
			//Partie nulle -> Restart
		}
		
		grid = updateBoard(false);
		grid.setAlignment(Pos.CENTER);

		invisibleGrid = updateBoard(true);
		invisibleGrid.setAlignment(Pos.CENTER);

		wallPreview = new Rectangle(65, 5, Color.RED);
		wallPreview.setOpacity(1);
		wallPreview.setStroke(null);
		wallPreview.setVisible(false);

		Scene scene = new Scene(new StackPane(), 800, 700);

		HBox action = actionList(scene, canDoAction);
		HBox uselessAction = actionList(scene, false);

		Label volumeLabel = Menu.createLabel("Volume", 40);	

		HBox sliderContainer = new HBox(10);
		sliderContainer.getChildren().addAll(volumeLabel, volumeSlider);
		sliderContainer.setAlignment(Pos.CENTER);

		HBox uselessSliderContainer = new HBox(10);
		uselessSliderContainer.getChildren().addAll(Menu.createLabel("Volume", 40), new Slider(0, 0.1, 0.05));
		uselessSliderContainer.setAlignment(Pos.CENTER);

		VBox uselessBox = new VBox(50);

		uselessSliderContainer.setVisible(false); // Rendre invisible tous les éléments de la uselessBox sauf la grid
		uselessAction.setVisible(false);
		uselessPlayerTurn.setVisible(false);

		//uselessBox.getChildren().addAll(uselessPlayerTurn, grid, uselessAction, uselessSliderContainer);
		uselessBox.setAlignment(Pos.CENTER);

		VBox box = new VBox(50);
		//box.getChildren().addAll(playerTurn, invisibleGrid, action, sliderContainer);
		box.getChildren().addAll(playerTurn, grid, action, sliderContainer);
		box.setAlignment(Pos.CENTER);

		if (canDoAction) {
			//handleMove(scene, players[this.currentTurn]);
			HandleMovePawn movePawn = new HandleMovePawn(this);
			movePawn.handleMove();
		}

		StackPane sceneContent = new StackPane();
		//sceneContent.getChildren().addAll(backgroundPane, uselessBox, wallPreview, box);
		sceneContent.getChildren().addAll(backgroundPane, box);
		scene.setRoot(sceneContent);

		/*scene.setOnMouseMoved(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (isPlacingWall) {
					mouseColumn = (int) (event.getX() - wallPreview.getWidth() / 2);
					mouseRow = (int) (event.getY() - wallPreview.getHeight() / 2);
					if (wallPreview.getWidth() > wallPreview.getHeight()) {
						wallPreview.setTranslateX(mouseColumn - 367);
						wallPreview.setTranslateY(mouseRow - 346);
					} else {
						wallPreview.setTranslateX(mouseColumn - 365 - 32);
						wallPreview.setTranslateY(mouseRow - 347 + 30);
					}
				}
			}
		});*/

		primaryStage.setScene(scene);
		primaryStage.sizeToScene();
		primaryStage.show();
	}

	private HBox actionList(Scene scene, boolean canDoAction) {
		Button exit = Menu.createButton("Exit", 110, 50, 20);
		exit.setOnAction(e -> handleExitButton());

		Button restart = Menu.createButton("Restart", 110, 50, 20);
		restart.setOnAction(e -> handleRestartButton());

		Button cancel = Menu.createButton("Cancel", 110, 50, 20);
		cancel.setOnAction(e -> handleCancel());
		cancel.setDisable(true);

		Button confirm = Menu.createButton("Confirm", 110, 50, 20);
		confirm.setOnAction(e -> handleConfirm());
		confirm.setDisable(true);

		Button wall = Menu.createButton("Wall (" + players[currentTurn].getRemainingWall() + ")", 110, 50, 20);
		wall.setOnAction(e -> {
			HandlePlaceWall placeWall = new HandlePlaceWall(this);
			placeWall.handlePlaceWall(scene, wall);
			//handlePlaceWall(scene, wall);
			wallPreview.setVisible(true);
			cancel.setDisable(false);
		});

		if (!canDoAction || players[currentTurn].getRemainingWall() == 0) {
			wall.setDisable(true);
		}

		if (hasMoved || hasPlacedWall) {
			cancel.setDisable(false);
			confirm.setDisable(false);
		}

		HBox box = new HBox(20);
		box.getChildren().addAll(exit, restart, wall, cancel, confirm);
		box.setAlignment(Pos.TOP_CENTER);

		return box;
	}

	protected GridPane updateBoard(boolean invisible) {
		GridPane grid = new GridPane();
		possibleCellMap.clear();
		cellWallMap.clear();
		positionWall.clear();
		for (int row = 0; row < Board.SIZE; row++) {
			for (int col = 0; col < Board.SIZE; col++) {
				Position pos = new Position(row, col);
				if (board.getBoard()[row][col] == Case.BORDER || board.getBoard()[row][col] == Case.POTENTIALWALL) {
					if (row % 2 == 1) {
						this.cell = new Rectangle(5, 30, Color.LIGHTGRAY);
					} else {
						this.cell = new Rectangle(30, 5, Color.LIGHTGRAY);
					}
				} else if (board.getBoard()[row][col] == Case.NULL) {
					this.cell = new Rectangle(5, 5, Color.LIGHTGRAY);
					positionWall.add(pos);
					cellWallMap.put(pos, this.cell);
				} else if (board.getBoard()[row][col] == Case.WALL) {
					// Wall Intersection
					if ((row + col) % 2 == 0) {
						this.cell = new Rectangle(5, 5, Color.RED);
					} else if (row % 2 == 1) {
						this.cell = new Rectangle(5, 30);
					} else {
						this.cell = new Rectangle(30, 5);
					}
					this.cell.setFill(Color.BLACK);
				} else if (board.getBoard()[row][col] == Case.PLAYER1) {
					cell = new Rectangle(30, 30);
					this.cell.setFill(new ImagePattern(penguin));
				} else if (board.getBoard()[row][col] == Case.PLAYER2) {
					cell = new Rectangle(30, 30);
					this.cell.setFill(new ImagePattern(wolf));
				} else if (board.getBoard()[row][col] == Case.PLAYER3) {
					cell = new Rectangle(30, 30);
					this.cell.setFill(new ImagePattern(gibbon));
				} else if (board.getBoard()[row][col] == Case.PLAYER4) {
					cell = new Rectangle(30, 30);
					this.cell.setFill(new ImagePattern(seagull));
				} else {
					cell = new Rectangle(30, 30);

					if (players[currentTurn].getPawn().getPossibleMove().contains(pos)) {

						switch (this.currentTurn) {
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

	private void handleCancel() {
		// Si on veut annuler le placement du mur en cours
		if (this.isPlacingWall) {
			this.isPlacingWall = false;
			this.wallPreview = null;
		}

		// Si on veut annuler un mur posé
		// Détecter si j'ai posé un mur
		if (this.hasPlacedWall) {
			Wall.removeLastWall(board);
			this.hasPlacedWall = false;
		}

		// Si on veut annuler un mouvement de pion
		// Détecter si j'ai bougé un pion
		if (hasMoved) {
			players[this.currentTurn].getPawn().resetMove(board);
			hasMoved = false;
		}

		// Réinitialiser l'affichage du plateau
		this.canDoAction = true;
		try {
			start(primaryStage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void handleConfirm() {
		if (hasPlacedWall) {
			hasPlacedWall = false;
			// Reset Wall preview
			this.isPlacingWall = false;
			this.wallPreview = null;

			// Update wall information
			players[currentTurn].setRemainingWall(players[currentTurn].getRemainingWall() - 1);

			// Change turn
			this.currentTurn = (currentTurn + 1) % board.getPlayerNumber();

			this.canDoAction = true;
			try {
				start(primaryStage);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (hasMoved) {
			hasMoved = false;
			Pawn p = this.players[currentTurn].getPawn();
			p.setLastPos(p.getPos());

			// Change turn
			this.currentTurn = (currentTurn + 1) % board.getPlayerNumber();
			this.canDoAction = true;
			try {
				start(primaryStage);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void handleRestartButton() {
		// Reset Wall preview
		this.isPlacingWall = false;
		this.wallPreview = null;

		// Reset action
		hasPlacedWall = false;
		hasMoved = false;
		this.canDoAction = true;

		// Reset the game state
		this.currentTurn = 0;
		this.board.initializeBoard();
		for (int i = 0; i < this.board.getPlayerNumber(); i++) {
			players[i].getPawn().setPos(
					new Position(Board.STARTINGPOSITIONPLAYERS[i].getX(), Board.STARTINGPOSITIONPLAYERS[i].getY()));
			players[i].getPawn()
					.setPossibleMove(players[i].getPawn().possibleMove(board, players[i].getPawn().getPos()));
			players[i].setRemainingWall(Board.MAXWALLCOUNT / this.board.getPlayerNumber());
		}
		grid = updateBoard(false);
		try {
			start(primaryStage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void handleExitButton() {
		// Save to implement

		// Reset Wall preview
		this.wallPreview = null;
		this.isPlacingWall = false;

		// Reset action
		this.hasPlacedWall = false;
		hasMoved = false;

		Menu menuInstance = new Menu();
		menuInstance.start(primaryStage);
	}
}
