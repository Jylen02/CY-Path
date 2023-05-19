package abstraction;

public class Wall {

	// private final int HEIGHT = 2;
	/**
	 * The orientation of the wall
	 */
	private Orientation orientation;
	
	/**
	 * The current position of the middle of the wall
	 */
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
	 */
	public boolean createWall(Board board, Player[] players, Integer turn) {
		if (verifyWall(board)) {
			updateWall(board, Case.WALL, 1);
			if (wallError(board, players, turn)) {
				return false;
			};
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Handles the possible errors that could happen when a player tries to place a
	 * wall.
	 * @param board   The game board.
	 * @param players Array of players in the game.
	 * @param turn    The current turn number.
	 */
	public boolean wallError(Board board, Player[] players, Integer turn) {
		// Check if the wall can't be instaured, restart the turn
		/*if (!this.createWall(board)) {
			System.out.println(this.getPosition());
			System.out.println("Error : Can't put a wall to these coordinates.");
			System.out.println(board);
			board.roundOfPlay(players, turn);
		} // Otherwise, check if all pawn can still reach the goal, if not, remove the
			// wall, then restart the turn
		else {*/
			for (int i = 0; i < players.length; i++) {
				players[i].getPawn().setPossibleDestination(
						players[i].getPawn().possibleMove(board, players[i].getPawn().getPos()));
			}
			if (!board.isWinnableForAll(players)) {
				this.updateWall(board, Case.POTENTIALWALL, -1);
				for (int i = 0; i < players.length; i++) {
					players[i].getPawn().setPossibleDestination(
							players[i].getPawn().possibleMove(board, players[i].getPawn().getPos()));
				}
				//System.out.println("Error : This wall blocks a player.");
				//System.out.println(board);
				board.roundOfPlay(players, turn);
				return true;
			}
			
			//A supprimer
			System.out.println(board);
			return false;
		//}
	}
}
