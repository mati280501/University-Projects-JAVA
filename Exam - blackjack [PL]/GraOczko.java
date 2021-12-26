import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class GraOczko {
	private Scanner scan = new Scanner(System.in);
	private FileWriter zapisPliku;
	private boolean wlaczony = true;
	private boolean czyGracDalej;
	List<Integer>rzuty = new LinkedList<>();
	
	
	public GraOczko() throws IOException{
		System.out.println("Podaj swoje imię: ");
		String imie = scan.nextLine().toLowerCase();
		this.zapisPliku = new FileWriter(imie+".txt");
	}
	
	private void rozpocznij() {
		int rzut = (int) (Math.random()*12+1);
		this.rzuty.add(rzut);
		System.out.println("Wylosowana liczba to: "+this.rzuty.get(this.rzuty.size()-1));
		this.zapiszDoPliku(String.format("%s. rzut \t [%s] suma: %s%n", this.rzuty.size(), this.rzuty.get(this.rzuty.size()-1), this.rzuty.stream().mapToInt(Integer::intValue).sum()));
		
		while(wlaczony) {
			if(!grajRunde())break;
		}
		
		if(this.zapisPliku != null) {
			try {
				this.zapisPliku.close();
			} catch (IOException e) {
				System.err.println("Nie udało się zamknąć pliku");
				e.printStackTrace();
			}
		}
	}
	
	private boolean grajRunde() {
		while(wlaczony) {
			try {
				czyGracDalej = koniecGry();
				break;
			} catch (NieprawidloweDaneException e) {
				System.out.println(e.getMessage());
			}
		}
		
		if(czyGracDalej) {
			int rzut = (int) (Math.random()*12+1);
			this.rzuty.add(rzut);
			System.out.println("Wylosowana liczba to: "+this.rzuty.get(this.rzuty.size()-1));
			System.out.println("Wszystkie rzuty: "+this.rzuty.toString());
			int suma = this.rzuty.stream().mapToInt(Integer::intValue).sum();
			System.out.println("Suma: "+suma);
			this.zapiszDoPliku(String.format("%s. rzut \t [%s] suma: %s%n", this.rzuty.size(), this.rzuty.get(this.rzuty.size()-1), this.rzuty.stream().mapToInt(Integer::intValue).sum()));
			
			if(suma==21) {
				System.out.println("OCZKO!");
				zapiszDoPliku("OCZKO\n");
				return false;
			}else if(suma>21) {
				System.out.println("PORAŻKA!");
				zapiszDoPliku("PORAŻKA\n");
				return false;
			}else {
				return true;
			}
		}else {
			System.out.println("SPASOWANO");
			this.zapiszDoPliku("PAS\n");
			try {
				this.zapisPliku.close();
				return false;
			} catch (IOException e) {
				System.err.println("Nie udało się zamknąć pliku");
				e.printStackTrace();
			}
		}
		return true;
	}
	
	private void zapiszDoPliku(String s) {
		try {
			this.zapisPliku.write(s);
		} catch (IOException e) {
			System.err.println("Nie udało się zapisać do pliku");
			e.printStackTrace();
			}
	}
	
	private boolean koniecGry() throws NieprawidloweDaneException {
		System.out.println("Czy chcesz zakończyć grę? (t/n)");
		String koniec;
		koniec = scan.nextLine().toLowerCase();
		if(koniec.equals("t"))return true;
		if(koniec.equals("n"))return false;
		throw new NieprawidloweDaneException();
	}
	
	public static void main(String[] args) {
		try {
			var gra = new GraOczko();
			gra.rozpocznij();
		} catch (IOException e) {
			System.err.println("Nie udało się otworzyć pliku");
			e.printStackTrace();
		}
	}
}
