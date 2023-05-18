package Abstraction;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a pawn in a game.
 */
public class Pawn {

	/**
	 * The current position of the pawn.
	 */
	private Position pos;

	/**
	 * The set of possible destinations for this pawn.
	 */
	private Set<Position> possibleDestination;

	/**
	 * The player number associated with the pawn.
	 */
	private Case playerNb;

	/**
	 * The finish line position(s) for this pawn.
	 */
	private Set<Position> finishLine;

	/**
	 * Creates a new pawn with the specified position and associated player.
	 *
	 * @param board  The board where the pawn is placed.
	 * @param pos    The current position of the pawn.
	 * @param player The player associated with the pawn.
	 */
	public Pawn(Board board, Position pos, Case player) {
		this.pos = pos;
		this.playerNb = player;
		this.possibleDestination = possibleMove(board, pos);
		this.finishLine();
	}

	/**
	 * Returns the current position of the pawn.
	 *
	 * @return The current position of the pawn.
	 */
	public Position getPos() {
		return pos;
	}

	/**
	 * Returns the player number associated with the pawn.
	 *
	 * @return The player number.
	 */
	public Case getPlayerNb() {
		return playerNb;
	}

	/**
	 * Returns the finish line position(s) for this pawn.
	 *
	 * @return The finish line position(s).
	 */
	public Set<Position> getFinishLine() {
		return finishLine;
	}

	/**
	 * Sets the finish line position(s) for the pawn.
	 *
	 * @param finishLine The new finish line position(s).
	 */
	public void setFinishLine(Set<Position> finishLine) {
		this.finishLine = finishLine;
	}

	/**
	 * Sets the position of the pawn.
	 *
	 * @param pos The new position of the pawn.
	 */
	public void setPos(Position pos) {
		this.pos = pos;
	}

	/**
	 * Returns the possible destinations for this pawn.
	 *
	 * @return The set of possible destinations.
	 */
	public Set<Position> getPossibleDestination() {
		return this.possibleDestination;
	}
	
	/**
	 * Sets the possible destinations for the Pawn.
	 *
	 * @param possibleDestination A set of Positions representing the possible destinations for the Pawn.
	 */
	public void setPossibleDestination(Set<Position> possibleDestination) {
		this.possibleDestination = possibleDestination;
	}

	/**
	 * Moves the pawn towards the top, if possible. Checks the potential wall and
	 * the next case in the board. Adds the new position to possible destinations if
	 * it is empty or triggers a special move.
	 *
	 * @param board          The game board
	 * @param possibleDestination	The set of Positions representing the possible destinations for the Pawn.
	 * @param pos            The current position of the pawn
	 * @param canSpecialMove Indicates if it can do a special move or not
	 */
	public void topMove(Board board, Set<Position> possibleDestination, Position pos, Boolean canSpecialMove) {
		if (board.getBoard()[pos.getX() - 1][pos.getY()] == Case.POTENTIALWALL) {
			if (board.getBoard()[pos.getX() - 2][pos.getY()] == Case.EMPTY) {
				possibleDestination.add(new Position(pos.getX() - 2, pos.getY()));
			} else {
				if (canSpecialMove) {
					specialMove(board, possibleDestination, new Position(pos.getX() - 2, pos.getY()), Movement.TOP);
				}
			}
		}
	}

	/**
	 * Moves the pawn towards the right, if possible. Checks the potential wall and
	 * the next case in the board. Adds the new position to possible destinations if
	 * it is empty or triggers a special move.
	 *
	 * @param board          The game board
	 * @param possibleDestination	The set of Positions representing the possible destinations for the Pawn.
	 * @param pos            The current position of the pawn
	 * @param canSpecialMove Indicates if it can do a special move or not
	 */
	public void rightMove(Board board, Set<Position> possibleDestination, Position pos, Boolean canSpecialMove) {
		if (board.getBoard()[pos.getX()][pos.getY() + 1] == Case.POTENTIALWALL) {
			if (board.getBoard()[pos.getX()][pos.getY() + 2] == Case.EMPTY) {
				possibleDestination.add(new Position(pos.getX(), pos.getY() + 2));
			} else {
				if (canSpecialMove) {
					specialMove(board, possibleDestination, new Position(pos.getX(), pos.getY() + 2), Movement.RIGHT);
				}
			}
		}
	}

	/**
	 * Moves the pawn towards the bottom, if possible. Checks the potential wall and
	 * the next case in the board. Adds the new position to possible destinations if
	 * it is empty or triggers a special move.
	 *
	 * @param board          The game board
	 * @param possibleDestination	The set of Positions representing the possible destinations for the Pawn.
	 * @param pos            The current position of the pawn
	 * @param canSpecialMove Indicates if it can do a special move or not
	 */
	public void botMove(Board board, Set<Position> possibleDestination, Position pos, Boolean canSpecialMove) {
		if (board.getBoard()[pos.getX() + 1][pos.getY()] == Case.POTENTIALWALL) {
			if (board.getBoard()[pos.getX() + 2][pos.getY()] == Case.EMPTY) {
				possibleDestination.add(new Position(pos.getX() + 2, pos.getY()));
			} else {
				if (canSpecialMove) {
					specialMove(board, possibleDestination, new Position(pos.getX() + 2, pos.getY()), Movement.BOT);
				}
			}
		}
	}

