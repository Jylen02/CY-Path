package Abstraction;

import java.util.Scanner;

public class Main {

	public static boolean accountWall(Board board) {
		if (board.getWallCount() < 20) {
			return true;
		} else {
			return false;
		}
	}

	// Player's turn
	public static void roundOfPlay(Player[] players, Integer turn, Board board, Scanner s) {
		// Verify if there max amount of wall is reach
		boolean endWall = accountWall(board);
		Player p = players[turn];
		// Display the possible destinations of a pawn
		p.getPawn().possibleMove(board);
		System.out.println("Possible move :");
		System.out.println(p.getPawn().getPossibleDestination());
		int input;
		// Check if the max amount of wall is reached, we can only move
		if (endWall == false) {
			System.out.println("The maximum amount of wall is reached, you can only move from now.");
			input = 1;
		}
		// Otherwise, choose an action
		else {
			System.out.println("Choice of action :");
			System.out.println(" - Move the pawn : 1 \n - Put a wall : 2");
			System.out.println("Please select the action you want (1 or 2) :");
			input = s.nextInt();
		}
		// Enter the coordinates for the pawn's move or wall's coordinates
		System.out.println("Please enter the coordinates : ");
		System.out.print("row = ");
		int row = s.nextInt();
		System.out.print("column = ");
		int column = s.nextInt();

		Position position = new Position(row, column);

		switch (input) {
		case 1:
			// Check if the move is in the possible move's list then move
			if (p.getPawn().getPossibleDestination().contains(position)) {
				board.move(position, p.getPawn());
			} // Otherwise, restart the turn
			else {
				System.out.println("Error : Please enter a valid coordinates.");
				board.show();
				roundOfPlay(players, turn, board, s);
			}
			break;
		case 2:
			// Choose wall's orientation
			System.out.println("Choice of the wall's orientation :");
			System.out.println(" - Vertical wall : 1 \n - Horizontal wall : 2");
			System.out.println("Please select the action you want (1 or 2) :");
			int orientation = s.nextInt();
			Wall wall;
			switch (orientation) {
			case 1:
				wall = new Wall(Orientation.VERTICAL, position);
				wallError(wall, board, players, turn, s);
				break;
			case 2:
				wall = new Wall(Orientation.HORIZONTAL, position);
				wallError(wall, board, players, turn, s);
				break;
			default:
				// Wrong value of wall's orientation
				System.out.println("Error : Incorrect wall's orientation.");
				board.show();
				roundOfPlay(players, turn, board, s);
				break;
			}
			break;
		default:
			// Wrong value of action
			System.out.println("Error : Action not disponible.");
			board.show();
			roundOfPlay(players, turn, board, s);
			break;
		}
	}

	public static void wallError(Wall wall, Board board, Player[] players, Integer turn, Scanner s) {
		// Check if the wall can't be instaured, restart the turn
		if (!wall.createWall(board)) {
			System.out.println("Error : Can't put a wall to these coordinates.");
			board.show();
			roundOfPlay(players, turn, board, s);
		} // Otherwise, check if all pawn can still reach the goal, if not, remove the
			// wall, then restart the turn
		else {
			// DFS sur tout les pions à faire ici
			// (new Dfs(board)).dfs(board, p.getPawn());
		}
	}

	public static boolean isWinnableForAll(Board board, Player[] players) {
		int width = (Board.TAILLE - 1) / 2;
		int nbVertex = (int) Math.pow((Board.TAILLE - 1) / 2, 2);
		Boolean[][] matrix = new Boolean[nbVertex][nbVertex];
		int begin;
		int end;
		
		for (int i = 0; i < nbVertex; i++) {
			for (int j = 0; j < i + 1; j++) {
				matrix[i][j] = false;
				matrix[j][i] = false;
			}
		}

		for (int i = 2; i < Board.TAILLE - 1; i += 2) {
			for (int j = 1; j < Board.TAILLE - 1; j += 2) {
				if (board.getBoard()[i][j] == Case.POTENTIALWALL) {
					begin = (i / 2 - 1) * (Board.TAILLE - 1) / 2 + (j - 1) / 2;
					end = begin + (Board.TAILLE - 1) / 2;
					matrix[begin][end] = true;
					matrix[end][begin] = true;
				}
			}
		}

		for (int i = 1; i < Board.TAILLE - 1; i += 2) {
			for (int j = 2; j < Board.TAILLE - 1; j += 2) {
				if (board.getBoard()[i][j] == Case.POTENTIALWALL) {
					begin = j / 2 - 1 + (i - 1) / 2 * width;
					end = begin + 1;
					matrix[begin][end] = true;
					matrix[end][begin] = true;
				}
			}
		}
		
		int i=0;
		while (i<4 && isWinnable(matrix,players[i])) {
			i++;
		}
		return i==4;
	}

	public static boolean isWinnable(Boolean[][] matrix, Player player) {
		int length = matrix.length;
		Boolean[] marking = new Boolean[length];
		for (int i = 0; i < length; i++) {
			marking[i] = false;
		}
		int vertex = 0 ;//player.getVertex();
		marking = dfs(matrix, vertex, marking);
		return false;
	}
	
	public static Boolean[] dfs(Boolean[][] matrix, Integer vertex, Boolean[] marking) {
		if (!marking[vertex]) {
			marking[vertex] = true;
			for (int i = 0; i < matrix.length; i++) {
				if (matrix[vertex][i]) {
					dfs(matrix, i, marking);
				}
			}
		}
		return marking;
	}
	
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int numberOfPlayers = 0;
		// Enter the number of players
		do {
			System.out.println("Please enter the number of players (2 or 4)");
			numberOfPlayers = s.nextInt();
		} while (numberOfPlayers != 2 && numberOfPlayers != 4);

		Player[] players = new Player[numberOfPlayers];
		for (int i = 0; i < numberOfPlayers; i++) {
			switch (i) {
			case 0:
				players[0] = new Player(Case.PLAYER1,
						new Pawn(new Position(Board.TAILLE - 2, Board.TAILLE / 2), Case.PLAYER1));
				break;
			case 1:
				players[1] = new Player(Case.PLAYER2, new Pawn(new Position(1, Board.TAILLE / 2), Case.PLAYER2));
				break;
			case 2:
				players[2] = new Player(Case.PLAYER3, new Pawn(new Position(Board.TAILLE / 2, 1), Case.PLAYER3));
				break;
			case 3:
				players[3] = new Player(Case.PLAYER4,
						new Pawn(new Position(Board.TAILLE / 2, Board.TAILLE - 2), Case.PLAYER4));
				break;
			default:
				break;
			}
		}

		Board board = new Board(numberOfPlayers);
		board.show();

		boolean win = false;
		int turn = 0;

		// While no one has won, play turn
		while (!win) {
			System.out.println("Tour de " + players[turn].getPlayerNumber() + ":");
			roundOfPlay(players, turn, board, s);
			board.show();
			// If someone has won, finish the game and display the winner
			if (players[turn].getPawn().isWinner()) {
				win = true;
				System.out.println(players[turn].getPlayerNumber() + " has won. Congratulations !");
			}
			turn = (turn + 1) % numberOfPlayers;
		}
		s.close();
	}
}
