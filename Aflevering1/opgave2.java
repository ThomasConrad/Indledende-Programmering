public class opgave2 {
	public static void main(String[] args) {
		//Udtrykket inden i println-funktionen kan reduceres ved algebra til: 6.5+5x=42. Derved: x=7.1
		//Udtrykket giver det forkerte hvis ikke at tallene ændres fra heltal til decimaltal, da 5/4*2=2.5 bliver rundet ned til 2.
		//Derved skal x faktisk være 7.2.
		double x = 7.2;
		//For at få resultatet 42 i stedet for 42.0, så bruges type-casting til at ændre resultatet til en integer.
		System.out.println((int)(1 + 3 + x + x + x + x + x + 5 / 4 * 2));
	}
}
