package Main;

import java.util.LinkedHashMap;

import Money.Bill;
import Money.Dime;
import Money.Money;
import Money.Nickel;
import Money.Penny;
import Money.Quarter;

public class ChangeMaker {
	private static final Money[] VALUES = { new Bill("Hundred", 100), new Bill("Fifty", 50), new Bill("Twenty", 20),
			new Bill("Ten", 10), new Bill("Five", 5), new Bill("One", 1), new Quarter(), new Dime(), new Nickel(),
			new Penny() };

	public static LinkedHashMap<Money, Integer> getCoins(double val, Money[] vals) {
		int remainder = (int) (val);

		LinkedHashMap<Money, Integer> temp = new LinkedHashMap<>();
		for (Money m : vals) {
			int count = remainder / ((int) (m.getValue() * 100));
			temp.put(m, count);
			remainder %= (int) (m.getValue() * 100);
		}
		return temp;

	}

	public static LinkedHashMap<Money, Integer> getCoins(double val) {
		return getCoins(val, VALUES);
	}
}