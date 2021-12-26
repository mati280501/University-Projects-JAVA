package pl.poznan.ue.kti.pki.player;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Odtwarzacz {
	private Map<String, Playlista> playlisty = new HashMap<String, Playlista> ();
	private boolean wlaczony = true;
	private Scanner scan = new Scanner(System.in);
	
	public Odtwarzacz() {
		try {
			Playlista rock = new Playlista("Rock");
			Playlista pop = new Playlista("Pop");
			Utwor u1= new Utwor("The Show Must Go On", "Queen", 1991);
			rock.dodajUtwor(u1);
			rock.dodajUtwor(new Utwor("Nothing Else Matters", "Metallica", 1992));
			rock.dodajUtwor(new Utwor("Ale Wkoło Jest Wesoło", "Perfect", 1981));
			pop.dodajUtwor(new Utwor("Billie Jean", "Michael Jackson", 1983));
			pop.dodajUtwor(new Utwor("Lucky", "Britney Spears", 2000));
			pop.dodajUtwor(new Utwor("Poker Face", "Lady Gaga", 2008));
			playlisty.put("ROCK", rock);
			playlisty.put("POP", pop);
		} catch(Exception e) {
			e.printStackTrace();
		}		
		
	}
	
	public void wlacz() throws IOException {
		wlaczony = true;
		while(wlaczony) {
			wyswietlMenu();
			int opcja = podajLiczbeCalkowita();
			wykonajOpcje(opcja);
		}
	}
	
	private void wykonajOpcje(int opcja) throws IOException {
		switch(opcja) {
		case 0:
			wyswietlPlaylisty();
			break;
		case 1:
			dodajPlayliste();
			break;
		case 2:
			wyswietlPlayliste();
			break;
		case 3:
			posortujPlayliste();
			break;
		case 4:
			usunPlayliste();
			break;
		case 5:
			dodajUtwor();
			break;
		case 6:
			przeniesUtwor();
			break;
		case 7:
			kopiujUtwor();
			break;
		case 8:
			skasujUtwor();
			break;
		case 9:
			wylacz();
			break;
		case 10:
			try {
				zapiszPlayliste();
			}catch(IOException e) {
				System.out.println(e.getMessage());
			}
			
			break;
		case 11:
			try {
				wczytajPlayliste();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			break;
		default:
			System.out.println("Podana opcja nie istnieje");
		}
	}
	
	private void wczytajPlayliste() throws Exception {
		System.out.println("Podaj nazwę playlisty z folderu pliki");
		String nazwa = podajTekst().toUpperCase();
		String lokalizacjaPliku = "pliki\\"+nazwa+".csv";
		File plik = new File(lokalizacjaPliku);
		if(plik.exists()) {
			FileReader fr = new FileReader(plik);
			BufferedReader br = new BufferedReader(fr);
			
			Playlista playlista = new Playlista(nazwa);
			
			for(String linia = br.readLine(); linia != null; linia = br.readLine()) {
				String[] elementyUtworu = linia.split(";");
				String tytul = elementyUtworu[0];
				String wykonawca = elementyUtworu[1];
				int rokWydania = Integer.parseInt(elementyUtworu[2]);
				
				Utwor utwor = new Utwor(tytul, wykonawca, rokWydania);
				playlista.dodajUtwor(utwor);
			}
			br.close();
			playlisty.put(nazwa, playlista);
			System.out.println("Dodano playlistę "+nazwa);
			playlista.wyswietlPlayliste();
		}else {
			System.out.println("plik w lokalizacji "+lokalizacjaPliku+" nie istnieje");
		}
	}

	private void zapiszPlayliste() throws IOException{
		System.out.println("Której playlisty chcesz użyć? Podaj nazwę:");
		wyswietlPlaylisty();
		String nazwa = podajTekst();
		Playlista playlista = playlisty.get(nazwa.toUpperCase());
		if(playlista==null) {
			System.out.println("Playlista o podanej nazwie nie istnieje");
		} else {
			String lokalizacjaPliku = "pliki\\"+nazwa.toUpperCase()+".csv";
			File plik = new File(lokalizacjaPliku);
			FileWriter fw = new FileWriter(plik);
			BufferedWriter bw = new BufferedWriter(fw);
			List<Utwor>utwory = playlista.podajUtwory();
			
			for (Utwor utwor: utwory) {
				bw.write(utwor.getTytul());
				bw.write(";");
				bw.write(utwor.getWykonawca());
				bw.write(";");
				bw.write(utwor.getRokWydania()+"");
				bw.write(";");
				bw.write("\n");
			}
			
			bw.flush();
			bw.close();
			System.out.println("Zapisano playliste "+nazwa+" w lokalizacji "+lokalizacjaPliku);
		}
		
	}

	private void skasujUtwor() {
		System.out.println("Której playlisty chcesz użyć? Podaj nazwę:");
		wyswietlPlayliste();
		String nazwa = podajTekst();
		Playlista playlista = playlisty.get(nazwa.toUpperCase());
		if(playlista==null) {
			System.out.println("Playlista o podanej nazwie nie istnieje");
		}else {
			playlista.wyswietlPlayliste();
			System.out.println("Który utwór skasować?");
			int numerUtworu = podajLiczbeCalkowita();
			try {
				String opis = playlista.pobierzUtwor(numerUtworu).toString();
				playlista.usunUtwor(numerUtworu);
				System.out.println(">>> skasowano utwór "+opis);
			}catch(IndexOutOfBoundsException e) {
				System.out.println("Utwór o numerze "+numerUtworu+" nie znajduje się na playliście");
			}
		}
	}

	private void kopiujUtwor() {
		
	}

	private void przeniesUtwor() {
		
	}

	private void dodajUtwor() {
		System.out.println("Której playlisty chcesz użyć? Podaj nazwę:");
		wyswietlPlayliste();
		String nazwa = podajTekst();
		Playlista playlista = playlisty.get(nazwa.toUpperCase());
		if(playlista==null) {
			System.out.println("Playlista o podanej nazwie nie istnieje");
		}else {
			System.out.println("Podaj tytuł utworu:");
			String tytul = podajTekst();
			System.out.println("Podaj wykonawcę utworu:");
			String wykonawca = podajTekst();
			System.out.println("Podaj rok wydania utworu:");
			int rokWydania = podajLiczbeCalkowita();
			
			try {
				Utwor utwor = new Utwor(tytul, wykonawca, rokWydania);
				playlista.dodajUtwor(utwor);
				System.out.println(">>> dodano utwór "+utwor.toString());
			}catch (Exception e) {
				System.out.println(e.getMessage());
			}
			
		}
		
	}

	private void usunPlayliste() {
		System.out.println("Podaj nazwę playlisty do usunięcia");
		String nazwa = podajTekst().toUpperCase();
		Playlista playlista = playlisty.remove(nazwa);
		if(playlista == null) {
			System.out.println("Playlista o podanej nazwie nie istnieje");
		}else {
			System.out.println("Playlista "+nazwa+" została usunięta");
		}
	}

	private void posortujPlayliste() {
		System.out.println("Podaj nazwę playlisty do posortowania");
		String nazwa = podajTekst().toUpperCase();
		Playlista playlista = playlisty.get(nazwa);
		if(playlista == null) {
			System.out.println("Playlista o podanej nazwie nie istnieje");
		}else {
			playlista.sortujAlfabetycznie();
			playlista.wyswietlPlayliste();
		}
		
	}

	private void wyswietlPlayliste() {
		System.out.println("Podaj nazwę playlisty do wyświetlenia");
		String nazwa = podajTekst().toUpperCase();
		Playlista playlista = playlisty.get(nazwa);
		if (playlista==null) {
			System.out.println("Playlista o podanej nazwie nie istnieje");
		}else {
			playlista.wyswietlPlayliste();
		}
		
	}
	
	private void dodajPlayliste() {
		System.out.println("Podaj nazwę dla nowej playlisty");
		String nazwa = podajTekst().toUpperCase();
		try {
			Playlista playlista = new Playlista(nazwa);
			boolean duplikat = playlisty.containsKey(nazwa);
			if(duplikat) {
				System.out.println("Playlista o nazwie "+nazwa+" już istnieje. Musisz podać inną nazwę");
			}else {
				playlisty.put(nazwa, playlista);
				System.out.println("Playlista o nazwie "+nazwa+" została dodana");
			}
		}catch (PlaylistaException e) {
			System.out.println(e.getMessage());
		}
	}
	
	private void wyswietlPlaylisty() {
		Set<String> klucze = playlisty.keySet();
		for(String klucz: klucze) {
			System.out.println("("+klucz+")");
		}
	}
	
	private void wylacz() {
		wlaczony = false;
		System.out.println("Odtwarzacz został wyłączony9");
	}

	private int podajLiczbeCalkowita() {
		String opcjaString = scan.nextLine();
		try {
			int opcjaInt = Integer.parseInt(opcjaString);
			return opcjaInt;
		} catch(NumberFormatException e) {
			System.out.println(opcjaString+" nie jest liczbą całkowitą.\n Podaj liczbę całkowitą");
			return podajLiczbeCalkowita();
		}
	}
	
	private String podajTekst() {
		String opcjaString = scan.nextLine();
		return opcjaString;
		
	}
	
	private void wyswietlMenu() {
		StringBuilder builder = new StringBuilder();
		builder.append("Co chcesz zrobić? Wybierz opcję:\n");
		builder.append("\t(0) wyświetl playlisty\n");
		builder.append("\t(1) dodać playlistę\n");
		builder.append("\t(2) wyswietlić playlistę\n");
		builder.append("\t(3) posortować playlistę\n");
		builder.append("\t(4) usunąć playlistę\n");
		builder.append("\t(5) dodać nowy utwór\n");
		builder.append("\t(6) przenieść utwór\n");
		builder.append("\t(7) skopiować utwór\n");
		builder.append("\t(8) skasować utwór\n");
		builder.append("\t(9) wyłączyć odtwarzacz\n");
		builder.append("\t(10) zapisać playlistę do pliku\n");
		builder.append("\t(11) odczytać playlistę z pliku\n");
		System.out.println(builder.toString());
	}
	
	public static void main(String[] args) throws IOException {
		Odtwarzacz odtwarzacz = new Odtwarzacz();
		odtwarzacz.wlacz();
	}
}
