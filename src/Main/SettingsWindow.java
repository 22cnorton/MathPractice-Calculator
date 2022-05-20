package Main;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import BreezySwing.GBDialog;
import BreezySwing.GBPanel;
import Utils.StringUtils;

public class SettingsWindow extends GBDialog {
	private static final long serialVersionUID = -8103304291297001397L;

	private GBPanel p1 = addPanel(1, 1, 1, 1), p2 = addPanel(2, 1, 1, 1), p3 = p1.addPanel(10, 2, 1, 1),
			p4 = p1.addPanel(9, 1, 1, 1);

	private JSpinner repsInput = p1.addSpinner(3, 2, 1, 1), lowerBoundInput = p1.addSpinner(1, 2, 1, 1),
			upperBoundInput = p1.addSpinner(2, 2, 1, 1);// switched from regular field
	private JSpinner[] spinners = { repsInput, lowerBoundInput, upperBoundInput };

	private JTextField lowerBoundLabel = p1.addTextField("Lower Bound", 1, 1, 1, 1),
			upperBoundLabel = p1.addTextField("Upper Bound", 2, 1, 1, 1),
			repsLabel = p1.addTextField("Repetitions", 3, 1, 1, 1),
			typeLabel = p1.addTextField("Question Type", 4, 1, 1, 1),
			firstLargerLabel = p1.addTextField("First Number is Always Larger", 5, 1, 1, 1),
			fractionAnswerLabel = p1.addTextField("Provide Answer as a Fraction", 6, 1, 1, 1),
			numType = p1.addTextField("Number Type", 10, 1, 1, 1),
			slowTxtBoxLabel = p4.addTextField("Text Box Speed", GridBagConstraints.SOUTH, 1, 1, 1, 1),
			slowTxtBoxExtra = p4.addTextField(" (Larger is Faster, Recomended 7)", GridBagConstraints.NORTH, 2, 1, 1,
					1),
			timedQuestionLabel = p1.addTextField("Timed Questions", 7, 1, 1, 1),
			timedQSpeedLab = p1.addTextField("Timed Question Speed (sec)", GridBagConstraints.CENTER, 8, 1, 1, 1);

	private JTextField[] labels = { lowerBoundLabel, upperBoundLabel, repsLabel, typeLabel, firstLargerLabel,
			fractionAnswerLabel, numType, slowTxtBoxLabel, slowTxtBoxExtra, timedQuestionLabel, timedQSpeedLab };

	@SuppressWarnings("rawtypes")
	private JComboBox typeInput = p1.addComboBox(4, 2, 1, 1);

	private JCheckBox firstLargerInput = p1.addCheckBox("", 5, 2, 1, 1);
	private JCheckBox fractionAnswer = p1.addCheckBox("", 6, 2, 1, 1);
	private JCheckBox timedQuestion = p1.addCheckBox("", 7, 2, 1, 1);
	private int maxSlide = 10;

	private JSlider timedQSpeed = p1.addSlider(5, 125, 32, 8, 2, 1, 1);
	private JSlider slowTxtBox = p1.addSlider(1, maxSlide, 7, 9, 2, 1, 1);

	private JComponent[] comps = { typeInput, firstLargerInput, fractionAnswer, slowTxtBox };

	private ButtonGroup bgNumType = new ButtonGroup();

	/*
	 * private JRadioButton rNorm, rMoney, rFraction, rCoin, rTime, rSeq, rSimpFrac,
	 * rDate, rRectangle, rCircle, rTriangle, rPercent, rTrapezoid, rChange;
	 */

	private JRadioButton[] radioButtons = new JRadioButton[ProblemTypes.values().length];
	/*
	 * { rNorm, rMoney, rFraction, rCoin, rTime, rSeq, rSimpFrac, rDate, rRectangle,
	 * rCircle, rTriangle, rPercent, rTrapezoid, rChange };
	 */

	private JButton enter = p2.addButton("Enter", 1, 1, 1, 1), exit = p2.addButton("Cancel", 2, 1, 2, 1),
			reset = p2.addButton("Reset", 1, 2, 1, 1);

	private JFrame arg0;
//	private Hashtable<Integer, Integer> dic=new Hashtable<Integer, Integer>();

	private int lowerMinBound, upperMaxBound;

