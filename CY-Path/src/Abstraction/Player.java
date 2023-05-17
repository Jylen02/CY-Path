package Abstraction;

public class Player {
	private Case playerNumber; // n° of the player
	private Pawn pawn;

	public Player(Case playerNumber, Pawn pawn) {
		this.playerNumber = playerNumber;
		this.pawn = pawn;
	}

	public Case getPlayerNumber() {
		return playerNumber;
	}

	public void setPlayerNumber(Case playerNumber) {
		this.playerNumber = playerNumber;
	}

	public Pawn getPawn() {
		return pawn;
	}

	public void setPawn(Pawn pawn) {
		this.pawn = pawn;
	}
}
