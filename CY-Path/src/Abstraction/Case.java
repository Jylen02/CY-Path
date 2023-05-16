package Abstraction;

public enum Case {
	NULL(-1),
	EMPTY(0), 
	PLAYER1(1),
	PLAYER2(2),
	PLAYER3(3),
	PLAYER4(4),
	POTENTIALWALL(5), 
	WALL(6),
	BORDER(7);
	private final int value;

	private Case(int i) {
		this.value = i;
	}
	
	public int getValue() {
		return this.value;
	}
}
