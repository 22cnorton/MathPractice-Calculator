package Money;

public class Bill extends Money {

	public Bill(String name, double value) {
		super(name, value);
	}

	public String getImageAddress() {// gets the expected address of the picture of the coin
		return "Coins/pictures/" + getName().toLowerCase() + "Dollar.png";
	}

}
