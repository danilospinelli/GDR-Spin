package service;

import datamodel.Personaggio;

public interface PersonaggioEffettoService {
	
	public void danno(Personaggio pg, int atk);
	public void verStamina(Personaggio pg, int costoStamina) throws StaminaInsufficienteException;
	public void sottraiStamina(Personaggio pg, int costoStamina);

}
