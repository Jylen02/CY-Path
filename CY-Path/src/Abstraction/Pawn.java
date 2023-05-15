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
		if(canMove(m)) {
		    switch (m) {
		        case HAUT:
		            this.pos.getPos().setY(this.pos.getPos().getY()+1);
		            /*if canmove(m)==2
		             * 		canmove(m)==0 -> recuperer mouvement latéraux ((enum.ordinal+4)%4) 
		             * 					  -> canmove(movement latéraux) 2x (car 2 movement)
		             * 					  -> réappeler la fonction qui dmd d'avancer
		            		canmove(m)==1; -> avance
		            		canmove(m)==2 -> ajoute pos + rappeler move(m) un truc du genre (si y'a plusieurs pions d'affilés)
		            		*/
		            break;
		        case BAS:
		        	this.pos.getPos().setY(this.pos.getPos().getY()-1);
		            break;
		        case DROITE:
		        	this.pos.getPos().setX(this.pos.getPos().getX()+1);
		            break;
		        case GAUCHE:
		        	this.pos.getPos().setX(this.pos.getPos().getX()-1);
		            break;
	    	}
	    }
	}

	
	public void specialMove() {
		
	}
	
	public boolean canMove(Movement m) {
		switch (m) {
        case HAUT:
            /*  Vérifier que la case au dessus appartient a la grille -> 1
             *  / que la case au dessus n'est pas occuper par un autre pion -> 2
             *  / que la case au dessus n'est pas bloquer par un mur -> 1
             *  return true;
        	*/  
            break;
        case BAS:
        	//
            break;
        case DROITE:
        	//
            break;
        case GAUCHE:
        	//
            break;
		}
		return false;
	}
	
}