	/**
	 * Moves the pawn towards the left, if possible. Checks the potential wall and
	 * the next case in the board. Adds the new position to possible destinations if
	 * it is empty or triggers a special move.
	 *
	 * @param board          The game board
	 * @param possibleDestination	The set of Positions representing the possible destinations for the Pawn.
	 * @param pos            The current position of the pawn
	 * @param canSpecialMove Indicates if it can do a special move or not
	 */
	public void leftMove(Board board, Set<Position> possibleDestination, Position pos, Boolean canSpecialMove) {
		if (board.getBoard()[pos.getX()][pos.getY() - 1] == Case.POTENTIALWALL) {
			if (board.getBoard()[pos.getX()][pos.getY() - 2] == Case.EMPTY) {
				possibleDestination.add(new Position(pos.getX(), pos.getY() - 2));
			} else {
				if (canSpecialMove) {
					specialMove(board, possibleDestination, new Position(pos.getX(), pos.getY() - 2), Movement.LEFT);
				}
			}
		}
	}

	/**
	 * Performs a special move depending on the specified movement direction. Checks
	 * the potential wall and triggers appropriate move or other moves.
	 *
	 * @param board The game board
	 * @param possibleDestination	The set of Positions representing the possible destinations for the Pawn.
	 * @param pos   The current position of the pawn
	 * @param m     The specified movement direction
	 */
	public void specialMove(Board board, Set<Position> possibleDestination, Position pos, Movement m) {
		switch (m) {
		case TOP:
			if (board.getBoard()[pos.getX() - 1][pos.getY()] == Case.POTENTIALWALL) {
				topMove(board, possibleDestination, pos, false);
			} else {
				leftMove(board, possibleDestination, pos, false);
				rightMove(board, possibleDestination, pos, false);
			}
			break;
		case RIGHT:
			if (board.getBoard()[pos.getX()][pos.getY() + 1] == Case.POTENTIALWALL) {
				rightMove(board, possibleDestination, pos, false);
			} else {
				topMove(board, possibleDestination, pos, false);
				botMove(board, possibleDestination, pos, false);
			}
			break;
		case BOT:
			if (board.getBoard()[pos.getX() + 1][pos.getY()] == Case.POTENTIALWALL) {
				botMove(board, possibleDestination, pos, false);
			} else {
				leftMove(board, possibleDestination, pos, false);
				rightMove(board, possibleDestination, pos, false);
			}
			break;
		case LEFT:
			if (board.getBoard()[pos.getX()][pos.getY() - 1] == Case.POTENTIALWALL) {
				leftMove(board, possibleDestination, pos, false);
			} else {
				topMove(board, possibleDestination, pos, false);
				botMove(board, possibleDestination, pos, false);
			}
			break;
		default:
			break;
		}

	}

	/**
	 * Calculates the possible moves for this pawn given a specific board and
	 * position.
	 *
	 * @param board The current state of the game board.
	 * @param pos   The position from which to calculate possible moves.
	 * @return The set of Positions representing the possible destinations for the Pawn.
	 */
	public Set<Position> possibleMove(Board board, Position pos) {
		Set<Position> possibleDestination = new HashSet<Position>();
		topMove(board, possibleDestination, pos, true);
		rightMove(board, possibleDestination, pos, true);
		botMove(board, possibleDestination, pos, true);
		leftMove(board, possibleDestination, pos, true);
		return possibleDestination;
	}

	/**
	 * Determines the finish line for this pawn based on the associated player.
	 */
	public void finishLine() {
		this.setFinishLine(new HashSet<Position>());
		switch (this.playerNb) {
		case PLAYER1:
			for (int j = 1; j < Board.SIZE; j += 2) {
				this.getFinishLine().add(new Position(1, j));
			}
			break;
		case PLAYER2:
			for (int j = 1; j < Board.SIZE; j += 2) {
				this.getFinishLine().add(new Position(Board.SIZE - 2, j));
			}
			break;
		case PLAYER3:
			for (int i = 1; i < Board.SIZE; i += 2) {
				this.getFinishLine().add(new Position(i, Board.SIZE - 2));
			}
			break;
		case PLAYER4:
			for (int i = 1; i < Board.SIZE; i += 2) {
				this.getFinishLine().add(new Position(i, 1));
			}
			break;
		default:
			break;
		}
	}

	/**
	 * Determines if this pawn is a winner based on its current position.
	 *
	 * @return true if the pawn's current position is part of the finish line, false otherwise.
	 */
	public Boolean isWinner() {
		if (this.getFinishLine().contains(this.getPos())) {
			return true;
		}
		return false;
	}
}