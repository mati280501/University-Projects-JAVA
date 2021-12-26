package pl.poznan.ue.kti.pki.player;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class Playlista {
	private String nazwa;
	List<Utwor> utwory = new LinkedList<Utwor>();
	
	public Playlista(String nazwa) throws PlaylistaException {
		if(nazwa == null || nazwa.trim().equals("")) {
			throw new PlaylistaException("Nazwa playlisty nie może być pusta");
		}
		
		this.nazwa = nazwa;
	}
	
	public void dodajUtwor(Utwor utwor) {
		if (utwor.getTytul()!=null && !utwor.getTytul().trim().equals(" ")) {
			boolean b = utwor.equals("");
			utwory.add(utwor);
		}else {
			System.out.println("Musisz podać nazwę utworu!");
		}
			
	}
	
	public Utwor pobierzUtwor(int numer) {
		Utwor utwor = utwory.get(numer-1);
		return utwor;
	}
	
	public void wyswietlPlayliste() {
		if(utwory.size() == 0) {
			System.out.println("---Playlista "+nazwa+" jest pusta---");
		}else {
			System.out.println("---Playlista "+nazwa+"---");
			for(int i=0; i<utwory.size(); i++) {
				System.out.println((i+1)+". \t"+utwory.get(i));
			}
		}	
	}
	
	public void usunUtwor(int numer) {
		utwory.remove(numer-1);
	}
	
	public void sortujAlfabetycznie() {
		Comparator<Utwor> comp = Comparator.comparing(Utwor::getWykonawca).thenComparing(Utwor::getTytul);
		Collections.sort(utwory, comp);
	}

	public void sortujWedlugRokuWydania() {
		Comparator<Utwor> comp = Comparator.comparingInt(Utwor::getRokWydania).reversed().thenComparing(Utwor::getWykonawca).thenComparing(Utwor::getTytul);
		Collections.sort(utwory, comp);
	}
	
	public void sortujDomyslnie() {
		Collections.sort(utwory);
	}
	
	List<Utwor> podajUtwory(){
		return utwory;
	}
}
