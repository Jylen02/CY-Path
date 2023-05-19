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
	 * The name of the player.
	 */
	private String name;
	
	/**
	 * Creates a new player with the specified player number and associated pawn.
	 *
	 * @param name 		   The player's name.
	 * @param pawn         The pawn associated with the player.
	 */
	public Player(String name, Pawn pawn) {
		this.name = name;
		this.pawn = pawn;
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
}
