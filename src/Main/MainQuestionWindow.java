package Main;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.Year;
import java.time.format.DateTimeFormatter;

import javax.management.AttributeNotFoundException;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import BreezySwing.GBPanel;
import Questions.DateQuestion;
import Questions.FractionQuestion;
import Questions.QuestionGenerator;

public class MainQuestionWindow extends QuestionWindow {
	private static final long serialVersionUID = 5417354320936377823L;
	private GBPanel p1 = addPanel(1, 1, 1, 1), p2 = addPanel(2, 1, 1, 1), p3 = p1.addPanel(3, 1, 1, 1),
			p4 = p1.addPanel(3, 1, 1, 1);

	private JTextField question = p1.addTextField("Question Here", 2, 1, 1, 1), input = p1.addTextField("", 3, 1, 1, 1),
			scoreField = p1.addTextField("Score: 0", 1, 1, 1, 1);

//	private JSpinner spinny=p3.addSpinner(3, 1, 1, 1);

	@SuppressWarnings("rawtypes")
	private JComboBox dayField = p3.addComboBox(1, 2, 1, 1), monthField = p3.addComboBox(1, 1, 1, 1),
			yearField = p3.addComboBox(1, 3, 1, 1);

	private JRadioButton yes = p4.addRadioButton("YES", GridBagConstraints.CENTER, 1, 1, 1, 1),
			no = p4.addRadioButton("NO", GridBagConstraints.CENTER, 1, 2, 1, 1);
	private JRadioButton[] bttns = { yes, no };
	private ButtonGroup bG = new ButtonGroup();
	/*
	 * private JButton enter = p2.addButton("Submit", 1, 1, 1, 1), exit =
	 * p2.addButton("Exit", 2, 1, 2, 1), skip = p2.addButton("Skip", 1, 2, 1, 1);
	 */

//	private Question q;
	private int tempLow;
//	private int txtBoxMod;

//	private int count = 0, score = 0, reps;
//	private boolean isComplete = false;
//	@Override
	/*
	 * public void clearSolutionField() { if (getQ() instanceof TimeQuestion q) {
	 * int day = ((LocalDate) q.getSolution()).getDayOfMonth();
	 *
	 * dayField.setSelectedIndex(day); monthField.setSelectedIndex(0);
	 * yearField.setSelectedIndex(0);
	 *
	 * } else { input.setText("0.00001"); } }
	 */

	@Override
	Object parse() {
		String s = input.getText();
		switch (Settings.getNumType()) {
		case TIME:
			return parseTime(s);
		case DATE:
			return parseDate();
		case SIMPLIFY_FRACTION:
			return parseFraction(s);
		case PRIME:
			return parsePrime();
		case MONEY:
			return parseMoney(s);
		default:
			return (Settings.isFractionAnswer() && (getQ().getOperator() == '÷' || getQ() instanceof FractionQuestion))
					? parseFraction(s)
					: Double.parseDouble(s);
		}
	}

	private double parseMoney(String s) {
		return Double.parseDouble(s.replaceAll("\\$", ""));
	}

	private boolean parsePrime() {
		return yes.isSelected();
	}

	@Override
	public void checkAnswer() {
		super.checkAnswer();
		input.grabFocus();
	}

	/*
	 * @Override void exitPressed() { ConfirmationDialog c = new
	 * ConfirmationDialog(getArg0(), this, "Are You Sure You Want to Quit?");
	 * c.setVisible(true); if (c.getDlgCloseIndicator().equals("Yes")) dispose(); }
	 */

	/*
	 * void skipPressed() { try { if (getScore() <= 0) throw new
	 * NumberFormatException("Score less than 0"); setScore(getScore() - 1);
	 * update(false); } catch (NumberFormatException e) { if
	 * (e.getMessage().equals("Score less than 0"))
	 * autoCloseMsgBox("Skip costs 1 point"); } }
	 */
	/*
	 * @Override public void buttonClicked(JButton buttonObj) { if (buttonObj ==
	 * getEnterButton()) {
	 *
	 * } else if (buttonObj == getExitButton()) {
	 *
	 * } else if (buttonObj == getSkipButton()) {
	 *
	 * } }
	 */

//	public boolean isComplete() {
//		return isComplete;
//	}
//
//	public int getScore() {
//		return score;
//	}
	private void nextQuestion() {
		try {
			setQ(QuestionGenerator.nextQuestion());
		} catch (AttributeNotFoundException anfe) {
			autoCloseMsgBox("A triangle could not be created\nAdjust the bounds before you try again");
			dispose();
		}

	}

	@Override
	public void update(boolean decReps) {
		input.setText("");
		if (getReps() > 1)
			nextQuestion();
		setCount(0);
		scoreField.setText("Score: " + getScore());
		question.setText(qToString());
		if (getQ() instanceof DateQuestion)
			setDateFields();
		else
			input.grabFocus();
		if (Settings.isDevMode())
			System.out.println(qGetSolution());
		if (decReps)
			decReps();
	}

