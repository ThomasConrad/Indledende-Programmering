/*************************************************
 * Program: GameOfLifeMain
 *
 * Author: Kristian Knudsen (s183779)
 *
 * Course: 02102 Indledende Programmering F12
 *
 * Purpose: Simulates Conway's Game of Life
 *
 * Usage: Run the program, follow the inputs.
 * Files need either the full or relative path. "test", "C:/ ... /file.gol", ...
 * IMPORTANT: The class GameOfLife only supports square grids.
 * ***********************************************/

import java.util.*;
import java.io.*;
public class GameOfLifeMain {
	public static void main(String[] args) {

		int INTERVAL = 200; //Tid i millisekunder mellem hvert skift. Kan ikke nødvendigvis følge med altid.
		int SIZE = 20;

		Scanner input = new Scanner(System.in);

		//Fra fil eller tilfædig med størrelse SIZE
		System.out.println("From file (1) or random (2)?");
		String answ;
		while (true) {
			answ = input.next();

			//Tilfældigt
			if (answ.equals("2")) {
				GameOfLife game = new GameOfLife(SIZE);
				while (true) {
					game.nextState();
					StdDraw.show(INTERVAL);
				}

			//Fra fil
			} else if (answ.equals("1")) {

				// Bed om fil-navn
				System.out.print("Enter file-name: ");
				String filename;
				Scanner level;
				// Load level
				while (true) {
					filename = input.next();
					try {
						level = new Scanner(new File("./" + filename));
						break;
					} catch (FileNotFoundException e) {
						System.out.print("Level not found!");
					}
				}

				//Finder størrelse af matricen
				//Kodet til at kunne tage u-kvadratiske matricer, selvom resten af programmet ikke understøtter dette!
				int width = 0;
				int height = 1; //Initialiseres til 1 da der benyttes en .nextLine for at finde width
				width = (level.nextLine().length()+1)/2;
				while (true) {
					try {
						level.nextLine();
						height++;
					} catch (NoSuchElementException e) {
						break;
					}
				}
				level.close();

				//Level genåbnes
				try {
					level = new Scanner(new File("./" + filename));
				} catch (FileNotFoundException e) {
					System.out.println("If this message appears, something really weird has happened.");
				}

				//Start-grid defineres
				int[][] initGrid = new int[width][height];
				for (int j = 0; j < height; j++) {
					for (int i = 0; i < width; i++) {
						//y-koordinaten tæller baglæns for at filen og grid'et har samme orientering i y-retningen
						initGrid[i][height-j-1] = level.nextInt();
					}
				}
				GameOfLife game = new GameOfLife(initGrid);
				while (true) {
					game.nextState();
					StdDraw.show(INTERVAL);
				}

			//Afslutning af valg mellem fil eller random
			} else {
				System.out.println("Invalid input.");
			}
		} //slut while-løkke

	}//slut main
}//slut klasse
