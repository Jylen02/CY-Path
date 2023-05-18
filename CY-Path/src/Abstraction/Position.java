package Abstraction;

/**
 * Represents a position in a 2D coordinate system.
 */
public class Position {

	/**
	 * The x-coordinate of the position (Rows of the board).
	 */
	private Integer x;

	/**
	 * The y-coordinate of the position (Columns of the board).
	 */
	private Integer y;

	/**
	 * Creates a new position with the specified coordinates.
	 *
	 * @param x The x-coordinate of the new position.
	 * @param y The y-coordinate of the new position.
	 */
	public Position(Integer x, Integer y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Returns the x-coordinate of the position.
	 *
	 * @return The x-coordinate of the position.
	 */
	public Integer getX() {
		return x;
	}

	/**
	 * Sets the x-coordinate of the position.
	 *
	 * @param x The new x-coordinate.
	 */
	public void setX(Integer x) {
		this.x = x;
	}

	/**
	 * Returns the y-coordinate of the position.
	 *
	 * @return The y-coordinate of the position.
	 */
	public Integer getY() {
		return y;
	}

	/**
	 * Sets the y-coordinate of the position.
	 *
	 * @param y The new y-coordinate.
	 */
	public void setY(Integer y) {
		this.y = y;
	}

	/**
	 * Returns a string representation of the position in the format "(x,y)".
	 *
	 * @return A string representation of the position.
	 */
	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}

	/**
	 * Checks if this position is equal to another object. The result is true if and
	 * only if the argument is not null and is a Position object that represents the
	 * same coordinate as this object.
	 *
	 * @param o The object to compare this position against.
	 * @return true if the given object represents a Position equivalent to this
	 *         position, false otherwise.
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof Position) {
			Position p = (Position) o;
			if (this.getX().equals(p.getX()) && this.getY().equals(p.getY())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns a hash code for this position. This implementation computes the hash
	 * code from the x and y coordinates by the formula (x * 100 + y).
	 *
	 * @return A hash code for this position.
	 */
	@Override
	public int hashCode() {
		return getX() * 100 + getY();
	}
}
