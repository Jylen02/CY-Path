package Abstraction;

import javafx.util.Pair;

public class Edge {
	private Pair<Vertex,Vertex> edge;
	
	public Edge(Vertex v1, Vertex v2) {
		this.edge = new Pair<>(v1, v2);
	}
	
	public Pair<Vertex, Vertex> getEdge(){
		return edge;
	}
}
