package abstraction;

import java.io.IOException;
import java.util.Scanner;

/**
 * The main class for running the game.
 */
public class Quoridor {

	/**
	 * Prompts the user to enter the coordinates of a position and returns that
	 * position.
	 *
	 * @param s The Scanner used for reading user input.
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
	 * Prompts the user for confirmation of an action and performs the corresponding
	 * action based on the input.
	 *
	 * @param s       The Scanner used for reading user input.
	 * @param action  The action to be confirmed.
	 * @param board   The game board.
	 * @param players An array of Player objects representing the players in the
	 *                game.
	 * @param turn    The current player's turn.
	 */
	public static void confirmation(Scanner s, int action, Board board) {
		int confirm = 0;
		Pawn p = board.getPlayers()[board.getCurrentTurn()].getPawn();
		System.out.println(board);
		while (confirm != 1 && confirm != 2) {
			System.out.println("Confirm your action :");
			System.out.println(" - 1) Yes \n - 2) No ");
			confirm = s.nextInt();
			switch (confirm) {
			case 1:
				if (action == 1) {
					p.setLastPos(p.getPos());
				} else {
					board.getPlayers()[board.getCurrentTurn()]
							.setRemainingWall(board.getPlayers()[board.getCurrentTurn()].getRemainingWall() - 1);
				}
				break;
			case 2:
				if (action == 1) {
					p.resetMove(board);
				} else {
					Wall.removeLastWall(board);
				}
				System.out.println(board);
				roundOfPlay(board);
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
	 * @param board       The game board.
	 * @param players     An array of Player objects representing the players in the
	 *                    game.
	 * @param turn        The current player's turn.
	 * @param position    The position where the wall is to be placed.
	 * @param orientation The orientation of the wall.
	 * @return true if the wall is successfully placed, false otherwise.
	 */
	public static boolean placeWall(Board board, Position position, Orientation orientation) {
		if (!Wall.createWall(board, orientation, position)) {
			System.out.println(board);
			System.out.println("Error : Replay the round !");
			roundOfPlay(board);
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
	public static void roundOfPlay(Board board) {
		Scanner s = new Scanner(System.in);
		int action;
		Position position;
		// Verify if there max amount of wall is reach
		Pawn p = board.getPlayers()[board.getCurrentTurn()].getPawn();
		if (p.getPossibleMove().isEmpty()) {
			System.out.println("You can't make any move, your turn has been skipped");
		} else {
			// Display the possible destinations of a pawn
			System.out.println("Possible move :");
			System.out.println(p.getPossibleMove());

			// Check if the max amount of wall is reached, the player can only move
			if (board.getPlayers()[board.getCurrentTurn()].getRemainingWall() == 0) {
				System.out.println("The maximum amount of wall is reached, you can only move from now.");
				action = 1;
			}
			// Otherwise, choose an action
			else {
				System.out.println("Choice of action :");
				System.out.println(" - 1) Move the pawn \n - 2) Put a wall (Remaining wall(s) : "
						+ board.getPlayers()[board.getCurrentTurn()].getRemainingWall() + ")");
				System.out.println("Please select the action you want (1 or 2) :");
				action = s.nextInt();
			}
			switch (action) {
			case 1:
				// Enter the coordinates for the pawn's move
				position = enterPosition(s);
				// Check if the move is in the possible move's list then move
				if (p.move(board, position)) {
					confirmation(s, action, board);
				} else {
					System.out.println(board);
					System.out.println("Error : Please enter a valid coordinates.");
					roundOfPlay(board);
				}
				break;
			case 2:
				// Enter the coordinates for the wall's coordinates
				position = enterPosition(s);
				// Choose wall's orientation
				System.out.println("Choice of the wall's orientation :");
				System.out.println(" - 1) Vertical wall \n - 2) Horizontal wall ");
				System.out.println("Please select the action you want (1 or 2) :");
				int orientation = s.nextInt();
				switch (orientation) {
				case 1:
					if (placeWall(board, position, Orientation.VERTICAL)) {
						confirmation(s, action, board);
					}
					break;
				case 2:
					if (placeWall(board, position, Orientation.HORIZONTAL)) {
						confirmation(s, action, board);
					}
					break;
				default:
					// Wrong value of wall's orientation
					System.out.println(board);
					System.out.println("Error : Incorrect wall's orientation.");
					roundOfPlay(board);
					break;
				}
				break;
			default:
				// Wrong value of action
				System.out.println("Error : Action not available.");
				System.out.println(board);
				roundOfPlay(board);
				break;
			}
		}
	}
	
	/**
	 * This method saves the game to a specific save file. 
	 * The user can select the location they want to save the game.
	 *
	 * @param board the current state of the board
	 * @param s     the Scanner object for user input
	 * @return      0 if the user chooses to exit, 1 if the game is successfully saved
	 */
	public static int saveChoice(Board board, Scanner s) {
		int save = 0;
		System.out.println("Save location : ");
		System.out.println(" - 1) Save 1 \n - 2) Save 2 \n - 3) Save 3 \n - 4) Exit");
		System.out.println("Please select the location you want (1, 2, 3 or 4)");
		save = s.nextInt();
		if (save != 1 && save != 2 && save != 3 && save != 4) {
			saveChoice(board, s);
		} else if (save==4) {
			return 0;
		} else {
			try {
				SaveLoadGame.save(board, "save" + save + ".svg");
				System.out.println("Your game progress has been successfully saved.");
			} catch (IOException e) {
				System.out.println("An error has occurred, please try again.");
				saveChoice(board, s);
			}
		}
		return 1;
	}
	
	/**
	 * This method loads a game from a specific save file. 
	 * The user can select the location they want to load the game from.
	 *
	 * @param board the current state of the board
	 * @param s     the Scanner object for user input
	 * @return      0 if the user chooses to exit, 1 if the game is successfully loaded
	 */
	public static int loadChoice(Board board, Scanner s) {
		int load = 0;
		System.out.println("Save location : ");
		System.out.println(" - 1) Save 1 \n - 2) Save 2 \n - 3) Save 3 \n - 4) Exit ");
		System.out.println("Please select the location you want (1, 2, 3 or 4)");
		load = s.nextInt();
		if (load != 1 && load != 2 && load != 3 && load!=4) {
			loadChoice(board, s);
		} else if (load==4){
			return 0;
		} else {
			try {
				SaveLoadGame.load(board, "save" + load + ".svg");
				System.out.println("The game has been successfully loaded.");
			} catch (Exception e) {
				System.out.println("An error has occurred, please try again.");
				loadChoice(board, s);
			}
		}
		return 1;
	}

	/**
	 * The main method for running the game.
	 * 
	 * @param args Command-line arguments (not used).
	 */
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int numberOfPlayers = 0;
		Board board = new Board(numberOfPlayers);
		int choice = 0;
		while (choice != 1 && choice != 2) {
			System.out.println("Menu :");
			System.out.println(" - 1) New game \n - 2) Load a game");
			System.out.println("Please select the action you want (1 or 2) :");
			choice = s.nextInt();
			switch (choice) {
			case 1:
				// Enter the number of players
				while (numberOfPlayers != 2 && numberOfPlayers != 4) {
					System.out.println("Please enter the number of players (2 or 4)");
					numberOfPlayers = s.nextInt();
					if (numberOfPlayers != 2 && numberOfPlayers != 4) {
						System.out.println("Your response is not in the list of available answers.");
					}
				}
				s.nextLine();
				System.out.println("Please enter the name of each players");
				board = new Board(numberOfPlayers);
				for (int i = 0; i < numberOfPlayers; i++) {
					System.out.println("Player " + (i + 1) + " : ");
					for (Case value : Case.values()) {
						if (value.getValue() == i + 1) {
							board.getPlayers()[i] = new Player(s.nextLine(),
									new Pawn(board,
											new Position(Board.STARTINGPOSITIONPLAYERS[i].getX(),
													Board.STARTINGPOSITIONPLAYERS[i].getY()),
											value),
									Board.MAXWALLCOUNT / numberOfPlayers);
						}
					}
				}
				break;
			case 2:
				choice = Quoridor.loadChoice(board, s);
				break;
			default:
				System.out.println("Your response is not in the list of available answers.");
				break;
			}
		}
		System.out.println(board);
		boolean win = false;
		// Initialize first possible move for each pawn
		for (int i = 0; i < board.getPlayerNumber(); i++) {
			board.getPlayers()[i].getPawn().setPossibleMove(
					board.getPlayers()[i].getPawn().possibleMove(board, board.getPlayers()[i].getPawn().getPos()));
		}
		// While no one has won, play turn
		while (!win) {
			System.out.println(board.getPlayers()[board.getCurrentTurn()].getName() + "'s turn :");
			choice = 0;
			while (choice != 1 && choice != 2) {
				System.out.println("Do you want to save the game : ");
				System.out.println(" - 1) Yes \n - 2) No");
				System.out.println("Please select your choice (1 or 2) :");
				choice = s.nextInt();
				switch (choice) {
				case 1:
					choice = Quoridor.saveChoice(board, s);
					if (choice == 1) {
						Quoridor.main(args);
						win = true;
					}
					break;
				case 2:
					roundOfPlay(board);
					System.out.println(board);
					// If someone has won, finish the game and display the winner
					if (board.getPlayers()[board.getCurrentTurn()].getPawn().isWinner()) {
						win = true;
						System.out.println(
								board.getPlayers()[board.getCurrentTurn()].getName() + " has won. Congratulations !");
					} else {
						for (int i = 0; i < numberOfPlayers; i++) {
							board.getPlayers()[i].getPawn().setPossibleMove(board.getPlayers()[i].getPawn()
									.possibleMove(board, board.getPlayers()[i].getPawn().getPos()));
						}
						board.setCurrentTurn((board.getCurrentTurn() + 1) % board.getPlayerNumber());
					}
					break;
				default:
					System.out.println("Your response is not in the list of available answers.");
					break;
				}
			}
		}
		s.close();
	}
}
