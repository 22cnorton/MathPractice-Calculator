package Questions;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javax.management.AttributeNotFoundException;

import Main.ProblemTypes;
import Main.Settings;
import Money.Coin;
import Money.Dime;
import Money.Nickel;
import Money.Penny;
import Money.Quarter;

public class QuestionGenerator {
	/**
	 * creates a new change question
	 * 
	 * @return the generated change question
	 */
	public static ChangeQuestion nextChangeQuestion() {
		int cost = new Random().nextInt(Math.abs(Settings.getUpperBound() - Settings.getLowerBound()))
				+ Settings.getLowerBound();
		/*
		 * causes the payment amount to vary
		 */
		final int[] ROUND_AMOUNTS = { 5, 10, 20 };
		int nextPay = ROUND_AMOUNTS[(int) (Math.random() * ROUND_AMOUNTS.length)];

		/*
		 * creates the payment by adding the amount to over pay to the cost, and
		 * subtracting it by the remainder to round it to the nearest five dollars
		 */
		int payment = (cost + nextPay) - (cost % nextPay);

		/*
		 * makes sure that the change is realistic, (ex) would not give a bill that
		 * would be handed back completely
		 */
		if (payment > 30 && (payment % 5 == 0 && payment % 2 != 0)) {
			payment -= 5;
			if (payment <= cost)
				payment += 10;
		}

		return new ChangeQuestion(payment, 0, cost, new Random().nextInt(99));
	}

	/**
	 * generates coins for a problem
	 *
	 * @return an array list of random coins
	 */
	public static CoinQuestion getCoins() {
		ArrayList<Coin> c = new ArrayList<>();
//		Random r = new Random();
//		int range = Settings.getUpperBound() - Settings.getLowerBound() + 1;
//		int j = r.nextInt(q);
		int size = (int) (Math.random() * (1 + Settings.getUpperBound() - Settings.getLowerBound()))
				+ Settings.getLowerBound();
//		System.out.println(size);
		while (c.size() < size) {
			switch ((int) (Math.random() * 4)) {
			case 0:
				c.add(new Quarter());
				break;
			case 1:
				c.add(new Dime());
				break;
			case 2:
				c.add(new Nickel());
				break;
			case 3:
				c.add(new Penny());
				break;
			}
		}

		Collections.sort(c, Collections.reverseOrder());
		return new CoinQuestion(c);
	}

	/**
	 * sorts and array of coins based on value
	 *
	 * @param arr the array of coins to be sorted
	 * @return and array list of coins sorted from highest to lowest value
	 */
	@SuppressWarnings("unused")
	@Deprecated
	private static ArrayList<Coin> coinSort(ArrayList<Coin> arr) {
		int n = arr.size();

		for (int i = 0; i < n - 1; i++) {
			// Find the minimum element in unsorted array
			int min_idx = i;
			for (int j = i + 1; j < n; j++) {
				if (arr.get(j).compareTo(arr.get(min_idx)) > 0)
					min_idx = j;
			}
			// Swap the found minimum element with the first element
			Coin temp = arr.get(min_idx);
			arr.set(min_idx, arr.get(i));
			arr.set(i, temp);
		}
		return arr;
	}

