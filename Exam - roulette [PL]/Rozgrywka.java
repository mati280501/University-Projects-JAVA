import java.util.Scanner;

public class Rozgrywka {
	private int tokeny;
	private int obstawionaLiczba;
	private String obstawionaMoneta;
	private boolean wlaczony = true;
	private Scanner scan = new Scanner(System.in);
	
	public Rozgrywka(){
		tokeny = 50;
	}
	
	private void wlacz() {
		while(wlaczony) {
			runda();
			if(koniecTokenow()) break;
			koniecGry();
		}
	}
	
	private boolean koniecTokenow() {
		if(tokeny<=0) {
			System.out.println("KONIEC GRY! Brak tokenów");
			return  true;
		}else {
			return false;
		}
	}

	private void koniecGry() {
		System.out.println("Czy chcesz rozegrać kolejną rundę? (TAK/NIE)");
		String wejscie = scan.nextLine().toUpperCase();
		if(wejscie.equals("TAK")){
			wlacz();
		}else if(wejscie.equals("NIE")) {
			wlaczony = false;
			System.out.println("KONIEC GRY! Zostało ci "+tokeny+" tokenów");
		}else {
			System.out.println("Podaj TAK lub NIE");
			koniecGry();
		}
	}

	private void runda() {
		tokeny -= 5;
		while(wlaczony) {
			System.out.println("Obstaw liczbę od 1-12");
			String wejscie = scan.nextLine();
			try {
				obstawionaLiczba = Integer.parseInt(wejscie);
				if(obstawionaLiczba>12 || obstawionaLiczba<1) throw new RuletkaException();
				break;
			} catch (NumberFormatException e) {
				System.out.println(wejscie+" nie jest liczbą całkowitą z zakresu 1-12");
				continue;
			}catch (RuletkaException e1) {
				System.out.println(e1.getMessage());
			}
		}
		while(wlaczony) {
			System.out.println("Obstaw stronę monety (HEADS/TAILS)");
			String wejscie = scan.nextLine();
			obstawionaMoneta = wejscie.toUpperCase();
			if(obstawionaMoneta.equals("HEADS")||obstawionaMoneta.equals("TAILS")) break;
			System.out.println("Nieprawidłowy wybór");	
		}
		int wylosowanaLiczba = (int) (Math.random() * 12 + 1);
		int wylosowanaMonetaInt = (int) (Math.random() * 2);
		String wylosowanaMoneta;
		if (wylosowanaMonetaInt == 0) {
			wylosowanaMoneta = "HEADS";
		}else {
			wylosowanaMoneta = "TAILS";
		}
		System.out.println("Wylosowano: "+wylosowanaLiczba+" "+wylosowanaMoneta);
		if(wylosowanaMoneta.equals(obstawionaMoneta)&&wylosowanaLiczba==obstawionaLiczba) {
			tokeny += 50;
			System.out.println("SUPER! Otrzymujesz 50 tokenów.");
		}else {
			System.out.println("SŁABO! Wpisowe przepadło.");
		}
		
	}
	
	public static void main(String[] args) {
		Rozgrywka rozgrywka = new Rozgrywka();
		rozgrywka.wlacz();
	}
}
