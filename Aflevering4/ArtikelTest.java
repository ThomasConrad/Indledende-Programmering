
public class ArtikelTest {
	
	public static void main(String[] args) {
		
		Forlag unipress = new Forlag("University Press", "Denmark");
		Tidsskrift logic = new Tidsskrift("Journal of Logic");
		Tidsskrift brain = new Tidsskrift("Brain");
		
		logic.setForlag(unipress);
		brain.setForlag(unipress);
		
		Artikel A = new Artikel("A", new String[] {"A. Abe","A. Turing"}, logic);
		Artikel B = new Artikel("B", new String[] {"B. Bim"}, logic);
		
		A.setReferenceListe(new Artikel[] {B});
		
		//brain.setIssn("1234-5678");
		//System.out.println(A);
		//System.out.println(B);
		//System.out.println(logic);
		//System.out.println(unipress);
		//System.out.println(brain);
	}
}