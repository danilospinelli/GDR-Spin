package controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import datamodel.Giocatore;
import datamodel.Gioco;
import datamodel.Personaggio;
import datamodel.Umano;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import service.BuffException;
import service.PersonaggioService;
import service.StaminaInsufficienteException;
import service.impl.AnimazioneBuilder;
import service.impl.GiocoImpl;
import service.impl.PersonaggioServiceFactory;
import service.impl.StregoneImpl;

public class Partita implements Initializable {
	
	@FXML
	private Pane pane_scena;
	
	// Main
	@FXML
	private Pane pane_main;
	@FXML
	private Pane pane_pgMain; // contiene l'immagine del pg main
	@FXML
	private ProgressBar progressBar_main;
	@FXML
	private Label label_nomeMain;
	@FXML
	private Label label_hpMain;
	@FXML
	private Label label_fullHpMain;
	@FXML
	private Label label_staminaMain; //stamina rimasta
	@FXML
	private Label label_nomeStaminaMain; // nome della stamina
	@FXML
	private Label label_statusMain;
	@FXML
	private Button button_info_main;
	
	// Oppo
	@FXML
	private Pane pane_oppo;
	@FXML
	private Pane pane_pgOppo; // contiene l'immagine del pg oppo
	@FXML
	private ProgressBar progressBar_oppo;
	@FXML
	private Label label_nomeOppo;
	@FXML
	private Label label_hpOppo;
	@FXML
	private Label label_fullHpOppo;
	@FXML
	private Label label_staminaOppo; //stamina rimasta
	@FXML
	private Label label_nomeStaminaOppo; //nome della stamina
	@FXML
	private Label label_statusOppo; // nome della stamina
	@FXML
	private Button button_info_oppo;
	
	// Comandi
	@FXML
	private Pane pane_comandi;
	@FXML
	private Button button_attacco1;
	@FXML
	private Button button_attacco2;
	@FXML
	private Button button_cura;
	@FXML
	private Label label_stamina1; //costo mossa 1
	@FXML
	private Label label_stamina2; //costo mossa 2
	@FXML
	private Label label_output;
	@FXML
	private Button button_info_attacco1;
	@FXML
	private Button button_info_attacco2;
	@FXML
	private Button button_info_cura;
	@FXML
	private Label label_cure; // cure rimaste
	
	// Buff
	@FXML
	private Pane pane_buff;
	@FXML
	private Button button_buffAtk;
	@FXML
	private Button button_buffDef;
	@FXML
	private Button button_buffSpeed;
	
	// Info
	@FXML
	private Pane pane_info_comandi;
	@FXML
	private Label label_info_comandi;
	@FXML
	private Button button_x_comandi;
	@FXML
	private Pane pane_info_main;
	@FXML
	private Label label_info_main;
	@FXML
	private Button button_x_main;
	@FXML
	private Pane pane_info_oppo;
	@FXML
	private Label label_info_oppo;
	@FXML
	private Button button_x_oppo;
	@FXML
	private Button button_x_buff;
	
	
	private static Partita controller;
	
	public Partita() {
		controller = this;
	}
	
	// Restituisce il controller di Partita, così da richiamarne i metodi da classi esterne
	public static Partita getController() {
		return controller;
	}
	
	public Pane getpane_pgMain() {
		return pane_pgMain;
	}
	
	public Pane getpane_pgOppo() {
		return pane_pgOppo;
	}
	
	public Pane getpane_comandi() {
		return pane_comandi;
	}
	
	public Label getlabel_statusMain() {
		return label_statusMain;
	}
	
	public Label getlabel_statusOppo() {
		return label_statusOppo;
	}
	
	public Label getlabel_cure() {
		return label_cure;
	}
	
	// Aggiunge un messaggio alla coda di output
	// kf = true -> aggiunge alla Timeline di animazioni
	// kf = false -> fa direttamente l'animazione
    public void setOutput(boolean kf, String s) {
        if(!kf) {
        	label_output.setText(s);
        }else { //if(kf)
        	AnimazioneBuilder.addAnimation(1.5, () -> label_output.setText(s));
        }
    }
	
	// Prepara gli elementi della vista Partita in base agli input dati (sarebbe initialize?)
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Gioco gioco = GiocoImpl.getGioco();
		Giocatore main = gioco.getGiocatori().get(0);
		Giocatore oppo = gioco.getGiocatori().get(1);
		Personaggio pgMain = main.getPg();
		Personaggio pgOppo = oppo.getPg();
		
