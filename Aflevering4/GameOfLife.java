/*************************************************
 * Program: GameOfLife
 *
 * Author: Kristian Knudsen (s183779)
 *
 * Course: 02102 Indledende Programmering F12
 *
 * Purpose: Class and methods for simulating Conway's Game of Life
 *
 * Usage: Use either constructor for initial state,
 * then use the nextState() method repeatedly.
 * IMPORTANT: Only square grids are supported currently!
 * ***********************************************/

import java.util.*;
public class GameOfLife {

	private int[][] grid;
	private int size;
	private final int CELLSIZE = 10; //Størrelse af hver celle i pixels

	public GameOfLife(int n) {
		Random rng = new Random();
		size = n;
		grid = new int[n][n];

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				grid[i][j] = rng.nextInt(2);
			}
		}
		initialize(n);
	}

	public GameOfLife(int[][] initialState) {
		grid = initialState;
		size = grid[0].length;
		initialize(size);
	}

	private void initialize(int n) {
		StdDraw.setCanvasSize(n*CELLSIZE,n*CELLSIZE);
		StdDraw.setXscale(0,n);
		StdDraw.setYscale(0,n);
	}

	//Næste stadie af simulering
	public void nextState() {
		//Ændringer foretages på en kopi af grid'et
		//Kopiering skal gøres på en lidt spøjs måde, da .clone() ikke virker så godt på 2D-arrays
		int[][] nextGrid = new int[size][size];
		for (int i = 0; i < size; i++) {
			nextGrid[i] = grid[i].clone();
		}

		//Tjekker alle felterne for om de skal ændres
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				//Cellen ændres ifølge reglerne
				switch (liveNeighbors(i,j)) {
					case 2:
						break;
					case 3:
						nextGrid[i][j] = 1;
						break;
					//I alle andre tilfælde dør cellen
					default:
						nextGrid[i][j] = 0;
						break;
				}
			}
		}
		for (int i = 0; i < size; i++) {
			grid[i] = nextGrid[i].clone();
		}
		draw();
	}

	//Tegner ét felt i koordinatsystemet. 0,0 er nederst til venstre, n-1,n-1 er øverst til højre
	public void drawCell(int x, int y) {
		StdDraw.filledSquare((double)x+0.5,(double)y+0.5,0.5);
	}

	//Tegner grid'et.
	public void draw() {
		StdDraw.clear();
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (grid[i][j] == 1) {drawCell(i,j);}
			}
		}
	}

	//Summerer state af de 8 omkringliggende celler. Derved fås antal levende naboer.
	private int liveNeighbors(int x, int y) {
		int sum = 0;

		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				//I tilfælde af at kanten rammes, antages bare at feltet er tomt
				//Hvis banen skulle være en torus skulle der her laves en ændring for at tjekke om x eller y løber over kanterne, og så tage fra den modsatte side i stedet.
				try {sum += grid[x+i][y+j];}
				catch (ArrayIndexOutOfBoundsException e){}
			}
		}
		//For-loop tæller også cellen der testes, så der trækkes fra summen hvis at cellen er i live:
		if (grid[x][y] == 1) {sum -= 1;}
		return sum;
	}
}
