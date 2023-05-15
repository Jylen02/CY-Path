package Abstraction;

public class Intersection {
	
	public Position p1;
	public Position p2;
	
	public Intersection(Position pE,Position pS) {
		this.p1=pE;
		this.p2=pS;
	}

	@Override
	public String toString() {
		return "Intersection [" + p1 + "," + p2 + "]";
	}
	
	
}
