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

	public void roundOfPlay(Player p,Board board) { // Tour de jeu
		Scanner s = new Scanner(System.in);
		System.out.println("Avancer : 1 \n Poser un mur : 2");
		int input=s.nextInt();
	
		System.out.print("Veuillez entrer les coordonnées x,y : ");
        String inputString = s.nextLine();

        String[] coordinates = inputString.split(" ");
        int x = Integer.parseInt(coordinates[0]);
        int y = Integer.parseInt(coordinates[1]);

        Position position = new Position(x, y);
        
        
		if(input==1) {
			p.getPawn().possibleMove(board);
			board.move(position, p.getPawn());
		}else if(input==2) {
			System.out.println("Mur vertical : 1 \n Mur horizontal : 2");
			int o=s.nextInt();
		}
		s.close();
	}

	public static void main(String[] args) {
		Board board = new Board(4);
		board.show();
		
		Player p1 = new Player(Case.PLAYER1, new Pawn(new Position(Board.TAILLE - 2, Board.TAILLE / 2), Case.PLAYER1));
		Player p2 = new Player(Case.PLAYER2, new Pawn(new Position(1, Board.TAILLE / 2), Case.PLAYER2));
		Player p3 = new Player(Case.PLAYER3, new Pawn(new Position(Board.TAILLE / 2, 1), Case.PLAYER3));
		Player p4 = new Player(Case.PLAYER4, new Pawn(new Position(Board.TAILLE / 2, Board.TAILLE - 2), Case.PLAYER4));

		p1.getPawn().possibleMove(board);
		board.move(new Position(Board.TAILLE - 2 - 2, Board.TAILLE / 2), p1.getPawn());

		p1.getPawn().possibleMove(board);
		board.move(new Position(Board.TAILLE - 2 - 4, Board.TAILLE / 2), p1.getPawn());

		p1.getPawn().possibleMove(board);
		board.move(new Position(Board.TAILLE - 2 - 6, Board.TAILLE / 2), p1.getPawn());

		p1.getPawn().possibleMove(board);
		board.move(new Position(Board.TAILLE - 2 - 8, Board.TAILLE / 2), p1.getPawn());

		p1.getPawn().possibleMove(board);
		board.move(new Position(Board.TAILLE - 2 - 10, Board.TAILLE / 2), p1.getPawn());

		p1.getPawn().possibleMove(board);
		board.move(new Position(Board.TAILLE - 2 - 12, Board.TAILLE / 2), p1.getPawn());

		p1.getPawn().possibleMove(board);
		board.move(new Position(Board.TAILLE - 2 - 14, Board.TAILLE / 2), p1.getPawn());

		p2.getPawn().possibleMove(board);
		board.move(new Position(5, Board.TAILLE / 2), p2.getPawn());

		p1.getPawn().possibleMove(board);
		board.move(new Position(Board.TAILLE - 2 - 16, Board.TAILLE / 2), p1.getPawn());

		System.out.println("\n Apres déplacement + saut \n");
		board.show();
	}
}
