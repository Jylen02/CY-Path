package Abstraction;

import java.util.HashMap;
import java.util.Map;

public class Graph {
	// Adjacency matrix
	private Boolean[][] matrix;
	// Vertex's grid
	private Player[][] grid;
	// Intersection of possible wall's placements
	private Map<Intersection, Boolean> wallIntersection;
	private Integer playerNumber;

	public Graph() {
		//initializeMatrix();
		// initializeGrid(this.playerNumber);
	    initializeWallIntersection();
	}

	public void initializeMatrix() {
		matrix = new Boolean[81][81];
		for (int i = 0; i < 81; i++) {
			for (int j = 0; j < i; j++) {
				// Top grid
				if (i < 9) {
					if (Math.abs(j - i) == 1 || i - j == 9) {
						matrix[i][j] = true;
						matrix[j][i] = true;
					}
				}
				// Left grid
				else if (i % 9 == 0) {
					if (j - i == 1 || Math.abs(j - i) == 9) {
						matrix[i][j] = true;
						matrix[j][i] = true;
					}
				}
				// Right grid
				else if (i % 9 == 8) {
					if (j - i == 1 || Math.abs(j - i) == 9) {
						matrix[i][j] = true;
						matrix[j][i] = true;
					}
				}
				// Bottom grid
				else if (i > 71) {
					if (Math.abs(j - i) == 1 || j - i == 9) {
						matrix[i][j] = true;
						matrix[j][i] = true;
					}
				} else {
					// Central grid
					if (Math.abs(j - i) == 1 || Math.abs(j - i) == 9) {
						matrix[i][j] = true;
						matrix[j][i] = true;
					}
				}
				// false
				if (matrix[i][j] == null) {
					matrix[i][j] = false;
					matrix[j][i] = false;
				}
				// Affichage
				if (matrix[i][j] == false) {
					System.out.print("0");
				} else if (matrix[i][j] == true) {
					System.out.print("1");
				}
			}
			System.out.println("");
		}
	}

	public void initializeGrid(Integer playerNumber) {
		grid = new Player[9][9];
	}

	public void initializeWallIntersection() {
		wallIntersection = new HashMap<Intersection, Boolean>();
		/*for (int i = 0; i < 10; i += 2) {
			for (int j = 0; j < 10; j += 2) {
				if (i > 0 && i < 10 && j > 0 && j < 10) {
					Position t0 = new Position(i, j);
					Position t1 = new Position(i - 1, j);
					Position t2 = new Position(i + 1, j);
					Position t3 = new Position(i, j - 1);
					Position t4 = new Position(i, j + 1);

					Intersection i1 = new Intersection(t0, t1);
					Intersection i2 = new Intersection(t0, t2);
					Intersection i3 = new Intersection(t0, t3);
					Intersection i4 = new Intersection(t0, t4);
					wallIntersection.put(i1, true);
					wallIntersection.put(i2, true);
					wallIntersection.put(i3, true);
					wallIntersection.put(i4, true);
					System.out.println(i1 + "," + i2 + "," + i3 + "," + i4);
				}
			}
		}*/
		//line intersection
		System.out.println("Intersection en ligne : ");
		for (int i = 0; i < 10; i ++) {
			for (int j = 0; j < 9; j ++) {
					Position t0 = new Position(i, j);
					Position t1 = new Position(i, j+1);
					Intersection i1 = new Intersection(t0, t1);
					wallIntersection.put(i1, true);
					System.out.println("iL="+i1);
			}
		}
		//column intersection
		System.out.println("Intersection en colonne : ");
		for (int k = 0; k < 9; k ++) {
			for (int l = 0; l < 10; l ++) {
					Position t0 = new Position(k, l);
					Position t1 = new Position(k+1, l);
					Intersection i1 = new Intersection(t0, t1);
					wallIntersection.put(i1, true);
					System.out.println("iC="+i1);
			}
		}
	}
		

	public static void main(String[] args) {
		Graph p = new Graph();
	}
}