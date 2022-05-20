package CalculatorWindows;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.FocusTraversalPolicy;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import BreezySwing.GBPanel;
import Main.Fraction;

public class FractionCalc extends CalculatorWindow {
	private static final long serialVersionUID = 8902366976945101320L;

	private GBPanel p1 = addPanel(1, 2, 1, 1), p2 = addPanel(2, 1, 3, 1), p3 = addPanel(3, 1, 3, 1),
			p4 = p1.addPanel(1, 1, 1, 3), p5 = p1.addPanel(1, 3, 1, 3);

//	private GBPanel p4 = addPanel(1, 1, 1, 1), p5 = addPanel(1, 3, 1, 1);
	private JTextField answer = p2.addTextField("5/4", GridBagConstraints.CENTER, 1, 1, 1, 1);

	/*
	 * private FocusListener foc = new FocusListener() {
	 * 
	 * @Override public void focusLost(FocusEvent e) {
	 * answer.setText(calcFrac().toString()); }
	 * 
	 * @Override public void focusGained(FocusEvent e) { // Unused } };
	 */

	private KeyListener key = new KeyAdapter() {
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
				dispose();
		}
	};
	private JTextField num1 = p4.addTextField("1", GridBagConstraints.SOUTHEAST, 1, 1, 1, 1),
			den1 = p4.addTextField("2", GridBagConstraints.NORTHEAST, 3, 1, 1, 1),
			num2 = p5.addTextField("3", GridBagConstraints.SOUTHWEST, 1, 3, 1, 1),
			den2 = p5.addTextField("4", GridBagConstraints.NORTHWEST, 3, 3, 1, 1);

	private JButton exit = p3.addButton("Exit", 1, 1, 1, 1);

	private JTextField[] inputs = { num1, den1, num2, den2 };
	{
		for (JTextField j : inputs) {
			j.setHorizontalAlignment(SwingConstants.CENTER);
//			j.addFocusListener(foc);
			j.addKeyListener(key);
		}
		exit.addKeyListener(key);
	}

	@SuppressWarnings("unused")
	private JSeparator frac1 = p4.addSeparator(true, GridBagConstraints.EAST, 2, 1, 1, 1),
			frac2 = p5.addSeparator(true, GridBagConstraints.WEST, 2, 3, 1, 1);

	private JComboBox<Character> sign = p1.addComboBox(GridBagConstraints.CENTER, 1, 2, 1, 1);
	{
		sign.addItem('+');
		sign.addItem('-');
		sign.addItem('*');
		sign.addItem('÷');
		sign.addKeyListener(key);
		/*
		 * sign.addActionListener(new ActionListener() {
		 * 
		 * @Override public void actionPerformed(ActionEvent e) {
		 * answer.setText(calcFrac().toString()); } });
		 */
	}

	private ArrayList<Component> comps = new ArrayList<>(
			Arrays.asList(new Component[] { num1, den1, sign, num2, den2, exit }));
//			Arrays.asList(null){num1,den1,sign,num2,den2,exit};

	private Timer t1 = new Timer(500, new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				answer.setText(calcFrac().toString());
			} catch (NumberFormatException nfe) {
				answer.setText("Disallowed Inputs");
			}
		}
	});

	public void buttonClicked(JButton buttonObj) {
		dispose();
	}

	private Fraction calcFrac() {
		Fraction f1 = new Fraction(Integer.parseInt(num1.getText()), Integer.parseInt(den1.getText()));
		Fraction f2 = new Fraction(Integer.parseInt(num2.getText()), Integer.parseInt(den2.getText()));
		Fraction f3;

		switch ((char) sign.getSelectedItem()) {
		case '+':
			f3 = f1.add(f2);
			break;
		case '-':
			f3 = f1.subtract(f2);
			break;
		case '*':
			f3 = f1.multiply(f2);
		default:
			f3 = f1.divide(f2);
		}

		return f3;
	}

	public FractionCalc(JFrame arg0) {
		super(arg0, "Fraction Calculator");

		p1.setOpaque(false);
		p2.setOpaque(false);
		p3.setOpaque(false);
		p4.setOpaque(false);
		p5.setOpaque(false);

		answer.setEditable(false);
		answer.setHorizontalAlignment(SwingConstants.CENTER);
		answer.setFocusable(false);
		answer.setBackground(Color.GRAY);
		answer.setForeground(Color.white);

		setFocusTraversalPolicy(new FocusTraversalPolicy() {

			@Override
			public Component getLastComponent(Container aContainer) {
				return exit;
			}

			@Override
			public Component getFirstComponent(Container aContainer) {
				return num1;
			}

			@Override
			public Component getDefaultComponent(Container aContainer) {
				return num1;
			}

			@Override
			public Component getComponentBefore(Container aContainer, Component aComponent) {
				if (aComponent.equals(num1))
					return exit;
				return comps.get(comps.indexOf(aComponent) - 1);
			}

			@Override
			public Component getComponentAfter(Container aContainer, Component aComponent) {
				if (aComponent.equals(exit)) {
					return num1;
				}
				return comps.get(comps.indexOf(aComponent) + 1);
			}
		});

		t1.start();
	}
}
