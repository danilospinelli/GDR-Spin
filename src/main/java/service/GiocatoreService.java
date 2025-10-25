package service;

import datamodel.Giocatore;

public interface GiocatoreService {
	
	public void turno(Giocatore gTurno, Giocatore gOdd);
	public void sceltaBuff(Giocatore main);

}
