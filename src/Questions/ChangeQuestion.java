package Questions;
import java.text.DecimalFormat;

public class ChangeQuestion extends Question {
	private int costD, costC, payD, payC;

	public ChangeQuestion(double payment, double cost) {
		costD = (int) cost;
		payD = (int) payment;

		String temp = String.valueOf(cost).split("\\.")[1];
		costC = Integer.parseInt(temp);

		temp = String.valueOf(payment).split("\\.")[1];
		payC = Integer.parseInt(temp);
	}

	public ChangeQuestion(int paymentD, int paymentC, int costD, int costC) {
		payD = paymentD;
		payC = paymentC;
		this.costD = costD;
		this.costC = costC;
	}

	@Override
	public Object getSolution() {
		DecimalFormat dec = new DecimalFormat("#0.00");
		return Double.parseDouble(dec.format(calcSolution()));
	}

	@Override
	public Object calcSolution() {
		int diffD = payD - costD;
		int diffC = payC - costC;

		if (diffC > 0) {
			diffD--;
			diffC = 100 - (payC - costC);
		}

//		ChangeCalc.getCoins(diffD + diffC / 100.0);

		return diffD + diffC / 100.0;
	}

	/*
	 * @Override public boolean answerEquals(Object o) { System.out.println(o);
	 * return o.equals(getSolution()); }
	 */

	@Override
	public String toString() {

		return "The cost is " + costD + "." + ((costC < 10) ? "0" + costC : Integer.toString(costC))
				+ " and the payment was " + payD + "." + ((payC < 10) ? "0" + payC : Integer.toString(payC))
				+ ". What is the change?";
	}

}