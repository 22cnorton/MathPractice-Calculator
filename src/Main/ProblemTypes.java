package Main;

public enum ProblemTypes {// IMPORTANT calcs must follow correct naming scheme
	NUMBERS(1), MONEY(1), SEQUENCE(1), PRIME(2), COINS(2), RECTANGLE(3), TRIANGLE(3), CIRCLE(3), TRAPEZOID(3),
	HEXAGON(3), CHANGE(4), SIMPLIFY_FRACTION(4), TIME(4), DATE(4), FRACTION(5), PERCENT(5);

	public final int scale;

	private ProblemTypes(int scale) {
		this.scale = scale;
	}
	
	 public String toString() {
		 return super.toString().replace('_', '\s');
	 }
}