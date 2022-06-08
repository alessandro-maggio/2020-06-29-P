package it.polito.tdp.PremierLeague.model;

public class Adiacenza {
	
	private Match vicino;
	private int peso;
	
	public Adiacenza(Match vicino, int peso) {
		super();
		this.vicino = vicino;
		this.peso = peso;
	}

	public Match getVicino() {
		return vicino;
	}

	public int getPeso() {
		return peso;
	}
	
	
	
	
	

}
