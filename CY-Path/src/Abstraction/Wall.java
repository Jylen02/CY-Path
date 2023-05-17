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
		if (this.getPosition().getX() == 0 || this.getPosition().getY() == 18 || this.getPosition().getX() == 18
				|| this.getPosition().getY() == 0) {
			return true;
		}
		return false;
	}

	/* A modifier pour prendre le board en parametre */
	public boolean hasWall(Board board) {
		if (this.getOrientation() == Orientation.VERTICAL) {
			if (board.getBoard()[this.getPosition().getX() - 1][this.getPosition().getY()] == Case.POTENTIALWALL
					&& board.getBoard()[this.getPosition().getX() + 1][this.getPosition()
							.getY()] == Case.POTENTIALWALL) {
				return false;
			}
		} else if (this.getOrientation() == Orientation.HORIZONTAL) {
			if (board.getBoard()[this.getPosition().getX()][this.getPosition().getY() - 1] == Case.POTENTIALWALL
					&& board.getBoard()[this.getPosition().getX()][this.getPosition().getY()
							+ 1] == Case.POTENTIALWALL) {
				return false;
			}
		}
		return true;
	}

	public boolean verifyWall(Board board) {
		if (this.hasWall(board) || this.outOfBorderWidth()) {
			System.out.println("Vous ne pouvez pas placer de murs ici");
			return false;
		}
		return true;
	}
	
	public void placeWall (Orientation orientation, Board board, Case type, int co) {
		int x = this.getPosition().getX();
		int y = this.getPosition().getY();
		if (orientation == Orientation.HORIZONTAL) {
			board.getBoard()[x][y - 1] = type;
			board.getBoard()[x][y + 1] = type;
			int counter = board.getWallCount() +co;
			board.setWallCount(counter);

		} 
		else if (orientation == Orientation.VERTICAL) {
			board.getBoard()[x - 1][y] = type;
			board.getBoard()[x + 1][y] = type;
			int counter = board.getWallCount() + co;
			board.setWallCount(counter);
		}
	}

	public void createWall(Board board) throws IncorrectWallException {

		if (verifyWall(board)) {
			placeWall(this.getOrientation(),board,Case.WALL,1);
			if ((new Dfs(board)).dfs(4, 8) == false) {
				placeWall(this.getOrientation(),board,Case.POTENTIALWALL,(-1));
				System.out.println("Ce mur bloque un joueur !");
			}
		}
		else {
			System.out.println("Vous ne pouvez pas placer de mur à cette emplacement");
		}
	}

	public static void main(String[] args) {
		Board p = new Board(4);
		Position po = new Position(2, 8);
		Wall w = new Wall(Orientation.VERTICAL, po);
		w.createWall(p);
		Position po2 = new Position(2, 10);
		Wall w2 = new Wall(Orientation.VERTICAL, po2);
		w2.createWall(p);
		Position po3 = new Position(4, 10);
		Wall w3 = new Wall(Orientation.HORIZONTAL, po3);
		w3.createWall(p);
		p.show();
	}

}
