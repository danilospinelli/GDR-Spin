package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class TitleScreen implements Initializable {
	
	@FXML
	private Button button_gioca;
	@FXML
	private Button button_abbandona;
	@FXML
	private Label label_abbandona;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		button_gioca.setOnAction(event -> gioca(event));
        button_abbandona.setOnAction(event -> abbandona());
	}
	
	@FXML
	private void gioca(ActionEvent event) {
		// cambio vista
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/viste/scelta_pg.fxml"));
			Parent root = loader.load();

			// Ottieni lo stage corrente dal bottone cliccato
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void abbandona() {
		button_gioca.setVisible(false);
        button_abbandona.setVisible(false);
        label_abbandona.setVisible(true);
	}
	
}
