package CalculatorWindows;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.util.Hashtable;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import BreezySwing.GBPanel;

//import java.io.Serial;

public class SequenceCalc extends CalculatorWindow {

	private static final long serialVersionUID = -5941577984971216544L;

	private GBPanel p1 = addPanel(1, 1, 1, 1), p2 = addPanel(2, 1, 1, 1), p3 = addPanel(3, 1, 1, 1);

	private JTextField startinglabel = p1.addTextField("Starting Number", 1, 1, 1, 1),
			countByLabel = p1.addTextField("Counting By", 1, 3, 1, 1),
			seqTermCount = p1.addTextField("Number of Terms", GridBagConstraints.SOUTH,3, 2, 1, 1);
	private JTextField output = p2.addTextField("0", GridBagConstraints.CENTER, 1, 1, 1, 1);
	private JTextField[] labels = { startinglabel, countByLabel, output, seqTermCount };

//	private JSeparator vertSep = p1.addSeparator(false,GridBagConstraints.NORTHWEST, 1, 2, 1, 3),
//			horzSep = p1.addSeparator(true, GridBagConstraints.PAGE_END, 3, 1, 3, 1);
//	private JSeparator[] seps = { vertSep, horzSep };

	private JSpinner startingInput = p1.addSpinner(2, 1, 1, 1), countBy = p1.addSpinner(2, 3, 1, 1);
	private JSpinner[] spinners = { startingInput, countBy };

	private JButton /* enter = p3.addButton("Calculate", 1, 1, 1, 1), */ exit = p3.addButton("Exit", 2, 1, 1, 1);

	private JSlider iterations = p1.addSlider(2, 14, 2, 4, 2, 1, 1);

	public void buttonClicked(JButton buttonObj) {
		/*
		 * if (buttonObj == enter) { System.out .println(findSeq((int)
		 * startingInput.getValue(), (int) countBy.getValue(), iterations.getValue()));
		 * } else
		 */if (buttonObj == exit) {
			dispose();
		}
	}

	private String findSeq(int start, int countBy, int iters) {
		String str = "";
		if (countBy == 0)
			return "Cannot count by zero";
//		for (int i = 1; countBy > 0 ? i <= iters * countBy : i >= iters * countBy; i += countBy)
//			str += (i * start) + ", ";
		int sign = Integer.signum(countBy);
		for (int i = start; i * sign < (iters * countBy + start) * sign; i += countBy) {
//			System.out.println(i);
			str += Integer.toString(i) + ", ";

		}
//return str;
		return (str.length() > 0) ? str.substring(0, str.length() - 2) : "error";
	}

	public SequenceCalc(JFrame arg0) {
		super(arg0, "Sequence Calculator", 550, 350, arg0);

		p1.setOpaque(false);
		p2.setOpaque(false);
		p3.setOpaque(false);
//		p1.setBackground(Color.DARK_GRAY);
//		p2.setBackground(Color.gray);

		for (JTextField j : labels) {
			if (j != output) {
				j.setBackground(getContentPane().getBackground());
				j.setBorder(null);
				j.setForeground(Color.white);
			}
			j.setEditable(false);
			j.setHorizontalAlignment(SwingConstants.CENTER);
			j.setFocusable(false);
		}
		startingInput.setValue(1);
		countBy.setValue(2);
//		Hashtable<Integer, JLabel> dic = new Hashtable<Integer, JLabel>();
//
////		int temp=2+3*(n-1);
//		int count = 1;
//		for (int i = iterations.getMinimum(); i <= iterations.getMaximum(); i++) {
//			if ((iterations.getMinimum() + 3 * (count - 1)) % i == 0) {
//				dic.put(i, new JLabel(Integer.toString(i)));
//				dic.get(i).setForeground(Color.white);
//				count++;
////				System.out.println(i);
//			}
//		}
		Hashtable<Integer, JLabel> dic = new Hashtable<Integer, JLabel>();

		int rng = iterations.getMaximum() - iterations.getMinimum();
		int countNum = 2;
		while (countNum <= rng) {
			if (rng % countNum == 0)
				break;
			countNum++;
		}

		for (int i = iterations.getMinimum(); i <= iterations.getMaximum(); i++) {
			JLabel j = new JLabel((i + countNum) % countNum != 0 ? "" : Integer.toString(i));
			j.setForeground(Color.white);
			dic.put(i, j);
		}

		iterations.setSnapToTicks(true);
		iterations.setLabelTable(dic);
		iterations.setPaintTicks(true);
		iterations.setPaintLabels(true);
//		iterations.setMajorTickSpacing(5);
		iterations.setMinorTickSpacing(1);
		iterations.setForeground(Color.white);

		ChangeListener lis = new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				output.setText(
						findSeq((int) startingInput.getValue(), (int) countBy.getValue(), iterations.getValue()));
			}
		};
		iterations.addChangeListener(lis);
		iterations.setValue(iterations.getMaximum() / 2 + 1);
		/*
		 * for (JSeparator j : seps) { j.setBackground(Color.gray);
		 * j.setForeground(Color.gray); }
		 */

		for (JSpinner j : spinners) {
			((JSpinner.DefaultEditor) j.getEditor()).getTextField().setHorizontalAlignment(JTextField.CENTER);
			j.addChangeListener(lis);
//			((SpinnerNumberModel) j.getModel()).setMaximum(10000);
//			((SpinnerNumberModel) j.getModel()).setMinimum(-10000);

			j.addChangeListener(new ChangeListener() {

				@Override
				public void stateChanged(ChangeEvent e) {
					JSpinner spin = ((JSpinner) e.getSource());
					int val = (int) spin.getValue();
					if (val > 10000)
						spin.setValue(10000);
					else if (val < -10000)
						spin.setValue(-10000);
				}
			});

		}
		validate();
	}

}
