package Money;

public abstract class Coin extends Money {// create a coin with a value and a name

	public Coin(String name, double value) {// constructor method
		super(name, value);
	}

	public String getImageAddress() {// gets the expected address of the picture of the coin
		return "Coins/pictures/" + getName().toLowerCase() + ".png";
	}

	public String toString() {
		return getName() + " worth $" + getValue();
	}
}