package datamodel;

import java.util.ArrayList;
import java.util.List;

public class Gioco {
	
	private List<Giocatore> giocatori;
	private Giocatore gTurno;
	
	public Gioco() {
		giocatori = new ArrayList<>();
		gTurno = null;
	}
	
	public List<Giocatore> getGiocatori(){
		return giocatori;
	}
	
	public void setGiocatori(List<Giocatore> g) {
		giocatori = g;
	}
	
	public Giocatore getGTurno(){
		return gTurno;
	}
	
	public void setGTurno(Giocatore g) {
		gTurno = g;
	}

}
