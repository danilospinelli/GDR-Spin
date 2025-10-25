package service.impl;

import java.util.ArrayList;
import java.util.List;

import controller.Partita;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public abstract class AnimazioneBuilder {

	public static final double DURATA_PAUSA = 4.0;

	private static List<Timeline> timelines = new ArrayList<>();

	// Aggiunge un KeyFrame alla Timeline di animazioni finale
	public static void addAnimation(double d, Runnable action) {
		Timeline t = new Timeline();
		t.getKeyFrames().add(new KeyFrame(Duration.seconds(d), e -> action.run()));
		timelines.add(t);
	}

	// Aggiunge una Timeline all'animazione
	public static void addTimeline(Timeline t) {
		timelines.add(t);
	}

	// Aumenta il delay di DURATA_PAUSA per spezzare i turni dei 2 Giocatori
	public static void pausaTurni() {
		Timeline t = new Timeline();
		t.getKeyFrames().add(new KeyFrame(Duration.seconds(DURATA_PAUSA)));
		timelines.add(t);
	}

	// Costruisce e avvia la Timeline sulle Timeline della lista
	// turno = true se faccio animazioni dei turni
	// turno = false se faccio altre animazioni
	public static void buildAndPlay() {
		if (timelines.isEmpty())
			return;
		
		pausaTurni();
		// collega tutte le timeline con setOnFinished
		for (int i = 0; i < timelines.size() - 1; i++) {
			Timeline current = timelines.get(i);
			Timeline next = timelines.get(i + 1);

			current.setOnFinished(e -> next.play());
		}
		
		// alla fine dell'ultima timeline, pulisce e passa al turno successivo
	    Timeline ultima = timelines.get(timelines.size() - 1);
	    ultima.setOnFinished(e -> {
	        svuotaAnimazione();
	        GiocoImpl.getIstanza().prossimoTurno();
	    });

		// avvia la prima
		timelines.get(0).play();
	}

	// Aggiunge alla lista delle Timeline le animazioni di inizio prossimo turno, da
	// eseguire alla fine
	// delle animazioni del turno precedente
	public static void animazioniInizioTurno() {
		Timeline t1 = new Timeline(
				new KeyFrame(Duration.seconds(0.2), e -> Partita.getController().setOutput(false, "Tocca a te.")));

		Timeline t2 = new Timeline(
				new KeyFrame(Duration.seconds(0.2), e -> Partita.getController().getpane_comandi().setVisible(true)));

		t1.setOnFinished(e -> t2.play());
		t1.play();
	}

	// Resetta la lista di KeyFrames e il delay
	public static void svuotaAnimazione() {
		timelines.clear();
	}

}
