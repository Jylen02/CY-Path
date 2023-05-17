package Abstraction;

public class Position {
	private Integer x;
	private Integer y;

	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}

	public Position(Integer x, Integer y) {
		this.x = x;
		this.y = y;
	}

	public Integer getX() {
		return x;
	}

	public void setX(Integer x) {
		this.x = x;
	}

	public Integer getY() {
		return y;
	}

	public void setY(Integer y) {
		this.y = y;
	}

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

	@Override
	public int hashCode() {
		return getX() * 100 + getY();
	}
}
