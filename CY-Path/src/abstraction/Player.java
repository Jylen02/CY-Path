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

	/**
	 * Method for serializing the Board class.
	 *
	 * @param out the ObjectOutputStream to write the Board object
	 * @throws IOException if an I/O error occurs while writing to the underlying OutputStream
	 */
	private void writeObject(ObjectOutputStream out) throws IOException {
		out.defaultWriteObject();
	}

	/**
	 * Method for deserializing the Board class.
	 *
	 * @param in the ObjectInputStream to read the Board object from
	 * @throws IOException if an I/O error occurs while reading from the underlying InputStream
	 * @throws ClassNotFoundException if the class of a serialized object cannot be found
	 */
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
	}
}
