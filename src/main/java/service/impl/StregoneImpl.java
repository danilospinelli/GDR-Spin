package service.impl;

import controller.Partita;
import datamodel.Giocatore;
import datamodel.ListaClassi;
import datamodel.Personaggio;
import datamodel.Stregone;
import datamodel.Umano;
import datamodel.Virtuale;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import service.BuffException;
import service.StaminaInsufficienteException;

public class StregoneImpl extends PersonaggioAbstractImpl {

	// STRUTTURA MOSSA:
	// 1. "hai usato mossa x"
	// 2. animazione
	// 3. effetto - output
	// 4. aggiorna elementi grafici

	// Spell: dà un buff alle statistiche di atk / def / speed per 2 / 3 / 5 turni
	@Override
	public void attacco2(Giocatore main, Giocatore oppo) throws StaminaInsufficienteException, BuffException {
		Personaggio pgMain = main.getPg();
		  
		verStamina(pgMain, pgMain.getCostoStamina2());
		verBuff(pgMain);
		
		Partita p = Partita.getController();
		p.setOutput(true, pgMain.getNome() + " ha usato " + pgMain.getNomeAttacco2() + "!");
		
		// animazione mossa
		animazioneAttacco2(true, main, oppo);
		
		sottraiStamina(pgMain, pgMain.getCostoStamina2());
		  
		// rimanda la gestione degli effeti della mossa ad altri metodi
		GiocatoreServiceFactory.getGiocatoreService(main.getClass()).sceltaBuff(main);
		
		// se è Virtuale và normale, se è Umano queste azioni le fa dopo in terminaAttacco2()
		if(main.getClass() == Virtuale.class) {
			p.aggiornaStamina(true, main);
			
			// alla fine del turno, scala il buff se è in corso
			countdownBuff();

			// avvia animazione
			AnimazioneBuilder.buildAndPlay();
		}
	}
	
	private void terminaAttacco2(Giocatore main) {
		Partita p = Partita.getController();
		p.aggiornaStamina(true, main);
		
		// alla fine del turno, scala il buff se è in corso
		countdownBuff();

		// avvia animazione
		AnimazioneBuilder.buildAndPlay();
	}

	// Verifica se il personaggio pgMain è già sotto effeto di un buff, se lo è,
	// lancia una BuffException
	public void verBuff(Personaggio pgMain) throws BuffException {
		if (((Stregone) pgMain).getCountStatus() > 0) {
			throw new BuffException();
		}
	}

	// Aumenta l'attacco del 60%
	public void buffAtk(Giocatore g) {
		int perc = 60;
		Personaggio pg = g.getPg();
		
		pg.setAtk1(pg.getAtk1() + (pg.getAtk1() * perc / 100));
		((Stregone) pg).setCountStatus(2 + 1);
		
		Partita p = Partita.getController();
		p.setOutput(true, pg.getNome() + " ha incrementato il suo Atk del " + perc + "%!");
		
		// aggiorna label_status
		Label label_status;
		if (g.getClass() == Umano.class)
			label_status = p.getlabel_statusMain();
		else
			label_status = p.getlabel_statusOppo();
		
		AnimazioneBuilder.addAnimation(1.5, () -> label_status.setText("ATK + " + perc + "%"));
		
		if(g.getClass() == Umano.class)
			terminaAttacco2(g);
	}

	// Aumenta la difesa del 30%
	public void buffDef(Giocatore g) {
		int perc = 30;
		Personaggio pg = g.getPg();
		
		pg.setDef(pg.getDef() + (pg.getDef() * perc / 100));
		((Stregone) pg).setCountStatus(3 + 1);
		
		Partita p = Partita.getController();
		p.setOutput(true, pg.getNome() + " ha incrementato la sua Def del " + perc + "%!");
		
		// aggiorna label_status
		Label label_status;
		if (g.getClass() == Umano.class)
			label_status = p.getlabel_statusMain();
		else
			label_status = p.getlabel_statusOppo();
		
		AnimazioneBuilder.addAnimation(1.5, () -> label_status.setText("DEF + " + perc + "%"));
		
		if(g.getClass() == Umano.class)
			terminaAttacco2(g);
	}

	// Aumenta la velocità del 100%
	public void buffSpeed(Giocatore g) {
		int perc = 100;
		Personaggio pg = g.getPg();
		
		pg.setSpeed(pg.getSpeed() + (pg.getSpeed() * perc / 100));
		((Stregone) pg).setCountStatus(5 + 1);
		
		Partita p = Partita.getController();
		p.setOutput(true, pg.getNome() + " ha incrementato la sua Speed del " + perc + "%!");
		
		// aggiorna label_status
		Label label_status;
		if (g.getClass() == Umano.class)
			label_status = p.getlabel_statusMain();
		else
			label_status = p.getlabel_statusOppo();
		
		AnimazioneBuilder.addAnimation(1.5, () -> label_status.setText("SPEED + " + perc + "%"));
		
		if(g.getClass() == Umano.class)
			terminaAttacco2(g);
	}
	
