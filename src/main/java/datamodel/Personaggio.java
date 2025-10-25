package datamodel;

public class Personaggio {
	
	private String nome;
	private ListaClassi classe;
	// Stats
	private int hp;
	private int fullHp;
	private int atk1;
	private int atk2;
	private int def;
	private int speed;
	private int stamina;
	private int fullStamina;
	private int costo_stamina1;
	private int costo_stamina2;
	private int heal;
	private int recovery;
	private int numeroCure;
	// Info
	private String nomeStamina;
	private String nomeAttacco1;
	private String nomeAttacco2;
	private String nomeCura;
	private String infoAttacco1;
	private String infoAttacco2;
	private String infoCura;
	// Layout
	private String pathImmagine;
	private int widthImmagine;
	private int heightImmagine;
	private int layoutX;
	private int layoutY;
	private int layoutXFine;
	private int layoutYFine;
	
	public Personaggio(String nome, ListaClassi classe, int hp, int atk1, int atk2, int def, int speed, 
			int stamina, int cs1, int cs2, int heal, int recovery, int nc, String nomeStamina, 
			String nomeAttacco1, String nomeAttacco2,  String nomeCura, String infoAtt1, String infoAtt2, 
			String infoCura, String path, int width, int height, int x, int y, int xf, int yf) {
		
		this.nome = nome;
		this.classe = classe;
		
		this.hp = hp;
		this.fullHp = hp;
		this.atk1 = atk1;
		this.atk2 = atk2;
		this.def = def;
		this.speed = speed;
		this.stamina = stamina;
		this.fullStamina = stamina;
		this.costo_stamina1 = cs1;
		this.costo_stamina2 = cs2;
		this.heal = heal;
		this.recovery = recovery;
		this.numeroCure = nc;
		
		this.nomeStamina = nomeStamina;
		this.nomeAttacco1 = nomeAttacco1;
		this.nomeAttacco2 = nomeAttacco2;
		this.nomeCura = nomeCura;
		this.infoAttacco1 = infoAtt1;
		this.infoAttacco2 = infoAtt2;
		this.infoCura = infoCura;
		
		this.pathImmagine = path;
		this.widthImmagine = width;
		this.heightImmagine = height;
		this.layoutX = x;
		this.layoutY = y;
		this.layoutXFine = xf;
		this.layoutYFine = yf;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String n) {
		nome = n;
	}
	
	public ListaClassi getClasse() {
		return classe;
	}
	
	public void setClasse(ListaClassi c) {
		classe = c;
	}
	
	public int getHp() {
		return hp;
	}
	
	public void setHp(int h) {
		hp = h;
	}
	
	public int getFullHp() {
		return fullHp;
	}
	
	public void setFullHp(int h) {
		fullHp = h;
	}
	
	public int getAtk1() {
		return atk1;
	}
	
	public void setAtk1(int a) {
		atk1 = a;
	}
	
	public int getAtk2() {
		return atk2;
	}
	
	public void setAtk2(int a) {
		atk2 = a;
	}
	
	public int getDef() {
		return def;
	}
	
	public void setDef(int d) {
		def = d;
	}
	
	public int getSpeed() {
		return speed;
	}
	
	public void setSpeed(int s) {
		speed = s;
	}
	
	public int getStamina() {
		return stamina;
	}
	
	public void setStamina(int s) {
		stamina = s;
	}
	
	public int getFullStamina() {
		return fullStamina;
	}
	
	public void setFullStamina(int s) {
		fullStamina = s;
	}
	
	public int getCostoStamina1() {
		return costo_stamina1;
	}
	
	public void setCostoStamina1(int cs) {
		costo_stamina1 = cs;
	}
	
	public int getCostoStamina2() {
		return costo_stamina2;
	}
	
	public void setCostoStamina2(int cs) {
		costo_stamina2 = cs;
	}
	
	public int getHeal() {
		return heal;
	}
	
	public void setHeal(int h) {
		heal = h;
	}
	
	public int getRecovery() {
		return recovery;
	}
	
	public void setRecovery(int r) {
		recovery = r;
	}
	
	public int getNumeroCure() {
		return numeroCure;
	}
	
	public void setNumeroCure(int nc) {
		numeroCure = nc;
	}
	
	public String getNomeStamina() {
		return nomeStamina;
	}
	
	public void setNomeStamina(String s) {
		nomeStamina = s;
	}
	
	public String getNomeAttacco1() {
		return nomeAttacco1;
	}
	
	public void setNomeAttacco1(String n) {
		nomeAttacco1 = n;
	}
	
	public String getNomeAttacco2() {
		return nomeAttacco2;
	}
	
	public void setNomeAttacco2(String n) {
		nomeAttacco2 = n;
	}
	
	public String getNomeCura() {
		return nomeCura;
	}
	
	public void setNomeCura(String n) {
		nomeCura = n;
	}
	
	public String getInfoAttacco1() {
		return infoAttacco1;
	}
	
	public void setInfoAttacco1(String i) {
		infoAttacco1 = i;
	}
	
	public String getInfoAttacco2() {
		return infoAttacco2;
	}
	
	public void setInfoAttacco2(String i) {
		infoAttacco2 = i;
	}
	
	public String getInfoCura() {
		return infoCura;
	}
	
	public void setInfoCura(String i) {
		infoCura = i;
	}
	
	public String getPathImmagine() {
		return pathImmagine;
	}
	
	public void setPathImmagine(String p) {
		pathImmagine = p;
	}
	
	public int getWidthImmagine() {
		return widthImmagine;
	}
	
	public void setWidthImmagine(int w) {
		widthImmagine = w;
	}
	
	public int getHeightImmagine() {
		return heightImmagine;
	}
	
	public void setHeightImmagine(int h) {
		heightImmagine = h;
	}
	
	public int getLayoutX() {
		return layoutX;
	}
	
	public void setLayoutX(int x) {
		layoutX = x;
	}
	
	public int getLayoutY() {
		return layoutY;
	}
	
	public void setLayoutY(int y) {
		layoutY = y;
	}
	
	public int getLayoutXFine() {
		return layoutXFine;
	}
	
	public void setLayoutXFine(int xf) {
		layoutXFine = xf;
	}
	
	public int getLayoutYFine() {
		return layoutYFine;
	}
	
	public void setLayoutYFine(int yf) {
		layoutYFine = yf;
	}
	
	@Override
	public String toString() {
		return  "Nome: " + nome + "\n" +
				"Classe: " + classe + "\n" +
				"HP: " + hp + "\n" + 
				"Stamina: " + stamina + "\n" +
				"Atk: " + atk1 + "\n" +
				"Def: " + def + "\n" +
				"Speed: " + speed + "\n" +
				"Heal: " + heal + "\n" +
				"Recovery: " + recovery + "\n" +
				"Cure rimaste: " + numeroCure;
	}
	
}
