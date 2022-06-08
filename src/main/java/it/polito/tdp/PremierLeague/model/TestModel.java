package it.polito.tdp.PremierLeague.model;

public class TestModel {

	public static void main(String[] args) {
		
		Model model = new Model();
		
		String string= model.creaGrafo(2, 85);
		String s2= model.connessioneMax(2);
		
		System.out.println(string);
		System.out.println(s2);
		

	}

}
