package Abstraction;

public enum Case {
	NULL(-1), // Intersection between walls
	EMPTY(0),  // Empty cases
	PLAYER1(1), // Player 1's pawn
	PLAYER2(2), // Player 2's pawn
	PLAYER3(3), // Player 3's pawn
	PLAYER4(4), // Player 4's pawn
	POTENTIALWALL(5), // Emplacement for a wall
	WALL(6), // Wall
	BORDER(7); // Border
	private final int value;

	private Case(int i) {
		this.value = i;
	}
	
	public int getValue() {
		return this.value;
	}
}
