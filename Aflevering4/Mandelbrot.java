/*************************************************
 * Program: Mandelbrot
 *
 * Author: Thomas Conrad (s183742)
 *
 * Course: 02102 Indledende Programmering F12
 *
 * Purpose: To calculate and illustrate the Mandelbrot set
 *
 * Usage: Insert initial conditions and drawing mode.
 * Thereafter drag an area to zoom in on. Use +/- 
 * to change the drawn resolution.
 * ***********************************************/

import java.io.*;
import java.util.*;
import java.awt.*;

public class Mandelbrot {
    static final int MAX = 255;
    static int points;
    static Scanner console = new Scanner(System.in);
    static String colourFile;
    static int[][] colours = new int[256][3];

    public static void main(String[] args) {

        double[] center = new double[2];
        double width[] = new double[2];
        int mode;
        // Robust input af værdier
        System.out.print("Enter mode. 1->b/w, 2->random colours 3->colour of choice: ");
        mode = tryReadInt(console);
        while (mode != 3 && mode != 2 && mode != 1) {
            System.out.println("Wrong pick, try again!");
            System.out.print("Enter mode. 1->b/w, 2->random colours 3->colour of choice: ");
            mode = tryReadInt(console);
        }
        if (mode == 3) {
            System.out.print("Enter colour map name: ");
            while (loadColour(console.next()))
                ;
        }
        System.out.print("Enter center real part: ");
        center[0] = tryReadDouble(console);
        System.out.print("Enter center imaginary part: ");
        center[1] = tryReadDouble(console);
        System.out.print("Enter width: ");
        width[0] = tryReadDouble(console);
        while (width[0] < 0) {
            System.out.print("Enter a positve width: ");
            width[0] = tryReadDouble(console);
        }
        width[1] = width[0];
        System.out.print("Enter resolution [1-700]: ");
        points = tryReadInt(console);
        while (points < 1) {
            System.out.print("Enter a positve resolution: ");
            points = tryReadInt(console);
        }

        StdDraw.setCanvasSize(700, 700);
        drawVersion(mode, width, center);

        // Starter tilstanden hvor man kan zoome og ændre opløsning
        while (true) {
            if (StdDraw.mousePressed()) {
                Complex init = new Complex(StdDraw.mouseX(), StdDraw.mouseY());
                while (StdDraw.mousePressed()) {
                }
                Complex end = new Complex(StdDraw.mouseX(), StdDraw.mouseY());
                Complex diagonal = end.minus(init);
                if (diagonal.abs() == 0) { // checker at man har trukket og ikke bare klikket.
                    continue;
                }
                Complex scale = diagonal.norm().times(new Complex(1000, 0)); // udregner en vinduesskala ud fra
                                                                             // diagonalen

                width[0] = Math.abs(diagonal.getRe());
                width[1] = Math.abs(diagonal.getIm());
                center[0] = init.getRe() + diagonal.getRe() / 2;
                center[1] = init.getIm() + diagonal.getIm() / 2;

                StdDraw.setCanvasSize(Math.abs((int) scale.getRe()), Math.abs((int) scale.getIm()));
                System.out.println("Center:" + center[0] + " + " + center[1] + " i. WidthRe: " + width[0] + ", WidthIm: " + width[1] );
                drawVersion(mode, width, center); // tegner en ny fra det nye centrum

            }
            if (StdDraw.hasNextKeyTyped()) { // Checker om man vil ændre opløsningen
                int sign = StdDraw.nextKeyTyped();
                if (sign == '-' && points > 2) {
                    points *= 0.9;
                    drawVersion(mode, width, center);

                }
                if (sign == '+' && points < 800) {
                    points *= 1.1;
                    if (points < 10) {
                        points += 1;
                    }
                    drawVersion(mode, width, center);

                }
                if (sign == ' ') {
                    width[0] = 2;
                    width[1] = 2;
                    center[0] = -0.5;
                    center[1] = 0;
                    StdDraw.setCanvasSize(700, 700);
                    drawVersion(mode, width, center);

                }
            }
        }
    }

    // metode til at udregne antal iterationer af mandelbrot
    public static int iterate(Complex z0) {
        Complex z = new Complex(z0);
        for (int i = 0; i < MAX; i++) {
            if (z.abs() > 2.0) {
                return i;
            }
            z = z.times(z).plus(z0);
        }
        return MAX;
    }

    // Sætter vinduesskalaen så den passer inden for det man har zoomet ind på
    public static void setScale(double[] width, double[] center) {
        StdDraw.setXscale(-width[0] / 2 + center[0] - width[0] / (2 * points),
                width[0] / 2 + center[0] + width[0] / (2 * points - 2));
        StdDraw.setYscale(-width[1] / 2 + center[1] - width[1] / (2 * points),
                width[1] / 2 + center[1] + width[1] / (2 * points - 2));
    }

