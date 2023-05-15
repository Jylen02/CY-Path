package Abstraction;

import java.util.Map;

public class Graph{
	//Adjacency matrix
	private Boolean[][] matrix;
	//Vertex's grid
	private Player[][] grid;
	//Intersection of possible wall's placements
	private Map<Intersection,Boolean> wallIntersection;
	private Integer playerNumber;
	
	public Graph() {
		this.matrix = initializeMatrix();
		this.grid = initializeGrid(this.playerNumber);
		this.wallIntersection = initializeWallIntersection();
	}
	
	public Boolean[][] initializeMatrix() {
		Boolean[][] matrix = new Boolean[81][81];
		for (int i=0; i < 81; i++) {
			for (int j=0; j < 81; j++) {
				//Top grid
				if (i<9 || j<9) {
					if (j-i==1 || j-i==9) {
						matrix[i][j]=true;
					}
				}
				//Left grid
				else if (i%9==0 || j%9==0) {
					if (j-i==1 || j-i==9) {
						matrix[i][j]=true;
					}
				}
				//Right grid
				else if (i%9==8 || j%9==8) {
					if (j-i==1 || j-i==9) {
						matrix[i][j]=true;
					}
				}
				//Bottom grid
				else if (i>71 || j>71) {
					if (j-i==1 || j-i==9) {
						matrix[i][j]=true;
					}
				}
				else {
					//Central grid
					if (Math.abs(j-i)==1 || Math.abs(j-i)==9) {
						matrix[i][j]=true;
					}
					else {
						matrix[i][j]=false;
					}
				}
				if (matrix[i][j]=false) {
					System.out.print("0");
				}
				else {
					System.out.print("1");
				}
			}
			System.out.println("");
		}
		return matrix;
	}
	
	public Player[][] initializeGrid(Integer playerNumber) {
		Player[][] grid = new Player[9][9];
		return grid;
	}
	
	public Map<Intersection,Boolean> initializeWallIntersection(){
		return null;
	}
	
	
	public static void main(String[] args) {
		System.out.println("test");
	}
}