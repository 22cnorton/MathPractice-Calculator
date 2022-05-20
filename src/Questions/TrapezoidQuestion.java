package Questions;

import java.text.DecimalFormat;

import Main.Settings;

public class TrapezoidQuestion extends ShapeQuestion {
	private int b1, b2, h;
	private double s1;

	public TrapezoidQuestion(int b1, int b2, int h) {
		super();
		this.b1 = b1;
		this.b2 = b2;
		this.h = h;
		this.s1 = getSide();
	}

	private double getSide() {
		int triBase = Math.abs(b1 - b2);
		double d = Math.sqrt(Math.pow(triBase, 2) + Math.pow(h, 2));
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
	protected double getArea() {
		double d = 0.5 * h * (b1 + b2);

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
		double d = b1 + b2 + h + s1;
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
			return "The height is: " + h + " and the bases are: " + b1 + ", " + b2 + " What is the area?";
		return "The sides are " + h + ", " + s1 + ", " + b1 + ", " + b2 + " What is the perimeter?";
	}
}
