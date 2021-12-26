package pl.poznan.ue.kti.pki.player;

public class Utwor implements Comparable<Utwor>{
	private String tytul;
	private String wykonawca;
	private int rokWydania;
	
	public Utwor(String tytul, String wykonawca, int rokWydania) throws Exception{
		if (czyPusty(tytul)) {
			throw new Exception("Tytuł utworu nie może być pusty!");
		}
		if (czyPusty(wykonawca)){
			throw new Exception("Wykonawca nie może być pusty!");
		}
		if(rokWydania<1900 || rokWydania>2021){
			throw new Exception("Rok wydania musi być w zakresie 1900 - 2021!");
		}
		
		this.tytul = tytul;
		this.wykonawca = wykonawca;
		this.rokWydania = rokWydania;
	}

	public String getTytul() {
		return tytul;
	}

	public String getWykonawca() {
		return wykonawca;
	}

	public int getRokWydania() {
		return rokWydania;
	}

	@Override
	public String toString() {
		return tytul + " (" + wykonawca + ") [" + rokWydania + "]";
	}
	
	public int compareTo(Utwor o) {
		
		int wynik = this.tytul.compareTo(o.getTytul());
		if(wynik == 0) {
			if(this.rokWydania > o.getRokWydania()) {
				return 1;
			}else if (this.rokWydania < o.getRokWydania()){
				return -1;
			} else {
				return 0;
			}
		}		
		return wynik;
	}
	
	private boolean czyPusty(String tekst) {
		if(tekst == null || tekst.trim().equals("")) {
			return true;
		}else {
			return false;
		}
	}
	
}
