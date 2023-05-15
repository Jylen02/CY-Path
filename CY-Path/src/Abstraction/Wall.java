package Abstraction;

import java.util.HashMap;
import java.util.Map;

public class Wall {
	
	/* Attributes */
	private final int HEIGHT = 2;
	private Orientation orientation;
	private Position position;
	
	/* Constructor */
	public Wall(Orientation orientation, Position position) {
		this.orientation=orientation;
		this.position=position;
	}
	
	/*Getters and setters */
	public Orientation getOrientation() {
		return orientation;
	}
	public void setOrientation(Orientation orientation) {
		this.orientation = orientation;
	}
	public Position getPosition() {
		return position;
	}
	public void setPosition(Position position) {
		this.position = position;
	}
	
	/* Useful methods */
	public boolean outOfBorderWidth() {
		if(this.getPosition().getX() == 0 || this.getPosition().getX() == 9 || this.getPosition().getX() == 0 || this.getPosition().getX() == 9)
			return false;
		return true;
	}
	
	public boolean hasWall(){
		return false;
	}
	
	public boolean VerifyWall() throws IncorrectWallException {
		try {
			if(this.hasWall() || this.outOfBorderWidth() || (DFS() == false)) {
				throw new IncorrectWallException();
			}

			else {
				/* Quand cela marche */
				/* Map intersection */
				HashMap<Intersection,Boolean> wallIntersection;
				if(this.getOrientation() == orientation.HORIZONTAL) {
					wallIntersection.replace(new Intersection(new Position(this.getPosition().getX()-1, this.getPosition().getY()), this.getPosition()), false);
					wallIntersection.replace(new Intersection(this.getPosition(), new Position(this.getPosition().getX()+1, this.getPosition().getY())), false);
				}
				else if(this.getOrientation() == orientation.VERTICAL) {
					wallIntersection.replace(new Intersection(new Position(this.getPosition().getX(), this.getPosition().getY()-1), this.getPosition()), false);
					wallIntersection.replace(new Intersection(this.getPosition(), new Position(this.getPosition().getX(), this.getPosition().getY()+1)), false);
				}
				else {
					System.out.println("PROBLEME ENUMERATION");
				}
			}
			
			
		}
		catch (IncorrectWallException e) {
			System.out.println("Vous ne pouvez pas placer de murs ici, il y'en a dejà un");
		}
		
	}
}
