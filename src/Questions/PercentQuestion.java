package Questions;
import java.text.DecimalFormat;

public class PercentQuestion extends Question {
	private int num;
	private double percent;

	public PercentQuestion(int num, double percent) {
		super();
		this.num = num;
		this.percent = percent;
	}

	@Override
	Object calcSolution() {
		double d = num * percent * 0.01;
		String text = Double.toString(Math.abs(d));
		int integerPlaces = text.indexOf('.');
		int decimalPlaces = text.length() - integerPlaces - 1;
		if (decimalPlaces > 2) {
			DecimalFormat dec = new DecimalFormat("#0.00");
			return Double.parseDouble(dec.format(d));
		}
		return d;
	}

	public String toString() {
		DecimalFormat dec=new DecimalFormat("#0.##%");
		return "What is " + dec.format(percent/100) + " of " + num + "?";
	}

}