	@Override
	public void buttonClicked(JButton buttonObj) {
		if (buttonObj == enter) {
			try {
				int lB = ((int) lowerBoundInput.getValue()), uB = ((int) upperBoundInput.getValue());

				/*
				 * if (uB < lB && (!rSeq.isSelected() && !rSimpFrac.isSelected())) { throw new
				 * UnsupportedOperationException("Lower Bound must be smaller than Upper Bound"
				 * ); }
				 *
				 * if (rSimpFrac.isSelected() && lB <= 1) throw new
				 * NumberFormatException("Highest Mult Pos"); else if (rCoin.isSelected() && (lB
				 * < 1 || uB > 50)) throw new NumberFormatException("Coin limits"); else if
				 * (rChange.isSelected() && lB < 1) throw new
				 * NumberFormatException("Change Limits"); else if (rTime.isSelected()) {
				 * Settings.setLowerBound(1); Settings.setUpperBound(12); } else if
				 * ((rRectangle.isSelected() || rCircle.isSelected() || rTrapezoid.isSelected()
				 * || rTriangle.isSelected()) && lB <= 0) { throw new
				 * NumberFormatException("Shapes need positive numbers"); } else { }
				 */

				Settings.setLowerBound(lB);
				Settings.setUpperBound(uB);

				int reps = (int) repsInput.getValue();
				if (reps < 1) {
					throw new NumberFormatException("Non Negative");
				}
				Settings.setqNum(reps);
				switch (typeInput.getSelectedIndex()) {
				case 0:
					Settings.setType('+');
					break;
				case 1:
					Settings.setType('-');
					break;
				case 2:
					Settings.setType('*');
					break;
				case 3:
					Settings.setType('÷');
					break;
				case 4:
					Settings.setType('r');
					break;
				}

				for (int i = 0; i < radioButtons.length; i++) {
					if (radioButtons[i].isSelected()) {
						Settings.setNumType(ProblemTypes.values()[i]);
						break;
					}
				}
				/*
				 * if (rNorm.isSelected()) Settings.setNumType(0); else if (rMoney.isSelected())
				 * Settings.setNumType(1); else if (rFraction.isSelected())
				 * Settings.setNumType(2); else if (rCoin.isSelected()) Settings.setNumType(3);
				 * else if (rTime.isSelected()) Settings.setNumType(4); else if
				 * (rSeq.isSelected()) Settings.setNumType(5); else if (rSimpFrac.isSelected())
				 * Settings.setNumType(6); else if (rDate.isSelected()) Settings.setNumType(7);
				 * else if (rRectangle.isSelected()) Settings.setNumType(8); else if
				 * (rCircle.isSelected()) Settings.setNumType(9); else if
				 * (rTriangle.isSelected()) Settings.setNumType(10); else if
				 * (rPercent.isSelected()) Settings.setNumType(11); else if
				 * (rTrapezoid.isSelected()) Settings.setNumType(12); else if
				 * (rChange.isSelected()) Settings.setNumType(13);
				 */

//				s.setType((char) typeInput.getSelectedItem());
//				System.out.println(s);
				Settings.setFirstLarger(firstLargerInput.isSelected());
				Settings.setFractionAnswer(fractionAnswer.isSelected());
				Settings.setTimed(timedQuestion.isSelected());
//				System.out.println(timedQSpeed.getValue());
				Settings.setTimedSpeed(timedQSpeed.getValue());
//				System.out.println(Settings.isFractionAnswer());
//				System.out.println(Settings.isFirstLarger());
				dispose();
			} catch (NumberFormatException e) {
				if (e.getMessage().equals("Non Negative"))
					messageBox("Reps must be a number greater than 0");
				else if (e.getMessage().equals("Coin limits"))
					messageBox("Coins problems have a\nmax of 50 and a min of 1");
				else if (e.getMessage().equals("Highest Mult Pos"))
					messageBox("The highest multiple must be greter than 1");
				else if (e.getMessage().equals("Shapes need positive numbers"))
					messageBox("Shape must have a positive lower bound");
				else
					messageBox("Must use numbers");
			} catch (UnsupportedOperationException e) {
				messageBox(e.getMessage());
			}
			Settings.setSlowTxt(slowTxtBox.getValue());
//			Settings.writeFiles();
		} else if (buttonObj == exit) {
			dispose();
		} else if (buttonObj == reset) {
			ConfirmationDialog c = new ConfirmationDialog(arg0, this, "Are You Sure You Want to Reset?");
			c.setVisible(true);
			if (c.getDlgCloseIndicator().equals("Yes")) {// if the user confirms to exit
				lowerBoundInput.setValue(1);
				upperBoundInput.setValue(10);
				repsInput.setValue(10);
				typeInput.setSelectedIndex(0);
				firstLargerInput.setSelected(true);
//				slowTxtBox.setSelected(false);
				slowTxtBox.setValue(4);
				radioButtons[0].setSelected(true);
				timedQuestion.setSelected(false);
				timedQSpeed.setValue(32);
//				rNorm.setSelected(true);
				try {
					FileOutputStream fileOut = new FileOutputStream("score.ser");
					ObjectOutputStream out = new ObjectOutputStream(fileOut);
					out.writeObject(0);
					out.close();
					fileOut.close();
//				System.out.printf("Serialized data is saved in /tmp/score.ser");
				} catch (IOException i) {
					i.printStackTrace();
				}
				buttonClicked(enter);
//				Settings.writeFiles();
				dispose();
			}
		}
	}

