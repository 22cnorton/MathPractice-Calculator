package Main;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import BreezySwing.GBFrame;
import BreezySwing.GBPanel;
import CalculatorWindows.CalculatorWindow;
import Utils.StringUtils;

public class MathGUI extends GBFrame {
	private static final long serialVersionUID = 5604156169564285160L;

	static JFrame frm = new MathGUI();

	private GBPanel p1 = addPanel(1, 1, 1, 1), p2 = addPanel(2, 1, 1, 1), p3 = addPanel(3, 1, 1, 1);

	private JButton config = p1.addButton("Settings", 3, 1, 1, 1), start = p1.addButton("Start", 2, 1, 1, 1),
			exit = p3.addButton("Exit", 1, 1, 1, 1),
			calcSelectBttn = p2.addButton("Open Calculator", GridBagConstraints.WEST, 1, 2, 1, 1);

	/*
	 * , coinculator = p2.addButton("Coin Calculator", 1, 1, 1, 1), dateulator =
	 * p2.addButton("Date Calculator", 2, 1, 1, 1), makeChange =
	 * p2.addButton("Change Calculator", 3, 1, 1, 1), seqCalc =
	 * p2.addButton("Sequence Calculator", 4, 1, 1, 1)
	 */

	private JButton[] bttns = { config, start, exit, calcSelectBttn };
	/* , coinculator, dateulator, makeChange, seqCalc */

	private JTextField scoreField = p1.addTextField("Total Score: 0", 1, 1, 1, 1);

	private int runningScore = 0;

	private JComboBox<String> calcSelect = p2.addComboBox(GridBagConstraints.EAST, 1, 1, 1, 1);
//	private JSeparator sep=p3.addSeparator(true, 4, 1, 1, 1);
	private Color defaultColor;

//private JSlider slid=p3.addSlider(1, 10, 3, 4, 1, 1, 1);
//	private JFormattedTextField form=p3.addMoneyTextField(100, 4, 1, 1, 1);
	/*
	 * private JTable jt = p1.addJTable(new String[][] { { "Name", "Age" }, { "Joe",
	 * "34" }, { "Steve", "56" } }, 4, 1, 5, 5);
	 */
//final	Class[] CALC_CLASSES = { NumbersCalc.class, MoneyCalc.class,SequenceCalc.class };

	private Map<ProblemTypes, Class<?>> calcs = new HashMap<>();
	{
		/*
		 * gets all classes that follow the naming scheme for calcs and adds them to the
		 * arrayF
		 */
		for (ProblemTypes p : ProblemTypes.values()) {
			try {
				calcs.put(p,
						Class.forName("CalculatorWindows." + StringUtils.capitalize(p + "_calc").replaceAll(" ", "")));
			} catch (ClassNotFoundException e) {
				try {
					final String ERROR = e.getLocalizedMessage();

					File myObj = new File("error.txt");
					final String DATA = readFile(myObj) + LocalDateTime.now().toString() + " " + ERROR + "\n";
					writeFile(DATA, myObj);
					System.out.println("Check Error log");
				} catch (IOException ioe) {
					System.out.println("An error occurred.");
					ioe.printStackTrace();
				}
			}

		}
	}

	private File scoreFile = new File("score.ser");

	/*
	 * private JSpinner spin = p3.addSpinner(3, 1, 1, 1); private JButton but =
	 * p3.addButton("Test", 4, 1, 1, 1);
	 *
	 * private JLabel label=p3.addLabel("Test", 4, 2, 1, 1); JSpinner jspin
	 * =p3.addDateSpinner(4, 1, 1, 1); private static Settings s; private FileReader
	 * reader; private Properties p = System.getProperties(); private Set
	 * set=p.entrySet();
	 *
	 * private Properties props = new Properties();
	 */
	private int getScore() {
		return runningScore;
	}

//	private static final String TASKBAR_IMAGE = "src/icon/piIcon.png";

