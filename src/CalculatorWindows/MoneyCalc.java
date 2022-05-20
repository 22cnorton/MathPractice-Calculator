package CalculatorWindows;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.Timer;

import BreezySwing.GBPanel;

public class MoneyCalc extends CalculatorWindow {
	private static final long serialVersionUID = 4353671116145752613L;

	private GBPanel p1 = addPanel(1, 2, 1, 1), p2 = addPanel(2, 1, 3, 1), p3 = addPanel(3, 1, 3, 1),
			p4 = p1.addPanel(1, 1, 2, 1), p5 = p1.addPanel(3, 1, 2, 1);

	private JTextField num1 = p4.addTextField("1.11", GridBagConstraints.EAST, 1, 2, 1, 1),
			num2 = p5.addTextField("2.22", GridBagConstraints.WEST, 1, 2, 1, 1);

	private JTextField lab1 = p4.addTextField("$", GridBagConstraints.EAST, 1, 1, 1, 1),
			lab2 = p5.addTextField("$", GridBagConstraints.WEST, 1, 1, 1, 1), lab3 = p4.addTextField("", 1, 3, 1, 1),
			lab4 = p5.addTextField("", 1, 3, 1, 1), answer = p2.addTextField("", GridBagConstraints.CENTER, 1, 1, 1, 1);

	private JTextField[] labs = { lab1, lab2, lab3, lab4, answer };
	{
		for (JTextField j : labs) {
			j.setHorizontalAlignment(JLabel.RIGHT);
			j.setEditable(false);
			j.setFocusable(false);
			j.setBackground(getContentPane().getBackground());
			j.setForeground(Color.white);
		}
		answer.setBackground(Color.gray);
		answer.setHorizontalAlignment(JLabel.CENTER);
	}

	private JButton exit = p3.addButton("Exit", GridBagConstraints.CENTER, 1, 1, 1, 1);

	private JComboBox<Character> sign = p1.addComboBox(GridBagConstraints.CENTER, 2, 1, 1, 1);
	{
		sign.addItem('+');
		sign.addItem('-');
	}

	private Timer t1 = new Timer(500, new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			answer.setText(getAnswer());
		}
	});
	{
		t1.setInitialDelay(0);
	}

	private String getAnswer() {
		try {
			int val1 = (int) (Double.parseDouble(num1.getText()) * 100);
			int val2 = (int) (Double.parseDouble(num2.getText()) * 100);
			if ((val1 < 0 || val2 < 0)) {
				throw new NumberFormatException();
			}
			int val3 = 0;

			switch (sign.getSelectedIndex()) {
			case 0:
				val3 = val1 + val2;
				break;
			default:
				val3 = val1 - val2;
			}

			DecimalFormat dec = new DecimalFormat("$#,##0.00");

			return dec.format(val3 / 100.0);
		} catch (NumberFormatException nfe) {
			return "Invalid Inputs";
		}
	}

	public void buttonClicked(JButton buttonObj) {
		dispose();
	}

	public MoneyCalc(JFrame arg0) {
		super(arg0, "Money Calculator");

		p1.setOpaque(false);
		p2.setOpaque(false);
		p3.setOpaque(false);
		p4.setOpaque(false);
		p5.setOpaque(false);

		num1.setHorizontalAlignment(JLabel.LEFT);
		num2.setHorizontalAlignment(JLabel.LEFT);
		KeyListener key = new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
					dispose();
			}
		};
		num1.addKeyListener(key);
		num2.addKeyListener(key);
		sign.addKeyListener(key);
		exit.addKeyListener(key);

		t1.start();
	}
}