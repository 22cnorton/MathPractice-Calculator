package CalculatorWindows;

import java.awt.GridBagConstraints;
import java.text.DecimalFormat;

import javax.swing.JFrame;
import javax.swing.JTextField;

public class HexagonCalc extends ShapeCalc {
	private static final long serialVersionUID = -2899711242332545083L;

	private JTextField sideInput = getP1().addTextField("2", GridBagConstraints.WEST, 1, 2, 1, 1),
			sideLabel = getP1().addTextField("Side: ", GridBagConstraints.EAST, 1, 1, 1, 1),
			spacer = getP1().addTextField("", 1, 3, 1, 1);

	private JTextField[] labs = { sideLabel, spacer };

	@Override
	public String getAreaAnswer() {
		try {
			int num1 = Integer.parseInt(sideInput.getText());

			if (num1 <= 0)
				throw new NumberFormatException();

			double area = Math.pow(num1, 2) * ((3 * Math.sqrt(3)) / 2);

			String text = Double.toString(Math.abs(area));
			int integerPlaces = text.indexOf('.');
			int decimalPlaces = text.length() - integerPlaces - 1;
			if (decimalPlaces > 2) {
				DecimalFormat dec = new DecimalFormat("#0.00");
				area = Double.parseDouble(dec.format(area));
			}

			return "Area: " + (area) + " Units^2";
		} catch (NumberFormatException nfe) {
			return "Invalid Inputs";
		}
	}

	@Override
	public String getPerAnswer() {
		try {
			int num1 = Integer.parseInt(sideInput.getText());

			if (num1 <= 0)
				throw new NumberFormatException();

			return "Perimeter: " + (num1 * 6) + " Units";
		} catch (NumberFormatException nfe) {
			return "Invalid Inputs";
		}
	}

	public HexagonCalc(JFrame arg0) {
		super(arg0, "Hexagon Calculator");

		setLabs(labs);

		sideInput.addKeyListener(getKey());
	}
}
