package abstraction;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents the game board for the game. It contains the board layout, player
 * and wall count, and various methods for manipulating the board.
 */
public class Board implements Serializable {

	/**
	 * The representation of the board.
	 */
	private Case[][] board;

	/**
	 * The number of players playing the game.
	 */
	private int playerNumber;

	/**
	 * The information of each players.
	 */
	private Player[] players;

	/**
	 * The current turn of the game.
	 */
	private int currentTurn;

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
	 * The starting position of each player.
	 */
	public static final Position[] STARTINGPOSITIONPLAYERS = { new Position(Board.SIZE - 2, Board.SIZE / 2),
			new Position(1, Board.SIZE / 2), new Position(Board.SIZE / 2, 1),
			new Position(Board.SIZE / 2, Board.SIZE - 2) };

	/**
	 * Constructs a Board object with the specified player number.
	 *
	 * @param playerNumber The number of players in the game between 2 and 4.
	 */
	public Board(int playerNumber) {
		this.playerNumber = playerNumber;
		this.currentTurn = 0;
		this.players = new Player[playerNumber];
		initializeBoard();
	}

	/**
	 * Return the board layout.
	 *
	 * @return The board layout.
	 */
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
	
	/**
	 * Returns the array of Player objects representing the players in the game.
	 *
	 * @return the array of players
	 */
	public Player[] getPlayers() {
		return players;
	}
	
	/**
	 * Sets the array of Player objects representing the players in the game.
	 *
	 * @param players the array of players to be set
	 */
	public void setPlayers(Player[] players) {
		this.players = players;
	}
	
	/**
	 * Returns the current turn in the game.
	 *
	 * @return the current turn
	 */
	public int getCurrentTurn() {
		return currentTurn;
	}
	
	/**
	 * Sets the current turn in the game.
	 *
	 * @param currentTurn the current turn to be set
	 */
	public void setCurrentTurn(int currentTurn) {
		this.currentTurn = currentTurn;
	}

	/**
	 * Returns the last wall placed.
	 *
	 * @return The last wall placed.
	 */
	public Wall getLastWall() {
		return lastWall;
	}

	/**
	 * Sets the last wall placed.
	 *
	 * @param lastWall the last wall placed.
	 */
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
		for (int i = 0; i < this.getPlayerNumber(); i++) {
			for (Case value : Case.values()) {
				if (value.getValue() == i + 1) {
					this.getBoard()[Board.STARTINGPOSITIONPLAYERS[i].getX()][Board.STARTINGPOSITIONPLAYERS[i]
							.getY()] = value;
				}
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
	public boolean isWinnableForAll() {
		int i = 0;
		while (i < this.getPlayers().length && this.isWinnable(this.getPlayers()[i].getPawn())) {
			i++;
		}
		return i == this.getPlayers().length;
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
		for (Position pos : player.getPossibleMove()) {
			marking = this.dfs(pos, player, marking, player.getPossibleMove());
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
	 * @param pos          The position from which to start the DFS.
	 * @param player       The player for whom to perform the DFS.
	 * @param marking      A set of positions marking the nodes visited during the
	 *                     DFS.
	 * @param possibleMove The set of Positions representing the possible moves for
	 *                     the Pawn.
	 * @return The updated marking set after performing the DFS.
	 */
	public Set<Position> dfs(Position pos, Pawn player, Set<Position> marking, Set<Position> possibleMove) {
		if (!marking.contains(pos)) {
			marking.add(pos);
			possibleMove = player.possibleMove(this, pos);
			for (Position pos1 : possibleMove) {
				marking = this.dfs(pos1, player, marking, possibleMove);
			}
		}
		return marking;
	}

	/**
	 * Serializes the current state of the Board class.
	 * This method is automatically called during serialization.
	 *
	 * @param out the ObjectOutputStream to which the board state is written
	 * @throws IOException if an I/O error occurs while writing to the ObjectOutputStream
	 */
	private void writeObject(ObjectOutputStream out) throws IOException {	// Méthode pour sérialiser la classe Board
		out.defaultWriteObject();
	}

	/**
	 * Deserializes the state of the Board class.
	 * This method is automatically called during deserialization.
	 *
	 * @param in the ObjectInputStream from which the board state is read
	 * @throws IOException if an I/O error occurs while reading from the ObjectInputStream
	 * @throws ClassNotFoundException if the class of a serialized object cannot be found
	 */
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException { 	// Méthode pour désérialiser la classe Board
		in.defaultReadObject();
	}
}