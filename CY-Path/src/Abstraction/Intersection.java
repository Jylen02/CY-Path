package Abstraction;

public class Intersection {
	
	public Position p1;
	public Position p2;
	
	public Intersection(Position p1,Position p2) {
		this.p1=p1;
		this.p2=p2;
	}

	@Override
	public String toString() {
		return "Intersection [" + p1 + "," + p2 + "]";
	}
	
	
}
