package abstraction;

import java.util.Scanner;

/**
 * The main class for running the game.
 */
public class Quoridor {

	/**
	 * The main method for running the game.
	 * 
	 * @param args Command-line arguments (not used).
	 */
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int numberOfPlayers = 0;
		// Enter the number of players
		do {
			System.out.println("Please enter the number of players (2 or 4)");
			numberOfPlayers = s.nextInt();
		} while (numberOfPlayers != 2 && numberOfPlayers != 4);
		s.nextLine();
		System.out.println("Please enter the name of each players");
		Player[] players = new Player[numberOfPlayers];

		Board board = new Board(numberOfPlayers);

		for (int i = 0; i < numberOfPlayers; i++) {
			switch (i) {
			case 0:
				System.out.println("Player 1: ");
				players[0] = new Player(s.nextLine(),
						new Pawn(board, new Position(Board.SIZE - 2, Board.SIZE / 2), Case.PLAYER1));
				break;
			case 1:
				System.out.println("Player 2: ");
				players[1] = new Player(s.nextLine(), new Pawn(board, new Position(1, Board.SIZE / 2), Case.PLAYER2));
				break;
			case 2:
				System.out.println("Player 3: ");
				players[2] = new Player(s.nextLine(), new Pawn(board, new Position(Board.SIZE / 2, 1), Case.PLAYER3));
				break;
			case 3:
				System.out.println("Player 4: ");

				players[3] = new Player(s.nextLine(),
						new Pawn(board, new Position(Board.SIZE / 2, Board.SIZE - 2), Case.PLAYER4));
				break;
			default:
				break;
			}
		}
		System.out.println(board);

		boolean win = false;
		int turn = 0;
		// Initialize first possible move for each pawn
		for (int i = 0; i < numberOfPlayers; i++) {
			players[i].getPawn()
					.setPossibleDestination(players[i].getPawn().possibleMove(board, players[i].getPawn().getPos()));
		}
		// While no one has won, play turn
		
		while (!win) {
			System.out.println(players[turn].getName() + "'s turn :");
			board.roundOfPlay(players, turn);
			board.show();
			// If someone has won, finish the game and display the winner
			if (players[turn].getPawn().isWinner()) {
				win = true;
				System.out.println(players[turn].getName() + " has won. Congratulations !");
			}
			for (int i = 0; i < numberOfPlayers; i++) {
				players[i].getPawn().setPossibleDestination(
						players[i].getPawn().possibleMove(board, players[i].getPawn().getPos()));
			}
			turn = (turn + 1) % numberOfPlayers;
		}
		s.close();
	}
}
