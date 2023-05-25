package abstraction;

import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;

/**
 * The SaveLoadGame class provides methods to save and load the current state of the game board to/from a file.
 */
public class SaveLoadGame {

	/**
	 * Saves the current state of the board to a specified file. The state of the
	 * board includes the current board layout, the number of players, the array of
	 * players, the current turn, and the last wall placed.
	 *
	 * @param board    The current state of the board
	 * @param Filename The name of the file to which the board state will be saved
	 * @throws IOException if an error occurs while writing to the file
	 */
	public static void save(Board board, String Filename) throws IOException {
		try {
			// Creates the file named : Filename
			FileOutputStream fileOut = new FileOutputStream(Filename);
			// Creates output stream in the file
			ObjectOutputStream out = new ObjectOutputStream(fileOut);

			// Binary conversion (bytes sequences)
			out.writeObject(board.getBoard());
			out.writeObject(board.getPlayerNumber());
			out.writeObject(board.getPlayers());
			out.writeObject(board.getCurrentTurn());
			out.writeObject(board.getLastWall());

			out.close();
			fileOut.close();

		} catch (IOException e) {
			throw new IOException(e.getMessage());
		}
	}

	/**
	 * Loads a saved state of the board from a specified file. The state of the
	 * board includes the current board layout, the number of players, the array of
	 * players, the current turn, and the last wall placed.
	 *
	 * @param board    The board object to which the loaded state will be applied
	 * @param Filename The name of the file from which the board state will be
	 *                 loaded
	 * @throws Exception if an error occurs while reading from the file or if the
	 *                   class of a serialized object cannot be found
	 */
	public static void load(Board board, String Filename) throws Exception {
		try {
			// Gets the file named : Filename
			FileInputStream fileIn = new FileInputStream(Filename);
			// Creates input stream for the file
			ObjectInputStream in = new ObjectInputStream(fileIn);

			// Same order as the previous method
			board.setBoard((Case[][]) in.readObject());
			board.setPlayerNumber((Integer) in.readObject());
			board.setPlayers((Player[]) in.readObject());
			board.setCurrentTurn((Integer) in.readObject());
			board.setLastWall((Wall) in.readObject());

			in.close();
			fileIn.close();

		} catch (Exception e) {
			throw new Exception();
		}
	}
}
