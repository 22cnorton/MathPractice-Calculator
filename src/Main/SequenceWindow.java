package Main;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import BreezySwing.GBDialog;
import BreezySwing.GBPanel;
import Questions.SequenceQuestion;

//originally used just for sequence questions. sequence questions was integrated into main question window

@Deprecated
public class SequenceWindow extends GBDialog {
	private static final long serialVersionUID = 3522462216758069601L;

	private JFrame arg0;

	private GBPanel p1 = addPanel(2, 1, 1, 1), p2 = addPanel(3, 1, 1, 1), p3 = addPanel(1, 1, 1, 1);

	private JTextField label = p1.addTextField("Complete the Sequence", 1, 1, 1, 1),
			questionField = p1.addTextField("", 2, 1, 1, 1);

	private JTextField scoreField = p3.addTextField("Score: 0", 1, 1, 1, 1);

	private JTextField answerField = p1.addTextField("", 3, 1, 1, 1);

	@SuppressWarnings("unused")
	private JButton enter = p2.addButton("Enter", 1, 1, 1, 1), exit = p2.addButton("Exit", 2, 1, 1, 1);

//	private Settings s;

	private SequenceQuestion q;

	private Random r = new Random();

	private int count = 0, score = 0, reps;

	public int getScore() {
		return score;
	}

	@Override
	public void buttonClicked(JButton buttonObj) {
		if (buttonObj == enter) {
			if (Integer.parseInt(answerField.getText()) == q.getAnswer()) {
				score += (3 - count);
				scoreField.setText("Score: " + score);
				messageBox("Correct!");
				count = 0;
				answerField.setText("");
				int u = r.nextInt(Settings.getUpperBound()) + 1;

				q = new SequenceQuestion(u, r.nextInt(8) + 3, Settings.getLowerBound());
				questionField.setText(q.getQuestion());
				reps--;
			} else if (count < 2) {
				count++;
				messageBox("Try again\nYou have " + (3 - count) + " tries remaining");
			} else {
				messageBox("Good try\nThe solution is " + q.getAnswer());
				count = 0;
				answerField.setText("");
				int u = r.nextInt(Settings.getUpperBound()) + 1;

				q = new SequenceQuestion(u, r.nextInt(8) + 2, Settings.getLowerBound());
				questionField.setText(q.getQuestion());
				reps--;
			}
			if (reps < 1) {
				messageBox("You finished!\nYour final score was " + score + "/" + (Settings.getqNum() * 3));
				dispose();
			}
			answerField.grabFocus();
		} else {
			ConfirmationDialog c = new ConfirmationDialog(arg0, this,"Are You Sure You Want to Quit?");
			c.setVisible(true);
			if (c.getDlgCloseIndicator().equals("Yes"))
				dispose();
		}
	}

	public SequenceWindow(JFrame arg0) {
		super(arg0);
		this.arg0 = arg0;
//		this.s =s;
		setTitle("Complete The Sequence");
		getContentPane().setBackground(Color.darkGray);
		setSize(500, 350);
		setLocationRelativeTo(arg0);

		int u = r.nextInt(Settings.getUpperBound()) + 1;

		q = new SequenceQuestion(u, r.nextInt(10) + 3, Settings.getLowerBound());

		p1.setOpaque(false);
		p2.setOpaque(false);
		p3.setOpaque(false);

		label.setBackground(getContentPane().getBackground());
		label.setEditable(false);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setForeground(Color.white);

		questionField.setBackground(getContentPane().getBackground());
		questionField.setEditable(false);
		questionField.setHorizontalAlignment(SwingConstants.CENTER);
		questionField.setForeground(Color.white);
		questionField.setText(q.getQuestion());

		answerField.setHorizontalAlignment(SwingConstants.CENTER);

		scoreField.setBackground(getContentPane().getBackground());
		scoreField.setEditable(false);
		scoreField.setHorizontalAlignment(SwingConstants.RIGHT);
		scoreField.setForeground(Color.white);

		reps = Settings.getqNum();

		answerField.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				if (e.getKeyChar() == '\n') {
					buttonClicked(enter);
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
	}
}