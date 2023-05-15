package Abstraction;

public class Pawn {
	private Vertex pos;
	private Player player;
	
	public Pawn(Vertex pos,Player player) {
		this.pos=pos;
		this.player=player;
	}
	
	public Vertex getPos() {
		return pos;
	}
	public void setPos(Vertex pos) {
		this.pos = pos;
	}
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	/**
	 * Moves in a specified direction.
	 *
	 * @param m the movement direction
	 */
	public void move(Movement m) {
	    switch (m) {
	        case HAUT:
	            this.pos.getPos().setY(this.pos.getPos().getY()+1);
	            break;
	        case BAS:
	        	this.pos.getPos().setY(this.pos.getPos().getY()-1);
	            break;
	        case DROITE:
	        	this.pos.getPos().setX(this.pos.getPos().getX()+1);
	            break;
	        default:
	        	this.pos.getPos().setX(this.pos.getPos().getX()-1);
	            break;
	    }
	}

	
	public void specialMove() {
		
	}
	
	public void canMove() {
		
	}
	
}
