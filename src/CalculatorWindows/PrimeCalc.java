package CalculatorWindows;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import BreezySwing.GBPanel;

public class PrimeCalc extends CalculatorWindow {
	private static final long serialVersionUID = 920721102356356790L;

	private GBPanel p1 = addPanel(1, 1, 3, 1), p2 = addPanel(2, 2, 1, 1), p3 = addPanel(3, 1, 3, 1),
			p4 = addPanel(2, 1, 1, 1), p5 = addPanel(2, 3, 1, 1);

	private JButton exit = p3.addButton("Exit", 1, 1, 1, 1);

	private JTextField answer = p2.addTextField("", GridBagConstraints.CENTER, 1, 1, 1, 1),
			num1 = p1.addTextField("1", GridBagConstraints.WEST, 1, 2, 1, 1),
			label = p1.addTextField("Number", GridBagConstraints.EAST, 1, 1, 1, 1),
			spacer = p1.addTextField("", GridBagConstraints.EAST, 1, 3, 1, 1);

	private JTextField[] labs = { label, spacer };
	{
		for (JTextField j : labs) {
			j.setHorizontalAlignment(SwingConstants.RIGHT);
			j.setEditable(false);
			j.setFocusable(false);
			j.setBackground(getContentPane().getBackground());
			j.setForeground(Color.white);
		}
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

	private KeyListener key = new KeyAdapter() {
		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				buttonClicked(exit);
			}
		}
	};

	private String getAnswer() {
		try {
			var num = Math.abs(Long.parseLong(num1.getText()));

			boolean isPrime = (num == 1 || num == 0) ? false : true;
			for (int i = 2; i < num / 2 + 1; i++) {
				if (num % i == 0) {
					isPrime = false;
					break;
				}
			}

			return (isPrime ? "Is Prime" : "Not Prime");
		} catch (NumberFormatException nfe) {
			return "Invalid Inputs";
		}
	}

	@Override
	public void buttonClicked(JButton buttonObj) {
		dispose();
	}

	public PrimeCalc(JFrame arg0) {
		super(arg0, "Prime Calculator");
		p1.setOpaque(false);
		p2.setOpaque(false);
		p3.setOpaque(false);
		p4.setOpaque(false);
		p5.setOpaque(false);

		num1.addKeyListener(key);
		exit.addKeyListener(key);

		answer.setEditable(false);
		answer.setHorizontalAlignment(SwingConstants.CENTER);
		answer.setFocusable(false);
		answer.setBackground(Color.GRAY);
		answer.setForeground(Color.white);

		num1.setHorizontalAlignment(SwingConstants.CENTER);

		t1.start();
	}

}
