package Abstraction;

import java.util.ArrayList;

public class Grid {
	private ArrayList<ArrayList<Integer>> grid;
	private Graph graph;
	
	public Grid() {
		//Creating a grid
		this.grid = new ArrayList<>();
		for (int i=0; i < 9; i++) {
			//Creating each line of the grid
			ArrayList<Integer> line = new ArrayList<>();
		    for (int j = 0; j < 9; j++) {
		    	//Initializing each line of the grid
		        line.add(j, 0);
		    }
		    //Initializing the start position of each pawn
		    
		//Adding each line to the grid
	    grid.add(line);
		}
		
		//Initializing the associated graph
		this.setGraph(new Graph());
		System.out.println("HELlOOOOOOO!!!");
	}

	public Graph getGraph() {
		return graph;
	}

	public void setGraph(Graph graph) {
		this.graph = graph;
	}

	
}
