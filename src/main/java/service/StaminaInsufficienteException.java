package service;

@SuppressWarnings("serial")
public class StaminaInsufficienteException extends Exception{
	
	public StaminaInsufficienteException() {
		super("Non hai abbastanza Stamina per questa mossa.");
	}

}
