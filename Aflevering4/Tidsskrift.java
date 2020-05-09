
public class Tidsskrift {
	String titel;
	private Forlag forlag;
	private String issn;
	
	public Tidsskrift(String titel, Forlag forlag, String issn) {
		this.titel = titel;
		this.forlag = forlag;
		this.issn = issn;
	}
	public Tidsskrift(String titel) {
		this.titel = titel;
	}
	
	public void setIssn(String issn) {
		this.issn = issn;
	}
	public void setForlag(Forlag forlag) {
		this.forlag = forlag;
	}
	
	public String toString() {
		String output = titel;
		output += (forlag != null) ? (", " + forlag.navn) : "";
		output += (issn != null) ? (", ISSN " + issn) : "";
		
		return output;
	}
}