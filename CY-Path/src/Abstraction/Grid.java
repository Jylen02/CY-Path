package Abstraction;

import java.util.ArrayList;

public class Grid {
	private ArrayList<ArrayList<String>> grid;
	private Graph graph;
	
	public Grid() {
		//Creating a grid
		this.grid = new ArrayList<>();
		for (int i=0; i < 9; i++) {
			//Creating each line of the grid
			ArrayList<String> line = new ArrayList<>();
		    for (int j = 0; j < 9; j++) {
		    	//Initializing each line of the grid
		        line.add(j, ".");
		    }
		//Adding each line to the grid
	    grid.add(line);
		}
		
		//Initializing the associated graph
		this.graph = new Graph();
	}

	
}