	private final class LabelGetter extends Thread {

		private Hashtable<Integer, JLabel> dic;

		@Override
		public void run() {
			dic = getAutoSpeedLabels();
		}

		public LabelGetter() {
			super();
			start();
		}

		public Hashtable<Integer, JLabel> getDic() {
			return dic;
		}

		private Hashtable<Integer, JLabel> getAutoSpeedLabels() {
			Hashtable<Integer, JLabel> dic = new Hashtable<>();

			int range = maxSlide - slowTxtBox.getMinimum();
			int countNum = 2;
			while (countNum <= range) {
				if (range % countNum == 0)
					break;
				countNum++;
			}

			for (int i = slowTxtBox.getMinimum(); i <= maxSlide; i++) {
				JLabel j = new JLabel((i + countNum - 1) % countNum != 0 ? ""
						: Integer.toString(maxSlide - i + slowTxtBox.getMinimum()));
				j.setForeground(Color.white);
				dic.put(i, j);
			}
			return dic;
		}
	}

	@SuppressWarnings("unchecked")
	public SettingsWindow(JFrame arg0, int score) {
		super(arg0);
		this.arg0 = arg0;
		setTitle("Settings");
		getContentPane().setBackground(Color.darkGray);
		setSize(550, 600);
		setLocationRelativeTo(arg0);

//		this.s = s;
		p1.setOpaque(false);
		p2.setOpaque(false);
		p3.setOpaque(false);
		p4.setOpaque(false);
		LabelGetter labGet = new LabelGetter();

		setButtons(score);

		lowerMinBound = Integer.MIN_VALUE + 1;
		upperMaxBound = Integer.MAX_VALUE - 1;

//		Settings.readFiles();
//		System.out.println(Settings.isFractionAnswer());
//		System.out.println(Settings.isFirstLarger());
		fractionAnswer.setSelected(Settings.isFractionAnswer());
		firstLargerInput.setSelected(Settings.isFirstLarger());
		fractionAnswerLabel.setEnabled(false);
		fractionAnswer.setEnabled(false);

		if (score < 120)
			getQSpeedValues(score);

		timedQSpeed.setSnapToTicks(true);
		timedQSpeed.setMajorTickSpacing(30);
		timedQSpeed.setMinorTickSpacing(10);
		timedQSpeed.setForeground(Color.white);
		timedQSpeed.setPaintLabels(true);
		timedQSpeed.setPaintTicks(true);
		timedQSpeed.setValue(Settings.getTimedSpeed());

//		slowTxtBox.setSelected(Settings.isSlowTxt());
		slowTxtBox.setSnapToTicks(true);
		slowTxtBox.setLabelTable(labGet.getDic());
//		slowTxtBox.setPaintTrack(false);
		slowTxtBox.setPaintLabels(true);
		slowTxtBox.setPaintTicks(true);
		slowTxtBox.setMajorTickSpacing(2);
		slowTxtBox.setMinorTickSpacing(1);
//		slowTxtBox.setForeground(Color.white);
		slowTxtBox.setValue(Settings.getSlowTxt());
//		slowTxtBox.setBackground(Color.red);

		typeInput.addItem('+');
		typeInput.addItem('-');
		typeInput.addItem('*');
		typeInput.addItem('÷');
		typeInput.addItem("Random");

		switch (Settings.getType()) {
		case '+':
			typeInput.setSelectedIndex(0);
			break;
		case '-':
			typeInput.setSelectedIndex(1);
			break;
		case '*':
			typeInput.setSelectedIndex(2);
			break;
		case '÷':
			typeInput.setSelectedIndex(3);
			break;
		default:
			typeInput.setSelectedIndex(4);
			break;
		}
		firstLargerInput.setForeground(Color.white);
//		typeInput.setSelectedItem(Settings.getType());

//		labels[5].setEnabled(false);
//		fractionAnswer.setEnabled(false);

		firstLargerInput.setSelected(Settings.isFirstLarger());

		upperBoundInput.setValue(Settings.getUpperBound());
		lowerBoundInput.setValue(Settings.getLowerBound());

//!rSeq.isSelected() && !rSimpFrac.isSelected()
		lowerBoundInput.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				JSpinner s = (JSpinner) e.getSource();
				int val = (int) s.getValue();
				if (val < lowerMinBound)
					s.setValue(lowerMinBound);
				else if (val > (int) upperBoundInput.getValue() - 1
						&& (!radioButtons[ProblemTypes.SEQUENCE.ordinal()].isSelected()
								&& !radioButtons[ProblemTypes.SIMPLIFY_FRACTION.ordinal()].isSelected()))
					s.setValue((int) upperBoundInput.getValue() - 1);
			}
		});

		upperBoundInput.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				JSpinner s = (JSpinner) e.getSource();
				int val = (int) s.getValue();
				if (val < (int) lowerBoundInput.getValue() + 1
						&& (!radioButtons[ProblemTypes.SEQUENCE.ordinal()].isSelected()
								&& !radioButtons[ProblemTypes.SIMPLIFY_FRACTION.ordinal()].isSelected()))
					s.setValue(s.getNextValue());
