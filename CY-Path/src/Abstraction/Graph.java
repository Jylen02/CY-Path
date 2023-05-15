package Abstraction;

import java.util.ArrayList;

public class Graph extends Grid{
	//List of edges connecting a pair of vertex
	private ArrayList<Edge> edges;
	
	public Graph() {
		//Creating a list for the edges
		this.edges = new ArrayList<>();
		for (int i=0; i < 9; i++) {
			for (int j=0; j < 9; j++) {
				//Adding for each adjacent vertex, an edge connecting them
				try{
					addEdge(new Vertex(i,j),new Vertex(i+1,j+1));
					addEdge(new Vertex(i,j),new Vertex(i+1,j-1));
					addEdge(new Vertex(i,j),new Vertex(i-1,j-1));
					addEdge(new Vertex(i,j),new Vertex(i-1,j+1));
				} catch(IndexOutOfBoundsException e) {
					throw e;
				}
				
			}
		}
	}
	
	public void addEdge(Vertex v1, Vertex v2) {
		Edge edge = new Edge(v1, v2);
		this.edges.add(edge);
	}
}
