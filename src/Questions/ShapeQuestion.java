package Questions;

import Main.Settings;

public abstract class ShapeQuestion extends Question {

	public ShapeQuestion() {
		super();
	}

	protected Object calcSolution() {
		if (Settings.isFirstLarger()) {
			return getArea();
		} else {
			return getPerimeter();
		}
	}

	protected abstract double getArea();

	protected abstract double getPerimeter();
}
