package CalculatorWindows;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.text.DecimalFormat;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import BreezySwing.GBPanel;

public class TriangleCalc extends ShapeCalc {
	private static final long serialVersionUID = 336661027592930781L;
	private GBPanel p6 = getP1().addPanel(4, 1, 3, 1);

	private int align = GridBagConstraints.CENTER;

	private JTextField b1 = getP1().addTextField("3", align, 1, 2, 1, 1), b2 = getP1().addTextField("4", align, 2, 2, 1, 1),
			b3 = getP1().addTextField("5", align, 3, 2, 1, 1);

	private JTextField l1 = getP1().addTextField("Side 1", align, 1, 1, 1, 1),
			l2 = getP1().addTextField("Side 2", align, 2, 1, 1, 1), l3 = getP1().addTextField("Side 3", align, 3, 1, 1, 1),
			spacer = getP1().addTextField("", 3, 3, 1, 1), spacer2 = p6.addTextField("", 1, 1, 1, 1);

	private JTextField[] labels = { l1, l2, l3, spacer, spacer2 };

	private JCheckBox sidesOrHeight = p6.addCheckBox("Scalene Triangle", align, 1, 2, 1, 1);
	{
		sidesOrHeight.setForeground(Color.white);
		sidesOrHeight.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				if (!sidesOrHeight.isSelected()) {
					sidesOrHeight.setText("Scalene Triangle");
					l1.setText("Side 1");
					l2.setText("Side 2");
					b3.setVisible(true);
					l3.setVisible(true);
				} else {
					sidesOrHeight.setText("Right Triangle");
					l1.setText("Base");
					l2.setText("Height");
					b3.setVisible(false);
					l3.setVisible(false);
				}
			}
		});
	}

	String getAreaAnswer() {
		try {
			int num1 = Integer.parseInt(b1.getText());
			int num2 = Integer.parseInt(b2.getText());
			int num3 = Integer.parseInt(b3.getText());

//			System.out.println("Num1: " + num1 + " Num2: " + num2 + " Num3: " + num3);
			if (num1 <= 0 || num2 <= 0 || num3 <= 0)
				throw new NumberFormatException();
			double answer = 0;
			if (!sidesOrHeight.isSelected()) {
				double n = (num1 + num2 + num3) / 2.0;
//				System.out.println(n);
				answer = Math.sqrt(n * (n - num1) * (n - num2) * (n - num3));
			} else {
				int base = num1;
				int height = num2;
				answer = 0.5 * base * height;
			}
			if (Double.isNaN(answer)) {
				throw new ArithmeticException("Not a triangle");
			}

			DecimalFormat dec = new DecimalFormat("#,##0.###");
			return "Area: " + dec.format(answer) + " Units^2";
		} catch (NumberFormatException nfe) {
			return "Invalid Inputs";
		}
	}

	String getPerAnswer() {
		try {
			int num1 = Integer.parseInt(b1.getText());
			int num2 = Integer.parseInt(b2.getText());
			int num3 = Integer.parseInt(b3.getText());
			if (num1 <= 0 || num2 <= 0 || num3 <= 0)
				throw new NumberFormatException();
			double answer = 0;
			if (!sidesOrHeight.isSelected()) {
				answer = num1 + num2 + num3;
			} else {
				int base = num1;
				int height = num2;
				double hyp = Math.sqrt(Math.pow(height, 2) + Math.pow(base, 2));
				answer = base + hyp + height;
			}

			if (Double.isNaN(answer)) {
				throw new ArithmeticException("Not a triangle");
			}
			DecimalFormat dec = new DecimalFormat("#,##0.###");
			return "Perimeter: " + dec.format(answer) + " Units";
		} catch (NumberFormatException nfe) {
			return "Invalid Inputs";
		}
	}

	public TriangleCalc(JFrame arg0) {
		super(arg0, "Triangle Calculator");

		p6.setOpaque(false);
		setLabs(labels);

		b1.addKeyListener(getKey());
		b2.addKeyListener(getKey());
		b3.addKeyListener(getKey());
		sidesOrHeight.addKeyListener(getKey());

	}
}
