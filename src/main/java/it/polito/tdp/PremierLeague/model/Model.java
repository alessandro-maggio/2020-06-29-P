package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	
	
	private Map<Integer, Match> idMap;
	private PremierLeagueDAO dao;
	private Graph<Match, DefaultWeightedEdge> grafo;
	private List<Match> vertici;
	private List<Coppia> archi;
	private List<Match> best;
	private int livOttimo=0;
	
	
	public Model() {
		this.dao= new PremierLeagueDAO();
		this.idMap= this.dao.listAllMatches();
	}
	
	
	public String creaGrafo(int mese, int time) {
		
		this.grafo= new SimpleWeightedGraph<Match, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		vertici= dao.getVertici(idMap, mese);
		
		Graphs.addAllVertices(this.grafo, vertici);
		
		archi= dao.getArchi(idMap, mese, time);
		
		for(Coppia c: archi) {
			Graphs.addEdge(this.grafo, c.getM1(), c.getM2(), c.getPeso());
		}
		
		String s= "GRAFO CREATO!\n#VERTICI: "+this.grafo.vertexSet().size()+"\n#ARCHI: "+this.grafo.edgeSet().size();
		
		return s;
		
	}
	
	
	public String connessioneMax(int time) {
		
		Collections.sort(archi);
		int max=archi.get(0).getPeso();
		String string= "Coppie con connessione massina:\n\n";

		for(Coppia c: archi) {
			if(c.getPeso()==max) {
				string+=c.getM1()+" - "+c.getM2()+" ("+c.getPeso()+")\n";
			}
		}
		
		return string;
	
	}
	
	
	public String cercaPercorsoMax(Match partenza, Match arrivo) {
		
		List<Match> parziale= new ArrayList<>();
		best= new ArrayList<>();
		parziale.add(partenza);
		int L= 0;
		cerca(arrivo, parziale, L);
		
		
		String s="Percorso a peso massimo: \n";
		
		for(Match m: best) {
			s+=m.toString()+"\n";
		}
		
		return s;
	
	}
	
	private void cerca(Match arrivo, List<Match> parziale, int L) {
		
		if(parziale.get(parziale.size()-1).equals(arrivo)) {
			
			if(L > livOttimo) {
				
				best= new ArrayList<>(parziale);
				livOttimo= L;
			
			}
			return;
		}
			
			Match last= parziale.get(parziale.size()-1);
			List<Match> vicini= Graphs.neighborListOf(this.grafo, last);
			for(Match m: vicini) {
				DefaultWeightedEdge e= this.grafo.getEdge(parziale.get(parziale.size()-1), m);
				if(e!=null && !parziale.contains(m)) {
					
					if(last.getTeamHomeID()!= m.getTeamHomeID() || last.getTeamAwayID()!= m.getTeamAwayID()
							|| last.getTeamHomeID()!=m.getTeamAwayID() || last.getTeamAwayID() != m.getTeamAwayID()) {
					
					/*if((last.getTeamHomeID()==m.getTeamHomeID() && last.getTeamAwayID()==m.getTeamAwayID()) || (last.getTeamHomeID() == m.getTeamAwayID() && last.getTeamAwayID()== m.getTeamHomeID())) {
						continue;
					}*/
						
						parziale.add(m);
						L+=this.grafo.getEdgeWeight(e);
						cerca(arrivo, parziale, L);
						parziale.remove(m);
						L-=this.grafo.getEdgeWeight(e);
						
						
					}
				}
			}		
	}
	
	
	public List<Match> getVertici(){
		return vertici;
	}
	
	public int getPesoOttimo() {
		return livOttimo;
	}
	
	
}
