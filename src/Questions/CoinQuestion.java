package Questions;

import java.util.ArrayList;

import Money.Coin;

public class CoinQuestion extends Question {
	private ArrayList<Coin> coins = new ArrayList<Coin>();

	public CoinQuestion(ArrayList<Coin> c) {
		coins = c;
	}

	@Override
	protected Object calcSolution() {
		int total = 0;
		for (Coin c : coins) // gets the correct answer
			total += c.getValue() * 100;
		return total;
	}

	public ArrayList<Coin> getCoins() {
		return coins;
	}

	public Object getSolution() {
		return calcSolution();
	}

	public void setCoins(ArrayList<Coin> coins) {
		this.coins = coins;
	}

	public int size() {
		return coins.size();
	}

	public Coin get(int index) {
		return coins.get(index);
	}

	public String toString() {
		String str = "";
		for (Coin c : coins) {
			str += c.toString() + ", ";
		}
		return str.substring(0, str.length() - 2);
	}

}
