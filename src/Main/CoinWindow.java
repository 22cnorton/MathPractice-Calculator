package Main;

import java.awt.Color;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import BreezySwing.GBPanel;
import Questions.CoinQuestion;
import Questions.QuestionGenerator;

public class CoinWindow extends QuestionWindow {
	private static final long serialVersionUID = 4113164551804514669L;

	private GBPanel p1 = addPanel(2, 1, 1, 1), p2 = addPanel(3, 1, 1, 1), p3 = addPanel(4, 1, 1, 1);
	private ArrayList<JLabel> images = new ArrayList<JLabel>();

	private JTextField question = p2.addTextField("How Many cents?", 1, 1, 1, 1),
			answer = p2.addTextField("", 1, 2, 1, 1), centsLabel = p2.addTextField("¢", 1, 3, 1, 1),
			scoreField = addTextField("Score: 0", 1, 1, 1, 1);

	private void drawImage() {
		int sz = ((CoinQuestion) getQ()).size();
		for (int i = 0; i < sz; i++) {// adds the image for each stored coin
			images.get(i).setIcon(new ImageIcon(
					Toolkit.getDefaultToolkit().createImage(((CoinQuestion) getQ()).get(i).getImageAddress())));
			images.get(i).repaint();
//			images.get(i).
		}
		for (int i = sz; i < Settings.getUpperBound(); i++) {// sets remaining images to be nukk
			images.get(i).setIcon(null);
			images.get(i).repaint();
		}
		validate();
	}

	/**
	 * constructor
	 *
	 * @param arg0 JFrame
	 */
	public CoinWindow(JFrame arg0) {
		super(arg0);
		setArg0(arg0);
		setTitle("Coins");
		getContentPane().setBackground(Color.darkGray);
		setSize(1000, Settings.getUpperBound() * 15 + 300);
		setLocationRelativeTo(arg0);
//		this.s = s;
		addButtons(p3);
		question.setBackground(getContentPane().getBackground());
		question.setEditable(false);
		question.setHorizontalAlignment(SwingConstants.RIGHT);
		question.setForeground(Color.white);
		question.setBorder(null);
		question.setFocusable(false);

		centsLabel.setBackground(getContentPane().getBackground());
		centsLabel.setEditable(false);
		centsLabel.setHorizontalAlignment(SwingConstants.LEFT);
		centsLabel.setForeground(Color.white);
		centsLabel.setBorder(null);
		centsLabel.setFocusable(false);

		scoreField.setBackground(getContentPane().getBackground());
		scoreField.setEditable(false);
		scoreField.setHorizontalAlignment(SwingConstants.RIGHT);
		scoreField.setForeground(Color.white);
		scoreField.setBorder(null);
		scoreField.setFocusable(false);

		p1.setOpaque(false);
		p2.setOpaque(false);
		p3.setOpaque(false);
		answer.addKeyListener(getKey());

//		total = 0;
		setReps(Settings.getqNum());

		setQ(QuestionGenerator.getCoins());
		if (Settings.isDevMode())
			System.out.println(qGetSolution());

		int j = 1;
		for (int i = 0; i < Settings.getUpperBound(); i++) {// adds a label for for the max number of possible coins
			if (i % 8 == 0)
				j++;
			images.add(p1.addLabel("", j, (i % 8) + 1, 1, 1));
			images.get(i).setFocusable(false);
		}
		drawImage();
	}

	Object parse() {
		return Integer.parseInt(answer.getText());
	}

	void update(boolean decReps) {
		answer.setText("");
		if (getReps() > 0) {// if more questions should be asked
			setQ(QuestionGenerator.getCoins());
			drawImage();
		}
		setCount(0);
		scoreField.setText("Score: " + getScore());
		answer.grabFocus();
		if (Settings.isDevMode())
			System.out.println(qGetSolution());
		if (decReps)
			decReps();
	}
}