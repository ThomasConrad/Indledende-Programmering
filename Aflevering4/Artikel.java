
public class Artikel {
	String titel;
	String[] forfattere;
	Tidsskrift tidsskrift;
	private Artikel[] referenceliste;
	
	public Artikel(String titel, String[] forfattere, Tidsskrift tidsskrift) {
		this.titel = titel;
		this.forfattere = forfattere;
		this.tidsskrift = tidsskrift;
	}
	
	public void setReferenceListe(Artikel[] referenceliste) {
		this.referenceliste = referenceliste;
	}
	
	public String toString() {
		String output = forfattere[0];
		for (int i = 1; i < forfattere.length; i++) {
			output += " & ";
			output += forfattere[i];
		}
		output += ": \"" + titel + "\". " + tidsskrift.titel;
		
		return output;
	}
}