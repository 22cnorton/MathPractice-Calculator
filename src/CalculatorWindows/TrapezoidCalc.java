package CalculatorWindows;

import java.awt.GridBagConstraints;
import java.text.DecimalFormat;

import javax.swing.JFrame;
import javax.swing.JTextField;

public class TrapezoidCalc extends ShapeCalc {
	private static final long serialVersionUID = 4085913027740645433L;

	private int align = GridBagConstraints.CENTER;

	private JTextField b1 = getP1().addTextField("3", align, 1, 2, 1, 1),
			b2 = getP1().addTextField("4", align, 2, 2, 1, 1), b3 = getP1().addTextField("5", align, 3, 2, 1, 1);

	private JTextField l1 = getP1().addTextField("Base 1", align, 1, 1, 1, 1),
			l2 = getP1().addTextField("Base 2", align, 2, 1, 1, 1),
			l3 = getP1().addTextField("Height", align, 3, 1, 1, 1), spacer = getP1().addTextField("", 3, 3, 1, 1),
			spacer2 = getP4().addTextField("", 1, 1, 1, 1);

	private JTextField[] labels = { l1, l2, l3, spacer, spacer2 };

	String getPerAnswer() {
		try {
			int b1 = Integer.parseInt(this.b1.getText());
			int b2 = Integer.parseInt(this.b2.getText());
			int h = Integer.parseInt(this.b3.getText());
			if (b1 <= 0 || b2 <= 0 || h <= 0)
				throw new NumberFormatException();

			double s = Math.sqrt(Math.pow(((b1 - b2) / 2), 2) + Math.pow(h, 2));

			double answer = 2 * s + b1 + b2;

			DecimalFormat dec = new DecimalFormat("#,##0.###");
			return "Perimeter: " + dec.format(answer) + " Units";
		} catch (NumberFormatException nfe) {
			return "Invalid Inputs";
		}
	}

	String getAreaAnswer() {
		try {
			int b1 = Integer.parseInt(this.b1.getText());
			int b2 = Integer.parseInt(this.b2.getText());
			int h = Integer.parseInt(this.b3.getText());
			if (b1 <= 0 || b2 <= 0 || h <= 0)
				throw new NumberFormatException();

			double answer = 0.5 * h * (b1 + b2);
			DecimalFormat dec = new DecimalFormat("#,##0.###");
			return "Area: " + dec.format(answer) + " Units^2";
		} catch (NumberFormatException nfe) {
			return "Invalid Inputs";
		}
	}

	public TrapezoidCalc(JFrame arg0) {
		super(arg0, "Trapezoid Calc");

		setLabs(labels);

		b1.addKeyListener(getKey());
		b2.addKeyListener(getKey());
		b3.addKeyListener(getKey());
	}
}