    //Tegner et sort-hvidt mandelbrot-sæt
    public static void drawMandel(double[] width, double[] center) {
        StdDraw.clear();
        setScale(width, center);
        StdDraw.setPenColor();
        for (double i = 0; i < points; i++) {
            for (double j = 0; j < points; j++) {
                Complex z0 = new Complex((center[0] - width[0] / 2 + (width[0] * i) / (points - 1)),
                        (center[1] - width[1] / 2 + (width[1] * j) / (points - 1)));
                if (iterate(z0) == MAX) {
                    StdDraw.filledSquare(z0.getRe(), z0.getIm(), Math.max(width[0], width[1]) / (points));
                }
            }
            // StdDraw.text(z0.getRe(), z0.getIm() + 0.1, z0.toString());
        }
    }

    // Metoden til at tegne mandelbrød sættet med en selvvalgt farvning
    public static void drawColouredMandel(double[] width, double[] center) {
        StdDraw.clear();
        setScale(width, center);

        for (double i = 0; i < points; i++) {
            for (double j = 0; j < points; j++) {
                Complex z0 = new Complex((center[0] - width[0] / 2 + (width[0] * i) / (Double.valueOf(points) - 1)),
                        (center[1] - width[1] / 2 + (width[1] * j) / (Double.valueOf(points) - 1)));
                StdDraw.setPenColor(
                        new Color(colours[iterate(z0)][0], colours[iterate(z0)][1], colours[iterate(z0)][2]));
                StdDraw.filledSquare(z0.getRe(), z0.getIm(), 5*Math.max(width[0], width[1]) / (Double.valueOf(points))); // teger squares
                                                                                                       // som pixels
                // StdDraw.setPenColor(255,255,255);
                // StdDraw.point(z0.getRe(), z0.getIm());
            }
            // StdDraw.text(z0.getRe(), z0.getIm() + 0.1, z0.toString());
        }
    }
    
    //Tegner mandelbrot med tilfældige farver
    public static void drawRandomMandel(double[] width, double[] center) {
        StdDraw.clear();
        int[][] colours = generateColours();
        setScale(width, center);

        for (double i = 0; i < points; i++) {
            for (double j = 0; j < points; j++) {
                Complex z0 = new Complex((center[0] - width[0] / 2 + (width[0] * i) / (points - 1)),
                        (center[1] - width[1] / 2 + (width[1] * j) / (points - 1)));
                StdDraw.setPenColor(
                        new Color(colours[iterate(z0)][0], colours[iterate(z0)][1], colours[iterate(z0)][2]));
                StdDraw.filledSquare(z0.getRe(), z0.getIm(), Math.max(width[0], width[1]) / (points));
                // StdDraw.setPenColor(255,255,255);
                // StdDraw.point(z0.getRe(), z0.getIm());
            }
            // StdDraw.text(z0.getRe(), z0.getIm() + 0.1, z0.toString());
        }
    }

    //Tegner én af de tre versioner, afhængigt af den valgte mode
    public static void drawVersion(int mode, double[] width, double[] center) {
        StdDraw.show(0);
        switch (mode) {
        case 1:
            drawMandel(width, center);
            break;
        case 2:
            drawRandomMandel(width, center);
            break;
        case 3:
            drawColouredMandel(width, center);
            break;
        }
        StdDraw.show(0);
    }

    //Forsøger at læse farverne fra en fil i mappen ./mnd
    public static boolean loadColour(String colour) {
        try {
            Scanner colorFile = new Scanner(new File("mnd/" + colour + ".mnd"));
            int i = 0;
            while (colorFile.hasNextLine()) {
                int j = 0;
                Scanner line = new Scanner(colorFile.nextLine());
                while (line.hasNextInt()) {
                    colours[i][j] = line.nextInt();
                    j++;
                }
                line.close();
                i++;

            }
            colorFile.close();
            return false;
        } catch (FileNotFoundException e) {
            System.out.println("ColourFile not found! Try again!");
            System.out.print("Enter colour map name: ");
            return true;
        }
    }

    //Genererer en farvefil med tilfælige farver
    public static int[][] generateColours() {
        Random rand = new Random();
        int[][] colours = new int[256][3];
        for (int i = 0; i < 256; i++) {
            for (int j = 0; j < 3; j++) {
                colours[i][j] = rand.nextInt(256);
            }
        }
        return colours;
    }

    //Robust metode til at læse en double
    public static double tryReadDouble(Scanner console) {
        double val;
        while (true) {
            try {
                val = Double.parseDouble(console.next());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid, try again.");
                // continue is redundant
            }
        }
        return val;
    }

    //Robust metode til at læse et heltal
    public static int tryReadInt(Scanner console) {
        int val;
        while (true) {
            try {
                val = Integer.parseInt(console.next());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid, try again.");
                // continue is redundant
            }
        }
        return val;
    }
}
