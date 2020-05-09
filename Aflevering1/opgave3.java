public class opgave3 {

	public static final int LINES = 3;

	public static void main(String[] args) {
		//Først løkke printer en linje af outputtet for hver cyklus.
		for (int i = 1; i <= LINES ; i++) {
			//Anden løkke printer mønsteret for hver linje.
			for (int j = 1; j <= LINES; j++) {
				System.out.print("+-");
			}
			//For at skifte til en ny linje bruges println. Funktionskaldet skal være inden i løkken for at det virker efter hensigten.
		System.out.println("+");
		}
		//Funktionskaldet sad udenfor løkken inden ændringen.
		//System.out.println("+");
	}
}
