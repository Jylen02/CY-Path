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
	
	
}