//				else if
				else if (val > upperMaxBound)
					s.setValue(s.getPreviousValue());
				else if (val < 2 && (radioButtons[ProblemTypes.SEQUENCE.ordinal()].isSelected()
						|| radioButtons[ProblemTypes.SIMPLIFY_FRACTION.ordinal()].isSelected()))
					s.setValue(s.getNextValue());
			}
		});

		repsInput.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSpinner s = (JSpinner) e.getSource();
				int val = (int) s.getValue();
				if (val < 1)
					s.setValue(s.getNextValue());
				else if (val > 50)
					s.setValue(s.getPreviousValue());
//				s.ena
//				System.out.println(val);
			}
		});
		/*
		 * switch (Settings.getNumType()) { case FRACTION: //
		 * upperBoundInput.setValue(10); fractionAnswerLabel.setEnabled(true);
		 * fractionAnswer.setEnabled(true);
		 * firstLargerLabel.setText("Denominator is Always Larger"); break; case TIME:
		 * lowerBoundLabel.setEnabled(false); upperBoundLabel.setEnabled(false);
		 * lowerBoundInput.setEnabled(false); upperBoundInput.setEnabled(false);
		 * typeInput.setEnabled(false); typeLabel.setEnabled(false);
		 * firstLargerInput.setEnabled(false); firstLargerLabel.setEnabled(false);
		 * break; case SEQUENCE: typeInput.setEnabled(false);
		 * typeLabel.setEnabled(false); firstLargerInput.setEnabled(false);
		 * firstLargerLabel.setEnabled(false); lowerBoundLabel.setText("Counting by");
		 * break; case DATE: lowerBoundLabel.setEnabled(false);
		 * upperBoundLabel.setEnabled(false); lowerBoundInput.setEnabled(false);
		 * upperBoundInput.setEnabled(false); // typeInput.setEnabled(false); //
		 * typeLabel.setEnabled(false); typeInput.removeItem('*');
		 * typeInput.removeItem('÷'); firstLargerInput.setEnabled(false);
		 * firstLargerLabel.setEnabled(false); fractionAnswer.setEnabled(false);
		 * fractionAnswerLabel.setEnabled(false); break; case RECTANGLE: case CIRCLE:
		 * case TRIANGLE: case TRAPEZOID: firstLargerLabel.setText("Area/Perimeter");
		 * lowerMinBound = 0; if (Settings.isFirstLarger())
		 * firstLargerInput.setText("Area"); else firstLargerInput.setText("Perimeter");
		 * typeInput.setEnabled(false); typeLabel.setEnabled(false);
		 * fractionAnswer.setSelected(false); break; case COINS: upperMaxBound = 50;
		 * case CHANGE: // lowerBoundLabel.setText("Min number of coins"); //
		 * upperBoundLabel.setText("Max number of coins"); typeInput.setEnabled(false);
		 * typeLabel.setEnabled(false); firstLargerInput.setEnabled(false);
		 * firstLargerLabel.setEnabled(false); lowerMinBound = 1; // if
		 * (Settings.getNumType() == ProblemTypes.COINS) validate(); break; case
		 * PERCENT: firstLargerInput.setEnabled(false);
		 * firstLargerLabel.setEnabled(false); typeInput.setEnabled(false);
		 * typeLabel.setEnabled(false); break; case SIMPLIFY_FRACTION:
		 * lowerBoundLabel.setText("Highest Multiple"); typeInput.setEnabled(false);
		 * typeLabel.setEnabled(false); firstLargerInput.setEnabled(false);
		 * firstLargerLabel.setEnabled(false); // if (Settings.getNumType() !=
		 * ProblemTypes.SIMPLIFY_FRACTION) // lowerBoundInput.setValue(5); lowerMinBound
		 * = 1; validate(); break; default: // upperBoundInput.setText("10");
		 * lowerMinBound = Integer.MIN_VALUE + 1; upperMaxBound = Integer.MAX_VALUE - 1;
		 * firstLargerInput.setSelected(Settings.isFirstLarger());
		 *
		 * if (Settings.getType() == '÷' || Settings.getType() == 'r') {
		 * fractionAnswerLabel.setEnabled(true); fractionAnswer.setEnabled(true); } else
		 * { fractionAnswerLabel.setEnabled(false); fractionAnswer.setEnabled(false); }
		 * break; }
		 */

		for (JTextField l : labels) {
			l.setBackground(getContentPane().getBackground());
			l.setEditable(false);
			l.setHorizontalAlignment(SwingConstants.RIGHT);
			l.setForeground(Color.white);
			l.setBorder(null);
			l.setFocusable(false);
		}

		for (JRadioButton r : radioButtons) {
			bgNumType.add(r);
			r.setForeground(Color.white);
		}

		/*
		 * switch (Settings.getNumType()) { case Settings.NORM: rNorm.setSelected(true);
		 * break; case Settings.MONEY: rMoney.setSelected(true); break; case
		 * Settings.FRACTION: rFraction.setSelected(true); break; case Settings.COINS:
		 * rCoin.setSelected(true); break; case Settings.TIME: rTime.setSelected(true);
		 * break; case Settings.SEQUENCE: rSeq.setSelected(true); break; case
		 * Settings.SIMPLIFY_FRACTION: rSimpFrac.setSelected(true); break; case
		 * Settings.DATE: rDate.setSelected(true); break; case Settings.RECTANGLE:
		 * rRectangle.setSelected(true); break; case Settings.CIRCLE:
		 * rCircle.setSelected(true); break; case Settings.TRIANGLE:
		 * rTriangle.setSelected(true); break; case Settings.PERCENT:
		 * rPercent.setSelected(true); break; case Settings.TRAPEZOID:
		 * rTrapezoid.setSelected(true); break; case Settings.CHANGE:
		 * rChange.setSelected(true); break; }
		 */

		radioButtons[ProblemTypes.PERCENT.ordinal()].addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					firstLargerInput.setEnabled(false);
					firstLargerLabel.setEnabled(false);
					typeInput.setEnabled(false);
					typeLabel.setEnabled(false);
				} else {
					firstLargerInput.setEnabled(true);
					firstLargerLabel.setEnabled(true);
					typeInput.setEnabled(true);
					typeLabel.setEnabled(true);
				}
			}
		});

		final ArrayList<JRadioButton> SHAPES = new ArrayList<>(Arrays.asList(new JRadioButton[] {
				radioButtons[ProblemTypes.RECTANGLE.ordinal()], radioButtons[ProblemTypes.CIRCLE.ordinal()],
				radioButtons[ProblemTypes.TRIANGLE.ordinal()], radioButtons[ProblemTypes.TRAPEZOID.ordinal()],
				radioButtons[ProblemTypes.HEXAGON.ordinal()] }));

		firstLargerInput.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				for (JRadioButton j : SHAPES) {
					if (j.isSelected()) {
						if (e.getStateChange() == ItemEvent.SELECTED) {
							firstLargerInput.setText("Area");
						} else {
							firstLargerInput.setText("Perimeter");
						}
						break;
					}
				}
			}
		});
		ItemListener shapeListen = new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					firstLargerLabel.setText("Area/Perimeter");
					firstLargerInput.setSelected(!Settings.isFirstLarger());
					firstLargerInput.setSelected(Settings.isFirstLarger());
