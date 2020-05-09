import java.util.*;

public class RomanNumerals {
	
	static final String[] ROMANS = {"I","V","X","L","C","D","M"};
	
	public static void main(String[] args) {
		Scanner console = new Scanner(System.in); //Laver ny scanner til at læse input
		System.out.print("Enter positive integer to convert: ");
		int tal = console.nextInt();//Læser input og gemmer i tal
				
		System.out.println(tal + " = " + toRoman(tal)); 
		
		console.close();
				
	}
	
	static String toRoman(int arg) {
		
		if(arg < 1) {
			throw new IllegalArgumentException("Tallet er mindre end 1");
		}
		
		/** Her splittes inputtet i enere, tiere, hundereder og  tusinder **/
		
		int thousands = arg/1000;
		arg -= 1000*thousands;
		int hundreds = arg/100;
		arg -= 100*hundreds;
		int tens = arg/10;
		arg -= 10*tens;
		int singles = arg;
		
		
		return printStringNTimes("M",thousands) + partRoman(hundreds, 4) 
		+ partRoman(tens, 2) + partRoman(singles, 0); //Sætter de relevante strings sammen for at lave romertallet
		
	}
	
	static String partRoman(int arg, int place) { 
		/*Denne funktion tager hånd om de forskellige typer romertal
		 * som et tal kan splittes til. Den bruger ROMANS som er de forskellige tegn.
		 * arg er antallet af tiere, hundreder etc. place er om det er hundreder, tiere etc.
		*/
		if(arg == 9 || arg == 4) {
			return ROMANS[0+place] + ROMANS[arg/4+place];
		}
		else {
			return printStringNTimes(ROMANS[1+place],arg/5) + printStringNTimes(ROMANS[0+place],arg-(5*(arg/5)));
		}

		
		
	}
	
	static String printStringNTimes(String arg, int n) { //simpel funktion der printer en streng n gange.
		String out = "";
		for (int i = 0; i < n; i++) {
			out = out +  arg;
		}
		return out;
		
	}
}
