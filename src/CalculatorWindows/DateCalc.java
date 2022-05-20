package CalculatorWindows;
import java.awt.*;
import java.awt.event.*;
import java.time.*;
import java.time.temporal.*;

import javax.swing.*;
import BreezySwing.*;

/**
 * finds the number of days before or after the current date the selected date
 * is
 * 
 * @author 22cnorton
 *
 */
public class DateCalc extends CalculatorWindow {
	private static final long serialVersionUID = -6510998048261871999L;

	private GBPanel p1 = addPanel(1, 1, 1, 1), p2 = addPanel(2, 1, 1, 1), p3 = addPanel(3, 1, 1, 1);

	@SuppressWarnings("rawtypes")
	private JComboBox dayField = p1.addComboBox(1, 2, 1, 1), monthField = p1.addComboBox(1, 1, 1, 1),
			yearField = p1.addComboBox(1, 3, 1, 1);

	private JTextField answer = p2.addTextField("0 days from now", 1, 1, 1, 1),
			mAnswer = p2.addTextField("0 months from now", 2, 1, 1, 1),
			yAnswer = p2.addTextField("0 years from now", 3, 1, 1, 1);

	private JTextField[] answers = { answer, mAnswer, yAnswer };

//	private JButton enter = p3.addButton("Calculate", 1, 1, 1, 1);
	private JButton exit = p3.addButton("Exit", 2, 1, 1, 1);

	public void buttonClicked(JButton buttonObj) {// button clicked method
		dispose();
	}

	/**
	 * finds the number of days, months and years the selected date is from the
	 * current date
	 */
	private void calcDate() {
		try {
			int day = dayField.getSelectedIndex() + 1;
			Month month = Month.of(monthField.getSelectedIndex() + 1);
			int year = yearField.getSelectedIndex() + 1970;

			LocalDate ld = LocalDate.of(year, month, day);

			long days = ChronoUnit.DAYS.between(ld, LocalDate.now());
			long months = ChronoUnit.MONTHS.between(ld, LocalDate.now());
			long years = ChronoUnit.YEARS.between(ld, LocalDate.now());

			if (ld.isBefore(LocalDate.now())) {// changes the output if the selected date is before or after the current
												// date
				answer.setText(Math.abs(days) + " days ago");
				mAnswer.setText(Math.abs(months) + " months ago");
				mAnswer.setText(Math.abs(years) + " years ago");
			} else {
				answer.setText(Math.abs(days) + " days from now");
				mAnswer.setText(Math.abs(months) + " months from now");
				yAnswer.setText(Math.abs(years) + " years from now");
			}
			/**
			 * if an invalid date is entered the last valid day of the selected month is
			 * entered
			 */
		} catch (DateTimeException e) {
			messageBox("That date does not exist");
			dayField.setSelectedIndex(Month.of(monthField.getSelectedIndex() + 1).minLength() - 1);
			calcDate();
		}
	}

	@SuppressWarnings("unchecked")
	private void drawDays() {
//		dayField.removeAll();
		int temp = dayField.getSelectedIndex();
		dayField.removeAllItems();
		int days = Month.of(monthField.getSelectedIndex() + 1).length(Year.isLeap(yearField.getSelectedIndex() + 1970));
		for (int i = 1; i <= days; i++) {// fills the combo box with 31 days
			dayField.addItem(i);
		}
		dayField.setSelectedIndex(Math.min(temp, days - 1));
	}

	/**
	 * Calculates the number of days before or after the current date
	 * 
	 * @param arg0 JFrame
	 */
	@SuppressWarnings("unchecked")
	public DateCalc(JFrame arg0) {
		super(arg0,"Date Calculator",500,350,arg0);
		/*
		 * setTitle("Date Calculator"); getContentPane().setBackground(Color.darkGray);
		 * setSize(500, 350); setLocationRelativeTo(arg0);
		 */

		p1.setOpaque(false);
		p2.setOpaque(false);
		p3.setOpaque(false);
//
//		mAnswer.setVisible(false);
//		yAnswer.setVisible(false);

		for (JTextField a : answers) {// sets all text fields to not be editable
			a.setBackground(getContentPane().getBackground());
			a.setEditable(false);
			a.setHorizontalAlignment(JLabel.CENTER);
			a.setForeground(Color.white);
			a.setBorder(null);
		}

		LocalDate date = LocalDate.now();

		final int temp = (date.getYear() / 10 + 2) * 10;// gets the start date of the second decade from now
		for (int i = 1970; i <= temp; i++) {// fills out the years from 1970 until the previously selected year
			yearField.addItem(i);
		}
		yearField.setSelectedIndex(date.getYear() - 1970);

		for (int i = 1; i <= 12; i++) {// fills out the combo box with every month
			String str = Month.of(i).toString();
			monthField.addItem(str.substring(0, 1) + str.substring(1).toLowerCase());
		}
		monthField.setSelectedItem(date.getMonth());

		drawDays();
		ActionListener dates = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				drawDays();
			}
		};

		monthField.addActionListener(dates);
		yearField.addActionListener(dates);
		dayField.setSelectedItem(date.getDayOfMonth());

		KeyAdapter key = new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				int c = e.getKeyCode();
				if (c == KeyEvent.VK_ESCAPE)
					buttonClicked(exit);
			}
		};
		monthField.addKeyListener(key);
		dayField.addKeyListener(key);
		yearField.addKeyListener(key);
		exit.addKeyListener(key);

		ItemListener lis = new ItemListener() {// when a change is made to any of the drop downs the date is
												// recalculated

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					calcDate();
				}
			}
		};
		dayField.addItemListener(lis);
		monthField.addItemListener(lis);
		yearField.addItemListener(lis);

		answer.setFocusable(false);
		mAnswer.setFocusable(false);
		yAnswer.setFocusable(false);
	}
}