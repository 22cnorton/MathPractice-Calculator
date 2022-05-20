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

	private JButton[] bttns = { config, start, exit, calcSelectBttn };

	private JTextField scoreField = p1.addTextField("Total Score: 0", 1, 1, 1, 1);

	private int runningScore = 0;

	private JComboBox<String> calcSelect = p2.addComboBox(GridBagConstraints.EAST, 1, 1, 1, 1);
	private Color defaultColor;

	private Map<ProblemTypes, Class<?>> calcs = new HashMap<>();
	{
		for (ProblemTypes p : ProblemTypes.values()) {
			try {
				calcs.put(p, Class.forName("CalculatorWindows."
						+ StringUtils.properCase(p.toString() + " calc").replaceAll(" ", "")));
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

	private int getScore() {
		return runningScore;
	}

	public MathGUI() {// constructor
		p1.setOpaque(false);
		p2.setOpaque(false);
		p3.setOpaque(false);
		Settings.readFiles();

		for (int i = 0; i < ProblemTypes.values().length; i++)
			calcSelect.addItem(StringUtils.properCase(ProblemTypes.values()[i].toString()));
		calcSelect.setSelectedIndex(Settings.getNumType().ordinal());

		scoreField.setBackground(Color.black);
		scoreField.setEditable(false);
		scoreField.setBorder(null);
		scoreField.setHorizontalAlignment(SwingConstants.RIGHT);
		scoreField.setForeground(Color.white);
		scoreField.setFocusable(false);
		scoreField.setText("Total Score: " + getScore());
		defaultColor = start.getBackground();
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
		KeyAdapter key = new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {// Shift + backslash activates dev_mode
				final int SCORE_CHANGE = 15;
				final int C = e.getKeyCode(), M = e.getModifiersEx();
				if (C == KeyEvent.VK_BACK_SLASH && e.getModifiersEx() == InputEvent.SHIFT_DOWN_MASK) {
					Settings.setDevMode(!Settings.isDevMode());
					Color c = (Settings.isDevMode()) ? Color.GRAY : Color.BLACK;
					frm.getContentPane().setBackground(c);
					scoreField.setBackground(c);

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
		calcSelect.addFocusListener(foc);
		calcSelect.addKeyListener(key);
		for (JButton j : bttns) {
			j.addKeyListener(key);
			j.addFocusListener(foc);
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
				((CalculatorWindow) calcs.get(ProblemTypes.values()[calcSelect.getSelectedIndex()])
						.getConstructor(JFrame.class).newInstance(this)).setVisible(true);

			} catch (NullPointerException | InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException npe) {
				messageBox("That calculator does not exist yet");
			}
		}
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
		}
		myReader.close();
		return data;
	}

	public static void main(String[] args) {// main method
		frm.getContentPane().setBackground(Color.black);
		frm.setTitle("Math Training");
		frm.setSize(600, 500);
		frm.setLocationRelativeTo(null);
		frm.setVisible(true);

	}
}