//					firstLargerInput.getItemListeners()[0].itemStateChanged(e);
					typeInput.setEnabled(false);
					typeLabel.setEnabled(false);
					lowerMinBound = 0;
				} else {
					firstLargerLabel.setText("First Number is Always Larger");
					firstLargerInput.setText("");
					typeInput.setEnabled(true);
					typeLabel.setEnabled(true);
					lowerMinBound = Integer.MIN_VALUE + 1;
					upperMaxBound = Integer.MAX_VALUE - 1;
				}
			}
		};

		typeInput.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED
						&& (e.getItem().equals('÷') || (e.getItem().equals("Random"))
								&& radioButtons[ProblemTypes.NUMBERS.ordinal()].isSelected())) {
					labels[5].setEnabled(true);
					fractionAnswer.setEnabled(true);
				} else if (radioButtons[ProblemTypes.NUMBERS.ordinal()].isSelected()) {
					labels[5].setEnabled(false);
					fractionAnswer.setSelected(false);
					fractionAnswer.setEnabled(false);
				}
			}
		});

		for (JRadioButton j : SHAPES)
			j.addItemListener(shapeListen);

		/*
		 * radioButtons[ProblemTypes.RECTANGLE.ordinal()].addItemListener(shapeListen);
		 * radioButtons[ProblemTypes.CIRCLE.ordinal()].addItemListener(shapeListen);
		 * radioButtons[ProblemTypes.TRIANGLE.ordinal()].addItemListener(shapeListen);
		 * radioButtons[ProblemTypes.TRAPEZOID.ordinal()].addItemListener(shapeListen);
		 * radioButtons[ProblemTypes.HEXAGON.ordinal()].addItemListener(shapeListen);
		 */

		radioButtons[ProblemTypes.PRIME.ordinal()].addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				boolean selected = true;
				if (e.getStateChange() == ItemEvent.SELECTED)
					selected = false;
				firstLargerInput.setSelected(selected);
				firstLargerInput.setEnabled(selected);
				firstLargerLabel.setEnabled(selected);
				typeInput.setEnabled(selected);
				typeLabel.setEnabled(selected);
			}
		});

		radioButtons[ProblemTypes.SIMPLIFY_FRACTION.ordinal()].addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					lowerBoundLabel.setText("Highest Multiple");
					typeInput.setEnabled(false);
					typeLabel.setEnabled(false);
					firstLargerInput.setEnabled(false);
					firstLargerLabel.setEnabled(false);
					if (Settings.getNumType() != ProblemTypes.SIMPLIFY_FRACTION)
						lowerBoundInput.setValue(5);
					lowerMinBound = 1;
					validate();
				} else {
					lowerBoundLabel.setText("Lower Bound");
					typeInput.setEnabled(true);
					typeLabel.setEnabled(true);
					firstLargerInput.setEnabled(true);
					firstLargerLabel.setEnabled(true);
					if (Settings.getNumType() == ProblemTypes.SIMPLIFY_FRACTION)
						lowerBoundInput.setValue(1);
					lowerMinBound = Integer.MIN_VALUE + 1;
					upperMaxBound = Integer.MAX_VALUE - 1;
					if ((int) lowerBoundInput.getValue() > (int) upperBoundInput.getValue())
						lowerBoundInput.setValue((int) upperBoundInput.getValue() - 1);
					validate();
				}
			}
		});

		radioButtons[ProblemTypes.MONEY.ordinal()].addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					typeInput.removeItem('*');
					typeInput.removeItem('÷');
					typeInput.removeItem("Random");
					lowerMinBound = 1;
					if ((int) lowerBoundInput.getValue() < lowerMinBound)
						lowerBoundInput.setValue(lowerMinBound);
					if ((int) upperBoundInput.getValue() > upperMaxBound)
						upperBoundInput.setValue(upperMaxBound);
				} else {
					typeInput.addItem('*');
					typeInput.addItem('÷');
					typeInput.addItem("Random");
					lowerMinBound = Integer.MIN_VALUE + 1;
				}

			}
		});

		if (Settings.getNumType() == ProblemTypes.FRACTION)
			labels[4].setText("Denominator is Always Larger");

		radioButtons[ProblemTypes.FRACTION.ordinal()].addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					fractionAnswerLabel.setEnabled(true);
					fractionAnswer.setEnabled(true);
					firstLargerLabel.setText("Denominator is Always Larger");
