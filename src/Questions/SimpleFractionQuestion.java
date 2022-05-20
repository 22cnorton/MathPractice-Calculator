package Questions;
import Main.Fraction;

public class SimpleFractionQuestion extends Question {

	private Fraction f1;

	public SimpleFractionQuestion(int num, int den, char op) {
		super(op);
		f1 = new Fraction(num, den);
	}

	@Override
	Object calcSolution() {
		return f1.simplify();
	}

	
	public String toString() {
		return "Simplify "+f1.toString();  
	}
}
