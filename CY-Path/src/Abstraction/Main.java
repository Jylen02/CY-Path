package Abstraction;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * The main class for running the game.
 */
public class Main {
	/**
	 * Check if maximum amount of walls has been placed on the board.
	 * 
	 * @param board The game board.
	 * @return true if less than 20 walls have been placed, false otherwise.
	 */
	public static boolean accountWall(Board board) {
		if (board.getWallCount() < 20) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Handles the turn for a player.
	 * 
	 * @param players Array of players in the game.
	 * @param turn    The current turn number.
	 * @param board   The game board.
	 * @param s       Scanner for input.
	 */
	
	public static void roundOfPlay(Player[] players, Integer turn, Board board, Scanner s) {
		// Verify if there max amount of wall is reach
		boolean endWall = accountWall(board);
		Pawn p = players[turn].getPawn();
		// Display the possible destinations of a pawn
		System.out.println("Possible move :");
		System.out.println(p.getPossibleDestination());
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
			if (p.getPossibleDestination().contains(position)) {
				board.move(position, p);
				p.setPossibleDestination(p.possibleMove(board, p.getPos()));
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

	/**
	 * Handles the possible errors that could happen when a player tries to place a
	 * wall.
	 * 
	 * @param wall    The wall to place.
	 * @param board   The game board.
	 * @param players Array of players in the game.
	 * @param turn    The current turn number.
	 * @param s       Scanner for input.
	 */
	public static void wallError(Wall wall, Board board, Player[] players, Integer turn, Scanner s) {
		// Check if the wall can't be instaured, restart the turn
		if (!wall.createWall(board)) {
			System.out.println("Error : Can't put a wall to these coordinates.");
			board.show();
			roundOfPlay(players, turn, board, s);
		} // Otherwise, check if all pawn can still reach the goal, if not, remove the
			// wall, then restart the turn
		else {
			for (int i=0; i<players.length; i++) {
				players[i].getPawn().setPossibleDestination(players[i].getPawn().possibleMove(board,players[i].getPawn().getPos()));
            }
			if (!isWinnableForAll(board, players)) {
				wall.updateWall(board, Case.POTENTIALWALL, -1);
				for (int i=0; i<players.length; i++) {
					players[i].getPawn().setPossibleDestination(players[i].getPawn().possibleMove(board,players[i].getPawn().getPos()));
	            }
				System.out.println("Error : This wall blocks a player.");
				board.show();
				roundOfPlay(players, turn, board, s);
			}
		}
	}

	/**
	 * Checks if the current board configuration allows all players to reach their
	 * respective goals.
	 * 
	 * @param board   The game board.
	 * @param players Array of players in the game.
	 * @return true if all players can reach their goals, false otherwise.
	 */
	public static boolean isWinnableForAll(Board board, Player[] players) {
		int i=0;
		while (i<players.length && isWinnable(board,players[i].getPawn())) {
			i++;
		}
		return i==players.length;
	}

	/**
	 * Check if a game board is winnable for a given player.
	 * 
	 * @param board  The game board.
	 * @param player The Pawn of the player to check.
	 * @return true if the game is winnable for the player, false otherwise.
	 */
	public static boolean isWinnable(Board board, Pawn player) {
		Set<Position> marking = new HashSet<Position>();
		marking.add(player.getPos());
		for (Position pos : player.getPossibleDestination()) {
			marking = dfs(board, pos, player, marking, player.getPossibleDestination());
		}
		for (Position pos: player.getFinishLine()) {
			if (marking.contains(pos)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Performs a depth-first search (DFS) on the game board from a given position.
	 * 
	 * @param board   The game board.
	 * @param pos     The position from which to start the DFS.
	 * @param player  The player for whom to perform the DFS.
	 * @param marking A set of positions marking the nodes visited during the DFS.
	 * @param possibleDestination	The set of Positions representing the possible destinations for the Pawn.
	 * @return The updated marking set after performing the DFS.
	 */
	public static Set<Position> dfs(Board board, Position pos, Pawn player, Set<Position> marking, Set<Position> possibleDestination) {
		if (!marking.contains(pos)) {
			marking.add(pos);
			possibleDestination = player.possibleMove(board, pos);
			for (Position pos1 : possibleDestination) {
				marking = dfs(board, pos1, player, marking, possibleDestination);
			}
		}
		return marking;
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
						new Pawn(board, new Position(Board.TAILLE - 2, Board.TAILLE / 2), Case.PLAYER1));
				break;
			case 1:
				System.out.println("Player 2: ");
				players[1] = new Player(s.nextLine(), new Pawn(board, new Position(1, Board.TAILLE / 2), Case.PLAYER2));
				break;
			case 2:
				System.out.println("Player 3: ");
				players[2] = new Player(s.nextLine(), new Pawn(board, new Position(Board.TAILLE / 2, 1), Case.PLAYER3));
				break;
			case 3:
				System.out.println("Player 4: ");
				
				players[3] = new Player(s.nextLine(),
						new Pawn(board, new Position(Board.TAILLE / 2, Board.TAILLE - 2), Case.PLAYER4));
				break;
			default:
				break;
			}
		}

		board.show();

		boolean win = false;
		int turn = 0;
		//Initialize first possible move for each pawn
		for (int i=0; i<numberOfPlayers; i++) {
			players[i].getPawn().setPossibleDestination(players[i].getPawn().possibleMove(board,players[i].getPawn().getPos()));
		}
		//While no one has won, play turn
		while (!win) {
			System.out.println(players[turn].getName() + "'s turn :");
			roundOfPlay(players, turn, board, s);
			board.show();
			// If someone has won, finish the game and display the winner
			if (players[turn].getPawn().isWinner()) {
				win = true;
				System.out.println(players[turn].getName() + " has won. Congratulations !");
			}
			for (int i=0; i<numberOfPlayers; i++) {
				players[i].getPawn().setPossibleDestination(players[i].getPawn().possibleMove(board,players[i].getPawn().getPos()));
            }
			turn = (turn + 1) % numberOfPlayers;
		}
		s.close();
	}
}
