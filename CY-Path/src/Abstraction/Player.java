package Abstraction;

/**
 * Represents a player in a game.
 */
public class Player {

	/**
	 * The player's number or identifier.
	 */
	private Case playerNumber;

	/**
	 * The pawn associated with the player.
	 */
	private Pawn pawn;

	/**
	 * The name of the player.
	 */
	private String name;
	// private .... icone; TO IMPLEMENT WITH IHM

	/**
	 * Creates a new player with the specified player number and associated pawn.
	 *
	 * @param playerNumber The player's number or identifier.
	 * @param pawn         The pawn associated with the player.
	 */
	public Player(Case playerNumber, Pawn pawn) {
		this.playerNumber = playerNumber;
		this.pawn = pawn;
	}

	/**
	 * Returns the player's number or identifier.
	 *
	 * @return The player's number or identifier.
	 */
	public Case getPlayerNumber() {
		return playerNumber;
	}

	/**
	 * Sets the player's number or identifier.
	 *
	 * @param playerNumber The new player number.
	 */
	public void setPlayerNumber(Case playerNumber) {
		this.playerNumber = playerNumber;
	}

	/**
	 * Returns the pawn associated with the player.
	 *
	 * @return The pawn associated with the player.
	 */
	public Pawn getPawn() {
		return pawn;
	}

	/**
	 * Sets the pawn associated with the player.
	 *
	 * @param pawn The new associated pawn.
	 */
	public void setPawn(Pawn pawn) {
		this.pawn = pawn;
	}

	/**
	 * Computes the vertex based on the player's pawn position. The vertex is
	 * calculated by the formula: (x / 2) * 9 + y / 2, where x and y are the
	 * coordinates of the pawn's position.
	 *
	 * @param player The player whose pawn position is to be used.
	 * @return The computed vertex.
	 */
	public int getVertex(Player player) {
		Position pos = player.getPawn().getPos();
		int x = pos.getX();
		int y = pos.getY();
		return (x / 2) * 9 + y / 2;
	}
}
