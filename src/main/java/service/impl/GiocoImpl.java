package service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import controller.Partita;
import datamodel.Giocatore;
import datamodel.Gioco;
import datamodel.ListaClassi;
import datamodel.Personaggio;
import datamodel.Umano;
import datamodel.Virtuale;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import service.GiocatoreService;

public class GiocoImpl {
	
	// PATTERN SINGLETON per avere sempre accesso alla variabile Gioco gioco anche ricreando da zero
	// un'ostanza di GiocoImpl; da metodi esterni (come nelle classi controller) non posso passare
	// come parametro il gioco, quindi deve essere accessibile sempre, ma se lo facessi static
	// ogni volta che voglio richiamare un metodo di GiocoImpl se crea una nuova istanza di Gioco,
	// quindi uso il pattern singleton così ce ne può essere solo uno
	private static GiocoImpl istanza;
	private static Gioco gioco = new Gioco();
	
	private GiocoImpl() {}
	
	public static GiocoImpl getIstanza() {
		if (istanza == null)
			istanza = new GiocoImpl();
		return istanza;
	}
	
	public static Gioco getGioco() {
		return gioco;
	}
	
	// Crea i 2 Giocatori con i loro Personaggi (in base agli input su interfaccia)
	public void inizializzaPartita(String nome, ListaClassi classe) {
		Personaggio pg1;
		Personaggio pg2;

		pg1 = PersonaggioFactory.getPersonaggio(classe);
		pg1.setNome(nome);
		
		List<ListaClassi> classiOppo = new ArrayList<>(Arrays.asList(ListaClassi.values()));
		classiOppo.remove(classe); // lista classi possibili per l'oppo
		ListaClassi randomClasse = classiOppo.get(new Random().nextInt(classiOppo.size()));
		pg2 = PersonaggioFactory.getPersonaggio(randomClasse);
		
		Giocatore g1 = new Umano(pg1);
		Giocatore g2 = new Virtuale(pg2);
		
		// creazione Gioco
		gioco.getGiocatori().add(g1);
		gioco.getGiocatori().add(g2);
	}
	
	// Setta il primo giocatore a giocare
	public void iniziaPartita() {
		// setta il gTurno iniziale
		speedPriority();
		gioco.setGTurno(gioco.getGiocatori().get(0));
		
		partita();
	}
	
	// "Loop" di partita
	public void partita() {
		if(!finePartita()){
			inizioTurno();
		}else {
			risultati();
		}
	}
	
	// Definisce l'inizio di un turno; se un turno deve riniziare da capo, si riparte da qui
	public void inizioTurno() {
		Giocatore gTurno = gioco.getGTurno();
		Giocatore gOdd = gioco.getGiocatori().get((gioco.getGiocatori().indexOf(gTurno) + 1) % gioco.getGiocatori().size());
		GiocatoreService gioimpl = GiocatoreServiceFactory.getGiocatoreService(gTurno.getClass());
		gioimpl.turno(gTurno, gOdd);
	}
	
	// Chiamato quando un turno è terminato, aggiorna il gTurno
    public void prossimoTurno() {
    	// prende l'indice del giocatore di turno (che ha appena finito)
        int index = gioco.getGiocatori().indexOf(gioco.getGTurno());
        if(index == 1) // se ha finito il secondo Giocatore della coppia, ricalcolo l'ordine dei Giocatori
        	speedPriority();
        
        gioco.setGTurno(gioco.getGiocatori().get((index + 1) % gioco.getGiocatori().size()));
        
        partita(); // passa al prossimo turno
    }
	
	// true se la partita è finita, false se continua
	public boolean finePartita() {
		Personaggio pg1 = gioco.getGiocatori().get(0).getPg();
		Personaggio pg2 = gioco.getGiocatori().get(1).getPg();
		
		return pg1.getHp() == 0 || pg2.getHp() == 0;
	}
	
	// Ordina i giocatori in base all'attributo speed
	// USARE COMPARATOR / COMPARABLE?
	public void speedPriority() {
		Giocatore g1 = gioco.getGiocatori().get(0);
		Giocatore g2 = gioco.getGiocatori().get(1);
		Personaggio pg1 = g1.getPg();
		Personaggio pg2 = g2.getPg();
		gioco.getGiocatori().clear();
		
		if(pg1.getSpeed() > pg2.getSpeed()) {
			gioco.getGiocatori().add(g1);
			gioco.getGiocatori().add(g2);
		}else if(pg1.getSpeed() < pg2.getSpeed()) {
			gioco.getGiocatori().add(g2);
			gioco.getGiocatori().add(g1);
		}else { // if(pg1.getSpeed() == pg2.getSpeed())
			Random r = new Random();
			int x = r.nextInt(1);
			if(x == 0) {
				gioco.getGiocatori().add(g1);
				gioco.getGiocatori().add(g2);
			}else { // if(x == 1)
				gioco.getGiocatori().add(g2);
				gioco.getGiocatori().add(g1);
			}
		}
	}
	
	// Metodo di fine partita, fine gioco
	public void risultati() {
		// cambio vista
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/viste/fine.fxml"));
		    Parent root = loader.load();

		    // Ottieni lo Stage attuale dal pane_pgMain
		    Stage stage = (Stage) Partita.getController().getpane_pgMain().getScene().getWindow();
		    stage.setScene(new Scene(root));
		    stage.show();
		} catch (Exception e) {
	      	e.printStackTrace();
		}
	}

}
