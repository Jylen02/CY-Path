package presentation;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;

import abstraction.Board;
import abstraction.Case;
import abstraction.Pawn;
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

/**
 * The GameTurn class represents a game in the game application. It handles the
 * GUI, board updates, player actions, and game state.
 */
public class GameTurn extends Application {

	protected Stage primaryStage;
	private StackPane backgroundPane;

	// Board information
	protected Board board;
	protected GridPane grid;
	private Rectangle cell; // For the construction of the grid

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
	protected boolean canDoAction = true;
	protected boolean isPlacingWall = false;
	protected boolean hasPlacedWall = false;
	protected boolean hasMoved = false;

	private int mouseColumn;
	private int mouseRow;

	/**
	 * Constructs a GameTurn object with the given board, backgroundPane, and
	 * primaryStage.
	 *
	 * @param board          The board object representing the game board.
	 * @param backgroundPane The StackPane object representing the background pane
	 *                       of the GUI.
	 * @param primaryStage   The Stage object representing the primary stage of the
	 *                       application.
	 */
	public GameTurn(Board board, StackPane backgroundPane, Stage primaryStage) {
		this.board = board;
		this.backgroundPane = backgroundPane;
		this.primaryStage = primaryStage;
	}

	/**
	 * Starts the game turn by initializing the GUI components, updating the board,
	 * and handling player actions.
	 *
	 * @param primaryStage The Stage object representing the primary stage of the
	 *                     application.
	 * @throws Exception If an exception occurs during the start of the game turn.
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		Label playerTurn = Menu.createLabel(board.getPlayers()[board.getCurrentTurn()].getName() + "'s turn", 50);

		Pawn p = board.getPlayers()[board.getCurrentTurn()].getPawn();
		p.setPossibleMove(p.possibleMove(this.board, p.getPos()));
		if (p.getPossibleMove().isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle(board.getPlayers()[board.getCurrentTurn()].getName());
			alert.setHeaderText("You can't make any move, your turn has been skipped");
			alert.showAndWait();

			board.setCurrentTurn((board.getCurrentTurn() + 1) % board.getPlayerNumber());
			Menu.launchVerification(this, primaryStage);
		} else {
			grid = updateBoard();
			grid.setAlignment(Pos.CENTER);

			wallPreview = new Rectangle(30 * Wall.HEIGHT + 5 * (Wall.HEIGHT - 1), 5, Color.RED);
			wallPreview.setStroke(null);
			wallPreview.setVisible(false);

			Scene scene = new Scene(new StackPane(), 800, 700);

			HBox action = actionList(scene);

			Label volumeLabel = Menu.createLabel("Volume", 40);

			HBox sliderContainer = Menu.createHBox(10, volumeLabel, volumeSlider);

			VBox box = Menu.createVBox(50, playerTurn, grid, action, sliderContainer);

			if (canDoAction) {
				HandleMovePawn movePawn = new HandleMovePawn(this);
				movePawn.handleMove();
			}

			StackPane sceneContent = new StackPane();
			sceneContent.getChildren().addAll(backgroundPane, box, wallPreview);
			scene.setRoot(sceneContent);
			scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

			scene.setOnMouseMoved(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					if (isPlacingWall) {
						mouseColumn = (int) (event.getX() - 800 / 2);
						mouseRow = (int) (event.getY() - 700 / 2);
						wallPreview.setTranslateX(mouseColumn);
						wallPreview.setTranslateY(mouseRow);
					}
				}
			});

			primaryStage.setScene(scene);
			primaryStage.sizeToScene();
			primaryStage.show();
		}
	}

	/**
	 * Creates the action list UI component based on the current game state.
	 *
	 * @param scene The Scene object representing the current scene.
	 * @return The VBox object representing the action list UI component.
	 */
	private HBox actionList(Scene scene) {

		Button loadGame = Menu.createButton("Load", 80, 35, 15);
		loadGame.setDisable(true);
		loadGame.setOnAction(e -> {
			LoadGame loadGameInstance = new LoadGame(backgroundPane);
			Menu.launchVerification(loadGameInstance, primaryStage);
		});

		Button saveGame = Menu.createButton("Save", 80, 35, 15);
		saveGame.setDisable(true);
		saveGame.setOnAction(e -> {
			saveGame.setDisable(false);
			SaveGame saveGameInstance = new SaveGame(board, backgroundPane);
			Menu.launchVerification(saveGameInstance, primaryStage);
		});

		Button exit = Menu.createButton("Exit", 80, 35, 15);
		exit.setOnAction(e -> handleExit());

		Button restart = Menu.createButton("Restart", 80, 35, 15);
		restart.setOnAction(e -> handleRestart());

		Button cancel = Menu.createButton("Cancel", 90, 35, 15);
		cancel.setOnAction(e -> handleCancel());
		cancel.setDisable(true);
		cancel.getStyleClass().add("cancel-button");

		Button confirm = Menu.createButton("Confirm", 90, 35, 15);
		confirm.setOnAction(e -> handleConfirm());
		confirm.setDisable(true);
		confirm.getStyleClass().add("confirm-button");

		Button wall = Menu.createButton("Wall (" + board.getPlayers()[board.getCurrentTurn()].getRemainingWall() + ")",
				90, 35, 15);
		wall.getStyleClass().add("wall-button");
		wall.setOnAction(e -> {
			HandlePlaceWall placeWall = new HandlePlaceWall(this);
			placeWall.handlePlaceWall(scene, wall);
			wallPreview.setVisible(true);
			cancel.setDisable(false);
		});

		if (!canDoAction || board.getPlayers()[board.getCurrentTurn()].getRemainingWall() == 0) {
			wall.setDisable(true);
		}

		if (hasMoved || hasPlacedWall) {
			cancel.setDisable(false);
			confirm.setDisable(false);
		}

		if (!hasMoved && !isPlacingWall && !hasPlacedWall) {
			loadGame.setDisable(false);
			saveGame.setDisable(false);
		}

		HBox box = Menu.createHBox(20, exit, restart, cancel, wall, confirm, saveGame, loadGame);

		return box;
	}

