public class Student {
	private String nazwisko;
	private int numerAlbumu;
	private double srednia;
	
	public Student(String nazwisko, int numerAlbumu, double srednia) {
		this.nazwisko = nazwisko;
		this.numerAlbumu = numerAlbumu;
		this.srednia = srednia;
	}
	
	public String getNazwisko() {
		return nazwisko;
	}
	
	public int getNumer() {
		return numerAlbumu;
	}
	
	public double getSrednia() {
		return srednia;
	}

	@Override
	public String toString() {
		return ""+nazwisko+" "+numerAlbumu+": "+srednia+"";
	}
}
