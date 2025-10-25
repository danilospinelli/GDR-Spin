package service;

@SuppressWarnings("serial")
public class BuffException extends Exception {
	
	public BuffException() {
		super("Sei già sotto effetto di un buff. ");
	}

}
