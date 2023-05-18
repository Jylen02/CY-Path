package Abstraction;

public class Wall {

	/* Attributes */
	// private final int HEIGHT = 2;
	private Orientation orientation;
	private Position position;

	/**
	 * Constructs a new Wall object with the specified orientation and position.
	 *
	 * @param orientation the orientation of the wall (horizontal or vertical)
	 * @param position    the position of the wall
	 */
	public Wall(Orientation orientation, Position position) {
		this.orientation = orientation;
		this.position = position;
	}

	/**
	 * Returns the orientation of the wall.
	 *
	 * @return the orientation of the wall
	 */
	public Orientation getOrientation() {
		return orientation;
	}

	/**
	 * Sets the orientation of the wall.
	 *
	 * @param orientation the new orientation of the wall
	 */
	public void setOrientation(Orientation orientation) {
		this.orientation = orientation;
	}

	/**
	 * Returns the position of the wall.
	 *
	 * @return the position of the wall
	 */
	public Position getPosition() {
		return position;
	}

	/**
	 * Sets the position of the wall.
	 *
	 * @param position the new position of the wall
	 */
	public void setPosition(Position position) {
		this.position = position;
	}

	/**
	 * Checks if the center of the wall is at the edge of the board, indicating that
	 * the wall is out of bounds.
	 *
	 * @return true if the wall is out of bounds, false otherwise
	 */
	public boolean outOfBorderWidth() {
		if (this.getPosition().getX() <= 0 || this.getPosition().getY() >= 18 || this.getPosition().getX() >= 18
				|| this.getPosition().getY() <= 0) {
			return true;
		}
		return false;
	}

	/**
	 * Verifies if there is an existing wall that will conflict with the new wall on
	 * the given board.
	 *
	 * @param board the game board
	 * @return true if there is a wall conflict, false otherwise
	 */
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

	/**
	 * Verifies if the wall can be placed on the given board by checking for
	 * conflicts and out of bounds.
	 *
	 * @param board the game board
	 * @return true if the wall can be placed, false otherwise
	 */
	public boolean verifyWall(Board board) {
		if (this.outOfBorderWidth() || this.hasWall(board)) {
			return false;
		}
		return true;
	}

	/**
	 * Updates the state of the wall on the game board. Places or removes the wall
	 * from the board based on the given case type.
	 *
	 * @param board the game board
	 * @param type  the case type to update the wall to (WALL or NULL)
	 * @param co    the counter value for the wall count
	 */
	public void updateWall(Board board, Case type, int co) {
		int x = this.getPosition().getX();
		int y = this.getPosition().getY();

		if (this.getOrientation() == Orientation.HORIZONTAL) {
			// Update the wall state horizontally
			board.getBoard()[x][y - 1] = type;
			if (type == Case.WALL) {
				board.getBoard()[x][y] = type;
			} else {
				board.getBoard()[x][y] = Case.NULL;
			}
			board.getBoard()[x][y + 1] = type;

			// Update the wall count
			int counter = board.getWallCount() + co;
			board.setWallCount(counter);
		} else if (this.getOrientation() == Orientation.VERTICAL) {
			// Update the wall state vertically
			board.getBoard()[x - 1][y] = type;
			if (type == Case.WALL) {
				board.getBoard()[x][y] = type;
			} else {
				board.getBoard()[x][y] = Case.NULL;
			}
			board.getBoard()[x + 1][y] = type;

			// Update the wall count
			int counter = board.getWallCount() + co;
			board.setWallCount(counter);
		}
	}

	/**
	 * Creates the wall and verifies if it blocks a player's winning path using
	 * depth-first search.
	 *
	 * @param board the game board
	 * @return true if the wall can be created, false otherwise
	 * @throws IncorrectWallException if the wall placement is incorrect
	 */
	public boolean createWall(Board board) {
		if (verifyWall(board)) {
			updateWall(board, Case.WALL, 1);
			return true;
		} else {
			return false;
		}
	}
}
