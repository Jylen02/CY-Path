package abstraction;

/**
 * Represents a wall in a game.
 */
public class Wall {

	// private final int HEIGHT = 2;
	/**
	 * The orientation of the wall.
	 */
	private Orientation orientation;

	/**
	 * The current position of the middle of the wall.
	 */
	private Position position;

	/**
	 * Constructs a new Wall object with the specified orientation and position.
	 *
	 * @param orientation The orientation of the wall (horizontal or vertical).
	 * @param position    The position of the wall.
	 */
	public Wall(Orientation orientation, Position position) {
		this.orientation = orientation;
		this.position = position;
	}

	/**
	 * Returns the orientation of the wall.
	 *
	 * @return The orientation of the wall.
	 */
	public Orientation getOrientation() {
		return orientation;
	}

	/**
	 * Sets the orientation of the wall.
	 *
	 * @param orientation The new orientation of the wall.
	 */
	public void setOrientation(Orientation orientation) {
		this.orientation = orientation;
	}

	/**
	 * Returns the position of the wall.
	 *
	 * @return The position of the wall.
	 */
	public Position getPosition() {
		return position;
	}

	/**
	 * Sets the position of the wall.
	 *
	 * @param position The new position of the wall.
	 */
	public void setPosition(Position position) {
		this.position = position;
	}

	/**
	 * Checks if the center of the wall is at the edge of the board, indicating that
	 * the wall is out of bounds.
	 *
	 * @return true if the wall is out of bounds, false otherwise.
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
	 * @param board The game board.
	 * @return true if there is a wall conflict, false otherwise.
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
	 * @param board The game board.
	 * @return true if the wall can be placed, false otherwise.
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
	 * @param board The game board.
	 * @param type  The case type to update the wall to (WALL or NULL).
	 */
	public void updateWall(Board board, Case type) {
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

		} else if (this.getOrientation() == Orientation.VERTICAL) {
			// Update the wall state vertically
			board.getBoard()[x - 1][y] = type;
			if (type == Case.WALL) {
				board.getBoard()[x][y] = type;
			} else {
				board.getBoard()[x][y] = Case.NULL;
			}
			board.getBoard()[x + 1][y] = type;
		}
	}

	/**
	 * Verifies if the placed wall blocks a player and removes it if necessary.
	 * 
	 * @param board   The game board.
	 * @param players Array of players in the game.
	 * @param turn    The current turn number.
	 * @return true if the wall blocked a player, false otherwise.
	 */
	public boolean wallError(Board board, Integer turn) {
		for (int i = 0; i < board.getPlayers().length; i++) {
			board.getPlayers()[i].getPawn()
					.setPossibleMove(board.getPlayers()[i].getPawn().possibleMove(board, board.getPlayers()[i].getPawn().getPos()));
		}
		if (!board.isWinnableForAll()) {
			this.updateWall(board, Case.POTENTIALWALL);
			for (int i = 0; i < board.getPlayers().length; i++) {
				board.getPlayers()[i].getPawn()
						.setPossibleMove(board.getPlayers()[i].getPawn().possibleMove(board, board.getPlayers()[i].getPawn().getPos()));
			}
			return true;
		}
		return false;
	}

	/**
	 * Removes the last wall placed.
	 *
	 * @param board The game board.
	 */
	public static void removeLastWall(Board board) {
		board.getLastWall().updateWall(board, Case.POTENTIALWALL);
	}

	/**
	 * Creates the wall and verifies if it blocks a player's winning path using
	 * depth-first search.
	 *
	 * @param board       The game board.
	 * @param turn        The current turn number.
	 * @param orientation The orientation of the wall (horizontal or vertical).
	 * @param pos         The position of the wall.
	 * @return true if the wall has been created, false otherwise.
	 */
	public static boolean createWall(Board board, Integer turn, Orientation orientation,
			Position pos) {
		Wall wall = new Wall(orientation, pos);
		if (wall.verifyWall(board)) {
			wall.updateWall(board, Case.WALL);
			if (wall.wallError(board, turn)) {
				return false;
			}
			board.setLastWall(wall);
			return true;
		} else {
			return false;
		}
	}
}
