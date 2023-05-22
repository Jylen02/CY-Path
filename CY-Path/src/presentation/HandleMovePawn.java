package presentation;

import java.util.Map;

import abstraction.*;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Cell;
import javafx.scene.input.MouseButton;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class HandleMovePawn {
    private Stage primaryStage;
    private Player player;
    private Board board;
    private Map<Position, Cell> possibleCellMap;
    private MediaPlayer mediaPlayerPawnMove;
    private boolean isPlacingWall;
    private boolean hasMoved;
    private boolean canDoAction;

    public HandleMovePawn(Stage primaryStage, Player player, Board board, Map<Position, Cell> possibleCellMap, MediaPlayer mediaPlayerPawnMove, boolean isPlacingWall, boolean hasMoved, boolean canDoAction) {
        this.primaryStage = primaryStage;
        this.player = player;
        this.board = board;
        this.possibleCellMap = possibleCellMap;
        this.mediaPlayerPawnMove = mediaPlayerPawnMove;
        this.isPlacingWall = isPlacingWall;
        this.hasMoved = hasMoved;
        this.canDoAction = canDoAction;
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
}
