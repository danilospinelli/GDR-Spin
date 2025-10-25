package service;

import datamodel.Giocatore;

public interface PersonaggioService {
	
	public void attacco1(Giocatore main, Giocatore oppo) throws StaminaInsufficienteException;
	public void attacco2(Giocatore main, Giocatore oppo) throws StaminaInsufficienteException, BuffException;
	public void cura(Giocatore g);
	
	// Animazioni per le mosse del Guerriero
	// kf = true -> aggiunge alla Timeline di animazioni
	// kf = false -> fa direttamente l'animazione
	public void animazioneAttacco1(boolean kf, Giocatore mittente, Giocatore destinatario);
	public void animazioneAttacco2(boolean kf, Giocatore mittente, Giocatore destinatario);
	public void animazioneCura(boolean kf, Giocatore main);

}
