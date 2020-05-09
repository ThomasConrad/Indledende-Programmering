/*************************************************
 * Program: PrimeFactors
 * 
 * Author: Thomas Conrad (s183742)
 * 
 * Course: 02102 Indledende Programmering F12
 * 
 * Purpose: This program finds the primefactors of a number
 * 
 * Usage: You input the number in the console and the factors 
 * 		  are returned in the console.
 * ***********************************************/


import java.util.*; 

public class PrimeFactors {
	static List<Long> factors = new ArrayList<Long>(); //Initialiserer Arraylisten til at holde primfaktorerne
	public static void main(String[] args) {
		Scanner console = new Scanner(System.in); 
		System.out.print("Enter integer greater than 1 (0 to terminate): ");
		long input = console.nextLong(); //læser tal fra en scanner som den finder primfaktorerne til

		while(input != 0) { // Kører indtil man giver 0 som input
			primeFactor(input); //finder primfaktorerne til inputtet, og gemmer dem i factors
			Collections.sort(factors); //sorterer så man får de mindste faktorer først
			System.out.print("List of prime factors: " + arrayToString(factors));
			System.out.print("\nEnter integer greater than 1 (0 to terminate): ");
			input = console.nextLong();
			factors.clear(); //clearer primfaktorerne så den er klar til at modtage igen
			}
		console.close();
	}
	//Denne metode anvender rekursion til at finde alle primfaktorer
	public static void primeFactor(long arg) { 
		if (findFactor(arg) != 0) { //så længe tallet ikke er et primtal kører den igen
			primeFactor(findFactor(arg)); //Finder en primfaktor af tallet og skriver den til listen
			arg /= findFactor(arg); //deler med primfaktoren for at få den ud
			primeFactor(arg); //finder en ny primfaktor til arg
		}
		else {
			factors.add(arg); //Gemmer primfaktoren hver gang at den finder den.
		}
	}
	//Denne metode finder en primfaktor til et tal. Hvis den ikke finder et, returnerer den 0
	public static long findFactor(long arg) { 
		for (long i = (long) Math.sqrt(arg); i > 1; i--) {
			if (arg%i == 0) { 
				return i;
			}
		}	
		return (long) 0;
	}
	//Omdanner listen med faktorer til en streng til at printe
	public static String arrayToString(List<Long> arg) { 
		String out = arg.get(0).toString();
				for (int i = 1; i < arg.size(); i++) {
			out += ", " + arg.get(i).toString();
		}
		return out;
	}
}