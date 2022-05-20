package CalculatorWindows;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import BreezySwing.GBPanel;
import Main.Fraction;

public class SimplifyFractionCalc extends CalculatorWindow {

	private static final long serialVersionUID = 5215969518819852100L;

	private GBPanel p1 = addPanel(1, 2, 1, 1), p2 = addPanel(GridBagConstraints.CENTER, 3, 1, 3, 1),
			p3 = addPanel(1, 1, 1, 2), p4 = addPanel(1, 3, 1, 2), p5 = addPanel(2, 2, 1, 1);

	private KeyListener key = new KeyAdapter() {
		public void keyPressed(KeyEvent e) {
			int c = e.getKeyCode();
			if (c == KeyEvent.VK_ESCAPE)
				dispose();
		}
	};

	private JTextField num = p1.addTextField("2", GridBagConstraints.SOUTH, 1, 1, 1, 1),
			den = p1.addTextField("4", GridBagConstraints.NORTH, 3, 1, 1, 1),
			answer = p5.addTextField("", GridBagConstraints.CENTER, 1, 1, 1, 1);
	{
		answer.setEditable(false);
		answer.setHorizontalAlignment(SwingConstants.CENTER);
		answer.setFocusable(false);
		answer.setBackground(Color.GRAY);
		answer.setForeground(Color.white);

		num.setHorizontalAlignment(JLabel.CENTER);
		den.setHorizontalAlignment(JLabel.CENTER);
		num.addKeyListener(key);
		den.addKeyListener(key);
		CaretListener car = new CaretListener() {

			@Override
			public void caretUpdate(CaretEvent e) {
				try {
					answer.setText(new Fraction(Integer.parseInt(num.getText()), Integer.parseInt(den.getText()))
							.simplify().toString());
				} catch (NumberFormatException nfe) {
					answer.setText("Invalid Inputs");
				}

			}
		};
		num.addCaretListener(car);
		den.addCaretListener(car);
	}

	private JSeparator line = p1.addSeparator(true, 2, 1, 1, 1);
	{
		line.setForeground(Color.white);
	}

	private JButton exit = p2.addButton("Exit", GridBagConstraints.CENTER, 1, 1, 1, 1);
	{
		exit.addKeyListener(key);
	}

	public void buttonClicked(JButton buttonObj) {
		if (buttonObj == exit) {
			dispose();
		}
	}

	public SimplifyFractionCalc(JFrame arg0) {
		super(arg0, "Fraction Simplifier");

		p1.setOpaque(false);
		p2.setOpaque(false);
		p3.setOpaque(false);
		p4.setOpaque(false);
		p5.setOpaque(false);
	}

}
