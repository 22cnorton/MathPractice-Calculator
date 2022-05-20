package Main;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.InvalidClassException;
import java.text.DecimalFormat;
import java.util.LinkedHashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.Timer;

import BreezySwing.GBDialog;
import BreezySwing.GBPanel;
import Money.Bill;
import Money.Dime;
import Money.Money;
import Money.Nickel;
import Money.Penny;
import Money.Quarter;
import Questions.ChangeQuestion;
import Questions.Question;

public abstract class QuestionWindow extends GBDialog {// added in week
	private static final long serialVersionUID = -1506109807394639483L;
	private int count = 0, score = 0, reps = Settings.getqNum();
	private boolean isComplete = false;

	private Timer t = new Timer(Settings.getTimedSpeed() * 1000, new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			buttonClicked(enter);
		}
	});

	private JButton enter, exit, skip;
	private JFrame arg0;
	private Question q;
	private KeyListener key;

	public QuestionWindow(JFrame theMainFrm) {
		super(theMainFrm);
		arg0 = theMainFrm;
		key = new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					buttonClicked(getEnterButton());
				} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
					buttonClicked(getExitButton());
			}
		};
		t.setRepeats(false);
		if (Settings.isTimed())
			t.start();
	}

	public String getCoinsList() throws InvalidClassException {
		if (q instanceof ChangeQuestion) {
			String str = "";
			LinkedHashMap<Money, Integer> hsh = getCoinsMap();
			for (int i = 0; i < hsh.keySet().size(); i++) {
				str += Integer.toString(hsh.get(hsh.keySet().toArray()[i])) + " "
						+ ((Money) hsh.keySet().toArray()[i]).getName() + "\n";
			}
			return str;
		}
		throw new InvalidClassException("Only for change questions");
	}

	public LinkedHashMap<Money, Integer> getCoinsMap() throws InvalidClassException {
		if (q instanceof ChangeQuestion) {
			return ChangeMaker.getCoins(
					Double.parseDouble(new DecimalFormat("#.00").format((double) q.getSolution() * 100)),
					new Money[] { new Bill("Five", 5), new Bill("One", 1), new Quarter(), new Dime(), new Nickel(),
							new Penny() });
		}
		throw new InvalidClassException("Only for change questions");
	}

	/**
	 * all updates that need to be performed after the answer is checked
	 *
	 * @param decReps if the number of repetitions need to be decreased
	 */
	abstract void update(boolean decReps);

	abstract Object parse();

	private Object parseHelper() throws NumberFormatException {
		try {
			return parse();
		} catch (NumberFormatException e) {
			if (Settings.isTimed())
				return "non answer";
			else
				throw e;
		}
	}

	@Override
	public void buttonClicked(JButton buttonObj) {
		if (buttonObj == enter) {
			checkAnswer();
		} else if (buttonObj == exit) {
			exitPressed();
		} else if (buttonObj == skip) {
			skipPressed();
		}
	}

	public void exitPressed() {
		t.stop();
		ConfirmationDialog c = new ConfirmationDialog(getArg0(), this, "Are You Sure You Want to Quit?");
		c.setVisible(true);
		if (c.getDlgCloseIndicator().equals("Yes"))
			dispose();
		else if (Settings.isTimed())
			t.restart();
	}

	public void addButtons(GBPanel p) {
		setEnterButton(p);
		setExitButton(p);
		setSkipButton(p);
	}

	void skipPressed() {
		try {
			t.stop();
			if (getScore() <= 0)
				throw new NumberFormatException("Score less than 0");

			setScore(getScore() - 1);
			update(false);
		} catch (NumberFormatException e) {
			if (e.getMessage().equals("Score less than 0"))
				autoCloseMsgBox("Skip costs 1 point");
		}
		if (Settings.isTimed())
			t.restart();
	}

	public void checkAnswer() {
		try {
			Color col;
			String txt;
			int time = 750;
			if (q.answerEquals(parseHelper())) {
				// score += (3 - count);
				addScore((3 - getCount()) * Settings.getNumType().scale);
				update(true);
				col = Color.green;
				txt = "Correct!";

			} else if (getCount() < 2) {
				incCount();
				col = Color.orange;
				final int TRIES = 3 - getCount();
				txt = "Try again\nYou have " + TRIES + " tr" + (TRIES == 1 ? "y" : "ies") + " remaining";
			} else {
				txt = "Good try\nThe solution is " + q.getSolution();
				update(true);
				col = Color.red;
				time = 1550;
			}
			autoCloseMsgBox(txt, time, col);
		} catch (NumberFormatException e) {
			if (e.getMessage().equals("Wrong Fraction Characters"))
				autoCloseMsgBox("Fractions can only use\nintegers and /");
			else if (e.getMessage().equals("Wrong Time Characters"))
				autoCloseMsgBox("Time answers only uses integers and ':'");
			else
				autoCloseMsgBox("Must input numbers");
		}
		if (getReps() < 1) {
			t.stop();
			setComplete(true);
			autoCloseMsgBox("You finished!\nYour final score was " + getScore() + "/"
					+ (Settings.getqNum() * 3) * Settings.getNumType().scale, 1500);
			dispose();
		}
		if (Settings.isTimed())
			t.restart();
	}

	public JFrame getArg0() {
		return arg0;
	}

	public void setArg0(JFrame arg0) {
		this.arg0 = arg0;
	}

	public Object qGetSolution() {
		return q.getSolution();
	}

	public String qToString() {
		return q.toString();
	}

	public Question getQ() {
		return q;
	}

	public void setQ(Question q) {
		this.q = q;
	}

	public KeyListener getKey() {
		return key;
	}

	public void autoCloseMsgBox(String msg, int time) {
		autoCloseMsgBox(msg, time, Color.white);
	}

	public void autoCloseMsgBox(String msg) {
		autoCloseMsgBox(msg, 500, Color.white);
	}

	public void autoCloseMsgBox(String str, int time, Color col) {
		new CorrectMessage(arg0, this, time, col, str);
	}

	public void autoCloseMsgBox(String msg, int width, int height, int time) {
		new CorrectMessage(arg0, this, width, height, time, Color.white, msg);
	}

	public JButton getEnterButton() {
		return enter;
	}

	public void setEnterButton(GBPanel p) {
		enter = p.addButton("Enter", 1, 1, 1, 1);
		enter.addKeyListener(key);
	}

	public JButton getExitButton() {
		return exit;
	}

	public void setExitButton(GBPanel p) {
		exit = p.addButton("Exit", 2, 1, 2, 1);
		exit.addKeyListener(key);

	}

	public JButton getSkipButton() {
		return skip;
	}

	public void setSkipButton(GBPanel p) {
		skip = p.addButton("Skip", 1, 2, 1, 1);
		skip.setToolTipText("Skip the current question for the cost of 1 point");
		skip.addKeyListener(key);

	}

	public void addScore(int n) {
		score += n;
	}

	public void decReps() {
		reps--;
	}

	public void incCount() {
		count++;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getReps() {
		return reps;
	}

	public void setReps(int reps) {
		this.reps = reps;
	}

	public boolean isComplete() {
		return isComplete;
	}

	public void setComplete(boolean isComplete) {
		this.isComplete = isComplete;
	}
}
