package Abstraction;

import java.util.ArrayList;

public class Graph{
	//List of edges connecting a pair of vertex
	private ArrayList<Edge> edges;
	
	public Graph() {
		//Creating a list for the edges
		this.edges = new ArrayList<>();
		for (int i=0; i < 9; i++) {
			for (int j=0; j < 9; j++) {
				//Adding for each adjacent vertex, an edge connecting them
				addEdge(new Vertex(new Position(i,j)),new Vertex(new Position(i+1,j+1)));
				addEdge(new Vertex(new Position(i,j)),new Vertex(new Position(i+1,j-1)));
				addEdge(new Vertex(new Position(i,j)),new Vertex(new Position(i-1,j-1)));
				addEdge(new Vertex(new Position(i,j)),new Vertex(new Position(i-1,j+1)));
			}
		}
	}
	
	public void addEdge(Vertex v1, Vertex v2) {
		Edge edge = new Edge(v1, v2);
		this.edges.add(edge);
	}
}
