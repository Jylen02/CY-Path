package presentation;

import abstraction.*;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseButton;

public class HandleMovePawn {

	private GameTurn gameTurn;

    public HandleMovePawn(GameTurn gameTurn) {
        this.gameTurn = gameTurn;
    }

    public void handleMove() {
		for (Position position : gameTurn.players[gameTurn.currentTurn].getPawn().getPossibleMove()) {
			gameTurn.possibleCellMap.get(position).setOnMouseClicked(e -> {
			    if (e.getButton() == MouseButton.PRIMARY && !gameTurn.isPlacingWall) {
			        movePawn(gameTurn.players[gameTurn.currentTurn], position);
			    }
			});
		}
	}

    public void movePawn(Player p, Position pos) {
		if (p.getPawn().move(gameTurn.board, pos)) {
			gameTurn.mediaPlayerPawnMove.stop();
			gameTurn.mediaPlayerPawnMove.play();

			gameTurn.invisible = gameTurn.updateBoard(true);
			gameTurn.grid = gameTurn.updateBoard(false);

			gameTurn.canDoAction = false;
			gameTurn.hasMoved = true;
			try {
				gameTurn.start(gameTurn.primaryStage);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (p.getPawn().isWinner()) {
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("winner");
				alert.setHeaderText("The winner is  " + p.getName());
				alert.showAndWait();
				// Return to menu
				Menu menuInstance = new Menu();
				menuInstance.start(gameTurn.primaryStage);
			}
		}
	}
}
