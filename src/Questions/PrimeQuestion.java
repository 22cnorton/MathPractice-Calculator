package Questions;

public class PrimeQuestion extends Question {

	private int num;

	public PrimeQuestion(int num) {
		super('+');
		this.num = num;
	}

	@Override
	protected Object calcSolution() {
		int num = Math.abs(this.num);
		if (num == 1 || num == 0)
			return false;
		for (int i = 2; i < num / 2 + 1; i++) {
			if (num % i == 0)
				return false;
		}
		return true;
	}

	public String toString() {
		return "Is " + num + " a prime number?";
	}

}
