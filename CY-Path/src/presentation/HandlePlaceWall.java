package presentation;

import abstraction.Orientation;
import abstraction.Position;
import abstraction.Wall;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;

public class HandlePlaceWall {
	private GameTurn gameTurn;

    public HandlePlaceWall(GameTurn gameTurn) {
        this.gameTurn = gameTurn;
    }
    
    public void placeWall(Orientation orientation, Position position) {
		if (Wall.createWall(gameTurn.board, gameTurn.players, gameTurn.currentTurn, orientation, position)) {
			gameTurn.mediaPlayerWallPlaced.stop();
			gameTurn.mediaPlayerWallPlaced.play();
			// Mettre à jour l'affichage du plateau
			gameTurn.isPlacingWall = false;
			gameTurn.hasPlacedWall = true;
			// Supprimer le mur en cours de placement de la grille du plateau
			gameTurn.wallPreview = null;
			gameTurn.canDoAction = false;
			try {
				gameTurn.start(gameTurn.primaryStage);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

    public void handlePlaceWall(Scene scene, Button button) {
		button.setDisable(true);
		gameTurn.isPlacingWall = true;

		scene.setOnMouseClicked(e -> {
			if (e.getButton() == MouseButton.SECONDARY) {
				// Changer l'orientation du mur avec un clic droit
				updateWallOrientation();
			}
		});
		for (Position position : gameTurn.positionWall) {
			gameTurn.cellWallMap.get(position).setOnMouseEntered(e ->{
				if (gameTurn.wallPreview.getWidth() > gameTurn.wallPreview.getHeight()) {
					Wall.createWall(gameTurn.board, gameTurn.players, gameTurn.currentTurn, Orientation.HORIZONTAL, position);
				} else {
					Wall.createWall(gameTurn.board, gameTurn.players, gameTurn.currentTurn, Orientation.VERTICAL, position);
				}
				gameTurn.updateBoard(false);
				//Coloriage des murs selon l'orientation
				//gameTurn.wallPreview.setFill(Color.BLACK);
			});
			gameTurn.cellWallMap.get(position).setOnMouseExited(e -> {
				//Enlever coloriage
				//gameTurn.wallPreview.setFill(Color.RED);
				Wall.removeLastWall(gameTurn.board);
				gameTurn.updateBoard(false);
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

	public void updateWallOrientation() {
		// Mettre à jour la taille et l'orientation du mur en cours de placement
		if (gameTurn.wallPreview.getWidth() > gameTurn.wallPreview.getHeight()) {
			gameTurn.wallPreview.setWidth(5);
			gameTurn.wallPreview.setHeight(65);
		} else {
			gameTurn.wallPreview.setWidth(65);
			gameTurn.wallPreview.setHeight(5);
		}
	}
}
