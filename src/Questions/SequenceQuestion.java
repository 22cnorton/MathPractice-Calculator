package Questions;
import java.util.*;

public class SequenceQuestion extends Question {
	private ArrayList<Integer> seq = new ArrayList<Integer>();
	private int spot;

	public SequenceQuestion(int start, int reps, int space) {
		super();
		for (int i = 0; i < reps; i++) {
			seq.add((i * space) + start);
		}
		spot = (int) (Math.random() * seq.size());
//		System.out.println(seq);
	}

	public ArrayList<Integer> getSeq() {
		return seq;
	}

	public void setSeq(ArrayList<Integer> seq) {
		this.seq = seq;
	}

	public boolean equals(ArrayList<Integer> arr) {
		return seq.equals(arr);
	}

	public String getQuestion() {
		String str = "";
		for (int i = 0; i < spot; i++) {
			str += seq.get(i) + ", ";
		}
		str += "___, ";
		for (int i = spot + 1; i < seq.size(); i++) {
			str += seq.get(i) + ", ";
		}
		return str.substring(0, str.length() - 2);
	}

	public String toString() {
		return getQuestion();
	}

	public int getAnswer() {
		return seq.get(spot);
	}

	@Override
	Object calcSolution() {
		return (double) seq.get(spot);
	}
}