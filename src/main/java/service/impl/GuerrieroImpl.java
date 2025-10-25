package service.impl;

import controller.Partita;
import datamodel.Giocatore;
import datamodel.Guerriero;
import datamodel.Personaggio;
import service.BuffException;
import service.StaminaInsufficienteException;

public class GuerrieroImpl extends PersonaggioAbstractImpl {
	
	// STRUTTURA MOSSA:
	// 1. "hai usato mossa x"
	// 2. animazione
	// 3. effetto - output
	// 4. aggiorna elementi grafici

	// Attacca Pesante: subisci un rinculo rinculo
	@Override
	public void attacco2(Giocatore main, Giocatore oppo) throws StaminaInsufficienteException, BuffException {
		Personaggio pgMain = main.getPg();
		Personaggio pgOppo = oppo.getPg();
		
		int rinculo = ((Guerriero) pgMain).getRinculo();
		
		verStamina(pgMain, pgMain.getCostoStamina2());
		
		// creo l'animazione
		Partita p = Partita.getController();
		p.setOutput(true, pgMain.getNome() + " ha usato " + pgMain.getNomeAttacco2() + "!");
		
		// animazione mossa
		animazioneAttacco2(true, main, oppo);
		
		sottraiStamina(pgMain, pgMain.getCostoStamina2());
		danno(pgOppo, pgMain.getAtk2());
		// rinculo dell'Attacco Pesante
		pgMain.setHp(pgMain.getHp() - rinculo);
		if(pgMain.getHp() < 0)
			pgMain.setHp(0);
		Partita.getController().setOutput(true, pgMain.getNome() + " ha perso " + rinculo + " HP di rinculo. ");
		
		// aggiorna elementi grafici
		p.aggiornaStamina(true, main);
		p.aggiornaBarraHp(true, oppo);
		p.aggiornaBarraHp(true, main);
		
		// avvia animazione
		AnimazioneBuilder.buildAndPlay();
	}
	
	@Override
	public void animazioneAttacco1(boolean kf, Giocatore mittente, Giocatore destinatario) {
		Partita.getController().animazioneSemplice(kf, destinatario, "/immagini/Guerriero/Guerriero_Attacco1.gif", 200, 200, 8, 50);
	}
	
	@Override
	public void animazioneAttacco2(boolean kf, Giocatore mittente, Giocatore destinatario) {
		Partita.getController().animazioneSemplice(kf, destinatario, "/immagini/Guerriero/Guerriero_Attacco2.gif", 200, 200, 8, 50);
	}
	
	@Override
	public void animazioneCura(boolean kf, Giocatore main) {
		Partita.getController().animazioneSemplice(kf, main, "/immagini/Guerriero/Guerriero_Cura.gif", 134, 128, 26, 72);
	}
	
}
