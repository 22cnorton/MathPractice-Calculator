package Main;
import java.awt.Color;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import BreezySwing.GBPanel;
import Questions.CoinQuestion;
import Questions.QuestionGenerator;

public class CoinWindow extends QuestionWindow {
	private static final long serialVersionUID = 4113164551804514669L;

	private GBPanel p1 = addPanel(2, 1, 1, 1), p2 = addPanel(3, 1, 1, 1), p3 = addPanel(4, 1, 1, 1);
	private ArrayList<JLabel> images = new ArrayList<JLabel>();

	private JTextField question = p2.addTextField("How Many cents?", 1, 1, 1, 1),
			answer = p2.addTextField("", 1, 2, 1, 1), centsLabel = p2.addTextField("¢", 1, 3, 1, 1),
			scoreField = addTextField("Score: 0", 1, 1, 1, 1);

//	private JButton enter = p3.addButton("Enter", 1, 1, 1, 1), exit = p3.addButton("Exit", 2, 1, 1, 1);

//	private Settings s;
//	private int total;
//	private int reps, count, score = 0;
//	private boolean isComplete = false;

	/*
	 * @Override void exitPressed() {// exit button // opens a dialogue to confirm
	 * that the user wants to exit ConfirmationDialog c = new
	 * ConfirmationDialog(getArg0(), this, "Are You Sure You Want to Quit?");
	 * c.setVisible(true); if (c.getDlgCloseIndicator().equals("Yes"))// if the suer
	 * confirms to exit dispose(); }
	 */

	/*
	 * @Override void skipPressed() { try { if (getScore() <= 0) throw new
	 * NumberFormatException("Score less than 0"); setScore(getScore() - 1);
	 * update(false); } catch (NumberFormatException e) { if
	 * (e.getMessage().equals("Score less than 0"))
	 * autoCloseMsgBox("Skip costs 1 point"); } }
	 */
	/*
	 * @Override public void buttonClicked(JButton buttonObj) {// button clicked
	 * method if (buttonObj == getEnterButton()) {// when the enter button is
	 * pressed checkAnswer(); } else if (buttonObj == getExitButton()) { } else if
	 * (buttonObj == getSkipButton()) { } }
	 */
	/*
	 * private ArrayList<Coin> getCoins() { ArrayList<Coin> c = new ArrayList<>();
	 * Random r = new Random(); int range = Settings.getUpperBound() -
	 * Settings.getLowerBound() + 1; // int j = r.nextInt(q);
	 * 
	 * int q = r.nextInt(range / 4 + 1);// split in four, one segment for each coin
	 * type for (int i = 0; i < q; i++) {// generates the specified number of coins
	 * c.add(new Quarter()); }
	 * 
	 * q = r.nextInt(range / 4 + 1); for (int i = 0; i < q; i++) { c.add(new
	 * Dime()); }
	 * 
	 * q = r.nextInt(range / 4 + 1); for (int i = 0; i < q; i++) { c.add(new
	 * Nickel()); }
	 * 
	 * q = r.nextInt(range / 4 + 1 + range % 4);// pennies get the remainder of any
	 * range that cannot divide into 4 // evenly for (int i = 0; i < q; i++) {
	 * c.add(new Penny()); } if (c.size() < Settings.getLowerBound()) {// if there
	 * are too few coins it is filled in by each coin until the // min bound is
	 * reached for (int i = c.size(); i <= Settings.getLowerBound(); i++) { if (i %
	 * 4 == 0) { c.add(new Quarter()); } else if (i % 3 == 0) { c.add(new Dime()); }
	 * else if (i % 2 == 0) { c.add(new Nickel()); } else c.add(new Penny()); } c =
	 * coinSort(c);// puts the coins in order } return c; }
	 */

	/*
	 * private ArrayList<Coin> coinSort(ArrayList<Coin> arr) { int n = arr.size();
	 * 
	 * for (int i = 0; i < n - 1; i++) { // Find the minimum element in unsorted
	 * array int min_idx = i; for (int j = i + 1; j < n; j++) { if
	 * (arr.get(j).compareTo(arr.get(min_idx)) > 0) min_idx = j; } // Swap the found
	 * minimum element with the first element Coin temp = arr.get(min_idx);
	 * arr.set(min_idx, arr.get(i)); arr.set(i, temp); } return arr;
	 * 
	 * }
	 * 
	 */

	/**
	 * sets the images to match the currently selected coins
	 */

	/*
	 * @Override public void clearSolutionField() { answer.setText("0"); }
	 */

