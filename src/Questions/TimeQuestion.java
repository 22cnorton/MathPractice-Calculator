package Questions;

import java.time.*;

public class TimeQuestion extends Question {
	private LocalTime t1;
	private long minutes;

	public TimeQuestion() {
		super();
		t1 = LocalTime.NOON;
		minutes = 5;
	}

	public TimeQuestion(LocalTime t1, long min, char operator) {
		super(operator);
		this.t1 = t1;
		minutes = min;
	}

	public Object calcSolution() {
		switch (getOperator()) {
		case ('-'):
			return t1.minusMinutes(minutes);
		}
		return t1.plusMinutes(minutes);
	}

	public String toString() {
		switch (getOperator()) {
		case '-':
			return "It is " + t1.toString() + " what time was it " + minutes + " minutes ago?";
		}
		return "It is " + t1.toString() + " what time is it " + minutes + " minutes from now?";
	}
}
