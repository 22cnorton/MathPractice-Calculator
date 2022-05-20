package Main;

import java.awt.Color;
import java.awt.Component;
import java.io.InvalidClassException;
import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.LinkedHashMap;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import BreezySwing.GBPanel;
import Money.Bill;
import Money.Dime;
import Money.Money;
import Money.Nickel;
import Money.Penny;
import Money.Quarter;
import Questions.QuestionGenerator;

public class ChangeQuestionWindow extends QuestionWindow {
	private static final long serialVersionUID = -7593681122335373838L;

	private GBPanel p1 = addPanel(1, 1, 1, 1), p2 = addPanel(2, 1, 1, 1), p3 = addPanel(3, 1, 1, 1);

	private JTextField question = p1.addTextField("", 2, 1, 1, 1)/* , answerField = p1.addTextField("", 2, 1, 1, 1) */,
			scoreField = p1.addTextField("Score: 0", 1, 1, 1, 1);

	private JLabel fiveDollar = p2.addLabel("", 1, 1, 1, 1), dollar = p2.addLabel("", 1, 2, 1, 1),
			quarter = p2.addLabel("", 1, 3, 1, 1), dime = p2.addLabel("", 1, 4, 1, 1),
			nickel = p2.addLabel("", 1, 5, 1, 1), penny = p2.addLabel("", 1, 6, 1, 1);
	private JLabel[] pics = { fiveDollar, dollar, quarter, dime, nickel, penny };

	private JSpinner fiveSpinner = p2.addSpinner(2, 1, 1, 1), oneSpinner = p2.addSpinner(2, 2, 1, 1),
			quarterSpinner = p2.addSpinner(2, 3, 1, 1), dimeSpinner = p2.addSpinner(2, 4, 1, 1),
			nickelSpinner = p2.addSpinner(2, 5, 1, 1), pennySpinner = p2.addSpinner(2, 6, 1, 1);
	private JSpinner[] spin = { fiveSpinner, oneSpinner, quarterSpinner, dimeSpinner, nickelSpinner, pennySpinner };

	public boolean isExactChange() throws InvalidClassException {
		LinkedHashMap<Money, Integer> temp = getCoinsMap();
		try {
			LinkedHashMap<Money, Integer> answer = getCoinsMap();
			Money[] m = { new Bill("Five", 5), new Bill("One", 1), new Quarter(), new Dime(), new Nickel(),
					new Penny() };
			for (int i = 0; i < spin.length; i++) {
				answer.put(m[i], (int) spin[i].getValue());
			}
			HashSet<?> set1 = new HashSet<>(temp.values());
			HashSet<?> set2 = new HashSet<>(answer.values());

			return set1.equals(set2);
		} catch (InvalidClassException e) {
			e.printStackTrace();
			return true;
		}
	}

	@Override
	void update(boolean decReps) {
		try {
			if (!isExactChange()) {
				setScore(getScore() - 1);
				autoCloseMsgBox("The best way to make that change:\n" + getCoinsList(), 250, 250, 1500);
			}
		} catch (InvalidClassException e1) {

			e1.printStackTrace();
		}
		for (JSpinner j : spin)
			j.setValue(0);

		if (getReps() > 1)
			setQ(QuestionGenerator.nextChangeQuestion());
		setCount(0);
		scoreField.setText("Score: " + getScore());
		question.setText(qToString());
		if (Settings.isDevMode()) {
			try {
				System.out.println(getCoinsList());
			} catch (InvalidClassException e) {
				e.printStackTrace();
			}
		}
		if (decReps)
			decReps();
		((JSpinner.DefaultEditor) fiveSpinner.getEditor()).getTextField().grabFocus();
	}

	@Override
	Object parse() {
		int dollarAmount = ((int) fiveSpinner.getValue()) * 5 + ((int) oneSpinner.getValue());
		int centsAmount = (int) quarterSpinner.getValue() * 25 + (int) dimeSpinner.getValue() * 10
				+ (int) nickelSpinner.getValue() * 5 + (int) pennySpinner.getValue();
		double solution = dollarAmount + (centsAmount / 100.0);

		DecimalFormat dec = new DecimalFormat("#0.00");
		return Double.parseDouble(dec.format(solution));
	}

	public ChangeQuestionWindow(JFrame theMainFrm) {
		super(theMainFrm);
		setArg0(theMainFrm);
		setTitle("Questions");
		getContentPane().setBackground(Color.darkGray);
		setSize(1000, 450);
		setLocationRelativeTo(theMainFrm);

		p1.setOpaque(false);
		p2.setOpaque(false);
		p3.setOpaque(false);

		addButtons(p3);

		setQ(QuestionGenerator.nextChangeQuestion());
		if (Settings.isDevMode()) {
			try {
				System.out.println(getCoinsList());
			} catch (InvalidClassException e1) {
				e1.printStackTrace();
			}
		}

		question.setBackground(getContentPane().getBackground());
		question.setEditable(false);
		question.setHorizontalAlignment(SwingConstants.CENTER);
		question.setForeground(Color.white);
		question.setText(qToString());
		question.setFocusable(false);
		question.setBorder(null);

		ChangeListener cL = new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSpinner s = (JSpinner) e.getSource();
				int val = (int) s.getValue();
				if (val < 0)
					s.setValue(0);
			}
		};

		for (JSpinner j : spin) {
			j.addChangeListener(cL);
			Component editor = j.getEditor();
			JSpinner.DefaultEditor spinnerEditor = (JSpinner.DefaultEditor) editor;
			spinnerEditor.getTextField().setHorizontalAlignment(SwingConstants.CENTER);
			spinnerEditor.getTextField().addKeyListener(getKey());
		}

		final String STR = "Coins/pictures/";// the image address that is the same for all the coins

		fiveDollar.setIcon(new ImageIcon(STR + "fiveDollar.png"));
		dollar.setIcon(new ImageIcon(STR + "oneDollar.png"));
		quarter.setIcon(new ImageIcon(STR + "quarter.png"));
		dime.setIcon(new ImageIcon(STR + "dime.png"));
		nickel.setIcon(new ImageIcon(STR + "nickel.png"));
		penny.setIcon(new ImageIcon(STR + "penny.png"));

		for (JLabel j : pics) {
//			j.setIcon(new ImageIcon(STR+j.getName()+".png"));
			j.setFocusable(false);
		}

		scoreField.setBackground(getContentPane().getBackground());
		scoreField.setEditable(false);
		scoreField.setHorizontalAlignment(SwingConstants.RIGHT);
		scoreField.setForeground(Color.white);
		scoreField.setFocusable(false);
		scoreField.setBorder(null);
	}
}