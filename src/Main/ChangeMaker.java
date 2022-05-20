package Main;

import java.util.LinkedHashMap;

import Money.Bill;
import Money.Dime;
import Money.Money;
import Money.Nickel;
import Money.Penny;
import Money.Quarter;

public class ChangeMaker {
//	private int diff;
	private static final Money[] VALUES = { new Bill("Hundred", 100), new Bill("Fifty", 50), new Bill("Twenty", 20),
			new Bill("Ten", 10), new Bill("Five", 5), new Bill("One", 1), new Quarter(), new Dime(), new Nickel(),
			new Penny() };

//	public ChangeCalc(double cost, double pay) {
//		super();
//		this.diff = ((int) (pay * 100)) - ((int) (cost * 100));
////		System.out.println(diff);
//	}

//	public Money[] getValues() {
//		return values;
//	}
//
//	public void setValues(Money[] values) {
//		ChangeCalc.values = values;
//	}

	public static LinkedHashMap<Money, Integer> getCoins(double val, Money[] vals) {
		int remainder = (int) (val);
//		System.out.println(remainder);
//		int remainderD = (int) val;
//
//		String tmpStr = String.valueOf(val).split("\\.")[1];
//		int remainderC = Integer.parseInt(tmpStr);
//
//		int remainder = remainderD * 100 + remainderC;

		LinkedHashMap<Money, Integer> temp = new LinkedHashMap<>();
//		System.out.println(remainder);
//Money[] values= {new Quarter(),new Dime(),new Nickel(),new Penny()};
		for (Money m : vals) {
//			System.out.println((int) (m.getValue() * 100));
			int count = remainder / ((int) (m.getValue() * 100));
			temp.put(m, count);
//			System.out.println("1: "+remainder);
			remainder %= (int) (m.getValue() * 100);
//			System.out.println("2: "+remainder+"\n");
		}
		return temp;

	}

	public static LinkedHashMap<Money, Integer> getCoins(double val) {
//		System.out.println(val);
		return getCoins(val, VALUES);
	}

	/*
	 * public LinkedHashMap<Money, Integer> getCoins() { return getCoins(diff / 100,
	 * values);
	 * 
	 * int remainder = diff; LinkedHashMap<Money, Integer> temp = new
	 * LinkedHashMap<Money, Integer>(); // System.out.println(remainder);
	 * 
	 * for (Money m : values) { // System.out.println((int) (m.getValue() * 100));
	 * int count = remainder / ((int) (m.getValue() * 100)); temp.put(m, count); //
	 * System.out.println("1: "+remainder); remainder %= (int) (m.getValue() * 100);
	 * // System.out.println("2: "+remainder+"\n"); } return temp;
	 * 
	 * }
	 */
}