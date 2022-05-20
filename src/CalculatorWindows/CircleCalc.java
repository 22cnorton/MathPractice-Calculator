package CalculatorWindows;

import java.awt.GridBagConstraints;
import java.text.DecimalFormat;

import javax.swing.JFrame;
import javax.swing.JTextField;

public  class CircleCalc extends ShapeCalc {
	private static final long serialVersionUID = -1046217605002073376L;

	private JTextField radius = getP1().addTextField("2", GridBagConstraints.CENTER, 1, 2, 1, 1),
			rLabel = getP1().addTextField("Radius:", GridBagConstraints.EAST, 1, 1, 1, 1),
			spacer = getP1().addTextField("", GridBagConstraints.WEST, 1, 3, 1, 1);
	private JTextField labels[] = { rLabel, spacer };

	String getAreaAnswer() {
		int rad = Integer.parseInt(radius.getText());
		double area = Math.PI * Math.pow(rad, 2);
		DecimalFormat dec = new DecimalFormat("#,##0.###");
		return "Area: " + dec.format(area) + " Units^2";
	}

	String getPerAnswer() {
		int rad = Integer.parseInt(radius.getText());
		double per = 2 * Math.PI * rad;
		DecimalFormat dec = new DecimalFormat("#,##0.###");
		return "Circumference: " + dec.format(per) + " Units";
	}


	public CircleCalc(JFrame arg0) {
		super(arg0, "Circle Calculator");

		setLabs(labels);

		radius.addKeyListener(getKey());

	}
}