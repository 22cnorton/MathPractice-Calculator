package CalculatorWindows;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import BreezySwing.GBPanel;

@FunctionalInterface
interface NumParse {
	abstract String mathAsString();
}

public class NumbersCalc extends CalculatorWindow {

	private static final long serialVersionUID = -3102938452203243667L;

	private GBPanel p1 = addPanel(1, 1, 3, 1), p2 = addPanel(2, 2, 1, 1), p3 = addPanel(3, 1, 3, 1),
			p4 = addPanel(2, 1, 1, 1), p5 = addPanel(2, 3, 1, 1);

	private JButton exit = p3.addButton("Exit", 1, 1, 1, 1);

	private JTextField answer = p2.addTextField("2", GridBagConstraints.CENTER, 1, 1, 1, 1),
			num1 = p1.addTextField("1", GridBagConstraints.CENTER, 1, 1, 1, 1),
			num2 = p1.addTextField("1", GridBagConstraints.CENTER, 1, 3, 1, 1);
	private JComboBox<Character> sign =  p1.addComboBox(GridBagConstraints.CENTER, 1, 2, 1, 1);
	{
		sign.addItem('+');
		sign.addItem('-');
		sign.addItem('*');
		sign.addItem('÷');
	}
//	NumParse tmp = (i,j,c) ->

	private KeyListener key = new KeyAdapter() {
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				buttonClicked(exit);
			}
		}
	};
	NumParse asStr = () -> {
		try {
			char op = (char) sign.getSelectedItem();
//		try {
			int num1 = Integer.parseInt(this.num1.getText());
			int num2 = Integer.parseInt(this.num2.getText());

//		} catch (NumberFormatException nfe) {
//			answer.setText("Invalid Inputs");
//		}
			double out;
			switch (op) {
			case '-':
				out = num1 - num2;
				break;
			case '*':
				out = num1 * num2;
				break;
			case '÷':
				if (num2 == 0)
					throw new NumberFormatException();
				out = (double) num1 / num2;
				break;
			default:
				out = num1 + num2;
			}
			DecimalFormat decForm = new DecimalFormat("#,##0.#####");

			return decForm.format(out);
		} catch (NumberFormatException nfe) {
			return "Invalid inputs";
		}
	};

	private FocusListener foc = new FocusListener() {

		@Override
		public void focusLost(FocusEvent e) {
			answer.setText(asStr.mathAsString());

		}

		@Override
		public void focusGained(FocusEvent e) {

		}
	};
	private ActionListener lis = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			answer.setText(asStr.mathAsString());

		}
	};

	@Override
	public void buttonClicked(JButton buttonObj) {
		if (buttonObj == exit) {
			dispose();
		}
	}

	/*
	 * public String mathAsString(int num1, int num2, char op) { double out; switch
	 * (op) { case '-': out = num1 - num2; break; case '*': out = num1 * num2;
	 * break; case '÷': out = (double) num1 / num2; break; default: out = num1 +
	 * num2; } DecimalFormat decForm = new DecimalFormat("#,##0.#####");
	 *
	 * return decForm.format(out); }
	 */

	public NumbersCalc(JFrame arg0) {
		super(arg0, "Number Calculator");

		p1.setOpaque(false);
		p2.setOpaque(false);
		p3.setOpaque(false);
		p4.setOpaque(false);
		p5.setOpaque(false);

		num1.addFocusListener(foc);
		num2.addFocusListener(foc);
		sign.addActionListener(lis);

		num1.addKeyListener(key);
		num2.addKeyListener(key);
		sign.addKeyListener(key);
		exit.addKeyListener(key);

		answer.setEditable(false);
		answer.setHorizontalAlignment(SwingConstants.CENTER);
		answer.setFocusable(false);
		answer.setBackground(Color.GRAY);
		answer.setForeground(Color.white);

		num1.setHorizontalAlignment(SwingConstants.CENTER);
		num2.setHorizontalAlignment(SwingConstants.CENTER);
	}

}
