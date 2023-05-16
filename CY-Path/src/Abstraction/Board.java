package Abstraction;

public class Board {
	// Board
	private Case[][] board;
	private int playerNumber;
	private int wallCount;
	private static final int TAILLE = 19;
	private static final int MAXWALLCOUNT = 20;
	
	//Constructor
	public Board(int playerNumber) {
		this.playerNumber = playerNumber;
		initializeBoard();
		this.wallCount = 0;
	}

	//Getters & Setters
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

	//Initializing Board
	public void initializeBoard() {
		this.setBoard(new Case[TAILLE][TAILLE]);
		//
		for (int i = 0; i < TAILLE; i++) {
			for (int j = 0; j < TAILLE; j+=2) {
				if ((i == 0 && j == TAILLE-1) || (i == TAILLE-1 && j == TAILLE-1) ){
					getBoard()[i][j] = Case.NULL;
				}
				else if (i == 0 || i == TAILLE-1) {
					getBoard()[i][j] = Case.NULL;
					getBoard()[i][j+1] = Case.BORDER;
				}
				else if (i%2 == 1) {
					if (j==0) {
						getBoard()[i][j] = Case.BORDER;
						getBoard()[i][j+1] = Case.EMPTY;
					}
					else if (j == TAILLE-1) {
						getBoard()[i][j] = Case.BORDER;
					}
					else {
						getBoard()[i][j] = Case.POTENTIALWALL;
						getBoard()[i][j+1] = Case.EMPTY;
					}
				}
				else if (i%2 == 0) {
					if (j==0) {
						getBoard()[i][j] = Case.NULL;
						getBoard()[i][j+1] = Case.POTENTIALWALL;
					}
					else if (j == TAILLE-1) {
						getBoard()[i][j] = Case.NULL;
					}
					else {
						getBoard()[i][j] = Case.NULL;
						getBoard()[i][j+1] = Case.POTENTIALWALL;
					}
				}
			}
		}
		
		if (getPlayerNumber() >= 2) {
			getBoard()[TAILLE-2][TAILLE/2] = Case.PLAYER1;
			getBoard()[1][TAILLE/2] = Case.PLAYER2;
			if (getPlayerNumber() == 4) {
				getBoard()[TAILLE/2][1] = Case.PLAYER3;
				getBoard()[TAILLE/2][TAILLE-2] = Case.PLAYER4;
			}
		}
	}

	public void show() {
		for (int i = 0; i < TAILLE; i++) {
			for (int j = 0; j < TAILLE; j++) {
				if (this.board[i][j].getValue() == -1) {
					System.out.print("+ ");
				}
				else if (this.board[i][j].getValue() >= 6) {
					if (j%2 == 0) {
						System.out.print("| ");
					}
					else {
						System.out.print("- ");
					}
				}
				else if (this.board[i][j].getValue() == 5) {
					System.out.print("  ");
				}
				else{
					System.out.print(this.board[i][j].getValue() + " ");
				}
			}
			System.out.println();
		}
	}

	public void deleteEdge() {

	}

	public void addEdge() {

	}

	public static void main(String[] args) {
		Board p = new Board(4);
		p.show();
	}
}