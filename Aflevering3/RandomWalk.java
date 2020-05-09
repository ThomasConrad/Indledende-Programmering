/*************************************************
 * Program: RandomWalk
 * 
 * Author: Thomas Conrad (s183742)
 * 
 * Course: 02102 Indledende Programmering F12
 * 
 * Purpose: This program calculates a random walk
 * 
 * Usage: You input the gridsize in the console 
 * 		  and the program visualizes the walk in 2d
 * ***********************************************/

import java.util.Scanner; //brugt til input

public class RandomWalk {

	public static void main(String[] args) {

		Scanner console = new Scanner(System.in);
		System.out.print("Enter size of grid: ");
		int gridSize = console.nextInt(); //læser gridsize
		Walker white = new Walker(gridSize); //initialiserer en ny walker, se klassen walker
		StdDraw.setXscale(-gridSize-0.5, gridSize+0.5); //Setter gridstørrelsen, alt afhængigt af hvor stort det skal være
		StdDraw.setYscale(-gridSize-0.5, gridSize+0.5);
		StdDraw.setPenRadius(0.3/gridSize);
		while (white.InGrid()) { //walkeren tager skridt i en tilfældig retning mens den er i feltet
			white.walk();
		}
		System.out.println("Total number of steps = " + white.getSteps());
		
		console.close();

	}	

}