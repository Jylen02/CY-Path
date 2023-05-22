package abstraction;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Represents a player in a game.
 */
public class Player implements Serializable {

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
	 * @param name          The player's name.
	 * @param pawn          The pawn associated with the player.
	 * @param remainingWall The number of wall remaining for this player.
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

	/**
	 * Returns the number of wall remaining.
	 *
	 * @return The pawn associated with the player.
	 */
	public int getRemainingWall() {
		return remainingWall;
	}

	/**
	 * Sets the number of wall remaining.
	 *
	 * @param remainingWall The new player's name.
	 */
	public void setRemainingWall(int remainingWall) {
		this.remainingWall = remainingWall;
	}

	// Méthode pour sérialiser la classe Board
	private void writeObject(ObjectOutputStream out) throws IOException {
		out.defaultWriteObject();
	}

	// Méthode pour désérialiser la classe Board
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
	}
}
