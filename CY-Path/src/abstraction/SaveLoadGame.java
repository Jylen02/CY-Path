package abstraction;

import java.io.FileOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;

public class SaveLoadGame {

	public static void save(Board board, String Filename) throws IOException {
		try {
			FileOutputStream fileOut = new FileOutputStream(Filename);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);

			out.writeObject(board.getBoard());
			out.writeObject(board.getPlayerNumber());
			out.writeObject(board.getPlayers());
			out.writeObject(board.getCurrentTurn());
			out.writeObject(board.getLastWall());

			out.close();
			fileOut.close();

			System.out.println("Partie sauvegardée avec succès dans le fichier : " + Filename);
		} catch (IOException e) {
			throw new IOException(e.getMessage());
		}
	}

	public static void load(Board board, String Filename) throws IOException {
		try {
			FileInputStream fileIn = new FileInputStream(Filename);
			ObjectInputStream in = new ObjectInputStream(fileIn);

			board.setBoard((Case[][]) in.readObject());
			board.setPlayerNumber((Integer) in.readObject());
			board.setPlayers((Player[]) in.readObject());
			board.setCurrentTurn((Integer) in.readObject());
			board.setLastWall((Wall) in.readObject());
			in.close();
			fileIn.close();

		} catch (IOException e) {
			throw new IOException();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
