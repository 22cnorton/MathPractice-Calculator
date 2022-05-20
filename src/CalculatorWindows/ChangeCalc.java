package CalculatorWindows;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.LinkedHashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import BreezySwing.GBPanel;
import Main.ChangeMaker;
import Money.Money;

public class ChangeCalc extends CalculatorWindow {

	private static final long serialVersionUID = 4421157856193668474L;
	private JFrame arg0;

	private GBPanel p1 = addPanel(1, 1, 1, 1), p2 = addPanel(2, 1, 1, 1);

	private JTextField paymentField = p1.addTextField("$0.00", 1, 2, 1, 1),
			costField = p1.addTextField("$0.00", 2, 2, 1, 1);

	private JTextField paymentLabel = p1.addTextField("Payment:", 1, 1, 1, 1),
			costLabel = p1.addTextField("Cost:", 2, 1, 1, 1), spacer = p1.addTextField("", 1, 3, 1, 1);

	private JTextField[] labels = { paymentLabel, costLabel, spacer };

	private JButton enter = p2.addButton("Calculate", 1, 1, 1, 1), exit = p2.addButton("Exit", 2, 1, 1, 1);

	public void buttonClicked(JButton buttonObj) {
		if (buttonObj == enter) {
			try {

				double pay = Double.parseDouble(paymentField.getText().replaceAll("\\$|\\,", ""));
				double cost = Double.parseDouble(costField.getText().replaceAll("\\$|\\,", ""));

				if (pay < cost)
					throw new NumberFormatException("Cost Greater");
				if (pay <= 0 || cost <= 0)
					throw new NumberFormatException("Greater than Zero");
				LinkedHashMap<Money, Integer> m = ChangeMaker.getCoins(((int) (pay * 100)) - ((int) (cost * 100)));

				String[][] str = new String[m.size()][3];

				for (int i = 0; i < m.keySet().size(); i++) {
					str[i][0] = Integer.toString(m.get(m.keySet().toArray()[i]));
					str[i][1] = ((Money) m.keySet().toArray()[i]).getName();
					str[i][2] = ((Money) m.keySet().toArray()[i]).getImageAddress();
				}
				ChangeCalcAnswerWindow ccaw = new ChangeCalcAnswerWindow(arg0, this, str);
				ccaw.setVisible(true);

			} catch (NumberFormatException e) {
				if (e.getMessage().equals("Cost Greater"))
					messageBox("The payment must be greater than or equal\nto the cost");
				else if (e.getMessage().equals("Greater than Zero"))
					messageBox("Both values must be greater than zero");
				else if (e.getMessage().equals("Only Hundreds"))
					messageBox("This Calculator only works for numbers\nless than 1000");
				else
					e.printStackTrace();
				paymentField.grabFocus();
			} catch (IOException e) {
				messageBox("Error locating images");
			}
		} else if (buttonObj == exit) {
			dispose();
		}
	}

	public ChangeCalc(JFrame arg0) {
		super(arg0, "Calculate Change", 500, 350, arg0);
		this.arg0 = arg0;

		p1.setOpaque(false);
		p2.setOpaque(false);

		FocusListener focus = new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				try {
					String str = ((JTextField) e.getComponent()).getText();
					if (str.isEmpty())
						str = "0";
					str = str.replaceAll("[^0-9\\.]", "");
					((JTextField) e.getComponent())
							.setText(DecimalFormat.getCurrencyInstance().format(Double.parseDouble(str)));

				} catch (NumberFormatException nfe) {
					messageBox("Those are not valid characters");
				}
			}

			@Override
			public void focusGained(FocusEvent e) {
				JTextField c = ((JTextField) e.getComponent());
				try {
					String str = c.getText();
					c.setText(str.replaceAll("[^0-9\\.]", ""));
					c.selectAll();
				} catch (NumberFormatException nfe) {
					messageBox("Those are not valid characters");
				}
			}
		};

		paymentField.addFocusListener(focus);
		costField.addFocusListener(focus);

		KeyAdapter key = new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					buttonClicked(enter);
				else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
					buttonClicked(exit);
			}
		};
		costField.addKeyListener(key);
		paymentField.addKeyListener(key);
		enter.addKeyListener(key);
		exit.addKeyListener(key);

		for (JTextField j : labels) {
			j.setBackground(getContentPane().getBackground());
			j.setEditable(false);
			j.setHorizontalAlignment(SwingConstants.RIGHT);
			j.setForeground(Color.white);
			j.setFocusable(false);
		}
	}
}