//					lowerMinBound = Integer.MIN_VALUE + 1;
//					upperMaxBound = Integer.MAX_VALUE - 1;
				} else {
					fractionAnswerLabel.setEnabled(false);
					fractionAnswer.setEnabled(false);
					firstLargerLabel.setText("First Number is Always Larger");
				}

			}
		});

		radioButtons[ProblemTypes.TIME.ordinal()].addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					lowerBoundLabel.setEnabled(false);
					upperBoundLabel.setEnabled(false);
					lowerBoundInput.setEnabled(false);
					upperBoundInput.setEnabled(false);
//					typeInput.setEnabled(false);
//					typeLabel.setEnabled(false);
					firstLargerInput.setEnabled(false);
					firstLargerLabel.setEnabled(false);
					typeInput.removeItem('*');
					typeInput.removeItem('÷');
					typeInput.removeItem("Random");
					validate();
				} else {

					lowerBoundLabel.setEnabled(true);
					upperBoundLabel.setEnabled(true);
					lowerBoundInput.setEnabled(true);
					upperBoundInput.setEnabled(true);
//					typeInput.setEnabled(true);
//					typeLabel.setEnabled(true);
					firstLargerInput.setEnabled(true);
					firstLargerLabel.setEnabled(true);
					typeInput.addItem('*');
					typeInput.addItem('÷');
					typeInput.addItem("Random");
					if (Settings.getNumType() == ProblemTypes.TIME)
						upperBoundInput.setValue(10);
					validate();
				}
			}
		});
		radioButtons[ProblemTypes.DATE.ordinal()].addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					lowerBoundLabel.setEnabled(false);
					upperBoundLabel.setEnabled(false);
					lowerBoundInput.setEnabled(false);
					upperBoundInput.setEnabled(false);
