package Questions;
public abstract class Question extends Object {

	private char operator;

	public Question() {
		super();
		operator = '+';
	}

	public Question(char operator) {
		super();
		this.operator = operator;

	}

	public Object getSolution() {
		return calcSolution();
	}

	/**
	 * calculates the solution of the problem
	 * 
	 * @return the solution
	 */
	abstract Object calcSolution();

	public char getOperator() {
		return operator;
	}

	public void setOperator(char operator) {
		this.operator = operator;
	}

	/**
	 * Checks if two questions are equal
	 * 
	 * @param o the answer to be checked against
	 * @return {@code true} if the answer equals the parameter {@code false}
	 *         otherwise
	 */
	public boolean answerEquals(Object o) {
		return o.equals(getSolution());
	}
}