	private void drawImage() {
		int sz = ((CoinQuestion) getQ()).size();
		for (int i = 0; i < sz; i++) {// adds the image for each stored coin
			images.get(i).setIcon(new ImageIcon(
					Toolkit.getDefaultToolkit().createImage(((CoinQuestion) getQ()).get(i).getImageAddress())));
			images.get(i).repaint();
//			images.get(i).
		}
		for (int i = sz; i < Settings.getUpperBound(); i++) {// sets remaining images to be nukk
			images.get(i).setIcon(null);
			images.get(i).repaint();
		}
		validate();
	}

	/**
	 * constructor
	 *
	 * @param arg0 JFrame
	 */
	public CoinWindow(JFrame arg0) {
		super(arg0);
		setArg0(arg0);
		setTitle("Coins");
		getContentPane().setBackground(Color.darkGray);
		setSize(1000, Settings.getUpperBound() * 15 + 300);
		setLocationRelativeTo(arg0);
//		this.s = s;
		addButtons(p3);
		/*
		 * setEnterButton(p3); setExitButton(p3); setSkipButton(p3);
		 */
//		enter.setIcon(new ImageIcon(new Penny().getImageAddress()));

		// sets the labels to not editable
		question.setBackground(getContentPane().getBackground());
		question.setEditable(false);
		question.setHorizontalAlignment(SwingConstants.RIGHT);
		question.setForeground(Color.white);
		question.setBorder(null);
		question.setFocusable(false);

		centsLabel.setBackground(getContentPane().getBackground());
		centsLabel.setEditable(false);
		centsLabel.setHorizontalAlignment(SwingConstants.LEFT);
		centsLabel.setForeground(Color.white);
		centsLabel.setBorder(null);
		centsLabel.setFocusable(false);

		scoreField.setBackground(getContentPane().getBackground());
		scoreField.setEditable(false);
		scoreField.setHorizontalAlignment(SwingConstants.RIGHT);
		scoreField.setForeground(Color.white);
		scoreField.setBorder(null);
		scoreField.setFocusable(false);

		p1.setOpaque(false);
		p2.setOpaque(false);
		p3.setOpaque(false);

		/*
		 * if the enter key is pressed while selecting the answer field the enter button
		 * is pressed
		 */
		answer.addKeyListener(getKey());

//		total = 0;
		setReps(Settings.getqNum());

		setQ(QuestionGenerator.getCoins());
		if (Settings.isDevMode())
			System.out.println(qGetSolution());

		int j = 1;
		for (int i = 0; i < Settings.getUpperBound(); i++) {// adds a label for for the max number of possible coins
			if (i % 8 == 0)
				j++;
			images.add(p1.addLabel("", j, (i % 8) + 1, 1, 1));
			images.get(i).setFocusable(false);
		}
		drawImage();
	}

	Object parse() {
		return Integer.parseInt(answer.getText());
	}

	void update(boolean decReps) {
		answer.setText("");
		if (getReps() > 0) {// if more questions should be asked
			setQ(QuestionGenerator.getCoins());
			drawImage();
		}
		setCount(0);
		scoreField.setText("Score: " + getScore());
		answer.grabFocus();
		if (Settings.isDevMode())
			System.out.println(qGetSolution());
		if (decReps)
			decReps();
	}

	/*
	 * @Override public void checkAnswer() { try { int centsAnswer =
	 * Integer.parseInt(answer.getText()); if (centsAnswer == calcTotal()) {// if
	 * the user entered the correct answer messageBox("Correct"); addScore(3 -
	 * getCount()); scoreField.setText("Score: " + getScore()); answer.setText("");
	 * decReps(); if (getReps() > 0) {// if more questions should be asked coins =
	 * QuestionGenerator.getCoins(); drawImage(); } setCount(0); } else if
	 * (getCount() < 2) {// if the user is wrong and still has more tries
	 * incCount(); messageBox("Try Again\nYou have " + (3 - getCount()) +
	 * " tries remaining"); } else {// if the user was wrong and is out of tries
	 * messageBox("Good try\nThe correct answer is " + total + "¢"); setCount(0);
	 * decReps(); if (getReps() > 0) {// if more questions should be asked coins =
	 * QuestionGenerator.getCoins(); drawImage(); } total = 0; }
	 * 
	 * if (getReps() < 1) {// when the round is over setComplete(true);
	 * messageBox("You finished!\nYour final score was " + getScore() + "/" +
	 * (Settings.getqNum() * 3)); dispose(); } } catch (NumberFormatException e) {//
	 * when the user enters disallowed characters messageBox("Must enter numbers");
	 * } }
	 */

}