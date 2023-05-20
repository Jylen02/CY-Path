package abstraction;

import java.util.Scanner;

/**
 * The main class for running the game.
 */
public class Quoridor {
	
	/**
	  * Prompts the user to enter the coordinates of a position and returns that position.
	 *
	 * @param s 	The Scanner used for reading user input.
	 * @return The Position created from the entered coordinates.
	 */
	public static Position enterPosition(Scanner s) {
		System.out.println("Please enter the coordinates : ");
		System.out.print("row = ");
		int row = s.nextInt();
		System.out.print("column = ");
		int column = s.nextInt();
		return new Position(row, column);
	}
	
	/**
	 * Prompts the user for confirmation of an action and performs the corresponding action based on the input.
	 *
	 * @param s        The Scanner used for reading user input.
	 * @param action   The action to be confirmed.
	 * @param board    The game board.
	 * @param players  An array of Player objects representing the players in the game.
	 * @param turn     The current player's turn.
	 */
	public static void confirmation(Scanner s, int action, Board board, Player[] players, Integer turn) {
		int confirm = 0;
		Pawn p = players[turn].getPawn();
		System.out.println(board);
		while (confirm != 1 && confirm != 2) {
			System.out.println("Confirm your action :");
			System.out.println(" - Yes : 1 \n - No : 2");
			confirm = s.nextInt();
			switch (confirm) {
			case 1:
				if (action == 1) {
					p.setLastPos(p.getPos());
				} else {
					players[turn].setRemainingWall(players[turn].getRemainingWall() - 1);
				}
				break;
			case 2:
				if (action == 1) {
					p.resetMove(board);
				} else {
					Wall.removeLastWall(board);
				}
				System.out.println(board);
				roundOfPlay(board, players, turn);
				break;
			default:
				System.out.println("Error : Wrong value of confirmation");
				break;
			}
		}
	}

	/**
	 * Places a wall on the game board at the specified position and orientation.
	 *
	 * @param board       	The game board.
	 * @param players     	An array of Player objects representing the players in the game.
	 * @param turn        	The current player's turn.
	 * @param position    	The position where the wall is to be placed.
	 * @param orientation 	The orientation of the wall.
	 * @return true if the wall is successfully placed, false otherwise.
	 */
	public static boolean placeWall(Board board, Player[] players, Integer turn, Position position,
			Orientation orientation) {
		if (!Wall.createWall(board, players, turn, orientation, position)) {
			System.out.println(board);
			System.out.println("Error : Replay the round !");
			roundOfPlay(board, players, turn);
			return false;
		}
		return true;

	}

	/**
	 * Handles the turn for a player.
	 * 
	 * @param board   The game board.
	 * @param players Array of players in the game.
	 * @param turn    The current turn number.
	 */
	public static void roundOfPlay(Board board, Player[] players, Integer turn) {
		Scanner s = new Scanner(System.in);
		int action;
		Position position;
		// Verify if there max amount of wall is reach
		Pawn p = players[turn].getPawn();
		// Display the possible destinations of a pawn
		System.out.println("Possible move :");
		System.out.println(p.getPossibleMove());

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
			position = enterPosition(s);
			// Check if the move is in the possible move's list then move
			if (p.move(board, position)) {
				confirmation(s, action, board, players, turn);
			} else {
				System.out.println(board);
				System.out.println("Error : Please enter a valid coordinates.");
				roundOfPlay(board, players, turn);
			}
			break;
		case 2:
			// Enter the coordinates for the wall's coordinates
			position = enterPosition(s);
			// Choose wall's orientation
			System.out.println("Choice of the wall's orientation :");
			System.out.println(" - Vertical wall : 1 \n - Horizontal wall : 2");
			System.out.println("Please select the action you want (1 or 2) :");
			int orientation = s.nextInt();
			switch (orientation) {
			case 1:
				if (placeWall(board, players, turn, position, Orientation.VERTICAL)) {
					confirmation(s, action, board, players, turn);
				}
				break;
			case 2:
				if (placeWall(board, players, turn, position, Orientation.HORIZONTAL)) {
					confirmation(s, action, board, players, turn);
				}
				break;
			default:
				// Wrong value of wall's orientation
				System.out.println(board);
				System.out.println("Error : Incorrect wall's orientation.");
				roundOfPlay(board, players, turn);
				break;
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
		while (numberOfPlayers != 2 && numberOfPlayers != 4){
			System.out.println("Please enter the number of players (2 or 4)");
			numberOfPlayers = s.nextInt();
		} 
		s.nextLine();
		System.out.println("Please enter the name of each players");
		Player[] players = new Player[numberOfPlayers];
		Board board = new Board(numberOfPlayers);
		for (int i = 0; i < numberOfPlayers; i++) {
			System.out.println("Player " + (i + 1) + " : ");
			for (Case value : Case.values()) {
				if (value.getValue() == i + 1) {
					players[i] = new Player(s.nextLine(),
							new Pawn(board,
									new Position(Board.STARTINGPOSITIONPLAYERS[i].getX(),
											Board.STARTINGPOSITIONPLAYERS[i].getY()),
									value),
							Board.MAXWALLCOUNT / numberOfPlayers);
				}
			}
		}
		System.out.println(board);
		boolean win = false;
		int turn = 0;
		// Initialize first possible move for each pawn
		for (int i = 0; i < numberOfPlayers; i++) {
			players[i].getPawn()
					.setPossibleMove(players[i].getPawn().possibleMove(board, players[i].getPawn().getPos()));
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
				players[i].getPawn().setPossibleMove(
						players[i].getPawn().possibleMove(board, players[i].getPawn().getPos()));
			}
			turn = (turn + 1) % numberOfPlayers;
		}
		s.close();
	}
}
