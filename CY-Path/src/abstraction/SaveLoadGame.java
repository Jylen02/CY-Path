package abstraction;

import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;

public class SaveLoadGame {
	
	public static void save(Board board, String Filename) {
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
            System.out.println("Une erreur est survenue lors de la sauvegarde de la partie : " + e.getMessage());
        }
    }
	
	public static void load(Board board, String Filename) {
		try {
			FileInputStream fileIn = new FileInputStream("s.svg");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			
			board.setBoard((Case[][]) in.readObject());
			
			in.close();
			fileIn.close();
			
			System.out.println("La classe Board a été désérialisée avec succès !");
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
