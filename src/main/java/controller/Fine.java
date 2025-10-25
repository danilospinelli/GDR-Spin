package controller;

import java.net.URL;
import java.util.ResourceBundle;

import datamodel.Giocatore;
import datamodel.Gioco;
import datamodel.Personaggio;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import service.impl.GiocoImpl;

public class Fine implements Initializable {
	
	@FXML
	private Label label_output;
	@FXML
	private Pane pane_vincitore;
	@FXML
	private Pane pane_pareggio;
	@FXML
	private Pane pane_pareggio1;
	@FXML
	private Pane pane_pareggio2;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Gioco gioco = GiocoImpl.getGioco();
		Giocatore g1 = gioco.getGiocatori().get(0);
		Giocatore g2 = gioco.getGiocatori().get(1);
		
		if(g1.getPg().getHp() == 0 && g2.getPg().getHp() == 0)
			pareggio(g1, g2);
		else if(g1.getPg().getHp() == 0)
			settaVincitore(g2);
		else if(g2.getPg().getHp() == 0)
			settaVincitore(g1);
	}
	
	// Mette nella schermata nome e Personaggio del vincitore
	public void settaVincitore(Giocatore g) {
		Personaggio pg = g.getPg();

		label_output.setText("Il vincitore è " + pg.getNome() + "!");
		label_output.setAlignment(Pos.CENTER);
		
		ImageView img = new ImageView(new Image(getClass().getResource(pg.getPathImmagine()).toExternalForm()));
		pane_vincitore.getChildren().add(img);
		
		img.setFitHeight(pg.getHeightImmagine() + pg.getHeightImmagine()/2);
		img.setFitWidth(pg.getWidthImmagine() + pg.getWidthImmagine()/2);
		img.setLayoutX(pg.getLayoutXFine());
		img.setLayoutY(pg.getLayoutYFine());
	}
	
	// Mette nella schermata nomi e Personaggi dei Giocatori quando c'è un pareggio
	public void pareggio(Giocatore g1, Giocatore g2) {
		Personaggio pg1 = g1.getPg();
		Personaggio pg2 = g2.getPg();
		
		pane_vincitore.setVisible(false);
		pane_pareggio.setVisible(true);
		
		label_output.setText("C'è stato un pareggio!");
		label_output.setAlignment(Pos.CENTER);
		
		ImageView img1 = new ImageView(new Image(getClass().getResource(pg1.getPathImmagine()).toExternalForm()));
		ImageView img2 = new ImageView(new Image(getClass().getResource(pg2.getPathImmagine()).toExternalForm()));
		pane_pareggio1.getChildren().add(img1);
		pane_pareggio2.getChildren().add(img2);
		
		img1.setFitHeight(pg1.getHeightImmagine() + pg1.getHeightImmagine()/2);
		img1.setFitWidth(pg1.getWidthImmagine() + pg1.getWidthImmagine()/2);
		img1.setLayoutX(pg1.getLayoutXFine());
		img1.setLayoutY(pg1.getLayoutYFine());
		
		img2.setFitHeight(pg2.getHeightImmagine() + pg2.getHeightImmagine()/2);
		img2.setFitWidth(pg2.getWidthImmagine() + pg2.getWidthImmagine()/2);
		img2.setLayoutX(pg2.getLayoutXFine());
		img2.setLayoutY(pg2.getLayoutYFine());
	}

}
