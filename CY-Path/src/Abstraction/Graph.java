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
	//Test deleteIntersection
	/*private Position p1= new Position(1,1);
	private Position p2= new Position(1,2);
	private Intersection i0= new Intersection(p1,p2);*/
	//Print with color
	public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_CYAN = "\u001B[36m";
	
	

	public Graph() {
		initializeMatrix();
		initializeGrid(this.playerNumber);
	    initializeWallIntersection();
	}

	public void initializeMatrix() {
		matrix = new Boolean[81][81];
		//Set matrix's default value to false
		for (int i = 0; i < 81; i++) {
			for (int j = 0; j < 81; j++) {
				matrix[i][j] = false ;
			}
		}
		
		for (int i = 0; i < 81; i++) {
			for (int j = 0; j < i+1; j++) {
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
					if (i - j == 1 || Math.abs(j - i) == 9) {
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
			}
		}
		//Display the adjacency matrix
		//showMatrix();
	}

	public void showMatrix() {
		System.out.print("\t  ");
		for (int c = 0; c < 81; c++) {
			System.out.print(c + " ");
			if (c<10) {
				System.out.print(" ");
			}
		}
		System.out.println("");
		for (int i = 0; i < 81; i++) {
			System.out.print(i + "\t:");
			for (int j = 0; j < 81; j++) {
				if (matrix[i][j] == false) {
					System.out.print(" 0 ");
				} else if (matrix[i][j] == true) {
					System.out.print(ANSI_CYAN + " 1 " + ANSI_RESET);
					
				}
			}
			System.out.println("");
		}
	}
	
	public void initializeGrid(Integer playerNumber) {
		grid = new Player[9][9];
		//Set the grid's default value to EMPTY
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				grid[i][j] = Player.EMPTY ;
			}
		}
		if (playerNumber >= 2) {
			grid[8][5] = Player.PLAYER1;
			grid[0][5] = Player.PLAYER2;
			if (playerNumber == 4) {
				grid[5][0] = Player.PLAYER3;
				grid[5][8] = Player.PLAYER4;
			}
		}
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
		//for intersections that are not on the border
		//line intersection
		System.out.println("Intersection en ligne (=true): ");
		for (int i = 1; i < 9; i ++) {
			for (int j = 0; j < 9; j ++) {
					Position t0 = new Position(i, j);
					Position t1 = new Position(i, j+1);
					Intersection i1 = new Intersection(t0, t1);
					wallIntersection.put(i1, true);
					System.out.println("iL="+i1);
			}
		}
		//column intersection
		System.out.println("Intersection en colonne (=true): ");
		for (int k = 0; k < 9; k ++) {
			for (int l = 1; l < 9; l ++) {
					Position t0 = new Position(k, l);
					Position t1 = new Position(k+1, l);
					Intersection i1 = new Intersection(t0, t1);
					wallIntersection.put(i1, true);
					System.out.println("iC="+i1);
			}
		}
		//for intersections that are on the border
		//line intersection
		System.out.println("Intersection sur les bords ligne (=false): ");
		for (int m=0; m<9; m++) {
			Position t0 = new Position(0, m);
			Position t0b = new Position(9, m);
			Position t1 = new Position(0, m+1);
			Position t1b = new Position(9, m+1);
			Intersection i1 = new Intersection(t0, t1);
			Intersection i1b = new Intersection(t0b, t1b);
			wallIntersection.put(i1, false);
			wallIntersection.put(i1b, false);
			System.out.println("iL="+i1);
			System.out.println("iL="+i1b);
		}
		//column intersection
				System.out.println("Intersection sur les bords colonne (=false): ");
				for (int n=0; n<9; n++) {
					Position t0 = new Position(n, 0);
					Position t0b = new Position(n, 9);
					Position t1 = new Position(n+1, 0);
					Position t1b = new Position(n+1, 9);
					Intersection i1 = new Intersection(t0, t1);
					Intersection i1b = new Intersection(t0b, t1b);
					wallIntersection.put(i1, false);
					wallIntersection.put(i1b, false);
					System.out.println("iC="+i1);
					System.out.println("iC="+i1b);
					
				}
				
	
	
		
	}
	//delete edge from Matrix
	public void deleteEdge(int v1, int v2) {
		if (this.matrix[v1][v2]==true) {
			//System.out.println("matrix ("+v1+","+v2+"):"+matrix[v1][v2]);
			this.matrix[v1][v2]=false;
			//System.out.println("matrix ("+v1+","+v2+"):"+matrix[v1][v2]);
		}
		else {
			System.out.println("AlreadyBreak");
			// exception à traiter
			//throw new AlreadyBreakException();
		}
		
	}
	//add edge from Matrix
	public void addEdge(int v1, int v2) {
		if (this.matrix[v1][v2]==false) {
			this.matrix[v1][v2]=true;
			//System.out.println("matrix ("+v1+","+v2+"):"+matrix[v1][v2]);
		}
	}
		
	// delete an intersection
	public void deleteIntersection (Intersection i1) {
			/*wallIntersection.remove(i1);
			wallIntersection.put(i1,false);	*/
			wallIntersection.replace(i1, false);
	}

	public static void main(String[] args) {
		Graph p = new Graph();

		//deleteEdge(0,1);
		//deleteIntersection(i0);
	}
}