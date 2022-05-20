package Questions;
import Main.Fraction;
import Main.Settings;

/**
 * a question comprising of two fractions
 * 
 * @author 22cnorton
 *
 */
public class FractionQuestion extends Question {
	private Fraction f1, f2;

	/**
	 * creates a fraction question with the first fraction being {@code 1/2} and the
	 * second fraction being {@code 10/20} and the operator of {@code +}
	 */
	public FractionQuestion() {
		super();
		f1 = new Fraction(1, 2);
		f2 = new Fraction(10, 20);
	}

	/**
	 * creates a new fraction question
	 * 
	 * @param num1     numerator of fraction 1
	 * @param den1     denominator of fraction 1
	 * @param num2     numerator of fraction 2
	 * @param den2     denominator of fraction 2
	 * @param operator the operator to be used in the question
	 */
	public FractionQuestion(int num1, int den1, int num2, int den2, char operator) {
		super(operator);
		f1 = new Fraction(num1, den1);
		f2 = new Fraction(num2, den2);
	}

	public Object calcSolution() {
		Fraction out;
		switch (getOperator()) {// manipulates the fractions based on the problem operator
		case '-':
			out = f1.subtract(f2);
			break;
		case '*':
			out = f1.multiply(f2);
			break;
		case '÷':
			out = f1.divide(f2);
			break;
		default:
			out = f1.add(f2);
			break;
		}
		if (Settings.isFractionAnswer()) {// if the answer is as a fraction
			return out;
		}
		return out.asDecimal();// if the answer is as a decimal
	}

	public String toString() {
		return f1.toString() + " " + getOperator() + " " + f2.toString() + " =";
	}

}