	public static Question nextQuestion() throws AttributeNotFoundException {
		Random r = new Random();
		int reps = 0;
//		Question q;
//		if (s.getType() == '+') {
		int range = Math.abs(Settings.getUpperBound() - Settings.getLowerBound()) + 1;
		int i = r.nextInt(range) + Settings.getLowerBound();
		int j = r.nextInt(range) + Settings.getLowerBound();
		char op = Settings.getType();
		if (op == 'r') {
			int rOp = r.nextInt(4);
			switch (rOp) {
			case 0:
				op = '+';
				break;
			case 1:
				op = '-';
				break;
			case 2:
				op = '*';
				break;
			default:
				op = '÷';
			}
		}

		if ((Settings.isFirstLarger() && !Settings.getNumType().equals(ProblemTypes.FRACTION))
				|| Settings.getNumType().equals(ProblemTypes.TRAPEZOID)
				|| (Settings.getNumType().equals(ProblemTypes.MONEY) && Settings.getType() == '-')) {
			if (!Settings.getNumType().equals(ProblemTypes.MONEY) && i == j)
				if (j < Settings.getUpperBound())
					j++;
				else
					i--;

			int tempI = Math.max(i, j);
			int tempJ = Math.min(i, j);
			i = tempI;
			j = tempJ;
		}
		if (i == 0) {
			if (i < Settings.getUpperBound())
				i++;
			else
				i--;
		}

		if (j == 0) {
			if (j < Settings.getUpperBound())
				j++;
			else
				j--;
		}

		switch (Settings.getNumType()) {
		case MONEY:
			int cI = r.nextInt(100);
			int cJ = r.nextInt(100);
			return new MoneyQuestion(i, cI, j, cJ, op);
		case FRACTION:
			return nextFractionQuestion(r, range, i, j, op);
		case TIME:
			j = r.nextInt(60);
			int t = r.nextInt(Math.min(240, i * 60 + j));
			LocalTime lT1 = LocalTime.of(i, j);
			long minutes = (t);
			return new TimeQuestion(lT1, minutes, op);
		case SIMPLIFY_FRACTION:
			int n = r.nextInt(Settings.getUpperBound() - 1) + 2;
			int d = r.nextInt(Settings.getLowerBound() - 1) + 2;
			int temp = r.nextInt(d - 1) + 1;
			d *= n;
			n *= temp;

			return new SimpleFractionQuestion(n, d, '÷');
		case DATE:
			int year = (r.nextInt(21) - 10) + Integer.parseInt(Year.now().toString());
			Month mon = Month.of(r.nextInt(12) + 1);
			int day = r.nextInt(mon.length(Year.isLeap(year))) + 1;

			LocalDate ld1 = LocalDate.of(year, mon, day);
			int d1 = r.nextInt(ld1.lengthOfYear() - 1) * 3 + 1;

			return new DateQuestion(ld1, d1, op);
		case RECTANGLE:
			return new RectangleQuestion(i, j);
		case CIRCLE:
			return new CircleQuestion(i);
		case TRIANGLE:
			try {
				i = r.nextInt(range - 1) + Settings.getLowerBound() + 1;
				j = r.nextInt(range) + Settings.getLowerBound();
				int l = r.nextInt(range - 1) + Settings.getLowerBound() + 1;
				return new TriangleQuestion(i, j, l);
			} catch (ArithmeticException ae) {// likely better way to do
				reps++;
				if (reps > 200) {
					throw new AttributeNotFoundException("Triangle cannot be made");
				}
				return nextQuestion();
			}
		case PERCENT:
			j = r.nextInt(99) + 1;

			return new PercentQuestion(i, j);
		case TRAPEZOID:
			int l1 = r.nextInt(range) + Settings.getLowerBound();
			return new TrapezoidQuestion(i, j, l1);
		case SEQUENCE:
			int u = r.nextInt(Settings.getUpperBound()) + 1;
			return new SequenceQuestion(u, r.nextInt(10) + 3, Settings.getLowerBound());
		case PRIME:
			return new PrimeQuestion((r.nextInt(2) == 0) ? i : j);
		case HEXAGON:
			return new HexagonQuestion(i);
		default:
			return new NumberQuestion(i, j, op);
		}
	}

	private static Question nextFractionQuestion(Random r, int range, int i, int j, char op) {
		int dI, dJ;

		if (Settings.isFirstLarger()) {
			int tempRange = Settings.getUpperBound() - i + 1;
			dI = r.nextInt(tempRange) + i;
			tempRange = Settings.getUpperBound() - j + 1;
			dJ = r.nextInt(tempRange) + j;
		} else {
			dI = r.nextInt(range) + Settings.getLowerBound();
			dJ = r.nextInt(range) + Settings.getLowerBound();
		}
		if (dI == i || dJ == j)
			return nextFractionQuestion(r, range, i, j, op);
		else
			return new FractionQuestion(i, dI, j, dJ, op);
	}
}