	// Scala i turni di buff di un Personaggio Stregone; se scendono a 0, annulla i buff
	public void countdownBuff() {
		Giocatore gTurno = GiocoImpl.getGioco().getGTurno();
		Personaggio pgTurno = gTurno.getPg();
		if (pgTurno.getClasse() == ListaClassi.STREGONE) {
			if (((Stregone) pgTurno).getCountStatus() > 0) { // è sotto effetto di buff
				((Stregone) pgTurno).setCountStatus(((Stregone) pgTurno).getCountStatus() - 1);
				if (((Stregone) pgTurno).getCountStatus() == 0) {
					Partita.getController().setOutput(true, "L'effetto del buff è terminato. ");
					new StregoneImpl().restoreStatus(gTurno);
				} else { // countStatus == 1 || countStatus == 2
					Partita.getController().setOutput(true,
							"Turni del buff rimanenti: " + ((Stregone) pgTurno).getCountStatus());
				}
			}
		}
	}

	// Resetta le statistiche di atk / def / speed ai loro valori base
	public void restoreStatus(Giocatore g) {
		Personaggio pg = g.getPg();
		
		pg.setAtk1(Stregone.MAGE_ATK1);
		pg.setDef(Stregone.MAGE_DEF);
		pg.setSpeed(Stregone.MAGE_SPEED);
		
		
		Label label_status;
		if(g.getClass() == Umano.class)
			label_status = Partita.getController().getlabel_statusMain();
		else //if(g.getClass() == Virtuale.class)
			label_status = Partita.getController().getlabel_statusOppo();
		AnimazioneBuilder.addAnimation(1.5, () -> label_status.setText(""));
	}

	@Override
	public void animazioneAttacco1(boolean kf, Giocatore mittente, Giocatore destinatario) {
		ImageView animazione = new ImageView(
				new Image(getClass().getResource("/immagini/Stregone/Stregone_Attacco1.gif").toExternalForm()));
		animazione.setVisible(false);
		animazione.setFitWidth(94);
		animazione.setFitHeight(86);

		Pane pane;
		// KeyValues: definiscono i valori finali delle proprietà
		KeyValue kvX;
		KeyValue kvY;
		if (mittente.getClass() == Umano.class) { // parte da main
			pane = Partita.getController().getpane_pgMain();
			pane.getChildren().add(animazione);
			animazione.setLayoutX(127);
			animazione.setLayoutY(67);
			// definisco la destinazione della gif
			kvX = new KeyValue(animazione.layoutXProperty(), 300);
			kvY = new KeyValue(animazione.layoutYProperty(), -40);
		} else { // if(mittente.getClass() == Virtuale.class) ; parte da oppo
			animazione.setScaleX(-1);
			pane = Partita.getController().getpane_pgOppo();
			pane.getChildren().add(animazione);
			animazione.setLayoutX(-45);
			animazione.setLayoutY(67);
			// definisco la destinazione della gif
			kvX = new KeyValue(animazione.layoutXProperty(), -200);
			kvY = new KeyValue(animazione.layoutYProperty(), 200);
		}

		// KeyFrame: durata + valori finali
		// Timeline: contiene il KeyFrame; spostare la gif e rimuoverla dopo 4 secondi
		Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e -> animazione.setVisible(true)),
				new KeyFrame(Duration.seconds(2), kvX, kvY),
				new KeyFrame(Duration.seconds(2.5), e -> animazione.setVisible(false)));
		// new KeyFrame(Duration.seconds(2.5), e ->
		// pane.getChildren().remove(animazione))); CORRETTO

		if (kf) {
			// aggiungi a AnimazioneBuilder invece di avviare
			AnimazioneBuilder.addTimeline(timeline);
		} else {
			// esegui subito
			timeline.play();
		}
	}

	@Override
	public void animazioneAttacco2(boolean kf, Giocatore mittente, Giocatore destinatario) {
		if (mittente.getClass() == Umano.class)
			Partita.getController().animazioneSemplice(kf, mittente, "/immagini/Stregone/Stregone_Attacco2.gif", 185,
					185, 43, -23);
		else // if (g.getClass() == Virtuale.class)
			Partita.getController().animazioneSemplice(kf, mittente, "/immagini/Stregone/Stregone_Attacco2.gif", 185,
					185, -43, -23);
	}

	@Override
	public void animazioneCura(boolean kf, Giocatore main) {
		if (main.getClass() == Umano.class)
			Partita.getController().animazioneSemplice(kf, main, "/immagini/Stregone/Stregone_Cura.gif", 185, 185, 43,
					-20);
		else // if (main.getClass() == Virtuale.class)
			Partita.getController().animazioneSemplice(kf, main, "/immagini/Stregone/Stregone_Cura.gif", 185, 185, -43,
					-20);
	}

}
