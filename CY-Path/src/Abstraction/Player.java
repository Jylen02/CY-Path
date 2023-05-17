package Abstraction;

public class Player {
	private int playerNumber; // n° of the player
	private Pawn pawn;
	private Boolean myTurn = false;
	
	public Player(int playerNumber, Pawn pawn) {
		this.playerNumber = playerNumber;
		this.pawn = pawn;
	}

	public int getPlayerNumber() {
		return playerNumber;
	}

	public void setPlayerNumber(int playerNumber) {
		this.playerNumber = playerNumber;
	}

	public Pawn getPawn() {
		return pawn;
	}

	public void setPawn(Pawn pawn) {
		this.pawn = pawn;
	}

	public Boolean isMyTurn() {
		return myTurn;
	}

	public void setMyTurn(Boolean myTurn) {
		this.myTurn = myTurn;
	}
	
	public void changeTurn() {
		this.setMyTurn(true);
	}
}
