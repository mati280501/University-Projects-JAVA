import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Gra {
	private Gracz komputer;
	private Gracz gracz;
	private Scanner scan = new Scanner(System.in);
	private FileWriter plik;

	public static void main(String[] args) {
		Gra gra = new Gra();
		try {
			gra.rozpocznij();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void rozpocznij() throws IOException {
		komputer = new Gracz("komputer");
		System.out.println("Podaj pseudonim");
		String pseudonim = scan.nextLine();
		gracz = new Gracz(pseudonim);
		plik = new FileWriter(gracz.getPseudonim()+".txt");
		for(int i = 1; i <= 3; i++) {
			runda(i);
		}
		plik.flush();
		plik.close();
	}
	
	private void runda(int i) {
		System.out.println("Runda "+i);
		System.out.println("Podaj broń (P/K/N): ");
		String bron = pobierzBron();
		gracz.ustawBron(bron);
		komputer.ustawBron(wylosujBron());
		if(gracz.getBron().equals(komputer.getBron())){
			System.out.println("REMIS");
			zapiszDoPliku(i+"REMIS\n");
		}else if((gracz.getBron().equals("P")&&komputer.getBron().equals("K"))||(gracz.getBron().equals("K")&&komputer.getBron().equals("N"))||(gracz.getBron().equals("N")&&komputer.getBron().equals("P"))){
			System.out.println("Wygrał "+gracz.getPseudonim());
			gracz.zwiekszPunkty();
			zapiszDoPliku(i+"Wygrał "+gracz.getPseudonim()+"\n");
		}else {
			System.out.println("Wygrał "+komputer.getPseudonim());
			komputer.zwiekszPunkty();
			zapiszDoPliku(i+"Wygrał "+komputer.getPseudonim()+"\n");
		}
		
	}
	
	private String pobierzBron() {
		String bron = scan.nextLine().toUpperCase();
		if(bron.equals("P")||bron.equals("K")||bron.equals("N")) {
			return bron;
		}else {
			System.out.println("Musisz podać P, K lub N.");
			return pobierzBron();
		}
	}
	
	private String wylosujBron() {
		List<String> listaBroni = new ArrayList<String>();
		listaBroni.add("P");
		listaBroni.add("K");
		listaBroni.add("N");
		Collections.shuffle(listaBroni);
		String bron = listaBroni.get(0);
		return bron;
	}

	private void zapiszDoPliku(String s) {
		try {
			plik.write(s);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
