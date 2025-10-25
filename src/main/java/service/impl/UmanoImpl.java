package service.impl;

import controller.Partita;
import datamodel.Giocatore;

public class UmanoImpl extends GiocatoreAbstractImpl {

	// Turno del Giocatore Umano, fa apparire gli elementi grafici per farlo
	// interagire
	@Override
	public void turno(Giocatore gTurno, Giocatore gOdd) {
		Partita.getController().inizioTurnoMain(gTurno, gOdd);
	}
	
	// STREGONE: devi scegliere quale buff ottenere
	@Override
	public void sceltaBuff(Giocatore main) {
		Partita.getController().guiBuff(main);
	}

}
