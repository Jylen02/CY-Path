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
				if (Math.abs(j-i)==1 || Math.abs(j-i)==9) {
					matrix[i][j]=true;
				}
				else {
					matrix[i][j]=false;
				}
			}
		}
		return matrix;
	}
	
	public Player[][] initializeGrid(Integer playerNumber) {
		
		return grid;
	}
	
	public Map<Intersection,Boolean> initializeWallIntersection(){
		return null;
	}
}