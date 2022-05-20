package Questions;

import java.text.DecimalFormat;

import Main.Settings;

public class HexagonQuestion extends ShapeQuestion {
	private int side;

	public HexagonQuestion(int side) {
		this.side = side;
	}

	@Override
	protected double getArea() {
		double area = Math.pow(side, 2) * ((3 * Math.sqrt(3)) / 2);

		String text = Double.toString(Math.abs(area));
		int integerPlaces = text.indexOf('.');
		int decimalPlaces = text.length() - integerPlaces - 1;
		if (decimalPlaces > 2) {
			DecimalFormat dec = new DecimalFormat("#0.00");
			return Double.parseDouble(dec.format(area));
		}
		return area;
	}

	@Override
	protected double getPerimeter() {
		return side * 6;
	}

	@Override
	public String toString() {
		return "The sides are: " + side + " What is the " + (Settings.isFirstLarger() ? "area" : "perimter") + "?";
	}

}
