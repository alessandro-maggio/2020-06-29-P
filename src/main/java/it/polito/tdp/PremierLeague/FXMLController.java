/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.PremierLeague;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.PremierLeague.model.Match;
import it.polito.tdp.PremierLeague.model.Mese;
import it.polito.tdp.PremierLeague.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnConnessioneMassima"
    private Button btnConnessioneMassima; // Value injected by FXMLLoader

    @FXML // fx:id="btnCollegamento"
    private Button btnCollegamento; // Value injected by FXMLLoader

    @FXML // fx:id="txtMinuti"
    private TextField txtMinuti; // Value injected by FXMLLoader

    @FXML // fx:id="cmbMese"
    private ComboBox<Mese> cmbMese; // Value injected by FXMLLoader

    @FXML // fx:id="cmbM1"
    private ComboBox<Match> cmbM1; // Value injected by FXMLLoader

    @FXML // fx:id="cmbM2"
    private ComboBox<Match> cmbM2; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doConnessioneMassima(ActionEvent event) {
    	
    	
    	
    	try {
    		doCreaGrafo(event);
    		String s= this.model.connessioneMax(Integer.parseInt(txtMinuti.getText()));
    		txtResult.clear();
        	txtResult.appendText(s);
    		
			
		} catch (NumberFormatException e) {
			txtResult.clear();
    		txtResult.appendText("Inserire un numero MIN valido!\n");
		}
    	
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	
    	try {
    		
    		if(cmbMese.getValue()==null) {
    			txtResult.clear();
    			txtResult.appendText("Selezionare un mese!\n");
    			return;
    		}
    		String s =this.model.creaGrafo(cmbMese.getValue().getNumero(), Integer.parseInt(txtMinuti.getText()));
        	cmbM1.getItems().addAll(this.model.getVertici());
        	cmbM2.getItems().addAll(this.model.getVertici());
        	txtResult.clear();
        	txtResult.appendText(s);
    		
    	}catch(NumberFormatException e) {
    		txtResult.clear();
    		txtResult.appendText("Inserire un numero MIN valido!\n");
    	}
    	
    	
    	
    }

    @FXML
    void doCollegamento(ActionEvent event) {
    	
    	try {
    		
    		if(cmbM2.getValue()==null) {
    			txtResult.clear();
    			txtResult.appendText("Selezionare un match M2!\nSe non visualizzati, allora crare prima il grafo!\n");
    			return;
    		}
    		String s= this.model.cercaPercorsoMax(cmbM1.getValue(), cmbM2.getValue());
        	txtResult.clear();
        	txtResult.appendText(model.getPesoOttimo()+"\n");
        	txtResult.appendText(s);
    		
    	}catch (NullPointerException e) {
    		txtResult.clear();
    		txtResult.appendText("Selezionare un match M1!\nSe non visualizzati, allora crare prima il grafo!\n");
			
		}
    	
    	
    	
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnConnessioneMassima != null : "fx:id=\"btnConnessioneMassima\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCollegamento != null : "fx:id=\"btnCollegamento\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtMinuti != null : "fx:id=\"txtMinuti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbMese != null : "fx:id=\"cmbMese\" was not injected: check your FXML file 'Scene.fxml'.";        assert cmbM1 != null : "fx:id=\"cmbM1\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbM2 != null : "fx:id=\"cmbM2\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	cmbMese.getItems().clear();
    	
    	List<Mese> mesi= new ArrayList<>();
    	
    	Mese m1= new Mese("Gennaio", 1); mesi.add(m1);
    	Mese m2= new Mese("Febbraio", 2); mesi.add(m2); 
    	Mese m3= new Mese("Marzo", 3); mesi.add(m3);
    	Mese m4= new Mese("Aprile", 4); mesi.add(m4);
    	Mese m5= new Mese("Maggio", 5); mesi.add(m5);
    	Mese m6= new Mese("Giugno", 6); mesi.add(m6);
    	Mese m7= new Mese("Luglio", 7); mesi.add(m7);
    	Mese m8= new Mese("Agosto", 8); mesi.add(m8);
    	Mese m9= new Mese("Settembre", 9); mesi.add(m9);
    	Mese m10= new Mese("Ottobre", 10); mesi.add(m10);
    	Mese m11= new Mese("Novembre", 11); mesi.add(m11);
    	Mese m12= new Mese("Dicembre", 12); mesi.add(m12);
    	
    	cmbMese.getItems().addAll(mesi);
    }
    
    
}
