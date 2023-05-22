package presentation;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;

import abstraction.Board;
import abstraction.Case;
import abstraction.Orientation;
import abstraction.Pawn;
import abstraction.Player;
import abstraction.Position;
import abstraction.Wall;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
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

public class GameTurn extends Application {

	private Stage primaryStage;
	private StackPane backgroundPane;

	// Board information
	private Board board;
	private GridPane grid;
	private GridPane invisible;
	private Rectangle cell; // For the construction of the grid

	// Players information
	private Player[] players;
	private int currentTurn;
	private boolean canDoAction;

	private Image wolf = new Image(getClass().getResource("/image/wolfR.png").toExternalForm());
	private Image gibbon = new Image(getClass().getResource("/image/gibbonG.png").toExternalForm());
	private Image penguin = new Image(getClass().getResource("/image/penguinB.png").toExternalForm());
	private Image seagull = new Image(getClass().getResource("/image/seagullY.png").toExternalForm());
	
	private MediaPlayer mediaPlayerPawnMove = BackgroundMusic.getInstance().getPawnMovePlayer();
	private MediaPlayer mediaPlayerWallPlaced = BackgroundMusic.getInstance().getWallPlacedPlayer();
	private Slider volumeSlider = BackgroundMusic.getInstance().getVolumeSlider();
	

	private LinkedHashMap<Position, Rectangle> possibleCellMap = new LinkedHashMap<Position, Rectangle>();
	private Set<Position> positionWall = new LinkedHashSet<Position>();
	private LinkedHashMap<Position, Rectangle> cellWallMap = new LinkedHashMap<Position, Rectangle>();

	private Rectangle wallPreview;

	// Action information
	private boolean isPlacingWall;
	private boolean hasPlacedWall;
	private boolean hasMoved;

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

		grid = updateBoard(false);
		grid.setAlignment(Pos.CENTER);

		invisible = updateBoard(true);
		invisible.setAlignment(Pos.CENTER);

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

		uselessBox.getChildren().addAll(uselessPlayerTurn, grid, uselessAction, uselessSliderContainer);
		uselessBox.setAlignment(Pos.CENTER);

		VBox box = new VBox(50);
		box.getChildren().addAll(playerTurn, invisible, action, sliderContainer);
		box.setAlignment(Pos.CENTER);

		if (canDoAction && !isPlacingWall) {
			handleMove(scene, players[this.currentTurn]);
		}

		StackPane sceneContent = new StackPane();
		sceneContent.getChildren().addAll(backgroundPane, uselessBox, wallPreview, box);
		scene.setRoot(sceneContent);

		scene.setOnMouseMoved(new EventHandler<MouseEvent>() {
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
		});

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
			handlePlaceWall(scene, wall);
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

	private void handleMove(Scene scene, Player p) {
		for (Position position : p.getPawn().getPossibleMove()) {
			possibleCellMap.get(position).setOnMouseClicked(e -> {
				if (e.getButton() == MouseButton.PRIMARY && !isPlacingWall) {
					movePawn(p, position);
				}
			});
		}
	}

	private void movePawn(Player p, Position pos) {
		if (p.getPawn().move(this.board, pos)) {
			hasMoved = true;

			mediaPlayerPawnMove.stop();
			mediaPlayerPawnMove.play();


			invisible = updateBoard(true);
			grid = updateBoard(false);

			this.canDoAction = false;
			try {
				start(primaryStage);
			} catch (Exception e) {
				e.printStackTrace();
			}
			// mediaPlayerPawnMove.play();
			if (p.getPawn().isWinner()) {
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("winner");
				alert.setHeaderText("The winner is  " + p.getName());
				alert.showAndWait();
				// Finir la partie
				Menu menuInstance = new Menu();
				menuInstance.start(primaryStage);
			}
		}
	}

	private void placeWall(Orientation orientation, Position position) {
		if (Wall.createWall(this.board, this.players, this.currentTurn, orientation, position)) {
			// Mettre à jour l'affichage du plateau
			this.isPlacingWall = false;
			this.hasPlacedWall = true;
			// Supprimer le mur en cours de placement de la grille du plateau
			this.wallPreview = null;
			this.canDoAction = false;
			try {
				start(primaryStage);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void handlePlaceWall(Scene scene, Button button) {
		button.setDisable(true);
		this.isPlacingWall = true;

		scene.setOnMouseClicked(e -> {
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
					if (wallPreview.getWidth() > wallPreview.getHeight()) {
						placeWall(Orientation.HORIZONTAL, position);
					} else {
						placeWall(Orientation.VERTICAL, position);
					}
					mediaPlayerWallPlaced.stop();
					mediaPlayerWallPlaced.play();
				}
			});
		}
	}

	private void updateWallOrientation() {
		// Mettre à jour la taille et l'orientation du mur en cours de placement
		if (wallPreview.getWidth() > wallPreview.getHeight()) {
			this.wallPreview.setWidth(5);
			this.wallPreview.setHeight(65);
		} else {
			this.wallPreview.setWidth(65);
			this.wallPreview.setHeight(5);
		}
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
