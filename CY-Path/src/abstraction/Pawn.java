package abstraction;

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
	 * The last position of the pawn.
	 */
	private Position lastPos;

	/**
	 * The set of possible moves for this pawn.
	 */
	private Set<Position> possibleMove;

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
		this.lastPos = pos;
		this.playerNb = player;
		this.possibleMove = possibleMove(board, pos);
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
	 * Returns the last position of the pawn.
	 *
	 * @return The last position of the pawn.
	 */
	public Position getLastPos() {
		return lastPos;
	}
	
	/**
	 * Sets the last position of the pawn.
	 *
	 * @param lastPos The last position of the pawn.
	 */
	public void setLastPos(Position lastPos) {
		this.lastPos = lastPos;
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
	 * Returns the possible moves for this pawn.
	 *
	 * @return The set of possible moves.
	 */
	public Set<Position> getPossibleMove() {
		return this.possibleMove;
	}

	/**
	 * Sets the possible moves for the Pawn.
	 *
	 * @param possibleMove A set of Positions representing the possible moves
	 *                     for the Pawn.
	 */
	public void setPossibleMove(Set<Position> possibleMove) {
		this.possibleMove = possibleMove;
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
	 * Move the pawn in a given direction on the board.
	 * If the new position is valid and corresponds to a potential wall,
	 * the movement is performed by adding the new position to the set of possible moves.
	 * If the square is occupied and special movement is allowed, the specialMove method is called.
	 *
	 * @param board           The game board.
	 * @param possibleMove    The set of possible moves.
	 * @param pos             The current position of the pawn.
	 * @param canSpecialMove  Indicates whether special movement is allowed.
	 * @param offsetX         The horizontal offset for the movement.
	 * @param offsetY         The vertical offset for the movement.
	 */
	private void directionMove(Board board, Set<Position> possibleMove, Position pos, boolean canSpecialMove, int offsetX, int offsetY) {
        int newX = pos.getX() + offsetX;
        int newY = pos.getY() + offsetY;

        /* Verifies if the new position is on the grid */
        if (newX >= 1 && newX <= 17 && newY >= 1 && newY <= 17) {
            Position newPosition = new Position(newX, newY);
            if (board.getBoard()[newPosition.getX()-(offsetX / 2)][newPosition.getY()- (offsetY /2)] == Case.POTENTIALWALL) {
                    if (board.getBoard()[newPosition.getX()][newPosition.getY()] == Case.EMPTY) {
                        possibleMove.add(newPosition);
                    } else if (canSpecialMove) {
                        specialMove(board, possibleMove, newPosition, offsetX, offsetY);
                    }
            }
        }
    }
	
	/**
	 * Perform a special movement based on the given horizontal and vertical offsets.
	 * If a potential wall is present in the specified direction, the directionMove method is called
	 * to perform the movement in that direction. Otherwise, movements in the left and right directions
	 * are performed by calling the directionMove method.
	 *
	 * @param board           The game board.
	 * @param possibleMove    The set of possible moves.
	 * @param pos             The current position of the pawn.
	 * @param offsetX         The horizontal offset for the movement.
	 * @param offsetY         The vertical offset for the movement.
	 */
	private void specialMove(Board board, Set<Position> possibleMove, Position pos, int offsetX, int offsetY) {
		String combined = offsetX+"_"+offsetY;
		/* to use the switch depending on the two variables */
	    switch (combined) {
	        case "-2_0":
	            if (isPotentialWall(board, pos, -2, 0)) {
	                directionMove(board, possibleMove, pos, false,-2,0);
	            } else {
	            	 directionMove(board, possibleMove, pos, false,0,-2);
	            	 directionMove(board, possibleMove, pos, false,0,2);
	            }
	            break;
	        case "0_2":
	            if (isPotentialWall(board, pos, 0, 2)) {
	                directionMove(board, possibleMove, pos, false,0,2);
	            } else {
	            	 directionMove(board, possibleMove, pos, false,-2,0);
	            	 directionMove(board, possibleMove, pos, false,2,0);
	            }
	            break;
	        case "2_0":
	            if (isPotentialWall(board, pos, 2, 0)) {
	            	 directionMove(board, possibleMove, pos, false,2,0);
	            } else {
	            	directionMove(board, possibleMove, pos, false,0,-2);
	                directionMove(board, possibleMove, pos, false,0,2);
	            }
	            break;
	        case "0_-2":
	            if (isPotentialWall(board, pos, 0, -2)) {
	            	directionMove(board, possibleMove, pos, false,0,-2);
	            } else {
	            	 directionMove(board, possibleMove, pos, false,-2,0);
	            	 directionMove(board, possibleMove, pos, false,2,0);
	            }
	            break;
	        default:
	            break;
	    }
	}
	
	/**
	 * Checks if there is a potential wall at the specified position based on the given horizontal and vertical offsets.
	 *
	 * @param board     The game board.
	 * @param pos       The current position.
	 * @param offsetX   The horizontal offset from the current position.
	 * @param offsetY   The vertical offset from the current position.
	 * @return          True if there is a potential wall at the calculated position, false otherwise.
	 */
	private boolean isPotentialWall(Board board, Position pos, int offsetX, int offsetY) {
	    int newX = pos.getX() + offsetX;
	    int newY = pos.getY() + offsetY;

	    if (newX >= 1 && newX <= 17 && newY >= 1 && newY <= 17) {
	        return board.getBoard()[newX-(offsetX / 2)][newY-(offsetY / 2)] == Case.POTENTIALWALL;
	    }
	    return false;
	}

	
	/**
	 * Calculates the possible moves for this pawn given a specific board and
	 * position.
	 *
	 * @param board The current state of the game board.
	 * @param pos   The position from which to calculate possible moves.
	 * @return The set of Positions representing the possible moves for the
	 *         Pawn.
	 */
	 public Set<Position> possibleMove(Board board, Position pos) {
        Set<Position> possibleMove = new HashSet<>();
        directionMove(board, possibleMove, pos, true, -2, 0); // topMove
        directionMove(board, possibleMove, pos, true, 2, 0); // botMove
        directionMove(board, possibleMove, pos, true, 0, 2); // rightMove
        directionMove(board, possibleMove, pos, true, 0, -2); // leftMove
        return possibleMove;
    }

	/**
	 * Update the player's position.
	 *
	 * @param board The current state of the game board.
	 * @param pos   The new player's position.
	 */
	public void updatePos(Board board, Position pos) {
		board.getBoard()[this.getPos().getX()][this.getPos().getY()] = Case.EMPTY;
		this.setPos(pos);
		board.getBoard()[this.getPos().getX()][this.getPos().getY()] = this.getPlayerNb();
		this.setPossibleMove(this.possibleMove(board, this.getPos()));
	}

	/**
	 * If possible, moves a player to a new position on the board.
	 *
	 * @param board The game board.
	 * @param pos   The new position for the player.
	 * @return true if the move has been made, false otherwise.
	 */
	public boolean move(Board board, Position pos) {
		if (this.getPossibleMove().contains(pos)) {
			this.updatePos(board, pos);
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Resets the current player's move on the game board, restoring their position to the last recorded position.
	 *
	 * @param board The game board.
	 */
	public void resetMove(Board board) {
		this.updatePos(board, this.getLastPos());
	}

	/**
	 * Determines if this pawn is a winner based on its current position.
	 *
	 * @return true if the pawn's current position is part of the finish line, false
	 *         otherwise.
	 */
	public Boolean isWinner() {
		if (this.getFinishLine().contains(this.getPos())) {
			return true;
		}
		return false;
	}
}