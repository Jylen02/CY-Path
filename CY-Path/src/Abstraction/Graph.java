package Abstraction;

import java.util.HashMap;
import java.util.Map;

public class Graph{
	//Adjacency matrix
	private Boolean[][] matrix;
	//Vertex's grid
	private Player[][] grid;
	//Intersection of possible wall's placements
	
	private Integer playerNumber;
	private HashMap<Intersection,Boolean> wallIntersection;
	
	public HashMap<Intersection,Boolean> getWallIntersection(){
		return this.wallIntersection;
	}
	
	
	public Graph() {
		//this.matrix = initializeMatrix();
		//this.grid = initializeGrid(this.playerNumber);
		initializeWallIntersection();
	}
	public void initializeWallIntersection(){
		wallIntersection =  new HashMap<Intersection,Boolean>();
		for (int i=0; i<(10); i+=2) {
			for ( int j=0; j<10; j+=2) {
				if (i>0 && i<10) {
					if (j>0 && j<10) {
						Position t0 = new Position(i,j);
						Position t1 = new Position(i-1,j);
						Position t2 = new Position(i+1,j);
						Position t3 = new Position(i,j-1);
						Position t4 = new Position(i,j+1);
						
						Intersection i1= new Intersection (t0,t1);
						Intersection i2= new Intersection (t0,t2);
						Intersection i3= new Intersection (t0,t3);
						Intersection i4= new Intersection (t0,t4);
						wallIntersection.put(i1, true);
						wallIntersection.put(i2, true);
						wallIntersection.put(i3, true);
						wallIntersection.put(i4, true);
						System.out.println(i1+","+i2+","+i3+","+i4);
						
						
						}
					
					
				}
				
			}
			
			
		}
	}
	
	public Boolean[][] initializeMatrix() {
		Boolean[][] matrix = new Boolean[81][81];
		for (int i=0; i < 81; i++) {
			for (int j=0; j < i; j++) {
				//Top grid
				if (i<9) {
					if (Math.abs(j-i)==1 || i-j==9) {
						matrix[i][j]=true;
					}
				}
				//Left grid
				else if (i%9==0) {
					if (j-i==1 || Math.abs(j-i)==9) {
						matrix[i][j]=true;
					}
				}
				//Right grid
				else if (i%9==8) {
					if (j-i==1 || Math.abs(j-i)==9) {
						matrix[i][j]=true;
					}
				}
				//Bottom grid
				else if (i>71) {
					if (Math.abs(j-i)==1 || j-i==9) {
						matrix[i][j]=true;
					}
				}
				else {
					//Central grid
					if (Math.abs(j-i)==1 || Math.abs(j-i)==9) {
						matrix[i][j]=true;
					}
				}
				//false
				if (matrix[i][j]==null) {
					matrix[i][j]=false;
				}
				//Affichage
				if (matrix[i][j]==false) {
					System.out.print("0");
				}
				else if(matrix[i][j]==true) {
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
	

	
	
	public static void main(String[] args) {
<<<<<<< Updated upstream
		Graph p= new Graph();
		System.out.println("test");
=======
		Graph g = new Graph();
>>>>>>> Stashed changes
	}
}