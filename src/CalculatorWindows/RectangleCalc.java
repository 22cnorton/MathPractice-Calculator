package CalculatorWindows;

import java.awt.GridBagConstraints;

import javax.swing.JFrame;
import javax.swing.JTextField;

public class RectangleCalc extends ShapeCalc {
	private static final long serialVersionUID = 5010482002093714196L;

	private JTextField heightInput = getP1().addTextField("2", GridBagConstraints.WEST, 1, 2, 1, 1),
			widthInput = getP1().addTextField("1", GridBagConstraints.WEST, 2, 2, 1, 1),
			heightLabel = getP1().addTextField("Height: ", GridBagConstraints.EAST, 1, 1, 1, 1),
			widthLabel = getP1().addTextField("Width: ", GridBagConstraints.EAST, 2, 1, 1, 1),
			spacer = getP1().addTextField("", 1, 3, 1, 1);

	private JTextField[] labs = { heightLabel, widthLabel, spacer };

	public String getAreaAnswer() {
		try {
			int num1 = Integer.parseInt(heightInput.getText());
			int num2 = Integer.parseInt(widthInput.getText());

			if (num1 <= 0 || num2 <= 0)
				throw new NumberFormatException();

			return "Area: " + (num1 * num2) + " Units^2";
		} catch (NumberFormatException nfe) {
			return "Invalid Inputs";
		}
	}

	public String getPerAnswer() {
		try {
			int num1 = Integer.parseInt(heightInput.getText());
			int num2 = Integer.parseInt(widthInput.getText());

			if (num1 <= 0 || num2 <= 0)
				throw new NumberFormatException();

			return "Perimeter: " + (num1 * 2 + num2 * 2) + " Units";
		} catch (NumberFormatException nfe) {
			return "Invalid Inputs";
		}
	}

	public RectangleCalc(JFrame arg0) {
		super(arg0, "Rectangle Calculator");
		setLabs(labs);

		widthInput.addKeyListener(getKey());
		heightInput.addKeyListener(getKey());

	}
}
