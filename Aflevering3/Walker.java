/*************************************************
 * Author: Thomas Conrad (s183742)
 * 
 * Course: 02102 Indledende Programmering F12
 * 
 * Purpose: This class containts methods to simulate 
 * 					and initialize a random walk
 * ***********************************************/
import java.util.Random; //importerer random for at generere tilfældige tal

public class Walker {
	Random randGen = new Random();
	int[] position = {0,0};
	int grid;
	int steps = 0;
	
	//Constructor der laver walkeren
	public Walker(int gridSize) {
		grid = gridSize;
		System.out.println("Position: "+ "(" + String.valueOf(position[0]) +", "+ String.valueOf(position[1]) + ")"); //Printer dens position i starten for at ungå fencepost fejl
	}
	//metode der får walkeren til at tage et skridt i en tilfældig retning.
	public void walk() {
		int[] prev = { position[0], position[1] };
		steps++;
		int dir = randGen.nextInt(4); //generer den tilfældige retning. 0=højre, 1=op, 2=venstre, 3=ned
		position[0] += (int)Math.cos(dir*Math.PI/2) ; //Flytter positionen til en af de fire sider
		position[1] += (int)Math.sin(dir*Math.PI/2) ;
		System.out.println("Position: "+ "(" + String.valueOf(position[0]) +", "+ String.valueOf(position[1]) + ")");
		StdDraw.line(prev[0],prev[1],position[0],position[1]);

	}
	//Metode der checker om walkeren stadig er i det givne grid ved at se om begge er i griddet
	public boolean InGrid() {
		if(Math.abs(position[0]) == grid || Math.abs(position[1]) == grid) {
			return false;
		}
		return true;
	}
	
	/**Getters and Setters**/
	public int[] getPosition() {
		return position;
	}
	public void setPosition(int[] input) {
		this.position = input;
	}
	public int getSteps() {
		return steps;
	}
	public void setSteps(int input) {
		this.steps = input;
	}
	

}