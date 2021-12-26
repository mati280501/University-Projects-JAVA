import java.util.*;

public class DziennikStudentow {

	private static boolean jestWlaczony = false;
	private static final String[] opcje = new String[] {
			"Dodaj Nowego Studenta",
			"Wyświetl Listę Studentów",
			"Wyświetl Średnią Ocen Studentów",
			"Usuń Studenta",
			"Zakończ"
	};
	
	private static Scanner in = new Scanner(System.in);
	private static List<Student> listaStudentow = new ArrayList<>();
	
	public static void main(String[] args) {
		wyswietlMenu();
	}

	public static void wyswietlMenu() {
		if(jestWlaczony)return;
		jestWlaczony = true;
		while(jestWlaczony) {
			wyswietlOpcje();
			System.out.println("Wybierz opcję");
			int wybor = askInt(1, opcje.length);
			switch(wybor) {
				case 1:
					dodajStudenta();
					break;
				case 2:
					wyswietlListeStudentow();
					break;
				case 3:
					wyswietlSredniaOcen();
					break;
				case 4:
					usunStudenta();
					break;
				case 5:
					jestWlaczony = false;
					break;
				default:
					System.err.println("Podaj liczbę od 1 do 5!!!");
					break;
			}
		}	
	}

	private static void usunStudenta() {
		if(listaStudentow.size()==0) {
			System.out.println("Brak studentow");
			return;
		}
		wyswietlListeStudentow();
		System.out.println("Podaj numer albumu studenta do usunięcia");
		int numer = askInt(0, Integer.MAX_VALUE);
		
		for(Student s: listaStudentow) {
			if (s.getNumer() == numer) {
				listaStudentow.remove(s);
				System.out.println("Usunięto studenta: "+s);
				break;
			}
		}		
	}

	private static void wyswietlSredniaOcen() {
		if(listaStudentow.size() == 0) {
			System.out.println("Brak studentów");
			return;
		}
		double sumaWszystkich = 0d;
		for (Student s: listaStudentow) {
			sumaWszystkich += s.getSrednia();
		}
		System.out.println("Średnia wszystkich studentów wynosi: "+sumaWszystkich/listaStudentow.size());
	}

	private static void wyswietlListeStudentow() {
		if(listaStudentow.size() == 0) {
			System.out.println("Brak studentów");
			return;
		}
		for(int i = 0; i < listaStudentow.size(); i++) {
			System.out.println((i+1)+". "+listaStudentow.get(i));
		}		
	}

	private static void dodajStudenta() {
		String nazwisko = askString("Podaj nazwisko studenta");
		System.out.println("Podaj numer albumu studenta: ");
		int numer = askInt(0, Integer.MAX_VALUE);
		System.out.println("Podaj średnią studenta: ");
		double srednia = askDouble (2, 5);
		Student s1 = new Student (nazwisko, numer, srednia);
		listaStudentow.add(s1);
		System.out.println("Dodano studenta: "+s1.toString());
		
	}

	private static void wyswietlOpcje() {
		for(int i = 0; i < opcje.length; i++) {
			System.out.println("\t ("+(i+1)+") "+opcje[i]);
		}	
	}

	private static int askInt(int min, int max) {
		int x;
		while (true) {
			try {
				x = Integer.parseInt(in.nextLine());
				if(x <= max && x >= min) break;
				System.out.println("Liczba musi być z przedziału "+min+"-"+max+"!");
			}catch(NumberFormatException e) {
				System.out.println("Podano nieprawidłową liczbę!!!");
			}
		}
		return x;
	}
	
	private static double askDouble(double min, double max) {
		double x;
		while (true) {
			try {
				x = Double.parseDouble(in.nextLine());
				if(x <= max && x >= min) break;
				System.out.println("Liczba musi być z przedziału "+min+"-"+max+"!");
			}catch(NumberFormatException e) {
				System.out.println("Podano nieprawidłową liczbę!!!");
			}
		}
		return x;
	}
	
	private static String askString(String pytanie) {
		String x;
		while (true) {
			System.out.print(pytanie+": ");
			x = in.nextLine();
			if(!pytanie.isBlank()) break;
			System.out.println("Odpowiedź nie może być pusta");
		}
		return x;
	}
}
