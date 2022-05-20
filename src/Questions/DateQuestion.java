package Questions;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateQuestion extends Question {
	private LocalDate ld;
	private int days;

	public DateQuestion() {
		super();
		ld = LocalDate.now();
		days = 5;
	}

	public DateQuestion(LocalDate ld, int days, char op) {
		super(op);
		this.ld = ld;
		this.days = days;
	}

	public LocalDate getLd() {
		return ld;
	}

	public void setLd(LocalDate ld) {
		this.ld = ld;
	}

	@Override
	protected Object calcSolution() {
		LocalDate tmp;
		switch (getOperator()) {
		case '-':
			tmp = ld.minusDays(days);
		default:
			tmp = ld.plusDays(days);
		}
		return tmp.format(DateTimeFormatter.ofPattern("MMMM dd, yyyy"));
	}

	public String toString() {
		DateTimeFormatter d = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
		switch (getOperator()) {
		case '-':
			return "It is " + ld.format(d) + " what was the date " + days + " days ago?";
		default:
			return "It is " + ld.format(d) + " what is the date " + days + " days from now?";
		}
	}
}