//					typeInput.setEnabled(false);
//					typeLabel.setEnabled(false);
					typeInput.removeItem('*');
					typeInput.removeItem('÷');
					typeInput.removeItem("Random");
					firstLargerInput.setEnabled(false);
					firstLargerLabel.setEnabled(false);
					fractionAnswer.setEnabled(false);
					fractionAnswerLabel.setEnabled(false);
				} else {
					lowerBoundLabel.setEnabled(true);
					upperBoundLabel.setEnabled(true);
					lowerBoundInput.setEnabled(true);
					upperBoundInput.setEnabled(true);
//					typeInput.setEnabled(true);
//					typeLabel.setEnabled(true);
					typeInput.addItem('*');
					typeInput.addItem('÷');
					typeInput.addItem("Random");
					firstLargerInput.setEnabled(true);
					firstLargerLabel.setEnabled(true);
//					fractionAnswer.setEnabled(true);
//					fractionAnswerLabel.setEnabled(true);
				}
			}
		});
		radioButtons[ProblemTypes.SEQUENCE.ordinal()].addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					typeInput.setEnabled(false);
					typeLabel.setEnabled(false);
					firstLargerInput.setEnabled(false);
					firstLargerLabel.setEnabled(false);
					lowerBoundLabel.setText("Counting by");
				} else {
					typeInput.setEnabled(true);
					typeLabel.setEnabled(true);
					firstLargerInput.setEnabled(true);
					firstLargerLabel.setEnabled(true);
					lowerBoundLabel.setText("Lower Bound");
					if ((int) lowerBoundInput.getValue() > (int) upperBoundInput.getValue())
						lowerBoundInput.setValue((int) upperBoundInput.getValue() - 1);
				}
			}
		});

		ItemListener money = new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
//					lowerBoundLabel.setText("Min number of coins");
//					upperBoundLabel.setText("Max number of coins");
					typeInput.setEnabled(false);
					typeLabel.setEnabled(false);
					firstLargerInput.setEnabled(false);
					firstLargerLabel.setEnabled(false);
					lowerMinBound = 1;
					if (e.getItem().equals(radioButtons[ProblemTypes.COINS.ordinal()]))
						upperMaxBound = 50;
					else
						upperMaxBound = 100;
					if ((int) lowerBoundInput.getValue() < lowerMinBound)
						lowerBoundInput.setValue(lowerMinBound);
					if ((int) upperBoundInput.getValue() > upperMaxBound)
						upperBoundInput.setValue(upperMaxBound);

					validate();
				} else {
//					lowerBoundLabel.setText("Lower Bound");
//					upperBoundLabel.setText("Upper Bound");
					typeInput.setEnabled(true);
					typeLabel.setEnabled(true);
					firstLargerInput.setEnabled(true);
					firstLargerLabel.setEnabled(true);
					lowerMinBound = Integer.MIN_VALUE + 1;
					upperMaxBound = Integer.MAX_VALUE - 1;
					validate();
				}
			}
		};
		radioButtons[ProblemTypes.COINS.ordinal()].addItemListener(money);
		radioButtons[ProblemTypes.CHANGE.ordinal()].addItemListener(money);
