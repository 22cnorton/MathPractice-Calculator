package Money;

public abstract class Money implements Comparable<Money> {
	private String name;
	private int value;

	public Money(String name, double value) {// constructor method
		this.name = name;
		this.value = (int) (value * 100);

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getValue() {
		return value * 0.01;
	}

	public void setValue(double value) {
		this.value = (int) (value * 100);
	}

	/* protected int getRawValue() { return value; } */

	/**
	 * Checks if the two coins have the same name and value
	 *
	 * @param c the coin to be checked against
	 * @return {@code true} if both coins are the same; {@code false} otherwise
	 */
	public boolean equals(Money c) {
		return (c.getName().equalsIgnoreCase(name) && c.getValue() == value);
	}

	public abstract String getImageAddress();

	/**
	 * Compares the value of two coins
	 *
	 * @param m the money to be compared with
	 * @return the difference between this coin and the parameter
	 */
	@Override
	public int compareTo(Money m) {
//		int i=Integer.compare(value, m.value);
//		return Integer.compare(value, m.getValue());
		return value - m.value;
	}

	@Override
	public String toString() {
		return name + " worth $" + value / 100;
	}
}
