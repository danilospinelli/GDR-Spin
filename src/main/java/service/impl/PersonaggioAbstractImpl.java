package service.impl;

import controller.Partita;
import datamodel.Giocatore;
import datamodel.ListaClassi;
import datamodel.Personaggio;
import service.PersonaggioEffettoService;
import service.PersonaggioService;
import service.StaminaInsufficienteException;

public abstract class PersonaggioAbstractImpl implements PersonaggioService, PersonaggioEffettoService {
	
	// Il primo attacco dei Personaggi è base, il secondo è peculiare in base alla classe
	@Override
	public void attacco1(Giocatore main, Giocatore oppo) throws StaminaInsufficienteException {
		Personaggio pgMain = main.getPg();
		Personaggio pgOppo = oppo.getPg();
		
		verStamina(pgMain, pgMain.getCostoStamina1());
		
		// creo l'animazione
		Partita p = Partita.getController();
		p.setOutput(true, pgMain.getNome() + " ha usato " + pgMain.getNomeAttacco1() + "!");
		
		// animazione mossa
		PersonaggioServiceFactory.getPersonaggioService(pgMain.getClass()).animazioneAttacco1(true, main, oppo);
		
		sottraiStamina(pgMain, pgMain.getCostoStamina1());
		danno(pgOppo, pgMain.getAtk1());
		
		// aggiorna elementi grafici
		p.aggiornaStamina(true, main);
		p.aggiornaBarraHp(true, oppo);
		
		// alla fine del turno, se è uno Stregone, scala il buff se è in corso
		if(pgMain.getClasse() == ListaClassi.STREGONE)
			new StregoneImpl().countdownBuff();
		
		// avvia animazione
		AnimazioneBuilder.buildAndPlay();
	}
	
	// Aggiunge gli Hp e la stamina al Personaggio in base ai valore Heal e Recovery;
	// se ha finito le cure a disposizione lancia una Curaexception
	@Override
	public void cura(Giocatore main) {
		Personaggio pg = main.getPg();
		Partita p = Partita.getController();
		
		p.setOutput(true, pg.getNome() + " ha usato " + pg.getNomeCura() + "!");
		
		PersonaggioServiceFactory.getPersonaggioService(pg.getClass()).animazioneCura(true, main);
			
		int h, r;
		if(pg.getNumeroCure() == 0) { // ha finito le cure a disposizione
			h = 0;
		} else { // può curarsi
			if(pg.getHp() + pg.getHeal() > pg.getFullHp())
				h = pg.getFullHp() - pg.getHp();
			else
				h = pg.getHeal();
		}
		
		if(pg.getStamina() + pg.getRecovery() > pg.getFullStamina())
			r = pg.getFullStamina() - pg.getStamina();
		else
			r = pg.getRecovery();
		
		p.setOutput(true, pg.getNome() + " ha recuperato " + h + " HP e " + r + " punti stamina!");
		pg.setHp(pg.getHp() + h);
		pg.setStamina(pg.getStamina() + r);
		
		// aggiorna elementi grafici
		p.aggiornaBarraHp(true, main);
		p.aggiornaStamina(true, main);
		
		// toglie una cura tra le disponibili (se le aveva)
		if(pg.getNumeroCure() > 0) {
			pg.setNumeroCure(pg.getNumeroCure() - 1);
			p.setOutput(true, "Cure rimaste: " + pg.getNumeroCure());
			AnimazioneBuilder.addAnimation(1.5, () -> p.getlabel_cure().setText(String.format("%d", pg.getNumeroCure())));
		}
		
		// alla fine del turno, se è uno Stregone, scala il buff se è in corso
		if(pg.getClasse() == ListaClassi.STREGONE)
			new StregoneImpl().countdownBuff();
		
		// avvia animazione
		AnimazioneBuilder.buildAndPlay();
	}
	
	// Calcola il danno che il Personaggio riceve e lo applica agli Hp
	@Override
	public void danno(Personaggio pg, int atk) {
		int danno = atk - pg.getDef();
		if(danno < 0)
			danno = 0;
		
		Partita.getController().setOutput(true, pg.getNome() + " ha subito " + danno + " danni!");
		int hpLeft = pg.getHp() - danno;
		if(hpLeft < 0) // se gli Hp scendono sotto lo 0 rimettili a 0
			hpLeft = 0;
		pg.setHp(hpLeft);
	}
	
	// Se il Personaggio non ha abbastanza stamina per fare la mossa,
	// solleva eccezione; altrimenti sottrae la Stamina
	@Override
	public void verStamina (Personaggio pg, int costoStamina) throws StaminaInsufficienteException {
		 if (pg.getStamina() < costoStamina) {
			 throw new StaminaInsufficienteException();
		 }
	}
	
	// Sottrae la Stamina al Personaggio pg in base al costo della mossa
	@Override
	public void sottraiStamina(Personaggio pg, int costoStamina) {
		pg.setStamina(pg.getStamina() - costoStamina);
		Partita.getController().setOutput(true, pg.getNome() + " ha perso " + costoStamina + " punti stamina. ");
	}

}
