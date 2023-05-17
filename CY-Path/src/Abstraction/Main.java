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

	public static void roundOfPlay(Player p, Board board, Scanner s) { // Tour de jeu
		// System.out.println("Menu :");
		p.getPawn().possibleMove(board);
		System.out.println(" - Déplacements possible :");
		System.out.println(p.getPawn().getPossibleDestination());
		System.out.println(" - Avancer : 1 \n - Poser un mur : 2");
		System.out.println("Veuillez entrer l'option de votre choix (1 ou 2)");
		int input = s.nextInt();
		System.out.println("Veuillez entrer les coordonnées : ");
		System.out.println("	row = ");
		int row = s.nextInt();
		System.out.println("	column = ");
		int column = s.nextInt();

		Position position = new Position(row, column);

		switch (input) {
		case 1:
			if (p.getPawn().getPossibleDestination().contains(position)) {
				board.move(position, p.getPawn());
			} else {
				System.out.println("Erreur : Veuillez entrez les coordonnées d'un déplacement valide.");
				board.show();
				roundOfPlay(p, board, s);
			}
			break;
		case 2:
			System.out.println(" - Mur vertical : 1 \n - Mur horizontal : 2");
			int orientation = s.nextInt();
			Wall wall;
			switch (orientation) {
			case 1:
				wall = new Wall(Orientation.VERTICAL, position);
				wallError(wall, board, p, s);
				break;
			case 2:
				wall = new Wall(Orientation.HORIZONTAL, position);
				wallError(wall, board, p, s);
				break;
			default:
				System.out.println("Erreur : Orientation du mur incorrect.");
				board.show();
				roundOfPlay(p, board, s);
				break;
			}
			break;
		default:
			System.out.println("Erreur : Action indisponible.");
			board.show();
			roundOfPlay(p, board, s);
			break;
		}
	}

	public static void wallError(Wall wall, Board board, Player p, Scanner s) {
		if (!wall.createWall(board)) {
			System.out.println("Erreur : Impossible de placer un mur à ces coordonnées.");
			board.show();
			roundOfPlay(p, board, s);
		}
	}

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int numberOfPlayers = 0;
		do {
			System.out.println("Veuillez entrer le nombre de joueur (2 ou 4)");
			numberOfPlayers = s.nextInt();
		} while (numberOfPlayers != 2 && numberOfPlayers != 4);
		Player[] players = new Player[numberOfPlayers];
		for (int i = 0; i < numberOfPlayers; i++) {
			switch (i) {
			case 0:
				players[0] = new Player(Case.PLAYER1, new Pawn(new Position(Board.TAILLE - 2, Board.TAILLE / 2), Case.PLAYER1));
				break;
			case 1:
				players[1] = new Player(Case.PLAYER2, new Pawn(new Position(1, Board.TAILLE / 2), Case.PLAYER2));
				break;
			case 2:
				players[2] = new Player(Case.PLAYER3, new Pawn(new Position(Board.TAILLE / 2, 1), Case.PLAYER3));
				break;
			case 3:
				players[3] = new Player(Case.PLAYER4, new Pawn(new Position(Board.TAILLE / 2, Board.TAILLE - 2), Case.PLAYER4));
				break;
			default:
				break;
			}
		}

		Board board = new Board(numberOfPlayers);
		board.show();

		boolean win = false;
		// Player[] players = { p1, p2}; If there is only players
		int turn = 0;

		while (!win) {
			System.out.println("Tour de " + players[turn].getPlayerNumber() + ":");
			roundOfPlay(players[turn], board, s);
			board.show();
			if (players[turn].getPawn().isWinner()) {
				win = true;
				System.out.println(players[turn].getPlayerNumber() + " à gagné. Félicitation !");
			}
			turn = (turn + 1) % numberOfPlayers;
		}
		s.close();
	}
}
