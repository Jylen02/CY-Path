package Abstraction;

import java.util.HashSet;
import java.util.Set;

public class Pawn {
	private Position pos;
	private Set<Position> possibleDestination;
	
	public Pawn(Position pos,Case player) {
		this.pos=pos;
	}
	
	public Position getPos() {
		return pos;
	}
	public void setPos(Position pos) {
		this.pos = pos;
	}
	
	/**
	 * Moves in a specified direction.
	 *
	 * @param m the movement direction
	 */
	
	public void move() {
		//recupere case
		//if possibledestination.contains(case)
		//this.pos = case
	}
	
//	public void move(Movement m) {
//	    switch (m) {
//	        case HAUT:
//	            this.pos.setY(this.pos.getY()+1);
//	            if canmove(m)==2
//	              		canmove(m)==0 -> recuperer mouvement latéraux ((enum.ordinal+4)%4) 
//	              					  -> canmove(movement latéraux) 2x (car 2 movement)
//	              					  -> réappeler la fonction qui dmd d'avancer
//	            		canmove(m)==1; -> avance
//	            		canmove(m)==2 -> ajoute pos + rappeler move(m) un truc du genre (si y'a plusieurs pions d'affilés)
//	            		
//	            break;
//	        case BAS:
//	        	this.pos.setY(this.pos.getY()-1);
//	            break;
//	        case DROITE:
//	        	this.pos.setX(this.pos.getX()+1);
//	            break;
//	        case GAUCHE:
//	        	this.pos.setX(this.pos.getX()-1);
//	            break;
//    	}
//	}

	public void topMove(Board board, Position pos, Boolean jump) {
		if (board.getBoard()[pos.getX()+1][pos.getY()] == Case.POTENTIALWALL ) {
			if (board.getBoard()[pos.getX()+2][pos.getY()] == Case.EMPTY) {
				this.possibleDestination.add(new Position(pos.getX()+2,pos.getY()));
			}
			else {
				if (!jump) {
					specialMove(board, new Position(pos.getX(),pos.getY()-2), Movement.LEFT);
				}
			}
		}
	}
	
	public void rightMove(Board board, Position pos, Boolean jump) {
		if (board.getBoard()[pos.getX()+1][pos.getY()] == Case.POTENTIALWALL ) {
			if (board.getBoard()[pos.getX()+2][pos.getY()] == Case.EMPTY) {
				this.possibleDestination.add(new Position(pos.getX()+2,pos.getY()));
			}
			else {
				if (!jump) {
					specialMove(board, new Position(pos.getX(),pos.getY()-2), Movement.LEFT);
				}
			}
		}
	}
	public void botMove(Board board, Position pos, Boolean jump) {
		if (board.getBoard()[pos.getX()+1][pos.getY()] == Case.POTENTIALWALL ) {
			if (board.getBoard()[pos.getX()+2][pos.getY()] == Case.EMPTY) {
				this.possibleDestination.add(new Position(pos.getX()+2,pos.getY()));
			}
			else {
				if (!jump) {
					specialMove(board, new Position(pos.getX(),pos.getY()-2), Movement.LEFT);
				}
			}
		}
	}
	public void leftMove(Board board, Position pos, Boolean specialMove) {
		if (board.getBoard()[pos.getX()][pos.getY()-1] == Case.POTENTIALWALL ) {
			if (board.getBoard()[pos.getX()][pos.getY()-2] == Case.EMPTY) {
				this.possibleDestination.add(new Position(pos.getX(),pos.getY()-2));
			}
			else {
				if (!specialMove) {
					specialMove(board, new Position(pos.getX(),pos.getY()-2), Movement.LEFT);
				}
			}
		}
	}
	
	public void specialMove(Board board, Position pos, Movement m) {
		if (m==Movement.TOP) {
			if (board.getBoard()[pos.getX()+1][pos.getY()] == Case.POTENTIALWALL ) {
				topMove(board, pos, true);
			}
			else {
				leftMove(board, pos, true);
				rightMove(board, pos, true);
			}
		}
	}
	
	public Set<Position> possibleMove(Board board) {
		Set<Position> destination = new HashSet<Position>();
		
		
		return destination;
	}
	
}
