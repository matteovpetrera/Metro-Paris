package it.polito.tdp.metroparis.model;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.*;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.metroparis.db.MetroDAO;


public class Model {
	
	private Graph<Fermata, DefaultEdge> grafo ;
	private List<Fermata> fermate;
	private Map<Integer, Fermata> fermateIdMap;
	
	public void creaGrafo() {
		
		
		//crea il grafo di tipo simple
		this.grafo = new SimpleGraph<Fermata, DefaultEdge>(DefaultEdge.class);
		
		
		//aggiugni i vertici
		MetroDAO dao = new MetroDAO();
		this.fermate = dao.readFermate();
		
		fermateIdMap = new HashMap<>();
		
		for(Fermata f: this.fermate) {
			fermateIdMap.put(f.getIdFermata(), f);
		}
		
		Graphs.addAllVertices(this.grafo, this.fermate);
		
		//aggiungi gli archi
		
		//metodo 1: considero tutti i potenziali archi
		
		//for(Fermata partenza: this.grafo.vertexSet()) {
		//	for(Fermata arrivo: this.grafo.vertexSet()) {
		//		if(dao.isConnesse(partenza, arrivo) == true) {
		//			this.grafo.addEdge(partenza, arrivo);
		//		}
		//	}
		//}
		
		// metodo 2: data una fermata, trova la lista di quelle adiacente
		long tic = System.currentTimeMillis();
		for(Fermata partenza: this.grafo.vertexSet()) {
			List<Fermata> collegate = dao.trovaCollegate(partenza) ;
				
			for(Fermata arrivo: collegate) {
					this.grafo.addEdge(partenza, arrivo) ;
			}
		}
		long toc = System.currentTimeMillis();
		System.out.println("Elapsed time "+ (toc-tic));
				
		// metodo 2a: data una fermata, troviamo la lista di id connessi
		tic = System.currentTimeMillis();
		for(Fermata partenza: this.grafo.vertexSet()) {
			List<Fermata> collegate = dao.trovaIdCollegate(partenza, fermateIdMap) ;
					
			for(Fermata arrivo: collegate) {
				this.grafo.addEdge(partenza, arrivo) ;
			}
		}
		toc = System.currentTimeMillis();
		System.out.println("Elapsed time "+ (toc-tic));
		
		System.out.println("Grafo creato con "+this.grafo.vertexSet().size()+" vertici e "+this.grafo.edgeSet().size()+" archi");
	}
	
	
	
	public List<Fermata> getAllFermate(){
		
		MetroDAO dao = new MetroDAO();
		return dao.readFermate();
	}
	
	
	public boolean isGraficoLoaded() {
		return this.grafo.vertexSet().size()>0;
	}
}
