public class opgave1 {
	
	/***  Her deklareres de linjer som skal printes  ***/
	static String line1 = ("Use \"\\\\\" to obtain a ’backslash’ character. \n"); //\n er tilføjet for at ændre linje
	static String line2 = "Remember:\n";	
	
	public static void main(String[] args) {
		
		System.out.println(line1 + line2 + line1); 	//Her udskrives teksten i rækkefølge
	}
}


