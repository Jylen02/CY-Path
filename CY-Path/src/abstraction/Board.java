package abstraction;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents the game board for the game. It contains the board
 * layout, player and wall count, and various methods for manipulating the
 * board.
 */
public class Board {

	/**
	 * The representation of the board.
	 */
	private Case[][] board;

	/**
	 * The number of players playing the game.
	 */
	private int playerNumber;

	/**
	 * The last wall placed on the board.
	 */
	private Wall lastWall;
	
	/**
	 * The size of the board.
	 */
	public static final int SIZE = 19;

	/**
	 * The number of walls that can be placed on the board in total.
	 */
	public static final int MAXWALLCOUNT = 20;

	/**
	 * Constructs a Board object with the specified player number.
	 *
	 * @param playerNumber The number of players in the game between 2 and 4.
	 */
	public Board(int playerNumber) {
		this.playerNumber = playerNumber;
		initializeBoard();
	}

	public Case[][] getBoard() {
		return board;
	}

	/**
	 * Sets the board layout.
	 *
	 * @param board The new board layout.
	 */
	public void setBoard(Case[][] board) {
		this.board = board;
	}

	/**
	 * Returns the number of players in the game.
	 *
	 * @return The player number.
	 */
	public int getPlayerNumber() {
		return playerNumber;
	}

	/**
	 * Sets the number of players in the game.
	 *
	 * @param playerNumber The new player number.
	 */
	public void setPlayerNumber(int playerNumber) {
		this.playerNumber = playerNumber;
	}

	public Wall getLastWall() {
		return lastWall;
	}

	public void setLastWall(Wall lastWall) {
		this.lastWall = lastWall;
	}

	/**
	 * Initializes the board with the initial layout and player placements. All the
	 * different types filled in the array are present in the "Case" enumeration.
	 */
	public void initializeBoard() {
		this.setBoard(new Case[SIZE][SIZE]);
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j += 2) {
				// First line's right corner & Last line's right corner
				if ((i == 0 && j == SIZE - 1) || (i == SIZE - 1 && j == SIZE - 1)) {
					getBoard()[i][j] = Case.NULL;
				}
				// First line & Last line
				else if (i == 0 || i == SIZE - 1) {
					getBoard()[i][j] = Case.NULL;
					getBoard()[i][j + 1] = Case.BORDER;
				}
				// Odd line
				else if (i % 2 == 1) {
					// First column
					if (j == 0) {
						getBoard()[i][j] = Case.BORDER;
						getBoard()[i][j + 1] = Case.EMPTY;
					}
					// Last column
					else if (j == SIZE - 1) {
						getBoard()[i][j] = Case.BORDER;
					}
					// Other column
					else {
						getBoard()[i][j] = Case.POTENTIALWALL;
						getBoard()[i][j + 1] = Case.EMPTY;
					}
				}
				// Even line
				else if (i % 2 == 0) {
					// First column
					if (j == 0) {
						getBoard()[i][j] = Case.NULL;
						getBoard()[i][j + 1] = Case.POTENTIALWALL;
					}
					// Last column
					else if (j == SIZE - 1) {
						getBoard()[i][j] = Case.NULL;
					}
					// Other column
					else {
						getBoard()[i][j] = Case.NULL;
						getBoard()[i][j + 1] = Case.POTENTIALWALL;
					}
				}
			}
		}
		if (getPlayerNumber() >= 2) {
			// Set the two first player's pawn placement
			getBoard()[SIZE - 2][SIZE / 2] = Case.PLAYER1;
			getBoard()[1][SIZE / 2] = Case.PLAYER2;
			if (getPlayerNumber() == 4) {
				// Set the two others player's pawn placement
				getBoard()[SIZE / 2][1] = Case.PLAYER3;
				getBoard()[SIZE / 2][SIZE - 2] = Case.PLAYER4;
			}
		}
	}

	/**
	 * Return the current state of the board. To make this possible, each type of
	 * the "Case" enumeration is replaced by a specific display.
	 *
	 * @return A string representation of the current board.
	 */

	@Override
	public String toString() {
		String res = "   ";
		// First line of the column's coordinates
		for (int i = 0; i < SIZE; i++) {
			if (i >= 10) {
				res += "1 ";
			} else {
				res += "  ";
			}
		}
		res += "\n";
		// Second line of the column's coordinates
		res += "   ";
		for (int i = 0; i < SIZE; i++) {
			res += i % 10 + " ";
		}
		res += "\n";
		for (int i = 0; i < SIZE; i++) {
			// Column of the row's coordinates
			if (i < 10) {
				res += " ";
			}
			res += i + " ";
			for (int j = 0; j < SIZE; j++) {
				// If the case is an intersection : put a "+"
				if (this.getBoard()[i][j] == Case.NULL) {
					res += "+ ";
				} // If the case is a part of the grid, put a vertical "|" or horizontal "-" bar
				else if (this.getBoard()[i][j] == Case.BORDER || this.getBoard()[i][j] == Case.POTENTIALWALL) {
					if (j % 2 == 0) {
						res += "| ";
					} else {
						res += "- ";
					}
				} // If the case is a wall : put a "/"
				else if (this.getBoard()[i][j] == Case.WALL) {
					res += "/ ";
				} // If the case is empty : put a " "
				else if (this.getBoard()[i][j] == Case.EMPTY) {
					res += "  ";
				} // If the case is a player, put its value
				else {
					res += this.getBoard()[i][j].getValue() + " ";
				}
			}
			res += "\n";
		}
		return res;
	}

	/**
	 * Checks if the current board configuration allows all players to reach their
	 * respective goals.
	 * 
	 * @param players Array of players in the game.
	 * @return true if all players can reach their goals, false otherwise.
	 */
	public boolean isWinnableForAll(Player[] players) {
		int i = 0;
		while (i < players.length && this.isWinnable(players[i].getPawn())) {
			i++;
		}
		return i == players.length;
	}

	/**
	 * Check if a game board is winnable for a given player.
	 * 
	 * @param player The Pawn of the player to check.
	 * @return true if the game is winnable for the player, false otherwise.
	 */
	public boolean isWinnable(Pawn player) {
		Set<Position> marking = new HashSet<Position>();
		marking.add(player.getPos());
		for (Position pos : player.getPossibleDestination()) {
			marking = this.dfs(pos, player, marking, player.getPossibleDestination());
		}
		for (Position pos : player.getFinishLine()) {
			if (marking.contains(pos)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Performs a depth-first search (DFS) on the game board from a given position.
	 * 
	 * @param pos                 The position from which to start the DFS.
	 * @param player              The player for whom to perform the DFS.
	 * @param marking             A set of positions marking the nodes visited
	 *                            during the DFS.
	 * @param possibleDestination The set of Positions representing the possible
	 *                            destinations for the Pawn.
	 * @return The updated marking set after performing the DFS.
	 */
	public Set<Position> dfs(Position pos, Pawn player, Set<Position> marking, Set<Position> possibleDestination) {
		if (!marking.contains(pos)) {
			marking.add(pos);
			possibleDestination = player.possibleMove(this, pos);
			for (Position pos1 : possibleDestination) {
				marking = this.dfs(pos1, player, marking, possibleDestination);
			}
		}
		return marking;
	}
}