	private LocalTime parseTime(String str) {
		if (str.trim().matches(".*[^0-9:].*"))
			throw new NumberFormatException("Wrong Time Characters");

		str = str.trim().replaceAll(":+", ":");
		int colon = str.indexOf(':');
		if (colon == -1)
			throw new NumberFormatException("Wrong Time Characters");

		int hour = Integer.parseInt(str.substring(0, colon)), minute = Integer.parseInt(str.substring(colon + 1));

		return LocalTime.of(hour, minute);
	}

	private String parseDate() {
		return LocalDate.of(yearField.getSelectedIndex() + tempLow, Month.of(monthField.getSelectedIndex() + 1),
				dayField.getSelectedIndex() + 1).format(DateTimeFormatter.ofPattern("MMMM dd, yyyy"));
	}

	private Fraction parseFraction(String str) {
		if (str.trim().matches(".*[^0-9/\\-].*"))
			throw new NumberFormatException("Wrong Fraction Characters");

		str = str.trim().replaceAll("/+", "/");
		int slash = str.indexOf('/');
		int num;
		int den;
		if (slash == -1) {
			num = Integer.parseInt(str);
			den = 1;
		} else {
			num = Integer.parseInt(str.substring(0, slash));
			den = Integer.parseInt(str.substring(slash + 1));
		}
		return new Fraction(num, den);
	}

	@SuppressWarnings("unchecked")
	private void drawDays() {
//		dayField.removeAll();
		int temp = dayField.getSelectedIndex();
		dayField.removeAllItems();
		int days = Month.of(monthField.getSelectedIndex() + 1)
				.length(Year.isLeap(yearField.getSelectedIndex() + tempLow));
		for (int i = 1; i <= days; i++) {// fills the combo box with 31 days
			dayField.addItem(i);
		}
		dayField.setSelectedIndex(Math.min(temp, days - 1));
	}

	private void setDateFields() {
		LocalDate ld = ((DateQuestion) getQ()).getLd();
		yearField.setSelectedItem(ld.getYear());
		monthField.setSelectedIndex(ld.getMonth().getValue() - 1);
		dayField.setSelectedItem(ld.getDayOfMonth());
	}

	@SuppressWarnings("unchecked")
	public MainQuestionWindow(JFrame arg0){
		super(arg0);
		setTitle("Questions");
		getContentPane().setBackground(Color.darkGray);
		setSize(500, 350);
		setLocationRelativeTo(arg0);

		setArg0(arg0);

		p1.setOpaque(false);
		p2.setOpaque(false);
		p3.setOpaque(false);
		p4.setOpaque(false);
		addButtons(p2);
		/*
		 * setEnterButton(p2); setExitButton(p2); setSkipButton(p2);
		 */

		nextQuestion();
		if (Settings.isDevMode())
			System.out.println(qGetSolution());
		switch (Settings.getNumType()) {
		case DATE:
			p4.setVisible(false);
			LocalDate date = LocalDate.now();
			final int temp = (date.getYear() / 10 + 2) * 10;// gets the start date of the second decade from now
			tempLow = (date.getYear() / 10 - 2) * 10;// 2 decades earlier
			input.setVisible(false);
			for (int i = tempLow; i <= temp; i++) {// fills out the years from tempLow until the previously selected
													// year
				yearField.addItem(i);
			}

			for (int i = 1; i <= 12; i++) {// fills out the combo box with every month
				String str = Month.of(i).toString();
				monthField.addItem(str.substring(0, 1) + str.substring(1).toLowerCase());
			}

			drawDays();
			setDateFields();
			break;
		case PRIME:
			for (JRadioButton j : bttns) {
				bG.add(j);
				j.setForeground(Color.white);
				j.addKeyListener(getKey());
			}
			p3.setVisible(false);
			p4.setVisible(true);
			input.setVisible(false);
			yes.setSelected(true);
			break;
		default:
			p3.setVisible(false);
			p4.setVisible(false);
		}

		setReps(Settings.getqNum());

		ActionListener dates = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				drawDays();
			}
		};
		monthField.addActionListener(dates);
		yearField.addActionListener(dates);

		/*
		 * KeyAdapter enterLis = new KeyAdapter() {
		 *
		 * @Override public void keyPressed(KeyEvent e) { if (e.getKeyCode() ==
		 * KeyEvent.VK_ENTER) { buttonClicked(getEnterButton()); } else if
		 * (e.getKeyCode() == KeyEvent.VK_ESCAPE) buttonClicked(getExitButton()); } };
		 */
//		getSkipButton().setToolTipText("Skip the current question for the cost of 1 point");

		input.addKeyListener(getKey());
		monthField.addKeyListener(getKey());
		dayField.addKeyListener(getKey());
		yearField.addKeyListener(getKey());

		question.setBackground(getContentPane().getBackground());
		question.setEditable(false);
		question.setHorizontalAlignment(SwingConstants.CENTER);
		question.setForeground(Color.white);
		question.setText(qToString());
		question.setFocusable(false);

		scoreField.setBackground(getContentPane().getBackground());
		scoreField.setEditable(false);
		scoreField.setHorizontalAlignment(SwingConstants.RIGHT);
		scoreField.setForeground(Color.white);
		scoreField.setFocusable(false);

		input.setHorizontalAlignment(SwingConstants.CENTER);
	}

}