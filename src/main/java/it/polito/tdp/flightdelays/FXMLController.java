package it.polito.tdp.flightdelays;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.*;

import it.polito.tdp.flightdelays.model.Airline;
import it.polito.tdp.flightdelays.model.Arco;
import it.polito.tdp.flightdelays.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea txtResult;

    @FXML
    private ComboBox<Airline> cmbBoxLineaAerea;

    @FXML
    private Button caricaVoliBtn;

    @FXML
    private TextField numeroPasseggeriTxtInput;

    @FXML
    private TextField numeroVoliTxtInput;

    @FXML
    void doCaricaVoli(ActionEvent event) {
    	
    	Airline airline = cmbBoxLineaAerea.getValue();
    	
    	if(airline==null) {
    		txtResult.appendText("Errore, selezionare un aeroporto");
    		return;
    	}
    	
    	model.creaGrafo(airline);
    	
    	List<Arco> peggiori = model.getPeggiori(airline);
    	
    	txtResult.setText(String.format("Peggiori 10 tratte proposte da: %s\n", airline));;
    	
    	for(int i=0; i<10; i++) {
    		txtResult.appendText(peggiori.get(i)+"\n");
    	}

    }

    @FXML
    void doSimula(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'FlightDelays.fxml'.";
        assert cmbBoxLineaAerea != null : "fx:id=\"cmbBoxLineaAerea\" was not injected: check your FXML file 'FlightDelays.fxml'.";
        assert caricaVoliBtn != null : "fx:id=\"caricaVoliBtn\" was not injected: check your FXML file 'FlightDelays.fxml'.";
        assert numeroPasseggeriTxtInput != null : "fx:id=\"numeroPasseggeriTxtInput\" was not injected: check your FXML file 'FlightDelays.fxml'.";
        assert numeroVoliTxtInput != null : "fx:id=\"numeroVoliTxtInput\" was not injected: check your FXML file 'FlightDelays.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
		
		cmbBoxLineaAerea.getItems().addAll(model.getAirlines());
		
	}
}
