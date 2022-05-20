package Main;

public enum ProblemTypes {// IMPORTANT calcs must follow correct naming scheme
	NUMBERS(1), MONEY(1), SEQUENCE(1), PRIME(2), COINS(2), RECTANGLE(3), TRIANGLE(3), CIRCLE(3), TRAPEZOID(3),
	HEXAGON(3), CHANGE(4), SIMPLIFY_FRACTION(4), TIME(4), DATE(4), FRACTION(5), PERCENT(5);

	public final int scale;

	private ProblemTypes(int scale) {
		this.scale = scale;
	}

	/*
	 * original order
	 */

	/*
	 * NUMBERS(1), MONEY(1), FRACTION(4), COINS(2), TIME(3), SEQUENCE(1),
	 * SIMPLIFY_FRACTION(3), DATE(3), RECTANGLE(2), CIRCLE(2), TRIANGLE(2),
	 * TRAPEZOID(2), PERCENT(4), CHANGE(3), PRIME(2);
	 */
}