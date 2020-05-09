import java.util.*;
public class Palindrome {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		
		System.out.print("Enter line to check: ");
		String palindrome = input.nextLine();
		
		if (palindrome.replaceAll("[\\W\\d]", "").length() < 1) {
			System.out.println("Invalid input!");
		} else {
			// Bruger en regular expression til at fjerne alle tegn der ikke er A-Z
			System.out.println("\"" + palindrome + "\"" + (isPalindrome(palindrome.replaceAll("[\\W\\d]", "").toLowerCase()) ? " is " : " is not ") + "a palindrome.");
		}		
	}
	
	//Er ligeglad om der er special-tegn eller ej, men de fjernes i main()
	public static boolean isPalindrome(String palindrome) {
		
		for (int i=palindrome.length(); i>0 ;i--) {
			//Løkken kører både for- og baglæns gennem strengen, og sammenligner hver char.
			if (palindrome.charAt(palindrome.length()-i) != palindrome.charAt(i-1)) {
				return false;
			}
		}
		return true;
	}
}
