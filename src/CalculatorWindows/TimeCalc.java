package CalculatorWindows;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import BreezySwing.GBPanel;

public class TimeCalc extends CalculatorWindow {

	private static final long serialVersionUID = -6457761906962113610L;

	private GBPanel p1 = addPanel(1, 2, 1, 1), p2 = addPanel(2, 1, 3, 1), p3 = addPanel(3, 1, 3, 1);
	private JButton exit = p3.addButton("Exit", GridBagConstraints.CENTER, 1, 1, 1, 1);
	private JComboBox<Integer> hour = p1.addComboBox(GridBagConstraints.EAST, 1, 1, 1, 1);
	private JComboBox<String> half = p1.addComboBox(GridBagConstraints.WEST, 1, 3, 1, 1),
			minute = p1.addComboBox(GridBagConstraints.CENTER, 1, 2, 1, 1),
			sign = p1.addComboBox(GridBagConstraints.WEST, 2, 3, 1, 1),
			timeUnit = p1.addComboBox(GridBagConstraints.CENTER, 2, 2, 1, 1);
	{
		for (int i = 1; i <= 12; i++) {
			hour.addItem(i);
		}
		DecimalFormat decForm = new DecimalFormat("00");
		for (int i = 0; i <= 59; i++) {
			minute.addItem(decForm.format(i));
		}
		half.addItem("AM");
		half.addItem("PM");

		sign.addItem("Ago");
		sign.addItem("From Now");

		timeUnit.addItem("Minutes");
		timeUnit.addItem("Hours");

	}

	private JComboBox<?>[] combos = { hour, half, minute, sign, timeUnit };

	private ActionListener lis = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			answer.setText(getAnswer());

		}
	};

	private JTextField answer = p2.addTextField("", GridBagConstraints.CENTER, 1, 1, 1, 1);
	{
		answer.setEditable(false);
		answer.setHorizontalAlignment(SwingConstants.CENTER);
		answer.setFocusable(false);
		answer.setBackground(Color.GRAY);
		answer.setForeground(Color.white);
	}

	private JSpinner min = p1.addSpinner(GridBagConstraints.EAST, 2, 1, 1, 1);
	{
		((JSpinner.DefaultEditor) min.getEditor()).getTextField().setHorizontalAlignment(SwingConstants.RIGHT);
	}
	{
		for (JComboBox<?> j : combos) {
			j.addActionListener(lis);
		}
		min.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				if ((int) min.getValue() <= 0) {
					min.setValue(1);
				}
				answer.setText(getAnswer());
			}
		});
	}

	public void buttonClicked(JButton buttonObj) {
		dispose();
	}

	private String getAnswer() {
		LocalTime t1 = LocalTime.of((hour.getSelectedIndex() + 1) + (12 * half.getSelectedIndex()),
				minute.getSelectedIndex());
		int amount = ((int) min.getValue()) * (timeUnit.getSelectedIndex() == 0 ? 1 : 60);
		amount *= (sign.getSelectedIndex() == 0 ? -1 : 1);

		LocalTime t2 = t1.plusMinutes(amount);

//		System.out.println(amount);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a");
		return formatter.format(t2);
	}

	public TimeCalc(JFrame arg0) {
		super(arg0, "Time Calculator");
		p1.setOpaque(false);
		p2.setOpaque(false);
		p3.setOpaque(false);
		min.setValue(120);

		KeyListener key = new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
					dispose();
			}
		};

		((JSpinner.DefaultEditor) min.getEditor()).getTextField().addKeyListener(key);
		for (JComboBox<?> j : combos) {
			j.addKeyListener(key);
		}
		exit.addKeyListener(key);
	}
}
