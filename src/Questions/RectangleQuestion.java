package Questions;

import Main.Settings;

public class RectangleQuestion extends ShapeQuestion {
	private int length, width;

	public RectangleQuestion() {
		super();
		length = 5;
		width = 5;
	}

	public RectangleQuestion(int l, int w) {
		super();
		length = l;
		width = w;
	}

	@Override
	protected double getArea() {
		return length * width;
	}

	@Override
	protected double getPerimeter() {
		return length * 2 + width * 2;
	}

	@Override
	public String toString() {
		if (Settings.isFirstLarger())
			return "Length: " + length + " Width: " + width + " What is the area?";
		return "Length: " + length + " Width: " + width + " What is the perimeter?";
	}
}