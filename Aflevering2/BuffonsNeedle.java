import java.util.*;
public class BuffonsNeedle {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		Random rng = new Random();
		System.out.print("Enter number of iterations: ");
		
		//Den absolutte værdi benyttes så negative tal ikke ødelægger programmet, uden at skulle tjekke for dem.
		//Dette betyder dog at negative tal er gyldige inputs, selvom dette nok er noget uintuitivt.0
		int iterations = (input.hasNextInt()) ? Math.abs(input.nextInt()) : 0; //Hvis der ikke intastes et heltal sættes iterations til 0
		int successes = 0;
		double bottom = 0;
		
		//Simuleringen sker i form af en enkelt linje der tjekker om toppunktet er over 2.
		//Da bunden er afgrænset til kun at kunne være mellem 0 og 2 må nålen nødvendigvis krydse linjen hvis toppen er over 2.
		for (int i = 0; i < iterations; i++) {
			if (2*rng.nextDouble()+Math.sin(Math.toRadians(180)*rng.nextDouble())>2) {
				successes++;
			}
		}
		
		//Hvis 'iterations' er 0, så fås der her 0/0, hvilket evalueres til NaN (not a number).
		//Dette er fint, da det betyder at et ikke-validt input resulterer i NaN.
		System.out.println((double)iterations/(double)successes);
	}
}
