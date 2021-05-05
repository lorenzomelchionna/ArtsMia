package it.polito.tdp.artsmia.model;

import java.util.HashMap;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.artsmia.db.ArtsmiaDAO;

public class Model {

	private Graph<ArtObject,DefaultWeightedEdge> Grafo;
	
	private ArtsmiaDAO dao;
	
	private Map<Integer,ArtObject> idMap;
	
	public Model() {
		
		dao = new ArtsmiaDAO();
		
		idMap = new HashMap<>();
		
	}
	
	public void creaGrafo() {
		
		Grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		dao.listObjects(idMap);
		Graphs.addAllVertices(Grafo, idMap.values());
		
		//APPROCCIO 1 (troppo lento! troppi vertici!)
		/*for(ArtObject a1 : this.Grafo.vertexSet()) {
			for(ArtObject a2 : this.Grafo.vertexSet()) {
				if(!a1.equals(a2) && !this.Grafo.containsEdge(a1,a2)) {
					
					int peso = dao.getPeso(a1,a2);
					
					if(peso > 0)
						Graphs.addEdge(this.Grafo, a1, a2, peso);
					
				}
			}
		}*/
		
		//APPROCCIO 3
		for(Adiacenza a : dao.getAdiacenze()) 
			Graphs.addEdge(this.Grafo, idMap.get(a.getId1()), idMap.get(a.getId2()), a.getPeso());
		
		System.out.println("GRAFO CREATO!");
		System.out.println("#VERTICI: "+Grafo.vertexSet().size());
		System.out.println("#ARCHI: "+Grafo.edgeSet().size());
		
	}

	public int getNVertici() {
		return this.Grafo.edgeSet().size();
	}
	
	public int getNArchi() {
		return this.Grafo.edgeSet().size();
	}
	
}
