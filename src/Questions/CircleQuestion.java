package Questions;

import java.text.DecimalFormat;

import Main.Settings;

public class CircleQuestion extends ShapeQuestion {
	private int radius;

	public CircleQuestion(int r) {
		super();
		radius = r;
	}

	@Override
	protected double getArea() {
		double d = Math.PI * Math.pow(radius, 2);

		String text = Double.toString(Math.abs(d));
		int integerPlaces = text.indexOf('.');
		int decimalPlaces = text.length() - integerPlaces - 1;
		if (decimalPlaces > 2) {
			DecimalFormat dec = new DecimalFormat("#0.00");
			return Double.parseDouble(dec.format(d));
		}

		return d;
	}

	@Override
	protected double getPerimeter() {
		double d = 2 * Math.PI * radius;

		String text = Double.toString(Math.abs(d));
		int integerPlaces = text.indexOf('.');
		int decimalPlaces = text.length() - integerPlaces - 1;
		if (decimalPlaces > 2) {
			DecimalFormat dec = new DecimalFormat("#0.00");
			return Double.parseDouble(dec.format(d));
		}
		return d;
	}

	public String toString() {
		if (Settings.isFirstLarger())
			return "Radius: " + radius + " What is the area?";
		return "Radius: " + radius + " What is the circumference?";// Circumference
	}
}