//		radioButtons[ProblemTypes.MONEY.ordinal()].addItemListener(money);

		radioButtons[ProblemTypes.NUMBERS.ordinal()].addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if ((typeInput.getSelectedItem().equals('÷') || typeInput.getSelectedItem().equals("Random"))
						&& e.getStateChange() == ItemEvent.SELECTED) {
					labels[5].setEnabled(true);
					fractionAnswer.setEnabled(true);
				} else {
					labels[5].setEnabled(false);
					fractionAnswer.setSelected(false);
					fractionAnswer.setEnabled(false);
				}
			}

		});

		KeyListener keys = new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					try {
						if (getFocusOwner() instanceof JFormattedTextField spin)
							spin.setValue(Integer.parseInt(spin.getText()));
					} catch (NumberFormatException nfe) {
					} finally {
						buttonClicked(enter);
					}
				} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					buttonClicked(exit);
				}
			}
		};

		/*
		 * ((JSpinner.DefaultEditor)
		 * lowerBoundInput.getEditor()).getTextField().setHorizontalAlignment(JTextField
		 * .LEFT); ((JSpinner.DefaultEditor)
		 * lowerBoundInput.getEditor()).getTextField().setEditable(false);
		 *
		 * ((JSpinner.DefaultEditor)
		 * upperBoundInput.getEditor()).getTextField().setHorizontalAlignment(JTextField
		 * .LEFT); ((JSpinner.DefaultEditor)
		 * upperBoundInput.getEditor()).getTextField().setEditable(false);
		 *
		 * ((JSpinner.DefaultEditor)
		 * repsInput.getEditor()).getTextField().setHorizontalAlignment(JTextField.LEFT)
		 * ; ((JSpinner.DefaultEditor)
		 * repsInput.getEditor()).getTextField().setEditable(false);
		 */
		timedQuestion.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				timedQSpeed.setEnabled(timedQuestion.isSelected());
				timedQSpeedLab.setEnabled(timedQuestion.isSelected());
			}
		});

//		lowerBoundInput.setValue(Settings.getLowerBound());
//		upperBoundInput.setValue(Settings.getUpperBound());
//		int lowerVal = Settings.getLowerBound();
//		if ((int) lowerBoundInput.getValue() < lowerMinBound)
//			lowerVal = lowerMinBound;
//		System.out.println(Settings.getLowerBound());

//		int upperVal = Settings.getUpperBound();
//		if ((int) upperBoundInput.getValue() > upperMaxBound)
//			upperVal = upperMaxBound;

		repsInput.setValue(Settings.getqNum());
		for (JComponent j : comps) {
			j.addKeyListener(keys);
		}
		for (JRadioButton j : radioButtons) {
			j.addKeyListener(keys);
		}
//		((JSpinner.DefaultEditor) repsInput.getEditor()).getTextField().setFocusable(false);
//		repsInput.set
//		((DefaultEditor) repsInput.getEditor()).getTextField().setEditable(false);
		for (JSpinner j : spinners) {
			((JSpinner.DefaultEditor) j.getEditor()).getTextField().setHorizontalAlignment(SwingConstants.LEFT);
			((JSpinner.DefaultEditor) j.getEditor()).getTextField().addKeyListener(keys);
		}
		radioButtons[Settings.getNumType().ordinal()].setSelected(true);

		timedQuestion.setSelected(Settings.isTimed());
		timedQuestion.addKeyListener(keys);
		timedQSpeed.setEnabled(timedQuestion.isSelected());
		timedQSpeedLab.setEnabled(timedQuestion.isSelected());
		timedQSpeed.addKeyListener(keys);
		enter.addKeyListener(keys);
		exit.addKeyListener(keys);
		reset.addKeyListener(keys);

		// has to be after setting selected button otherwise lower bound is set to -1

	}

	private void getQSpeedValues(int score) {
		final double SPACE_APPART = 30.0;// the amount between each mark
		final int ENDING_MOD = 5;// the amount the final value with be evenly divisible by

		timedQSpeed.setMinimum((int) SPACE_APPART);
		for (int i = 90; i <= 120; i++) {// the maximum value
			if (i % ENDING_MOD == 0 && ((i - (int) SPACE_APPART) / SPACE_APPART) % 1 == 0) {//
				timedQSpeed.setMaximum(i);
				break;
			}
		}
	}

	private void setButtons(int score) {
		final int NUM_ROWS = 2;
		int bL = radioButtons.length;// Divide row by col, only need to find one number
		for (int k = 0; k < NUM_ROWS; k++) {
			for (int i = 0; i < (bL + 1) / NUM_ROWS; i++) {
				int pos = (i + (k * (bL + 1) / NUM_ROWS));
				if (pos >= bL)
					break;
				radioButtons[pos] = p3.addRadioButton(StringUtils.capitalize(ProblemTypes.values()[pos].toString()),
						i + 1, k + 1, 1, 1);
				int scale = ProblemTypes.values()[pos].scale;
				if (scale > ((int) (score / (scale / 1.5)) + 30) / 30) {
					radioButtons[pos].setEnabled(false);
				}
			}
		}
	}
}