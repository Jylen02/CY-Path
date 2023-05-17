package Abstraction;

import java.util.Scanner;

public class Main {

	public boolean accountWall(Board board) {
		if (board.getWallCount() < 20) {
			return true;
		} else {
			return false;
		}
	}

	public static void roundOfPlay(Player p, Board board,Scanner s) { // Tour de jeu
		System.out.println("Tour de "+p.getPlayerNumber());
		//System.out.println("Menu :");
		System.out.println(" - Avancer : 1 \n - Poser un mur : 2");
		System.out.println("Veuillez entrer l'option de votre choix (1 ou 2)");
		int input = s.nextInt();
		System.out.println("Veuillez entrer les coordonnées : ");
		System.out.println("	x = ");
		int x = s.nextInt();
		System.out.println("	y = ");
		int y = s.nextInt();

		Position position = new Position(x, y);

		if (input == 1) {
			p.getPawn().possibleMove(board);
			if(p.getPawn().getPossibleDestination().contains(position)) {
				board.move(position, p.getPawn());
			}
			else {
				System.out.println("Erreur : Veuillez entrez les coordonnées d'un déplacement valide.\\n");
				roundOfPlay(p,board,s);
			}
		} else if (input == 2) {
			System.out.println("Mur vertical : 1 \n Mur horizontal : 2");
			int orientation = s.nextInt();
			if (orientation == 1) {
				Wall wall = new Wall(Orientation.VERTICAL, position);
				if (!wall.createWall(board)) {
					System.out.println("Erreur : Impossible de placer un mur à ces coordonnées.\n");
					roundOfPlay(p, board,s);
				}

			} else if (orientation == 2) {
				Wall wall = new Wall(Orientation.HORIZONTAL, position);
				if (!wall.createWall(board)) {
					System.out.println("Erreur : Impossible de placer un mur à ces coordonnées.\\n");
					roundOfPlay(p, board,s);
				}

			}
		}else{
			System.out.println("Erreur : Orientation du mur incorrect.\\n");
			roundOfPlay(p,board,s);
		}
		board.show();
	}

	public static void main(String[] args) {
		Board board = new Board(4);
		board.show();

		Player p1 = new Player(Case.PLAYER1, new Pawn(new Position(Board.TAILLE - 2, Board.TAILLE / 2), Case.PLAYER1));
		Player p2 = new Player(Case.PLAYER2, new Pawn(new Position(1, Board.TAILLE / 2), Case.PLAYER2));
		Player p3 = new Player(Case.PLAYER3, new Pawn(new Position(Board.TAILLE / 2, 1), Case.PLAYER3));
		Player p4 = new Player(Case.PLAYER4, new Pawn(new Position(Board.TAILLE / 2, Board.TAILLE - 2), Case.PLAYER4));
		boolean win = false;
		Player[] players = { p1, p2, p3, p4 };
		int turn=0;
		Scanner s = new Scanner(System.in);
		while (!win) {
			roundOfPlay(players[turn],board,s);
			turn=(turn+1)%4;
		}
		s.close();
		
		/*
		 * p1.getPawn().possibleMove(board); board.move(new Position(Board.TAILLE - 2 -
		 * 2, Board.TAILLE / 2), p1.getPawn());
		 * 
		 * p1.getPawn().possibleMove(board); board.move(new Position(Board.TAILLE - 2 -
		 * 4, Board.TAILLE / 2), p1.getPawn());
		 * 
		 * p1.getPawn().possibleMove(board); board.move(new Position(Board.TAILLE - 2 -
		 * 6, Board.TAILLE / 2), p1.getPawn());
		 * 
		 * p1.getPawn().possibleMove(board); board.move(new Position(Board.TAILLE - 2 -
		 * 8, Board.TAILLE / 2), p1.getPawn());
		 * 
		 * p1.getPawn().possibleMove(board); board.move(new Position(Board.TAILLE - 2 -
		 * 10, Board.TAILLE / 2), p1.getPawn());
		 * 
		 * p1.getPawn().possibleMove(board); board.move(new Position(Board.TAILLE - 2 -
		 * 12, Board.TAILLE / 2), p1.getPawn());
		 * 
		 * p1.getPawn().possibleMove(board); board.move(new Position(Board.TAILLE - 2 -
		 * 14, Board.TAILLE / 2), p1.getPawn());
		 * 
		 * p2.getPawn().possibleMove(board); board.move(new Position(5, Board.TAILLE /
		 * 2), p2.getPawn());
		 * 
		 * p1.getPawn().possibleMove(board); board.move(new Position(Board.TAILLE - 2 -
		 * 16, Board.TAILLE / 2), p1.getPawn());
		 * 
		 * System.out.println("\n Apres déplacement + saut \n"); board.show();
		 */
	}
}
