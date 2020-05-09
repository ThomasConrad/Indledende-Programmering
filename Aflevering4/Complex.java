/*************************************************
 * Program: Complex
 *
 * Author: Thomas Conrad (s183742)
 *
 * Course: 02102 Indledende Programmering F12
 *
 * Purpose: Class and methods for using complex
 * numbers in java.
 *
 * Usage: Create a complex using a constructor,
 * then call the required methods.
 * ***********************************************/
public class Complex {
    private double re;
    private double im;

    //Constructor til 0 + 0i
    public Complex() {
        re = 0;
        im = 0;
    }

    //Constructor med input til koordinater
    public Complex(double re, double im) {
        this.re = re;
        this.im = im;
    }

    //Contructor fra komplest tal. Basically en klon.
    public Complex(Complex z) {
        this.re = z.re;
        this.im = z.im;
    }

    //Returnerer modulus af det komplekse tal
    public double abs() {
        return Math.sqrt(Math.pow(re, 2) + Math.pow(im, 2));
    }

    //Lægger et komplekst tal til tallet
    public Complex plus(Complex other) {
        return new Complex(this.re + other.re, this.im + other.im);
    }
    
    //Trækker et komplekst tal fra tallet
    public Complex minus(Complex other) {
        return new Complex(this.re - other.re, this.im - other.im);
    }

    //Ganger tallet med et andet komplekst tal
    public Complex times(Complex other) {
        return new Complex(this.re * other.re - this.im * other.im, this.im * other.re + this.re * other.im);
    }

    //Dividerer tallet med et andet komplekst tal
    public Complex divide(Complex other) {
        return new Complex((this.re * other.re + this.im * other.im)/(Math.pow(other.re, 2)+Math.pow(other.im, 2)), (this.im * other.re - this.re * other.im)/(Math.pow(other.re, 2)+Math.pow(other.im, 2)));
    }

    //Giver et komplekst tal i samme retning, med længden 1
    public Complex norm() {
        return new Complex(this.divide(new Complex(this.abs(),0)));
    }

    //Konverterer tallet til en streng
    public String toString() {
        return re + " + " + im + "i";
    }

    //Getters
    public double getRe() {
        return re;
    }

    public double getIm() {
        return im;
    }

}