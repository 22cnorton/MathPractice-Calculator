package Questions;
import java.text.DecimalFormat;

import Main.Fraction;
import Main.Settings;

/**
 * a question comprising of two integers
 * 
 * @author 22cnorton
 *
 */
public class NumberQuestion extends Question {
	private int num1, num2;

	/**
	 * creates a number question with a first value of {@code 1} and a second value
	 * of {@code 10} with a {@code +} operator
	 */
	public NumberQuestion() {
		super();
		num1 = 1;
		num2 = 10;
	}

	/**
	 * creates a number question with the specified values
	 * 
	 * @param num1 value 1
	 * @param num2 value 2
	 * @param op   the operator
	 */
	public NumberQuestion(int num1, int num2, char op) {
		super(op);
		this.num1 = num1;
		this.num2 = num2;
	}

//	public NumberQuestion(Question q) {
//		super(num1, num2, q.getOperator());
//	}
	@Deprecated
	public Fraction getFractionAnswer() {
		return new Fraction(num1, num2).simplify();
	}

	@Override
	protected Object calcSolution() {
		switch (getOperator()) {
		case '+':
			return (double) num1 + num2;
		case '-':
			return (double) num1 - num2;
		case '*':
			return (double) num1 * num2;
		case '÷':
//			Double d = new Double(9.0);
			if (!Settings.isFractionAnswer()) {
				double d = (double) num1 / num2;
				String text = Double.toString(Math.abs(d));
				int integerPlaces = text.indexOf('.');
				int decimalPlaces = text.length() - integerPlaces - 1;
				return (decimalPlaces > 3) ? Double.parseDouble(new DecimalFormat("#0.000").format(d)) : d;
			}
			return new Fraction(num1, num2).simplify();
		}
		return num1 + num2;
	}

//@
	public String toString() {
		return num1 + " " + getOperator() + " " + num2 + " =";
	}

}