		// pulisco i pane dei personaggi
		Node i;
		i = pane_pgMain.getChildren().get(0);
		pane_pgMain.getChildren().remove(i);
		i = pane_pgOppo.getChildren().get(0);
		pane_pgOppo.getChildren().remove(i);
		
		// carica le immagini
		for(Giocatore g : gioco.getGiocatori()){
			Personaggio pg = g.getPg();
			ImageView image = new ImageView();
			image.setImage(new Image(getClass().getResource(pg.getPathImmagine()).toExternalForm()));
			image.setFitWidth(pg.getWidthImmagine());
			image.setFitHeight(pg.getHeightImmagine());
			if(g.equals(gioco.getGiocatori().get(0))) { // pg = main
				image.setScaleX(-1); // ribalta l'immagine
				pane_pgMain.getChildren().add(image);
			} else //if(g.equals(gioco.getGiocatori().get(1))) ; pg = oppo
				pane_pgOppo.getChildren().add(image);
			image.setLayoutX(pg.getLayoutX());
			image.setLayoutY(pg.getLayoutY());
		}

		// inserisco i nomi dei giocatori
		label_nomeMain.setText(pgMain.getNome());
		label_nomeOppo.setText(pgOppo.getNome());
		
		// setta la label di output
		label_output.setText("");
		
		// fa sparire il pane_comandi
		Timeline t = new Timeline(
				new KeyFrame(Duration.ZERO, ev -> pane_comandi.setVisible(false)), 
				new KeyFrame(Duration.seconds(0.2)));
		t.play();
		
		// riempie le barre della vita
		progressBar_main.setProgress(1.0);
		progressBar_oppo.setProgress(1.0);
		
		// scrive gli Hp dei Personaggi
		label_hpMain.setText(String.format("%d", pgMain.getHp()));
		label_fullHpMain.setText(String.format("%d", pgMain.getFullHp()));
		label_hpOppo.setText(String.format("%d", pgOppo.getHp()));
		label_fullHpOppo.setText(String.format("%d", pgOppo.getFullHp()));
		
		// cambia nome mosse in base alla classe
		button_attacco1.setText(pgMain.getNomeAttacco1());
		button_attacco2.setText(pgMain.getNomeAttacco2());
		button_cura.setText(pgMain.getNomeCura());
		
		// scrive il costo delle mosse
		label_stamina1.setText(String.format("%d", pgMain.getCostoStamina1()));
		label_stamina2.setText(String.format("%d", pgMain.getCostoStamina2()));
		
		// scrive il numero delle cure
		label_cure.setText(String.format("%d", pgMain.getNumeroCure()));
		
		// scrive la stamina dei Personaggi
		label_nomeStaminaMain.setText(pgMain.getNomeStamina());
		label_nomeStaminaOppo.setText(pgOppo.getNomeStamina());
		label_staminaMain.setText(String.format("%d", pgMain.getFullStamina()));
		label_staminaOppo.setText(String.format("%d", pgOppo.getFullStamina()));
		
		// pulisce la label di status
		label_statusMain.setText("");
		label_statusOppo.setText("");
		
		// ------------------------------------------------------------------------
		
