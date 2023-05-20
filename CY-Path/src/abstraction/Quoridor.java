package abstraction;

import java.util.Scanner;

/**
 * The main class for running the game.
 */
public class Quoridor {

	/**
	 * Handles the turn for a player.
	 * 
	 * @param board   The game board.
	 * @param players Array of players in the game.
	 * @param turn    The current turn number.
	 */
	public static void roundOfPlay(Board board, Player[] players, Integer turn) {
		@SuppressWarnings("resource")
		Scanner s = new Scanner(System.in);
		int action;
		int row;
		int column;
		Position position;
		int confirm = 0;

		// Verify if there max amount of wall is reach
		Pawn p = players[turn].getPawn();

		// Display the possible destinations of a pawn
		System.out.println("Possible move :");
		System.out.println(p.getPossibleDestination());

		// Check if the max amount of wall is reached, the player can only move
		if (players[turn].getRemainingWall() == 0) {
			System.out.println("The maximum amount of wall is reached, you can only move from now.");
			action = 1;
		}
		// Otherwise, choose an action
		else {
			System.out.println("Choice of action :");
			System.out.println(" - Move the pawn : 1 \n - Put a wall (Remaining wall(s) : "
					+ players[turn].getRemainingWall() + ") : 2");
			System.out.println("Please select the action you want (1 or 2) :");
			action = s.nextInt();
		}

		switch (action) {
		case 1:
			// Enter the coordinates for the pawn's move
			System.out.println("Please enter the coordinates : ");
			System.out.print("row = ");
			row = s.nextInt();
			System.out.print("column = ");
			column = s.nextInt();
			position = new Position(row, column);
			// Check if the move is in the possible move's list then move
			if (!p.move(board, position)) {
				System.out.println(board);
				System.out.println("Error : Please enter a valid coordinates.");
				roundOfPlay(board, players, turn);
			}
			
			System.out.println(board);
			while (confirm != 1 && confirm != 2) {
				System.out.println("Confirm your action :");
				System.out.println(" - Yes : 1 \n - No : 2");
				confirm = s.nextInt();
				switch (confirm) {
				case 1:
					p.setLastPos(p.getPos());
					break;
				case 2:
					p.resetMove(board);
					System.out.println(board);
					roundOfPlay(board, players, turn);
					break;
				default:
					System.out.println("Error : Wrong value of confirmation");
					break;
				}
			}
			break;

		case 2:
			// Enter the coordinates for the wall's coordinates
			System.out.println("Please enter the coordinates : ");
			System.out.print("row = ");
			row = s.nextInt();
			System.out.print("column = ");
			column = s.nextInt();
			position = new Position(row, column);

			// Choose wall's orientation
			System.out.println("Choice of the wall's orientation :");
			System.out.println(" - Vertical wall : 1 \n - Horizontal wall : 2");
			System.out.println("Please select the action you want (1 or 2) :");
			int orientation = s.nextInt();
			switch (orientation) {
			case 1:
				if (!Wall.createWall(board, players, turn, Orientation.VERTICAL, position)) {
					System.out.println(board);
					System.out.println("Error : Replay the round !");
					roundOfPlay(board, players, turn);
				}
				break;
			case 2:
				if (!Wall.createWall(board, players, turn, Orientation.HORIZONTAL, position)) {
					System.out.println(board);
					System.out.println("Error : Replay the round !");
					roundOfPlay(board, players, turn);
				}
				break;
			default:
				// Wrong value of wall's orientation
				System.out.println(board);
				System.out.println("Error : Incorrect wall's orientation.");
				roundOfPlay(board, players, turn);
				break;
			}
			System.out.println(board);
			while (confirm != 1 && confirm != 2) {
				System.out.println("Confirm your action :");
				System.out.println(" - Yes : 1 \n - No : 2");
				confirm = s.nextInt();
				switch (confirm) {
				case 1:
					players[turn].setRemainingWall(players[turn].getRemainingWall() - 1);
					break;
				case 2:
					Wall.removeLastWall(board);
					System.out.println(board);
					roundOfPlay(board, players, turn);
					break;
				default:
					System.out.println("Error : Wrong value of confirmation");
					break;
				}
			}
			break;
		default:
			// Wrong value of action
			System.out.println("Error : Action not available.");
			System.out.println(board);
			roundOfPlay(board, players, turn);
			break;
		}
	}

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
						new Pawn(board, new Position(Board.SIZE - 2, Board.SIZE / 2), Case.PLAYER1),
						Board.MAXWALLCOUNT / numberOfPlayers);
				break;
			case 1:
				System.out.println("Player 2: ");
				players[1] = new Player(s.nextLine(), new Pawn(board, new Position(1, Board.SIZE / 2), Case.PLAYER2),
						Board.MAXWALLCOUNT / numberOfPlayers);
				break;
			case 2:
				System.out.println("Player 3: ");
				players[2] = new Player(s.nextLine(), new Pawn(board, new Position(Board.SIZE / 2, 1), Case.PLAYER3),
						Board.MAXWALLCOUNT / numberOfPlayers);
				break;
			case 3:
				System.out.println("Player 4: ");

				players[3] = new Player(s.nextLine(),
						new Pawn(board, new Position(Board.SIZE / 2, Board.SIZE - 2), Case.PLAYER4),
						Board.MAXWALLCOUNT / numberOfPlayers);
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
			roundOfPlay(board, players, turn);
			System.out.println(board);
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
