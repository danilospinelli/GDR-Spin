package datamodel;

public class Stregone extends Personaggio {

	// hp = 150
	// atk1 = 75
	// atk2 = 0
	// def = 15
	// speed = 80
	// stamina = 20
	// costo_stamina1 = 5
	// costo_stamina2 = 10
	// heal = 80
	// recovery = 9999
	// numeroCure = 2
	
	public static final String MAGE_NOMEPG = "Solaire";
	
	public static final int MAGE_HP = 150;
	public static final int MAGE_ATK1 = 75;
	public static final int MAGE_ATK2 = 0;
	public static final int MAGE_DEF = 15;
	public static final int MAGE_SPEED = 80;
	public static final int MAGE_STAMINA = 20;
	public static final int MAGE_COSTOSTAMINA1 = 5;
	public static final int MAGE_COSTOSTAMINA2 = 10;
	public static final int MAGE_HEAL = 80;
	public static final int MAGE_RECOVERY = 9999;
	public static final int MAGE_NUMEROCURE = 2;
	
	public static final String MAGE_NOMESTAMINA = "Mana";
	public static final String MAGE_NOMEATTACCO1 = "Fireball";
	public static final String MAGE_NOMEATTACCO2 = "Spell";
	public static final String MAGE_NOMECURA = "Heal";
	public static final String MAGE_INFO_ATTACCO1 = "Colpisci l'avversario con una Palla di Fuoco. ";
	public static final String MAGE_INFO_ATTACCO2 = "Aumenta Atk/Def/Speed del 60%/30%/100% per 2/3/5 turni. ";
	public static final String MAGE_INFO_CURA = "Ripristina qualche Hp e tutta la Stamina. ";
	
	public static final String MAGE_PATHIMMAGINE = "/immagini/Stregone/Stregone.png";
	public static final int MAGE_WIDTHIMMAGINE = 226;
	public static final int MAGE_HEIGHTIMMAGINE = 163;
	public static final int MAGE_LAYOUTX = -19;
	public static final int MAGE_LAYOUTY = 50;
	public static final int MAGE_LAYOUTXFINE = -69;
	public static final int MAGE_LAYOUTYFINE = 41;

	private int count_status;

	public Stregone () {
		super(MAGE_NOMEPG, ListaClassi.STREGONE, MAGE_HP, MAGE_ATK1, MAGE_ATK2, MAGE_DEF, MAGE_SPEED, 
				MAGE_STAMINA, MAGE_COSTOSTAMINA1, MAGE_COSTOSTAMINA2, MAGE_HEAL, MAGE_RECOVERY, 
				MAGE_NUMEROCURE, MAGE_NOMESTAMINA, MAGE_NOMEATTACCO1, MAGE_NOMEATTACCO2, MAGE_NOMECURA, 
				MAGE_INFO_ATTACCO1, MAGE_INFO_ATTACCO2, MAGE_INFO_CURA, MAGE_PATHIMMAGINE, 
				MAGE_WIDTHIMMAGINE, MAGE_HEIGHTIMMAGINE, MAGE_LAYOUTX, MAGE_LAYOUTY, MAGE_LAYOUTXFINE, 
				MAGE_LAYOUTYFINE);
		this.count_status = 0;
	}
	
	public int getCountStatus() {
		return count_status;
	}
	
	public void setCountStatus(int c) {
		count_status = c;
	}

}
