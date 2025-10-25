package datamodel;

public class Giocatore {
	
	private Personaggio pg;
	
	public Giocatore(Personaggio pg) {
		this.pg = pg;
	}
	
	public Personaggio getPg() {
		return pg;
	}
	
	public void setPg(Personaggio p) {
		pg = p;
	}
	
	@Override
	public String toString() {
		return  pg.toString();
	}

}
