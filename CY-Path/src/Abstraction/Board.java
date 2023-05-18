package Abstraction;

/**
 * The Board class represents the game board for the game. It contains the board
 * layout, player and wall count, and various methods for manipulating the
 * board.
 */
public class Board {
	// Board
	private Case[][] board;
	private int playerNumber;
	private int wallCount;
	public static final int TAILLE = 19;
	public static final int MAXWALLCOUNT = 20;

	/**
	 * Constructs a Board object with the specified player number.
	 *
	 * @param playerNumber the number of players in the game between 2 and 4
	 */
	// Constructor
	public Board(int playerNumber) {
		this.playerNumber = playerNumber;
		initializeBoard();
		this.wallCount = 0;
	}
	// Getters & Setters
	/**
	 * Returns the current wall count on the board.
	 *
	 * @return the wall count
	 */
	public int getWallCount() {
		return wallCount;
	}

	/**
	 * Sets the wall count on the board.
	 *
	 * @param wallCount the new wall count
	 */
	public void setWallCount(int wallCount) {
		this.wallCount = wallCount;
	}

	/**
	 * Returns the current board layout.
	 *
	 * @return the board layout
	 */
	public Case[][] getBoard() {
		return board;
	}

	/**
	 * Sets the board layout.
	 *
	 * @param board the new board layout
	 */
	public void setBoard(Case[][] board) {
		this.board = board;
	}

	/**
	 * Returns the number of players in the game.
	 *
	 * @return the player number
	 */
	public int getPlayerNumber() {
		return playerNumber;
	}

	/**
	 * Sets the number of players in the game.
	 *
	 * @param playerNumber the new player number
	 */
	public void setPlayerNumber(int playerNumber) {
		this.playerNumber = playerNumber;
	}

	// Initializing Board
	/**
	 * Initializes the board with the initial layout and player placements.
	 * All the different types filled in the array are present in the "Case" enumeration.
	 */
	public void initializeBoard() {
		this.setBoard(new Case[TAILLE][TAILLE]);
		for (int i = 0; i < TAILLE; i++) {
			for (int j = 0; j < TAILLE; j += 2) {
				// First line's right corner & Last line's right corner
				if ((i == 0 && j == TAILLE - 1) || (i == TAILLE - 1 && j == TAILLE - 1)) {
					getBoard()[i][j] = Case.NULL;
				}
				// First line & Last line
				else if (i == 0 || i == TAILLE - 1) {
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
					else if (j == TAILLE - 1) {
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
					else if (j == TAILLE - 1) {
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
			getBoard()[TAILLE - 2][TAILLE / 2] = Case.PLAYER1;
			getBoard()[1][TAILLE / 2] = Case.PLAYER2;
			if (getPlayerNumber() == 4) {
				// Set the two others player's pawn placement
				getBoard()[TAILLE / 2][1] = Case.PLAYER3;
				getBoard()[TAILLE / 2][TAILLE - 2] = Case.PLAYER4;
			}
		}
	}

	/**
	 * Displays the current state of the board.
	 * To make this possible, each type of the "Case" enumeration is replaced by a specific display.
	 */
	public void show() {
		// First line of the column's coordinates
		System.out.print("   ");
		for (int i = 0; i < TAILLE; i++) {
			if (i >= 10) {
				System.out.print("1 ");
			} else {
				System.out.print("  ");
			}
		}
		System.out.println();
		// Second line of the column's coordinates
		System.out.print("   ");
		for (int i = 0; i < TAILLE; i++) {
			System.out.print(i % 10 + " ");
		}
		System.out.println();
		for (int i = 0; i < TAILLE; i++) {
			// Column of the row's coordinates
			if (i < 10) {
				System.out.print(" ");
			}
			System.out.print(i + " ");
			for (int j = 0; j < TAILLE; j++) {
				// If the case is an intersection : put a "+"
				if (this.board[i][j] == Case.NULL) {
					System.out.print("+ ");
				} // If the case is a part of the grid, put a vertical "|" or horizontal "-" bar
				else if (this.board[i][j] == Case.BORDER || this.board[i][j] == Case.POTENTIALWALL) {
					if (j % 2 == 0) {
						System.out.print("| ");
					} else {
						System.out.print("- ");
					}
				} // If the case is a wall : put a "/"
				else if (this.board[i][j] == Case.WALL) {
					System.out.print("/ ");
				} // If the case is empty : put a " "
				else if (this.board[i][j] == Case.EMPTY) {
					System.out.print("  ");
				} // If the case is a player, put its value
				else {
					System.out.print(this.board[i][j].getValue() + " ");
				}
			}
			System.out.println();
		}
	}

	/**
	 * Moves a player to a new position on the board.
	 *
	 * @param pos    the new position for the player
	 * @param player the player to move
	 */
	public void move(Position pos, Pawn player) {
		this.board[player.getPos().getX()][player.getPos().getY()] = Case.EMPTY;
		player.setPos(pos);
		this.board[player.getPos().getX()][player.getPos().getY()] = player.getPlayerNb();
	}
}