	public MathGUI() {// constructor
		p1.setOpaque(false);
		p2.setOpaque(false);
		p3.setOpaque(false);
		Settings.readFiles();

//		Image taskbarIcon = new 

//		System.out.println(TriangleCalc.class.getSuperclass());
//		System.out.println(ChangeMaker.class.getCanonicalName());
//		try {
//			System.out.println(Class.forName("Main.ChangeMaker"));
//		} catch (ClassNotFoundException e1) {
//			e1.printStackTrace();
//		}
//		CalculatorWindows;
//System.out.println(CalculatorWindow.class);	
//		/**
//		 * finds values for the timed question slider that are marked nicely
//		 */
//		final double SPACE_APPART = 7.0;// the amount between each mark
//		final int ENDING_MOD = 5;// the amount the final value with be evenly divisible by
//		for (int i = 115; i <= 250; i++) {// the maximum value
//			for (int j = 1; j < 10; j++) {// the minimum value
//				if (i % ENDING_MOD == 0 && ((i - j) / SPACE_APPART) % 1 == 0) {//
//					System.out.println(i + ", " + j);
//				}
//			}
//		}

//		ArrayList<Field> fields = new ArrayList<>(Arrays.asList(NumberQuestion.class.getDeclaredFields()));
//
//
//		for (Field f : fields) {
//			java.lang.reflect.Type genericType = f.getGenericType();
//			if (((Class<?>) genericType).isPrimitive())
//				System.out.println(f.getName());
//		}

//		AutoCalc test = new AutoCalc(NumberQuestion.class, this);

//		System.out.println(Arrays.toString(fields));

//		System.out.println(Arrays.toString(NumberQuestion.class.getSuperclass().getDeclaredFields()));

//		String name = ProblemTypes.CHANGE.toString();
//		System.out.println(name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase());
//System.out.println(Settings.getProblemType());
		for (int i = 0; i < ProblemTypes.values().length; i++)
			calcSelect.addItem(StringUtils.capitalize(ProblemTypes.values()[i].toString()));
//			calcSelect.addItem(Settings.getType(i));
		calcSelect.setSelectedIndex(Settings.getNumType().ordinal());

//		sep.setForeground(Color.red);
//		sep.setBackground(Color.red);

//		slid.setPaintTrack(true);
//		slid.setPaintTicks(true);
////		slid.setUI(null);
////		 slid.setOrientation(JLabel.CENTER);
//        slid.setPaintLabels(true);
//		slid.setMajorTickSpacing(1);
////		slid.setMinorTickSpacing(1);
//		slid.setSnapToTicks(true);
//		slid.setForeground(Color.pink);
//		slid.setBackground(Color.red);
//		ArrayList<Coin> alc = new ArrayList<Coin>(Arrays.asList(new Coin[] { new Penny(),new Dime() }));
//		CoinQuestion qC = new CoinQuestion(alc);
//		System.out.println(qC);
//		System.out.println(qC.calcSolution());

		/*
		 * Component[] comp=p2.getComponents(); String strn=""; for(Component c:comp) {
		 * strn+=c.toString()+"\n"; }
		 *
		 * System.out.println(strn);
		 */
//		s = new Settings(1, 10, 10, 0, '+', true, false);
		scoreField.setBackground(Color.black);
		scoreField.setEditable(false);
		scoreField.setBorder(null);
		scoreField.setHorizontalAlignment(SwingConstants.RIGHT);
		scoreField.setForeground(Color.white);
		scoreField.setFocusable(false);
		scoreField.setText("Total Score: " + getScore());
		defaultColor = start.getBackground();
		/*
		 * Border b = start.getBorder(); int sz = 3; LineBorder border1 = new
		 * LineBorder(Color.lightGray, sz); EmptyBorder border2 = new EmptyBorder(3 -
		 * sz, 22 - sz, 5 - sz, 22 - sz); Border newBorder =
		 * BorderFactory.createCompoundBorder(border1, border2); FocusListener foc = new
		 * FocusListener() {
		 *
		 * @Override public void focusLost(FocusEvent e) { ((JButton)
		 * e.getComponent()).setBorder(b); ((JButton)
		 * e.getComponent()).setContentAreaFilled(false); ((JButton)
		 * e.getComponent()).setBackground(defaultColor); }
		 *
		 * @Override public void focusGained(FocusEvent e) { ((JButton)
		 * e.getComponent()).setBorder(newBorder); ((JButton)
		 * e.getComponent()).setContentAreaFilled(true); // ((JButton)
		 * e.getComponent()).conten } };
		 */
		FocusListener foc = new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				e.getComponent().setForeground(Color.black);
//				e.getComponent().setBackground(Color.white);
			}

			@Override
			public void focusGained(FocusEvent e) {
				e.getComponent().setForeground(Color.blue);
//				e.getComponent().setBackground(Color.black);
			}
		};
