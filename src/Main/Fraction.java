package Main;
import java.text.DecimalFormat;

/**
 * a fraction with methods for manipulating the fraction
 *
 * @author 22cnorton
 *
 */
public class Fraction extends Object {
	/**
	 * the numerator
	 */
	private int num;
	/**
	 * the denominator
	 */
	private int den;

	/**
	 * creates a fraction with a numerator and denominator
	 *
	 * @param num the numerator
	 * @param den the denominator
	 */
	public Fraction(int num, int den) throws NumberFormatException {
		if (den == 0) {
			throw new NumberFormatException("Zero denominator");
		}
		if (num < 0 && den < 0) {
			this.num = Math.abs(num);
			this.den = Math.abs(den);
		} else if (den < 0) {
			this.num = num * -1;
			this.den = Math.abs(den);
		} else {
			this.num = num;
			this.den = den;
		}
	}

	public Fraction(Fraction f) {
		this.num = f.getNum();
		this.den = f.getDen();
	}

	/**
	 * gets value of the numerator
	 *
	 * @return numerator
	 */
	public int getNum() {
		return num;
	}

	/**
	 * sets the numerator to the entered value
	 *
	 * @param num new numerator value
	 */
	public void setNum(int num) {
		this.num = num;
	}

	/**
	 * gets the value of the denominator
	 *
	 * @return denominator
	 */
	public int getDen() {
		return den;
	}

	/**
	 * sets the value of the denominator to the entered value
	 *
	 * @param den new denominator value
	 */
	public void setDen(int den) {
		this.den = den;
	}

	/**
	 * Calculates the fraction as a decimal and rounds it to 3 decimal places
	 *
	 * @return the fraction as a decimal
	 */
	public double asDecimal() {
		DecimalFormat dec = new DecimalFormat("#0.000");
		return Double.parseDouble(dec.format((double) num / den));
	}

	/**
	 * tests if two fractions are exactly equal
	 *
	 * @param f the fraction to be compared
	 * @return {@code true} if the two fractions are equal; {@code false} otherwise
	 */
	@Override
	public boolean equals(Object f) {
//		System.out.println(f);
		return (f instanceof Fraction && num == ((Fraction) f).getNum() && den == ((Fraction) f).getDen());
	}

	/**
	 * @return the fraction as a string with '{@code /}' between the numerator and
	 *         denominator
	 */
	@Override
	public String toString() {
		return num + (den == 1 ? "" : "/" + den);
	}

	/**
	 * Simplifies the fraction
	 *
	 * @return the simplified fraction
	 */
	public Fraction simplify() {
		int GCF = findGCF(num, den);// finds the gcf to simplify

		return new Fraction(num / GCF, den / GCF);
	}

	/**
	 * adds two fractions together
	 *
	 * @param num the fraction to be added
	 * @return the simplified form of the sum of the two fractions
	 */
	public Fraction add(Fraction num) {// adds two fractions
		int numerator = this.num * num.getDen() + num.getNum() * this.den;
		int denominator = this.den * num.getDen();
		return new Fraction(numerator, denominator).simplify();

	}

	/**
	 * subtracts two fractions
	 *
	 * @param num the fraction to be subtracted
	 * @return the simplified form of the difference of the two fractions
	 */
	public Fraction subtract(Fraction num) {// subtracts two fractions
		int numerator = this.num * num.getDen() - num.getNum() * this.den;
		int denominator = this.den * num.getDen();
		return new Fraction(numerator, denominator).simplify();

	}

	/**
	 * finds the greatest common factor of a the numerator and denominator of a
	 * fraction
	 *
	 * @param numerator
	 * @param denominator
	 * @return the greatest common factor of the numerator and denominator
	 */
	private int findGCF(int numerator, int denominator) {// finds the greatest common factor of two Fractions
		int GCF = 1;
		for (int i = 1; i <= Math.abs(Math.min(numerator, denominator)); i++) {// loops through every Fraction from one
																				// two the smallest of the two inputs
			if (numerator % i == 0 && denominator % i == 0)// if the current Fraction evenly divides both inputs
				GCF = i;
		}
		return GCF;
	}

	/**
	 * multiplies two fractions
	 *
	 * @param num the fraction to be multiplied
	 * @return the simplified form of the product of the two fractions
	 */
	public Fraction multiply(Fraction num) {// multiplies two fractions
		int numerator = this.num * num.getNum();
		int denominator = this.den * num.getDen();
		return new Fraction(numerator, denominator).simplify();

	}

	/**
	 * Divides two fractions
	 *
	 * @param num the fraction to be divided by
	 * @return the simplified form of the quotient of the two fractions
	 */
	public Fraction divide(Fraction num) {// divides two fractions
		int numerator = this.num * num.getDen();
		int denominator = this.den * num.getNum();
		return new Fraction(numerator, denominator).simplify();

	}
}
