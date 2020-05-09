import java.awt.*;
import java.util.*;
import java.io.*;

public class RacetrackConsole {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);

		int height = 20;
		int width = 20;
		StdDraw.setXscale(-width/2, width/2);
		StdDraw.setYscale(-height/2, height/2);

		StdDraw.setPenRadius(0.002);

		//Skab baggrunds-grid
		for (int i = -height; i < height; i++) {
			StdDraw.line(-width,i,width,i);
		}
		for (int i = -width; i < width; i++) {
			StdDraw.line(i,-height,i,height);
		}

		StdDraw.setPenRadius(0.01);

		/////////////////
		// Skab banen! //
		/////////////////

		//Bed om level-navn
		System.out.print("Enter level name: ");
		String levelname;
		Scanner level;

		//Load level
		while (true) {
			levelname = input.next();
			try {
				level = new Scanner(new File("./"+levelname));
				break;
			} catch (FileNotFoundException e) {
				System.out.print("Level not found!");
			}
		}

		//Hvor mange linjesegmenter
		int W = level.nextInt();
		Segment[] walls = new Segment[W];

		//Load koordinater for hver væg fra filen, og lav segmenter af dem
		double[] coords = new double[4];
		for (int i = 0; i < W; i++) {
			for (int j = 0; j < 4; j++) {
				coords[j] = level.nextDouble();
			}
			walls[i] = new Segment(coords[0], coords[1], coords[2], coords[3]);
		}
		//Load mållinje, sidste væg fra filen er målet
		for (int j = 0; j < 4; j++) {
			coords[j] = level.nextDouble();
		}
		Segment goal = new Segment(coords[0], coords[1], coords[2], coords[3]);

		//Tegn væggene og målet
		for (Segment wall : walls) {
			wall.draw();
		}
		StdDraw.setPenColor(0,255,0);
		goal.draw();

		//Sæt startpunkt for spillerne, sidste linje i filen
		int[] start = {level.nextInt(), level.nextInt()};
		level.close();
		StdDraw.setPenColor(0,0,0);
		StdDraw.point(start[0],start[1]);

		//Skab spillere og array med spillere og sejrherrer
		Random randGen = new Random();
		System.out.print("Indtast antal spillere: "); //antallet af spillere inputtes
		int playerCount = input.nextInt();
		ArrayList<Player> players = new ArrayList<Player>();
		ArrayList<Player> victors = new ArrayList<Player>();

		//Sæt tilfældige farver
		for(int i = 0; i < playerCount; i++){
			players.add(new Player(start[0],start[1],new Color(randGen.nextInt(256),randGen.nextInt(256),randGen.nextInt(256)),(i+1))); //spillerne bliver given en tilfældig farve hver
		}

		//Kører spillet for alle spillere, mens der er spillere tilbage.
		while(players.size() > 0){
			for(int i = 0; i < players.size();i++){
				Player pc = players.get(i);

				StdDraw.setPenRadius(0.01);

				//Input fra konsollen
				int d = input.nextInt();
				System.out.println(d);

				//Spiller flyttes
				pc.move(direction(d));
				Segment last = new Segment(pc.prevx,pc.prevy,pc.x,pc.y);
				StdDraw.setPenColor(pc.color);
				last.draw();

				//Tjek kollision med mure
				for (Segment wall : walls) {
					if (Segment.collision(last,wall)) {
						System.out.println("Player " + pc.index + " died. Steps: " + pc.steps);
						double[] I = Segment.intersection(last,wall);
						StdDraw.setPenColor(255,0,0);
						StdDraw.setPenRadius(0.02);
						StdDraw.point(I[0],I[1]);
						players.remove(i);
						break;
					}
				}

				//Tjek om mållinje krydses
				/*
				if (Segment.collision(last,goal)) {
					//Tjek om man krydser med uret ved at tage determinanten af vektorerne dannet af linjesegmenterne.
					//Hvis determinanten er positiv krydses der med uret, under antagelse at målet som vekter pejer væk fra centrum.
					if ((pc.x-pc.prevx)*(goal.B[1]-goal.A[1])-(pc.y-pc.prevy)*(goal.B[0]-goal.A[0]) > 0) {
						laps += 1;
					} else {
						laps -= 1;
					}
					System.out.println("LAPS: " + laps);
				}*/

				//Revideret version af ovenstående kode, tilføjer eventuelle vindere samt hvor hurtigt de gjorde det
				if (Segment.collision(last,goal)) {
					//Tjek om man krydser med uret ved at tage determinanten af vektorerne dannet af linjesegmenterne.
					//Hvis determinanten er positiv krydses der med uret, under antagelse at målet som vekter pejer væk fra centrum.
					if ((pc.x-pc.prevx)*(goal.B[1]-goal.A[1])-(pc.y-pc.prevy)*(goal.B[0]-goal.A[0]) > 0) {
						if (pc.side){
							victors.add(pc);
							System.out.println("Player " + pc.index + " has finished. Steps: " + pc.steps);
							players.remove(i);
						}
						pc.side = true;
					} else {
						pc.side = false;
						System.out.println("Det er den forkerte vej");
					}
				}
				//laver en sort prik på start- og slutpunkterne
				StdDraw.setPenColor();
				StdDraw.setPenRadius(0.02);
				StdDraw.point(pc.x, pc.y);
				StdDraw.point(pc.prevx, pc.prevy);
			}
		}
		//Førstplads(erne) findes
		if(victors.size() > 0){
			int bestPlayer = 0;
		for(int i = 1; i < victors.size(); i++){
			if(victors.get(i).steps <= victors.get(bestPlayer).steps){
				bestPlayer = i;
			}
		}

		//Vinder(ne) annonceres til sidst
		System.out.print("Player(s) ");
		for(int i = 0; i < bestPlayer; i++){
			if(victors.get(i).steps == victors.get(bestPlayer).steps){
				System.out.print((i+1) + ", ");
			}
		}
		System.out.print(bestPlayer+1 + " has won!");
		}
		else{ //hvis der ikke er nogen sejrherrer er alle døde
			System.out.println("All players have died");
		}

		//END
		input.close();
	}

	//Oversætter numpad-input til en (x,y) retning ifht. vektor-rally regler
	// 7  8 [9]
	// 4  5  6   --> (1,1)
	// 1  2  3
	public static int[] direction(int n) {
		int[] output = {0,0};
		//Venstre side af numpad
		if (n == 1||n == 4||n == 7) {
			output[0]--;
		//Højre side
		} else if (n == 3||n == 6||n == 9) {
			output[0]++;
		//Nederste række
		} if (n < 4) {
			output[1]--;
		//Øverste
		} else if (n > 6) {
			output[1]++;
		}

		return output;
	}

	public static class Player {

		//Nummer, antal træk og side af målet:
		int index;
		int steps = 0;
		boolean side = true;

		//Initialisering af position og hastighed.
		int x = 0;
		int y = 0;
		//Forrige placering
		private int prevx = 0;
		private int prevy = 0;
		int dx = 0;
		int dy = 0;

		Color color = new Color(0,0,0);

		//Normal constructor.
		Player(int x, int y) {
			this.x = x;
			this.y = y;

		}
		//Constructor med bestemt farve og nummer
		Player(int x, int y, Color color, int index) {
			this.x = x;
			this.y = y;
			this.color = color;
			this.index = index;
		}
		//Flytter efter reglerne for vektor-rally hvor dx og dy ifølgende reglerne for spillet skal være lig -1, 0, 1.
		//Input på 0,0 flytter den bare med den hastighed den allerede har.
		void move(int dx, int dy) {
			this.prevx = x;
			this.prevy = y;
			this.dx += dx;
			this.dy += dy;
			this.x += this.dx;
			this.y += this.dy;
			this.steps++;
		}
		void move(int[] direction) {
			move(direction[0], direction[1]);
		}
		int[] getPrev() {
			int[] temp = {this.prevx,this.prevy};
			return temp;
		}
	}

	public static class Segment {

		//Alt er privat da alle variabler for den generelle form afhænger af punkterne A og B
		//De skal altså opdateres på en specifik måde hvis A og B ændres.

		//Koordinat-baseret:
		private double[] A = new double[2];
		private double[] B = new double[2];

		//Generel form: y=Mx+K, x -> [a1,b1]
		private double M;
		private double K;

		//Special-tilfælde; hvis segmentet er vertikalt
		private boolean vertical = false;

		//Segment ud fra endepunkter
		Segment(double a1, double a2, double b1, double b2) {
			this.A[0] = a1; this.A[1] = a2;
			this.B[0] = b1; this.B[1] = b2;

			//Generel form for linje i xy-koordinatsystem:
			//Konstanterne M og K, svarende til hældning og skæring med y-aksen, udregnes ud fra koordinaterne af punkterne A og B.
			if (a1 == b1) {
				this.vertical = true;
			} else {
				this.M = (b2-a2)/(b1-a1);
				this.K = a2-this.M*a1;
			}
		}

		public void draw() {
			StdDraw.line(A[0], A[1], B[0], B[1]);
		}

		public static boolean isParallel(Segment l1, Segment l2) {
			if (l1.vertical && l2.vertical) {
				return true;
				//Hvis den ene er vertikal, men ikke den anden
			} else if ((l1.vertical && !l2.vertical) || (!l1.vertical && l2.vertical)) {
				return false;
			} else if (l1.M == l2.M) {
				return true;
			}
			return false;
		}

		public static double[] intersection(Segment l1, Segment l2) {
			if (isParallel(l1, l2)) {
				throw new IllegalArgumentException("Segments are parallel");
			}
			double x0;
			double y0;
			// Special-tilfælde for hvis én linje er vertikal
			if (l1.vertical) {
				x0 = l1.A[0];
				y0 = l2.M * x0 + l2.K;
			} else if (l2.vertical) {
				x0 = l2.A[0];
				y0 = l1.M * x0 + l1.K;
			} else { // Ellers, brug formler for skæring mellem to linjer
				x0 = (l2.K - l1.K) / (l1.M - l2.M);
				y0 = l1.M * x0 + l1.K;
			}

			double[] I = { x0, y0 };
			return I;
		}

		//Tjekker om linjesegmenter krydser hinanden
		public static boolean collision(Segment l1, Segment l2) {
			if (isParallel(l1,l2)) {
				return false;
			}
			double[] I = intersection(l1,l2);

			//Tjekker om skæringspunktet ligger inden for intervallet af x- og y-koordinater for både l1 og l2.
			//a1 <= I1 <= b1   eller   b1 <= I1 <= a1    OG    a2 <= I2 <= b2   eller   b2 <= I2 <= a2
			if (((l1.A[0] <= I[0]) && (I[0] <= l1.B[0])  ||  (l1.B[0] <= I[0]) && (I[0] <= l1.A[0]))
			 && ((l1.A[1] <= I[1]) && (I[1] <= l1.B[1])  ||  (l1.B[1] <= I[1]) && (I[1] <= l1.A[1]))
			 && ((l2.A[0] <= I[0]) && (I[0] <= l2.B[0])  ||  (l2.B[0] <= I[0]) && (I[0] <= l2.A[0]))
			 && ((l2.A[1] <= I[1]) && (I[1] <= l2.B[1])  ||  (l2.B[1] <= I[1]) && (I[1] <= l2.A[1]))) {
				return true;
			}
			return false;
		}

		//Segment ud fra generel linjeform
		/*
		Segment(double M, double K, double a1, double b1) {
			this.M = M;
			this.K = K;
			this.A[0] = a1;
			this.B[0] = b1;
			this.A[1] = M*a1+K;
			this.B[1] = M*b1+K;
			if (a1 == b1) {
				this.vertical = true;
			}
		}*/

	}
}
