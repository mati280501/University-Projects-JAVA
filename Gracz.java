
public class Gracz {
	private String pseudonim;
	private String bron;
	private int liczbaPunktow;
	
	public Gracz(String pseudonim){
		super();
		this.pseudonim = pseudonim;
	}

	public String getPseudonim() {
		return pseudonim;
	}
	
	public String getBron() {
		return bron;
	}
	
	public int getLiczbaPunktow() {
		return liczbaPunktow;
	}
	
	public void zwiekszPunkty() {
		liczbaPunktow += 1;
	}
	
	public void ustawBron(String bron) {
		this.bron = bron; 
	}
}
