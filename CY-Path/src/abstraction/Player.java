package abstraction;

/**
 * Represents a player in a game.
 */
public class Player {

	/**
	 * The pawn associated with the player.
	 */
	private Pawn pawn;

	/**
	 * Number of wall remaining for this player.
	 */
	private int remainingWall;

	/**
	 * The name of the player.
	 */
	private String name;

	/**
	 * Creates a new player with the specified player number and associated pawn.
	 *
	 * @param name The player's name.
	 * @param pawn The pawn associated with the player.
	 * @param 
	 */
	public Player(String name, Pawn pawn, int remainingWall) {
		this.name = name;
		this.pawn = pawn;
		this.remainingWall = remainingWall;
	}

	/**
	 * Returns the player's name.
	 *
	 * @return The player's name.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Sets the player's name.
	 *
	 * @param name The new player's name.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the pawn associated with the player.
	 *
	 * @return The pawn associated with the player.
	 */
	public Pawn getPawn() {
		return pawn;
	}

	public int getRemainingWall() {
		return remainingWall;
	}

	public void setRemainingWall(int remainingWall) {
		this.remainingWall = remainingWall;
	}
}
