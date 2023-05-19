package Presentation;

import Abstraction.Case;
import Abstraction.Orientation;
import Abstraction.Position;
import Abstraction.Wall;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class HandlePlaceWall extends Main implements EventHandler<ActionEvent> {
	// PlaceWall information
	private Rectangle wallPreview;
	private boolean isPlacingWall;
	private int mouseColumn;
	private int mouseRow;
	private Wall wall;
		
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

	public Wall getWall() {
		return wall;
	}

	public void setWall(Wall wall) {
		this.wall = wall;
	}
	
	@Override
	public void handle(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	/*private void handlePlaceWall(Scene scene) {
		// TODO: Implement the logic for handling the Place Wall button click
		//Création de la prévisualisation du mur
		this.setWallPreview(new Rectangle(65, 5));
		this.getWallPreview().setFill(Color.BLACK);
		this.getWallPreview().setOpacity(0.5);
		this.getWallPreview().setStroke(null);
		
		this.setPlacingWall(true);
		this.setWall(new Wall(Orientation.HORIZONTAL, new Position(0, 0))); // Orientation horizontale par défaut
		
		// Créer un conteneur pour le mur en cours de placement
	    StackPane wallContainer = new StackPane(this.getWallPreview());

	    // Gestion de l'événement de mouvement de la souris pour suivre le curseur
	    scene.setOnMouseMoved(e -> {
	    	mouseRow = (int) e.getX();
	    	mouseColumn = (int) e.getY();
	    	int row = cursorRowToIndex();
	    	int column = cursorColumnToIndex();
	    	if (0 < row && row < 19 && 0 < column && column < 19) {
	    		if (row%2==0 && column%2==0) {
		    		this.getWallPreview().setFill(Color.RED);
		    		this.getWallPreview().setX(row); // Mettre à jour la position X du rectangle
		    		this.getWallPreview().setY(column); // Mettre à jour la position Y du rectangle
	    		}
	    	}else {
	    		this.getWallPreview().setFill(Color.BLACK);
	    	}
	        wallContainer.setTranslateX(mouseRow-31);
	        wallContainer.setTranslateY(mouseColumn-75);
	        System.out.println(mouseColumn + "," + mouseRow + " : " + column + "," + row);
	    });

		// Gestion de l'événement de clic gauche pour placer le mur
		scene.setOnMouseClicked(e -> {
			if (e.getButton() == MouseButton.PRIMARY) {
				// Vérifier si la position du mur est valide (Case.NULL) et le placer
		    	int column = cursorColumnToIndex();
		    	int row = cursorRowToIndex();
		    	if (row%2==0 && column%2==0) {
		    		if (this.getBoard().getBoard()[row][column] == Case.NULL) {
						this.getWall().setPosition(new Position(row, column));
						this.getWall().wallError(this.getBoard(), this.getPlayers(), this.getCurrentTurn());
						// Mettre à jour l'affichage du plateau
						this.setPlacingWall(false);
						// Supprimer le mur en cours de placement de la grille du plateau
						this.setWallPreview(null);
						playBoard(false);
					}
		    	} else {
		    		 Alert alert = new Alert(Alert.AlertType.INFORMATION);
		    		 alert.setContentText("Les coordonnées entrées sont non valide");
		    	}
			} else if (e.getButton() == MouseButton.SECONDARY) {
				// Changer l'orientation du mur avec un clic droit
				if (this.getWall().getOrientation()==Orientation.HORIZONTAL) {
					this.getWall().setOrientation(Orientation.VERTICAL);
				} else {
					this.getWall().setOrientation(Orientation.HORIZONTAL);
				}
				// Mettre à jour l'affichage du mur en cours de placement
				updateWallPreview();
			}
		});
		
		// Mettre à jour la taille et l'orientation du mur en cours de placement
	    updateWallPreview();
	    // Ajouter le mur en cours de placement à la grille du plateau
	    if (!grid.getChildren().contains(wallContainer)) {
	        grid.getChildren().add(wallContainer);
	    }
	}

	private void updateWallPreview() {
		// Mettre à jour la taille et l'orientation du mur en cours de placement
		if (this.getWall().getOrientation()==Orientation.HORIZONTAL) {
			this.getWallPreview().setWidth(65);
			this.getWallPreview().setHeight(5);
		} else {
			this.getWallPreview().setWidth(5);
			this.getWallPreview().setHeight(65);
		}
	}

	private int cursorRowToIndex() {
		//TODO bien convertir le curseur
		return (int) ((mouseRow)/16);
	}
	
	private int cursorColumnToIndex() {
		//TODO bien convertir le curseur
		return (int) ((mouseColumn-76+8)/16);
	}
	*/

}