	/**
	 * Updates the board UI component by updating the positions of the pawns and
	 * walls.
	 */
	protected GridPane updateBoard() {
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
					if ((row + col) % 2 == 0) {
						this.cell = new Rectangle(5, 5, Color.RED);
					} else if (row % 2 == 1) {
						this.cell = new Rectangle(5, 30);
					} else {
						this.cell = new Rectangle(30, 5);
					}
					this.cell.setFill(Color.BLACK);
				} else if (board.getBoard()[row][col] == Case.PAWN1) {
					cell = new Rectangle(30, 30);
					this.cell.setFill(new ImagePattern(penguin));
				} else if (board.getBoard()[row][col] == Case.PAWN2) {
					cell = new Rectangle(30, 30);
					this.cell.setFill(new ImagePattern(wolf));
				} else if (board.getBoard()[row][col] == Case.PAWN3) {
					cell = new Rectangle(30, 30);
					this.cell.setFill(new ImagePattern(gibbon));
				} else if (board.getBoard()[row][col] == Case.PAWN4) {
					cell = new Rectangle(30, 30);
					this.cell.setFill(new ImagePattern(seagull));
				} else {
					cell = new Rectangle(30, 30);

					if (board.getPlayers()[board.getCurrentTurn()].getPawn().getPossibleMove().contains(pos)) {

						switch (board.getCurrentTurn()) {
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
				possibleCellMap.put(pos, this.cell);
				grid.add(cell, col, row);
			}
		}
		return grid;
	}

	/**
	 * Reloads the game turn by resetting the necessary flags.
	 *
	 * @param primaryStage The primary stage of the JavaFX application.
	 */
	private void reloadGameTurn(Stage primaryStage) {
		this.canDoAction = true;
		Menu.launchVerification(this, primaryStage);
	}

	/**
	 * Resets the action flags related to game actions.
	 * 
	 * Resets the flags that indicate the current game actions, including wall
	 * placement, wall placement confirmation, and pawn movement. By calling this
	 * method, all action flags are set to false, indicating that no actions have
	 * been performed.
	 */
	private void resetAction() {
		this.canDoAction = true;
		this.isPlacingWall = false;
		this.hasPlacedWall = false;
		this.hasMoved = false;
	}