//		Settings.readFiles();
		KeyAdapter key = new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {// Shift + backslash activates dev_mode
				final int SCORE_CHANGE = 15;
				final int C = e.getKeyCode(), M = e.getModifiersEx();
//				System.out.println(M);
				if (C == KeyEvent.VK_BACK_SLASH && e.getModifiersEx() == InputEvent.SHIFT_DOWN_MASK) {
					Settings.setDevMode(!Settings.isDevMode());
					Color c = (Settings.isDevMode()) ? Color.GRAY : Color.BLACK;
					/*
					 * Timer t = new Timer(2000, new ActionListener() {
					 *
					 * @Override public void actionPerformed(ActionEvent e) {
					 * getContentPane().setBackground(Color.black);
					 * scoreField.setBackground(Color.black); } }); t.setRepeats(false); t.start();
					 */
					frm.getContentPane().setBackground(c);
					scoreField.setBackground(c);
//					 System.out.println(Settings.isDevMode());

				} else if (C == KeyEvent.VK_ESCAPE)
					buttonClicked(exit);
				else if (getFocusOwner() instanceof JButton && C == KeyEvent.VK_ENTER) {
					buttonClicked((JButton) getFocusOwner());
				} else if (Settings.isDevMode()) {
					if ((C == KeyEvent.VK_EQUALS || C == KeyEvent.VK_MINUS)) {
						int score = SCORE_CHANGE;
						if (C == KeyEvent.VK_MINUS)
							score *= -1;
						switch (M) {
						case InputEvent.SHIFT_DOWN_MASK:
							score *= 4;
							break;
						case InputEvent.META_DOWN_MASK:
						case InputEvent.ALT_DOWN_MASK:
							score /= SCORE_CHANGE;
							break;
						}

						writeScore(score);
					} else if (C == KeyEvent.VK_E) {
						File myObj = new File("error.txt");
						try {
							switch (M) {
							case InputEvent.SHIFT_DOWN_MASK:
								messageBox(readFile(myObj), 500, 250);
								break;
							case InputEvent.ALT_DOWN_MASK:
//						System.out.println('t');
								writeFile("", myObj);
								break;
							}
						} catch (IOException e1) {
							e1.printStackTrace();
						} catch (StringIndexOutOfBoundsException siob) {
							messageBox("There are no errors");
						}
					}
				}
			}
		};
		/*
		 * LineBorder border3 = new LineBorder(Color.lightGray, 3); // EmptyBorder
		 * border4 = new EmptyBorder(0,0,0,0); // Border newBorder2 =
		 * BorderFactory.createCompoundBorder(border3, border4);
		 * calcSelect.addFocusListener(new FocusListener() {
		 *
		 * @Override public void focusLost(FocusEvent e) { JComboBox<?> j =
		 * ((JComboBox<?>) e.getComponent()); j.setBorder(b); //
		 * e.getComponent().setBackground(Color.gray);
		 *
		 * }
		 *
		 * @Override public void focusGained(FocusEvent e) { JComboBox<?> j =
		 * ((JComboBox<?>) e.getComponent()); j.setBorder(border3);
		 * j.setForeground(Color.red);
		 *
		 * } });
		 */
		calcSelect.addFocusListener(foc);
		calcSelect.addKeyListener(key);
		for (JButton j : bttns) {
			j.addKeyListener(key);
			j.addFocusListener(foc);
//			j.setBackground(Color.white);
//			j.setBorderPainted(false);
//			j.setFocusPainted(false);
			j.setContentAreaFilled(false);
			j.setBackground(defaultColor);
		}
		readScore();
	}

	private void writeScore(int score) {
		runningScore += score;
		scoreField.setText("Total Score: " + getScore());
		try {

			scoreFile.createNewFile();
			FileOutputStream fileOut = new FileOutputStream(scoreFile);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(runningScore);
			out.close();
			fileOut.close();
//		System.out.printf("Serialized data is saved in /tmp/score.ser");
		} catch (IOException i) {
			i.printStackTrace();
		}
	}

	private void readScore() {
		int score = 0;
		try {
			if (!scoreFile.createNewFile()) {
				FileInputStream fileIn = new FileInputStream(scoreFile);
				ObjectInputStream in = new ObjectInputStream(fileIn);
				score = (int) in.readObject();
				in.close();
				fileIn.close();
			} // runningScore=500;
			runningScore = score;
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		scoreField.setText("Total Score: " + getScore());
	}

	@Override
	public void buttonClicked(JButton buttonObj) {// button clicked method
		if (buttonObj == start) {// start button
			QuestionWindow q;
//			ProblemTypes t=ProblemTypes.values()[Settings.getNumType()];
			switch (Settings.getNumType()) {
			case COINS: // opens window for coin problems
				q = new CoinWindow(this);
				break;
			case CHANGE:
				q = new ChangeQuestionWindow(this);
				break;
			default:// opens window for all other questions
				q = new MainQuestionWindow(this);
			}
			q.setVisible(true);
			if (q.isComplete()) {
//				runningScore += q.getScore();
				writeScore(q.getScore());
			}
		} else if (buttonObj == config) {// opens the setting window
			SettingsWindow sW = new SettingsWindow(this, runningScore);
			sW.setVisible(true);
			Settings.writeFiles();
			readScore();
			calcSelect.setSelectedIndex(Settings.getNumType().ordinal());
		} else if (buttonObj == exit) {// closes the program
			System.exit(0);
		} else if (buttonObj == calcSelectBttn) {
			try {
				/* final CalculatorWindow C = */
				((CalculatorWindow) calcs.get(ProblemTypes.values()[calcSelect.getSelectedIndex()])
						.getConstructor(JFrame.class).newInstance(this)).setVisible(true);
				/* ; C */
				/*
				 * switch (ProblemTypes.values()[calcSelect.getSelectedIndex()]) { case NUMBERS:
				 * c = new NumberCalc(this); break; case MONEY: c = new MoneyCalc(this); break;
				 * case FRACTION: c = new FractionCalc(this); break; case COINS: c = new
				 * Coinculator(this); break; case TIME: c = new TimeCalc(this); break; case
				 * SEQUENCE: c = new SequenceCalculator(this); break; case SIMPLIFY_FRACTION: c
				 * = new SimplifyFractionCalc(this); break; case DATE: c = new Dateulator(this);
				 * break; case RECTANGLE: c = new RectangleCalc(this); break; case CIRCLE: c =
				 * new CircleCal(this); break; case TRIANGLE: c = new TriangleCalc(this); break;
				 * case TRAPEZOID: c = new TrapezoidCalc(this); break; case PERCENT: c = new
				 * PercentCalc(this); break; case CHANGE: c = new ChangeCalcWindow(this); break;
				 * case PRIME: c = new PrimeCalc(this); break; case HEXAGON: c = new
				 * HexagonCalc(this); break; default: c = null; }
				 */

			} catch (NullPointerException | InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException npe) {
				messageBox("That calculator does not exist yet");
			}
		}
		/*
		 * } else if (buttonObj == coinculator) {// opens the coin calculator
		 * Coinculator c = new Coinculator(this); c.setVisible(true); } else if
		 * (buttonObj == dateulator) {// opens the date calculator Dateulator d = new
		 * Dateulator(this); d.setVisible(true); } else if (buttonObj == makeChange) {
		 * ChangeCalcWindow c = new ChangeCalcWindow(this); c.setVisible(true); } else
		 * if (buttonObj == seqCalc) { CalculatorWindow c = new
		 * SequenceCalculator(this); c.setVisible(true); }
		 */
		/*
		 * else { spin.setValue(spin.getNextValue()); }
		 */
	}

	private void writeFile(final String DATA, File file) throws IOException {
		file.createNewFile();
		FileWriter writer = new FileWriter(file);
		writer.write(DATA);
		writer.close();
	}

	private String readFile(File myObj) throws IOException {
		Scanner myReader = new Scanner(myObj);

		String data = "";
		while (myReader.hasNextLine()) {
			data += myReader.nextLine() + "\n";
//					        System.out.println(data);
		}
		myReader.close();
		return data;
	}

	/*
	 * private void readFiles() {
	 *
	 * }
	 */

	public static void main(String[] args) {// main method
		frm.getContentPane().setBackground(Color.black);
		frm.setTitle("Math Training");
		frm.setSize(600, 500);
		frm.setLocationRelativeTo(null);
//		java.net.URL resource = frm.getClass().getClassLoader().getResource(TASKBAR_IMAGE);
//		Image icon = Toolkit.getDefaultToolkit().getImage(resource);
//		frm.setIconImage(icon);
		frm.setVisible(true);

	}
}