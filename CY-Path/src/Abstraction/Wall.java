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
		if (this.getPosition().getX() <= 0 || this.getPosition().getY() >= 18 || this.getPosition().getX() >= 18
				|| this.getPosition().getY() <= 0) {
			return true;
		}
		return false;
	}

	/* A modifier pour prendre le board en parametre */
	public boolean hasWall(Board board) {
		if (board.getBoard()[this.getPosition().getX()][this.getPosition().getY()] == Case.NULL) {
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
		}
		return true;
	}

	public boolean verifyWall(Board board) {
		if (this.outOfBorderWidth() || this.hasWall(board)) {
			return false;
		}
		return true;
	}

	public void placeWall(Orientation orientation, Board board, Case type, int co) {
		int x = this.getPosition().getX();
		int y = this.getPosition().getY();
		if (orientation == Orientation.HORIZONTAL) {
			board.getBoard()[x][y - 1] = type;
			board.getBoard()[x][y] = type;
			board.getBoard()[x][y + 1] = type;
			int counter = board.getWallCount() + co;
			board.setWallCount(counter);

		} else if (orientation == Orientation.VERTICAL) {
			board.getBoard()[x - 1][y] = type;
			board.getBoard()[x][y] = type;
			board.getBoard()[x + 1][y] = type;
			int counter = board.getWallCount() + co;
			board.setWallCount(counter);
		}
	}

	public boolean createWall(Board board) {

		if (verifyWall(board)) {
			placeWall(this.getOrientation(), board, Case.WALL, 1);
			//DFS fait dans le main et non ici
//			if ((new Dfs(board)).dfs(4, 8) == false) {
//				placeWall(this.getOrientation(), board, Case.POTENTIALWALL, (-1));
//				System.out.println("Ce mur bloque un joueur !");
//				return false;
//			}
			return true;
		} else {
			System.out.println("Vous ne pouvez pas placer de mur à cette emplacement");
			return false;
		}
	}
}
