package Questions;

import java.text.DecimalFormat;

import Main.Settings;

public class TriangleQuestion extends ShapeQuestion {
	private int s1, s2, s3;

	public TriangleQuestion(int s1, int s2, int s3) throws ArithmeticException {
		super();
		if (!(s1 + s2 > s3 && s1 + s3 > s2 && s2 + s3 > s1))
			throw new ArithmeticException("Not a triangle");

		this.s1 = s1;
		this.s2 = s2;
		this.s3 = s3;

//	    a + b > c
//	    a + c > b
//	    b + c > a

	}

	private double getHeight() {
		double a = getArea();
		a = a / s2 * 2;
		String text = Double.toString(Math.abs(a));
		int integerPlaces = text.indexOf('.');
		int decimalPlaces = text.length() - integerPlaces - 1;
		if (decimalPlaces > 2) {
			DecimalFormat dec = new DecimalFormat("#0.00");
			return Double.parseDouble(dec.format(a));
		}
		return a;
	}

	@Override
	protected double getArea() {
		double s = (s1 + s2 + s3) / 2.0;

		double d = Math.sqrt(s * (s - s1) * (s - s2) * (s - s3));

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
		return s1 + s2 + s3;
	}

	public String toString() {
		if (Settings.isFirstLarger()) {
			return "The base is: " + s2 + " and the height is: " + getHeight() + " What is the area?";
		}
		return "The sides are: " + s1 + ", " + s2 + ", " + s3 + " What is the perimeter?";
	}
}
