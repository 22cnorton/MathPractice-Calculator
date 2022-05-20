package Questions;
/**
 * a question comprising of two dollar amounts
 * 
 * @author 22cnorton
 *
 */
public class MoneyQuestion extends Question {
	private int cents1, cents2, dol1, dol2;

	/**
	 * Creates a money question with a first value of {@code $1.00} and a second
	 * value of {@code $2.00} with the {@code +} operator
	 */
	public MoneyQuestion() {
		super();
		dol1 = 1;
		dol2 = 2;
		cents1 = 0;
		cents2 = 0;
	}

	/**
	 * creates a new money question with the specified values
	 * 
	 * @param dollar1  the dollar amount for value 1
	 * @param cents1   the cents amount for value 1
	 * @param dollar2  the dollar amount for value 2
	 * @param cents2   the cents amount for value 2
	 * @param operator
	 */
	public MoneyQuestion(int dollar1, int cents1, int dollar2, int cents2, char operator) {
		super(operator);
		dol1 = dollar1;
		dol2 = dollar2;
		this.cents1 = cents1;
		this.cents2 = cents2;
//		this.cents2=1;
//		super.setSolution(calcSolution(cents1, cents2));
	}

	public Object calcSolution() {

		final double m1 = dol1 + cents1 / 100.0;
		final double m2 = dol2 + cents2 / 100.0;

		switch (getOperator()) {// performs the correct operation based on the problems operator
		case '-':
			return Math.round(((m1) - (m2)) * 100.0) / 100.0;
		case '*':
			return Math.round(((m1) * (m2)) * 100.0) / 100.0;
		case '÷':
			return Math.round(((m1) / (m2)) * 100.0) / 100.0;
		default:
			return Math.round(((m1) + (m2)) * 100.0) / 100.0;
		}
	}

	@Override
	public String toString() {
		String cents1 = (this.cents1 < 10) ? "0" + this.cents1 : Integer.toString(this.cents1),
				cents2 = (this.cents2 < 10) ? "0" + this.cents2 : Integer.toString(this.cents2);

		return "$" + dol1 + "." + cents1 + " " + getOperator() + " $" + dol2 + "." + cents2 + " =";
	}
}
