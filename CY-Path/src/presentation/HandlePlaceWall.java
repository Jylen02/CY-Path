package presentation;

import abstraction.Orientation;
import abstraction.Position;
import abstraction.Wall;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;

/**
 * Handles the placement of walls in the game.
 */
public class HandlePlaceWall {
	private GameTurn gameTurn;

	/**
	 * Constructs a new instance of HandlePlaceWall with the specified GameTurn.
	 *
	 * @param gameTurn The GameTurn object representing the current game state.
	 */
	public HandlePlaceWall(GameTurn gameTurn) {
		this.gameTurn = gameTurn;
	}

	/**
	 * Handles the wall placement based on user input.
	 *
	 * @param scene  The Scene where the wall placement is handled.
	 * @param button The Button used for wall placement.
	 */
	public void handlePlaceWall(Scene scene, Button button) {
		button.setDisable(true);
		gameTurn.isPlacingWall = true;

		scene.setOnMouseClicked(e -> {
			if (e.getButton() == MouseButton.SECONDARY) {
				updateWallOrientation();
			}
		});
		for (Position position : gameTurn.positionWall) {
			gameTurn.cellWallMap.get(position).setOnMouseEntered(e -> {
				gameTurn.wallPreview.setFill(Color.BLACK);
			});
			gameTurn.cellWallMap.get(position).setOnMouseExited(e -> {
				gameTurn.wallPreview.setFill(Color.RED);
			});

			gameTurn.possibleCellMap.get(position).setOnMouseClicked(e -> {
				if (e.getButton() == MouseButton.PRIMARY) {
					if (gameTurn.wallPreview.getWidth() > gameTurn.wallPreview.getHeight()) {
						placeWall(Orientation.HORIZONTAL, position);
					} else {
						placeWall(Orientation.VERTICAL, position);
					}
				}
			});
		}
	}

	/**
	 * Places a wall with the specified orientation and position.
	 *
	 * @param orientation The Orientation of the wall (HORIZONTAL or VERTICAL).
	 * @param position    The Position of the wall.
	 */
	public void placeWall(Orientation orientation, Position position) {
		if (Wall.createWall(gameTurn.board, orientation, position)) {
			gameTurn.mediaPlayerWallPlaced.stop();
			gameTurn.mediaPlayerWallPlaced.play();

			gameTurn.isPlacingWall = false;
			gameTurn.hasPlacedWall = true;
			gameTurn.wallPreview = null;
			gameTurn.canDoAction = false;
			Menu.launchVerification(gameTurn, gameTurn.primaryStage);
		}
	}

	/**
	 * Updates the wall orientation by adjusting the dimensions of the wall preview.
	 */
	public void updateWallOrientation() {
		if (gameTurn.wallPreview.getWidth() > gameTurn.wallPreview.getHeight()) {
			gameTurn.wallPreview.setWidth(5);
			gameTurn.wallPreview.setHeight(65);
		} else {
			gameTurn.wallPreview.setWidth(65);
			gameTurn.wallPreview.setHeight(5);
		}
	}
}
