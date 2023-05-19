package abstraction;

/**
 * Enum representing the different cases on the game board. Each case has a
 * specific value associated with it.
 */
public enum Case {

	/**
	 * Intersection between walls.
	 */
	NULL(-1),

	/**
	 * Empty cases.
	 */
	EMPTY(0),

	/**
	 * Player 1's pawn.
	 */
	PLAYER1(1),

	/**
	 * Player 2's pawn.
	 */
	PLAYER2(2),

	/**
	 * Player 3's pawn.
	 */
	PLAYER3(3),

	/**
	 * Player 4's pawn.
	 */
	PLAYER4(4),

	/**
	 * Emplacement for a potential wall.
	 */
	POTENTIALWALL(5),

	/**
	 * Placed wall.
	 */
	WALL(6),
	
	/**
	 * Border case.
	 */
	BORDER(7);

	private final int value;

	/**
	 * Constructs a new Case with the specified value.
	 *
	 * @param value the value associated with the case
	 */
	private Case(int value) {
		this.value = value;
	}

	/**
	 * Returns the value associated with the case.
	 *
	 * @return the value of the case
	 */
	public int getValue() {
		return this.value;
	}
}
