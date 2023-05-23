package presentation;

import abstraction.*;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseButton;

/**
 * Handles the movement of pawns in the game.
 */
public class HandleMovePawn {

	private GameTurn gameTurn;

	/**
	 * Constructs a new instance of HandleMovePawn with the specified GameTurn.
	 *
	 * @param gameTurn The GameTurn object representing the current game state.
	 */
	public HandleMovePawn(GameTurn gameTurn) {
		this.gameTurn = gameTurn;
	}

	/**
	 * Handles the movement of pawns based on user input.
	 */
	public void handleMove() {
		for (Position position : gameTurn.board.getPlayers()[gameTurn.board.getCurrentTurn()].getPawn()
				.getPossibleMove()) {
			gameTurn.possibleCellMap.get(position).setOnMouseClicked(e -> {
				if (e.getButton() == MouseButton.PRIMARY && !gameTurn.isPlacingWall) {
					movePawn(position);
				}
			});
		}
	}

	/**
	 * Moves the specified player's pawn to the given position.
	 */
	public void movePawn(Position pos) {
		if (gameTurn.board.getPlayers()[gameTurn.board.getCurrentTurn()].getPawn().move(gameTurn.board, pos)) {
			gameTurn.mediaPlayerPawnMove.stop();
			gameTurn.mediaPlayerPawnMove.play();

			gameTurn.invisibleGrid = gameTurn.updateBoard(true);
			gameTurn.grid = gameTurn.updateBoard(false);

			gameTurn.canDoAction = false;
			gameTurn.hasMoved = true;
			Menu.launchVerification(gameTurn, gameTurn.primaryStage);

			if (gameTurn.board.getPlayers()[gameTurn.board.getCurrentTurn()].getPawn().isWinner()) {
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("winner");
				alert.setHeaderText(
						"The winner is  " + gameTurn.board.getPlayers()[gameTurn.board.getCurrentTurn()].getName());
				alert.showAndWait();
				Menu menuInstance = new Menu();
				Menu.launchVerification(menuInstance, gameTurn.primaryStage);
			}
		}
	}
}