		// Buttons INFO
		button_info_main.setOnAction(event -> infoMain());
		button_info_oppo.setOnAction(event -> infoOppo());
		button_info_attacco1.setOnAction(event -> infoAttacco1());
		button_info_attacco2.setOnAction(event -> infoAttacco2());
		button_info_cura.setOnAction(event -> infoCura());
		button_x_comandi.setOnAction(event -> slideOutInfo());
		button_x_main.setOnAction(event -> slideOutInfoMain());
		button_x_oppo.setOnAction(event -> slideOutInfoOppo());
		button_x_buff.setOnAction(event -> annullaBuff());
	}
	
	// Fa apparire i Buttons per interagire nel proprio turno, quindi aspetta un'azione del Giocatore
	// (accade solo nel turno del Giocatore Umano)
	// * callback.run(); equivale a passare il turno (chiamata a prossimoTurno())
	public void inizioTurnoMain(Giocatore gTurno, Giocatore gOdd) {
		AnimazioneBuilder.animazioniInizioTurno();
		
		Personaggio pgTurno = gTurno.getPg();
		PersonaggioService pgimpl = PersonaggioServiceFactory.getPersonaggioService(pgTurno.getClass());
		
		// eventi sulla premuta dei Buttons
		button_attacco1.setOnAction(e -> {
			Timeline t = new Timeline(
					new KeyFrame(Duration.ZERO, ev -> pane_comandi.setVisible(false)), 
					new KeyFrame(Duration.seconds(0.2)));
			t.play();
			
			try {
        		pgimpl.attacco1(gTurno, gOdd);
        	}catch(StaminaInsufficienteException exc) {
        		Partita.getController().setOutput(false, exc.getMessage());
        		Timeline timeline = new Timeline(
        				new KeyFrame(Duration.seconds(1.5), e1 -> GiocoImpl.getIstanza().inizioTurno())
        				);
        		timeline.play();
        	}
	    });

	    button_attacco2.setOnAction(e -> {
	    	Timeline t = new Timeline(
					new KeyFrame(Duration.ZERO, ev -> pane_comandi.setVisible(false)), 
					new KeyFrame(Duration.seconds(0.2)));
			t.play();
	    	
	    	try {
	        	pgimpl.attacco2(gTurno, gOdd);
	        }catch(StaminaInsufficienteException | BuffException exc) {
	        	Partita.getController().setOutput(false, exc.getMessage());
	        	Timeline timeline = new Timeline(
        				new KeyFrame(Duration.seconds(1.5), e1 -> GiocoImpl.getIstanza().inizioTurno())
        				);
        		timeline.play();
	        }
	    });

	    button_cura.setOnAction(e -> {
	    	Timeline t = new Timeline(
					new KeyFrame(Duration.ZERO, ev -> pane_comandi.setVisible(false)), 
					new KeyFrame(Duration.seconds(0.2)));
			t.play();
	    	
	    	// effetti mossa
			pgimpl.cura(gTurno);
	    });
	}
	
	
	// Fa apparire i Buttons di scelta del buff dello Stregone e aspetta una risposta
	public void guiBuff(Giocatore main) {
		StregoneImpl s = new StregoneImpl();
		
		Timeline t = new Timeline(
				new KeyFrame(Duration.ZERO, ev -> pane_buff.setVisible(true)), 
				new KeyFrame(Duration.seconds(0.2)));
		t.play();
		
		button_buffAtk.setOnAction(e -> {
			Timeline ti = new Timeline(
					new KeyFrame(Duration.ZERO, ev -> pane_buff.setVisible(false)), 
					new KeyFrame(Duration.seconds(0.2)));
			ti.play();
			
			s.buffAtk(main);
	    });

	    button_buffDef.setOnAction(e -> {
	    	Timeline ti = new Timeline(
					new KeyFrame(Duration.ZERO, ev -> pane_buff.setVisible(false)), 
					new KeyFrame(Duration.seconds(0.2)));
			ti.play();
	    	
	    	s.buffDef(main);
	    });

	    button_buffSpeed.setOnAction(e -> {
	    	Timeline ti = new Timeline(
					new KeyFrame(Duration.ZERO, ev -> pane_buff.setVisible(false)), 
					new KeyFrame(Duration.seconds(0.2)));
			ti.play();
	    	
	    	s.buffSpeed(main);
	    });
	}
	
	// Aggiorna il progresso della barra della vita di un Personaggio di un Giocatore 
	// e il numero degli HP (dopo l'alterazione)
	// kf = true -> aggiunge alla Timeline di animazioni
	// kf = false -> fa direttamente l'animazione
	public void aggiornaBarraHp(boolean kf, Giocatore g) {
		ProgressBar bar;
	    Label label;
	    if (g.getClass() == Umano.class) {
	        bar = progressBar_main;
	        label = label_hpMain;
	    } else {
	        bar = progressBar_oppo;
	        label = label_hpOppo;
	    }

	    int hpFrom = Integer.parseInt(label.getText());
	    int hpTo = g.getPg().getHp();
	    int fullHp = g.getPg().getFullHp();

	    int step = hpTo > hpFrom ? 1 : -1; // guadagna HP -> step = 1 ; perde HP -> step = -1
	    int passi = Math.abs(hpTo - hpFrom); // valore assoluto
	    if (passi == 0) { // non c'è alterazione degli HP
	        return; // niente animazione
	    }

	    // Animazione della barra (progress da hpFrom/fullHp → hpTo/fullHp)
	    double progressFrom = (double) hpFrom / fullHp;
	    double progressTo = (double) hpTo / fullHp;

	    Timeline barraTimeline = new Timeline(
	        new KeyFrame(Duration.seconds(0), new KeyValue(bar.progressProperty(), progressFrom)),
	        new KeyFrame(Duration.seconds(1), new KeyValue(bar.progressProperty(), progressTo)) // durata animazione
	    );

	    // Animazione dei numeri (tick 1 a 1 molto veloce)
	    Timeline numeriTimeline = new Timeline();
	    for (int i = 0; i <= passi; i++) {
	        int valore = hpFrom + i * step;
	        numeriTimeline.getKeyFrames().add(
	            new KeyFrame(Duration.millis(i * (1000.0 / passi)), // distribuisci su 1s
	                e -> label.setText(String.valueOf(valore)))
	        );
	    }

	    // distingue i casi
	    if(!kf) {
	    	// Parti in simultanea
		    barraTimeline.play();
		    numeriTimeline.play();
	    } else { //if(kf)
	    	AnimazioneBuilder.addTimeline(barraTimeline);
	    	AnimazioneBuilder.addTimeline(numeriTimeline);
	    }
	}
	
	// Aggiorna la stamina rimasta del Personaggio di un Giocatore (dopo l'alterazione)
	// kf = true -> aggiunge alla Timeline di animazioni
	// kf = false -> fa direttamente l'animazione
	public void aggiornaStamina(boolean kf, Giocatore g) {
		Label label;
		if (g.getClass() == Umano.class) 
	        label = label_staminaMain;
	    else //if (g.getClass() == Virtuale.class)
	        label = label_staminaOppo;

	    int staminaFrom = Integer.parseInt(label.getText());
	    int staminaTo = g.getPg().getStamina();

	    int step = staminaTo > staminaFrom ? 1 : -1; // guadagna stamina -> step = 1 ; perde stamina -> step = -1
	    int passi = Math.abs(staminaTo - staminaFrom); // valore assoluto
	    if (passi == 0) { // non c'è alterazione della stamina
	        return; // niente animazione
	    }

	    // Animazione dei numeri (tick 1 a 1 molto veloce)
	    Timeline numeriTimeline = new Timeline();
	    for (int i = 0; i <= passi; i++) {
	        int valore = staminaFrom + i * step;
	        numeriTimeline.getKeyFrames().add(
	            new KeyFrame(Duration.millis(i * (500.0 / passi)), // distribuisci su 0.5s
	                e -> label.setText(String.valueOf(valore)))
	        );
	    }
	    
	    // distingue i casi
	    if(!kf)
	    	numeriTimeline.play();
	    else //if(kf)
	    	AnimazioneBuilder.addTimeline(numeriTimeline);
	}
	
	// Fa apparire un'immagine/gif per 4 secondi in determinate coordinate
	public void animazioneSemplice(boolean kf, Giocatore destinatario, String path, int width, int height, int layoutx, int layouty) {
		ImageView animazione = new ImageView(new Image(getClass().getResource(path).toExternalForm()));
		animazione.setVisible(false);
		animazione.setFitWidth(width);
		animazione.setFitHeight(height);
		Pane pane;
		if(destinatario.getClass() == Umano.class) { // subisce main
			animazione.setScaleX(-1);
			pane = pane_pgMain;
		}
		else { //if(destinatario.getClass() == Virtuale.class) ; subisce oppo
			pane = pane_pgOppo;
		}
		pane.getChildren().add(animazione);
		animazione.setLayoutX(layoutx);
		animazione.setLayoutY(layouty);
		
		// crea l'animazione (Timeline)
		// Timeline per mostrare la gif e rimuoverla dopo 4 secondi
		
	    Timeline timeline = new Timeline(
	        new KeyFrame(Duration.ZERO, e -> animazione.setVisible(true)),   // appare subito
	        new KeyFrame(Duration.seconds(4), e -> pane.getChildren().remove(animazione)));

	    if (kf) {
	        // aggiungi a AnimazioneBuilder invece di avviare
	        AnimazioneBuilder.addTimeline(timeline);
	    } else {
	        // esegui subito
	        timeline.play();
	    }
	}
	
	// Metodi per far apparire info per Personaggi e Mosse
	public void infoAttacco1() {
		Personaggio pg = GiocoImpl.getGioco().getGTurno().getPg(); // è Umano, quindi il Main
		slideInInfo(pg.getInfoAttacco1());
	}

	public void infoAttacco2() {
		Personaggio pg = GiocoImpl.getGioco().getGTurno().getPg(); // è Umano, quindi il Main
		slideInInfo(pg.getInfoAttacco2());
	}

	public void infoCura() {
		Personaggio pg = GiocoImpl.getGioco().getGTurno().getPg(); // è Umano, quindi il Main
		slideInInfo(pg.getInfoCura());
	}
	
	// Fa sparire il pane_info
	public void slideOutInfo() {
		 TranslateTransition slide = new TranslateTransition(Duration.seconds(0.5), pane_info_comandi);
		 slide.setFromY(0);
		 slide.setToY(-150);
		    
		 slide.setOnFinished(e -> {
			 pane_info_comandi.setVisible(false);
		     pane_info_comandi.setTranslateY(0); // reset per la prossima volta
		 });
		    
		 slide.play();
	}
	
	// fa entrare il pane_info con le info da mostrare
	public void slideInInfo(String text) {
		label_info_comandi.setText(text);
		label_info_comandi.setAlignment(Pos.TOP_LEFT);
		label_info_comandi.setTextAlignment(TextAlignment.LEFT);
		label_info_comandi.setWrapText(true);
		
		pane_info_comandi.setTranslateY(-150);
	    pane_info_comandi.setVisible(true);

	    TranslateTransition slide = new TranslateTransition(Duration.seconds(0.5), pane_info_comandi);
	    slide.setFromY(-150);
	    slide.setToY(0);
	    slide.play();
	}
	
	public void infoMain() {
		slideInInfoMain(GiocoImpl.getGioco().getGTurno().getPg());
	}
	
	public void infoOppo() {
		List<Giocatore> giocatori = GiocoImpl.getGioco().getGiocatori();
		slideInInfoOppo(giocatori.get((giocatori.indexOf(GiocoImpl.getGioco().getGTurno()) + 1) % giocatori.size()).getPg());
	}
	
	public void slideInInfoMain(Personaggio pg) {
		label_info_main.setText(pg.toString());
		label_info_main.setAlignment(Pos.TOP_LEFT);
		label_info_main.setTextAlignment(TextAlignment.LEFT);
		label_info_main.setWrapText(true);
		
		pane_info_main.setTranslateY(300);
	    pane_info_main.setVisible(true);

	    TranslateTransition slide = new TranslateTransition(Duration.seconds(0.5), pane_info_main);
	    slide.setFromY(300);
	    slide.setToY(0);
	    slide.play();
	}
	
	public void slideInInfoOppo(Personaggio pg) {
		label_info_oppo.setText(pg.toString());
		label_info_oppo.setAlignment(Pos.TOP_LEFT);
		label_info_oppo.setTextAlignment(TextAlignment.LEFT);
		label_info_oppo.setWrapText(true);
		
		pane_info_oppo.setTranslateY(-300);
	    pane_info_oppo.setVisible(true);

	    TranslateTransition slide = new TranslateTransition(Duration.seconds(0.5), pane_info_oppo);
	    slide.setFromY(-300);
	    slide.setToY(0);
	    slide.play();
	}
	
	public void slideOutInfoMain() {
		TranslateTransition slide = new TranslateTransition(Duration.seconds(0.5), pane_info_main);
		slide.setFromY(0); // parte dalla posizione visibile
		slide.setToY(300); // scende giù
		slide.setOnFinished(e -> {
			pane_info_main.setVisible(false);
			pane_info_main.setTranslateY(0); // reset per la prossima volta
		});
		slide.play();
	}
	
	public void slideOutInfoOppo() {
		TranslateTransition slide = new TranslateTransition(Duration.seconds(0.5), pane_info_oppo);
		slide.setFromY(0);
		slide.setToY(-300);
		    
		slide.setOnFinished(e -> {
			pane_info_oppo.setVisible(false);
			pane_info_oppo.setTranslateY(0); // reset per la prossima volta
		});
		    
		slide.play();
	}
	
	// Annulla la scelta del buff, torna alla scelta delle mosse;
	// deve anche annullare tutte le azioni fatte fino a quel momento:
	// - svuotare le animazioni
	// - ripristinare la stamina
	public void annullaBuff() {
		Timeline t = new Timeline(
				new KeyFrame(Duration.ZERO, ev -> pane_buff.setVisible(false)), 
				new KeyFrame(Duration.seconds(0.2)),
				new KeyFrame(Duration.seconds(0.2), ev -> pane_comandi.setVisible(true)), 
				new KeyFrame(Duration.seconds(0.4)));
		t.play();
		
		AnimazioneBuilder.svuotaAnimazione();
		
		Personaggio pg = GiocoImpl.getGioco().getGTurno().getPg();
		pg.setStamina(pg.getStamina() + pg.getCostoStamina2());
	}
	
}
