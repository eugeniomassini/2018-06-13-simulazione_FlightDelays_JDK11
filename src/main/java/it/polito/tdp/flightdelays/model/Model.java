package it.polito.tdp.flightdelays.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.flightdelays.db.FlightDelaysDAO;

public class Model {
	
	private FlightDelaysDAO dao;
	private Graph<Airport, DefaultWeightedEdge> graph;
	private List<Arco> tratte;
	private Map<String, Airport> idMap;
	
	public Model() {
		dao = new FlightDelaysDAO();
		idMap = new HashMap<String, Airport>();
		dao.loadAllAirports(idMap);
		
	}

	public List<Airline> getAirlines() {
		return dao.loadAllAirlines();
	}

	public void creaGrafo(Airline airline) {

		graph = new SimpleDirectedWeightedGraph<Airport, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		tratte = dao.getArchi(airline, idMap);
		for(Arco a: tratte) {
			
			if(this.graph.getEdge(a.getA1(), a.getA2())==null && this.graph.getEdge(a.getA2(), a.getA1())==null) {
				Graphs.addEdgeWithVertices(this.graph, a.getA1(), a.getA2(), a.getPeso());
				System.out.format("Aggiunto arco: %s, %s\n", a.getA1(), a.getA2());
			}
		}
		
		System.out.format("#Vertici: %d\n#Archi: %d", this.graph.vertexSet().size(), this.graph.edgeSet().size());
		
	}

	public List<Arco> getPeggiori(Airline airline) {
		
		Collections.sort(this.tratte, new Comparator<Arco>() {

			@Override
			public int compare(Arco o1, Arco o2) {
				// TODO Auto-generated method stub
				return -Double.compare(o1.getPeso(), o2.getPeso());
			}
			
		});
		
		return this.tratte;
	}

}
