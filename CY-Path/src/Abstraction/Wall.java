package Abstraction;


public class Wall {

	/* Attributes */
	// private final int HEIGHT = 2;
	private Orientation orientation;
	private Position position;

	/* Constructor */
	public Wall(Orientation orientation, Position position) {
		this.orientation = orientation;
		this.position = position;
	}

	/* Getters and setters */
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
		if (this.getPosition().getX() == 0 || this.getPosition().getX() == 18 || this.getPosition().getX() == 0
				|| this.getPosition().getX() == 18)
			return false;
		return true;
	}
	
	/* A modifier pour prendre le board en parametre */
	public boolean hasWall(Board board) {
		if (this.getOrientation() == Orientation.VERTICAL) {
			if (board.getBoard()[this.getPosition().getX()][this.getPosition().getY() - 1].getValue() == 5
					&& board.getBoard()[this.getPosition().getX()][this.getPosition().getY() + 1].getValue() == 5) {
				return false;
			}
		} else if (this.getOrientation() == Orientation.HORIZONTAL) {
			if (board.getBoard()[this.getPosition().getX() - 1][this.getPosition().getY()].getValue() == 6
					&& board.getBoard()[this.getPosition().getX() + 1][this.getPosition().getY()].getValue() == 6) {
				return false;
			}
		}
		return true;
	}
	
	private boolean DFS() {
		return true;
	}
	
	/* modifier exception : à mettre dans les méthodes d'au dessus */
	public boolean verifyWall(Board board) throws IncorrectWallException {
		try {
			if (this.hasWall(board) || this.outOfBorderWidth() || (DFS() == false)) {
				throw new IncorrectWallException("Error : Incorrect Wall");
			}
		}
		catch (IncorrectWallException e) {
			System.out.println("Vous ne pouvez pas placer de murs ici, il y'en a dejà un");
			return false;
		}
		return true;
	}
	public void createWall (Board board) {
			
			int x= this.getPosition().getX();
			int y= this.getPosition().getY();
					
			try {
				if(verifyWall(board)) {
					if (this.getOrientation()== Orientation.HORIZONTAL) {
					    board.getBoard()[x][y-1]=Case.WALL;
					    board.getBoard()[x][y+1]=Case.WALL;
						
					}
					else if (this.getOrientation()== Orientation.VERTICAL) {
						board.getBoard()[x-1][y]=Case.WALL;
					    board.getBoard()[x+1][y]=Case.WALL;
					}
					
				}
				
			}
			catch (IncorrectWallException e) {
				System.out.println("Vous ne pouvez pas placer de mur à cette emplacement");
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
}
	public static void main(String[] args) {
		Board p = new Board(4);
		Position po =new Position(2,2);
		Wall w = new Wall(Orientation.HORIZONTAL, po);
		w.createWall(p);
		p.show();
	}
	
}
