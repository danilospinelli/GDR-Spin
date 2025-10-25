package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import datamodel.ListaClassi;
import datamodel.Personaggio;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import service.impl.GiocoImpl;
import service.impl.PersonaggioFactory;

public class SceltaPG implements Initializable {
	
	@FXML
	private Button button_guerriero;
	@FXML
	private Button button_stregone;
	
	@FXML
	private Pane pane_show;
	@FXML
	private Label label_show_attacco1;
	@FXML
	private Label label_show_attacco2;
	@FXML
	private Label label_show_cura;
	
	@FXML
	private Pane pane_nome;
	@FXML
	private TextField textField_nome;
	@FXML
	private Button button_nome;
	
	private ListaClassi classe;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		button_guerriero.setOnAction(event -> showDetailsPg(event));
        button_stregone.setOnAction(event -> showDetailsPg(event));
        button_nome.setOnAction(event -> inputNome(event));
	}
	
	@FXML
	private void showDetailsPg(ActionEvent event) {
		if(!pane_show.isVisible())
			pane_show.setVisible(true);
		if(!pane_nome.isVisible())
			pane_nome.setVisible(true);
		
		// raccolta classe Personaggio
		Pane parent = (Pane) ((Button) event.getSource()).getParent();
	    Label label = (Label) parent.getChildren().get(1); // indice 1 → la label
	    String nomeClasse = label.getText();
	    classe = ListaClassi.valueOf(nomeClasse.toUpperCase());
	    
	    Personaggio tempPg = PersonaggioFactory.getPersonaggio(classe);
	    label_show_attacco1.setText(tempPg.getNomeAttacco1() + ": " + tempPg.getInfoAttacco1());
	    label_show_attacco2.setText(tempPg.getNomeAttacco2() + ": " + tempPg.getInfoAttacco2());
	    label_show_cura.setText(tempPg.getNomeCura() + ": " + tempPg.getInfoCura());
	}
	
	@FXML
	private void inputNome(ActionEvent event) {
		String nome = textField_nome.getText();
		if(!nome.equals("") && classe != null) {
			GiocoImpl gimpl = GiocoImpl.getIstanza();
			// Inizializza la partita
            gimpl.inizializzaPartita(nome, classe);
			// cambio vista
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/viste/partita.fxml"));
				Parent root = loader.load();

				// Ottieni lo stage corrente dal bottone cliccato
				Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
				Scene scene = new Scene(root);
				stage.setScene(scene);
				stage.show();
			} catch(IOException e) {
				e.printStackTrace();
			}
			
			gimpl.iniziaPartita();
		}
	}
	
}
