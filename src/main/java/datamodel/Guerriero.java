package datamodel;

public class Guerriero extends Personaggio {
	
	// hp = 250
	// atk1 = 50
	// atk2 = 85
	// def = 30
	// speed = 100
	// stamina = 200
	// costo_stamina1 = 50
	// costo_stamina2 = 100
	// heal = 75
	// recovery = 100
	// numeroCure = 3
	// rinculo = 40
	
	public static final String WARR_NOMEPG = "Artorias";
	
	public static final int WARR_HP = 250;
	public static final int WARR_ATK1 = 50;
	public static final int WARR_ATK2 = 85;
	public static final int WARR_DEF = 30;
	public static final int WARR_SPEED = 100;
	public static final int WARR_STAMINA = 200;
	public static final int WARR_COSTOSTAMINA1 = 50;
	public static final int WARR_COSTOSTAMINA2 = 100;
	public static final int WARR_HEAL = 75;
	public static final int WARR_RECOVERY = 100;
	public static final int WARR_NUMEROCURE = 3;
	public static final int WARR_RINCULO = 40;
	
	public static final String WARR_NOMESTAMINA = "Stam.";
	public static final String WARR_NOMEATTACCO1 = "A. Leggero";
	public static final String WARR_NOMEATTACCO2 = "A. Pesante";
	public static final String WARR_NOMECURA = "Estus";
	public static final String WARR_INFO_ATTACCO1 = "Colpisci l'avversario con la spada. ";
	public static final String WARR_INFO_ATTACCO2 = "Colpisci l'avversario caricando; subiusci un contraccolpo. ";
	public static final String WARR_INFO_CURA = "Ricarica Hp e Stamina. ";
	
	public static final String WARR_PATHIMMAGINE = "/immagini/Guerriero/Guerriero.png";
	public static final int WARR_WIDTHIMMAGINE = 185;
	public static final int WARR_HEIGHTIMMAGINE = 235;
	public static final int WARR_LAYOUTX = 0;
	public static final int WARR_LAYOUTY = 0;
	public static final int WARR_LAYOUTXFINE = -38;
	public static final int WARR_LAYOUTYFINE = -37;
	
	private int rinculo;
	
	public Guerriero () {
		super(WARR_NOMEPG, ListaClassi.GUERRIERO, WARR_HP, WARR_ATK1, WARR_ATK2, WARR_DEF, WARR_SPEED, 
				WARR_STAMINA, WARR_COSTOSTAMINA1, WARR_COSTOSTAMINA2, WARR_HEAL, WARR_RECOVERY, 
				WARR_NUMEROCURE,WARR_NOMESTAMINA, WARR_NOMEATTACCO1, WARR_NOMEATTACCO2,  WARR_NOMECURA, 
				WARR_INFO_ATTACCO1, WARR_INFO_ATTACCO2, WARR_INFO_CURA, WARR_PATHIMMAGINE, 
				WARR_WIDTHIMMAGINE, WARR_HEIGHTIMMAGINE, WARR_LAYOUTX, WARR_LAYOUTY, WARR_LAYOUTXFINE, 
				WARR_LAYOUTYFINE);
		rinculo = WARR_RINCULO;
	}
	
	public int getRinculo() {
		return rinculo;
	}
	
	public void setRinculo(int r) {
		rinculo = r;
	}

}
