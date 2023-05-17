package Abstraction;

public class Board {
	// Board
	private Case[][] board;
	private int playerNumber;
	private int wallCount;
	public static final int TAILLE = 19;
	public static final int MAXWALLCOUNT = 20;
	

	// Constructor
	public Board(int playerNumber) {
		this.playerNumber = playerNumber;
		initializeBoard();
		this.wallCount = 0;
	}

	// Getters & Setters
	public int getWallCount() {
		return wallCount;
	}

	public void setWallCount(int wallCount) {
		this.wallCount = wallCount;
	}

	public Case[][] getBoard() {
		return board;
	}

	public void setBoard(Case[][] board) {
		this.board = board;
	}

	public int getPlayerNumber() {
		return playerNumber;
	}

	public void setPlayerNumber(int playerNumber) {
		this.playerNumber = playerNumber;
	}

	// Initializing Board
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
					// Other colums
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
					// Other colums
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

	public void show() {
		System.out.print("   ");
		for (int i = 0; i < TAILLE; i++) {
			if (i>=10) {
				System.out.print("1 ");
			}
			else {
				System.out.print("  ");
			}
		}
		System.out.println();
		System.out.print("   ");
		for (int i = 0; i < TAILLE; i++) {
			System.out.print(i%10 + " ");
		}
		System.out.println();
		for (int i = 0; i < TAILLE; i++) {
			if (i<10) {
				System.out.print(" ");
			}
			System.out.print(i + " ");
			for (int j = 0; j < TAILLE; j++) {
				if (this.board[i][j] == Case.NULL) {
						System.out.print("+ ");
				} else if (this.board[i][j] == Case.BORDER || this.board[i][j] == Case.POTENTIALWALL) {
					if (j % 2 == 0) {
						System.out.print("| ");
					} else {
						System.out.print("- ");
					}
				} else if (this.board[i][j] == Case.WALL) {
					System.out.print("/ ");
			    }else if (this.board[i][j] == Case.EMPTY) {
						System.out.print("  ");
				} else {
					System.out.print(this.board[i][j].getValue() + " ");
				}
			}
			System.out.println();
		}
	}

	public void move(Position pos, Pawn player) throws ImpossibleMovementException {
		if (player.getPossibleDestination().contains(pos)) {
			this.board[player.getPos().getX()][player.getPos().getY()] = Case.EMPTY;
			player.setPos(pos);
			this.board[player.getPos().getX()][player.getPos().getY()] = player.getPlayerNb();
		} else {
			throw new ImpossibleMovementException("Error : Unauthorized movement");
		}
	}

	/*
	 * public void deleteEdge() {
	 * 
	 * }
	 * 
	 * public static void addEdge() {
	 * 
	 * }
	 */
}