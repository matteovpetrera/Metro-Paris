package it.polito.tdp.metroparis;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;



public class ProvaGrafo {
	
	public static void main(String[] args) {
		Graph<String, DefaultEdge> grafo = new SimpleGraph<String, DefaultEdge>(DefaultEdge.class);
	
		grafo.addVertex("r");
		grafo.addVertex("s");
		grafo.addVertex("t");
		grafo.addVertex("v");
		grafo.addVertex("w");
		grafo.addVertex("x");

		//.....
		
		grafo.addEdge("r", "s");
		grafo.addEdge("r", "v");
		grafo.addEdge("s", "w");
		grafo.addEdge("t", "x");
		grafo.addEdge("t", "w");
		grafo.addEdge("w", "x");
		//.....
		
		System.out.println(grafo.toString());
		
		System.out.println("Vertici: "+ grafo.vertexSet().size());
		System.out.println("Archi: " + grafo.edgeSet().size());
		
		for(String v: grafo.vertexSet()) {
			System.out.println("il vertice "+ v + " ha grado "+ grafo.degreeOf(v));
		}
	}
}