	/**
	 * Handles the cancellation action.
	 * 
	 * If the current wall placement is being cancelled, the isPlacingWall flag is
	 * set to false. If a wall has been placed and is being cancelled, the last
	 * placed wall is removed from the board and the hasPlacedWall flag is set to
	 * false. If a pawn move is being cancelled, the last moved pawn is reset to its
	 * previous position and the hasMoved flag is set to false. The board display is
	 * reset and the game is restarted.
	 */
	private void handleCancel() {
		if (this.isPlacingWall) {
			this.isPlacingWall = false;
		} else if (this.hasPlacedWall) {
			Wall.removeLastWall(board);
			this.hasPlacedWall = false;
		} else if (hasMoved) {
			board.getPlayers()[board.getCurrentTurn()].getPawn().resetMove(board);
			this.hasMoved = false;
		}

		reloadGameTurn(primaryStage);
	}

	/**
	 * Handles the confirmation action.
	 * 
	 * If a wall placement is being confirmed, the hasPlacedWall and isPlacingWall
	 * flags are set to false. Wall information is updated, including reducing the
	 * remaining wall count for the current player. The turn is changed to the next
	 * player. The board display is reset and the game is restarted.
	 * 
	 * If a pawn move is being confirmed, the hasMoved flag is set to false. The
	 * last position of the pawn is updated. The turn is changed to the next player.
	 * The board display is reset and the game is restarted.
	 */
	private void handleConfirm() {
		resetAction();
		if (hasPlacedWall) {
			board.getPlayers()[board.getCurrentTurn()]
					.setRemainingWall(board.getPlayers()[board.getCurrentTurn()].getRemainingWall() - 1);
		} else if (hasMoved) {
			Pawn p = board.getPlayers()[board.getCurrentTurn()].getPawn();
			p.setLastPos(p.getPos());
		}
		board.setCurrentTurn((board.getCurrentTurn() + 1) % board.getPlayerNumber());

		reloadGameTurn(primaryStage);
	}

	/**
	 * Handles the restart button action.
	 * 
	 * The wall preview is reset and the isPlacingWall flag is set to false. The
	 * action flags (hasPlacedWall, hasMoved, canDoAction) are reset to false. The
	 * game state is reset, including resetting the current turn and reinitializing
	 * the board. Each player's pawn position, last position, possible moves, and
	 * remaining walls are reset. The board display is updated and the game is
	 * restarted.
	 */
	private void handleRestart() {
		resetAction();

		this.board.setCurrentTurn(0);
		this.board.initializeBoard();
		for (int i = 0; i < this.board.getPlayerNumber(); i++) {
			board.getPlayers()[i].getPawn().setPos(
					new Position(Board.STARTINGPOSITIONPLAYERS[i].getX(), Board.STARTINGPOSITIONPLAYERS[i].getY()));
			board.getPlayers()[i].getPawn().setLastPos(
					new Position(Board.STARTINGPOSITIONPLAYERS[i].getX(), Board.STARTINGPOSITIONPLAYERS[i].getY()));
			board.getPlayers()[i].getPawn().setPossibleMove(
					board.getPlayers()[i].getPawn().possibleMove(board, board.getPlayers()[i].getPawn().getPos()));
			board.getPlayers()[i].setRemainingWall(Board.MAXWALLCOUNT / this.board.getPlayerNumber());
		}
		grid = updateBoard();
		reloadGameTurn(primaryStage);
	}

	/**
	 * Handles the exit button action.
	 * 
	 * The wall preview is reset and the isPlacingWall flag is set to false. The
	 * action flags (hasPlacedWall, hasMoved) are reset to false. The Menu instance
	 * is created and the game is exited.
	 * 
	 * Note: Saving functionality is not implemented in this method.
	 */
	private void handleExit() {
		resetAction();

		Menu menuInstance = new Menu();
		Menu.launchVerification(menuInstance, primaryStage);
	}
}
