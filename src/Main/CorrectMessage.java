package Main;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.Timer;

import BreezySwing.GBDialog;
import BreezySwing.GBPanel;

public class CorrectMessage extends GBDialog {
	private static final long serialVersionUID = 1911581207023834388L;

	private GBPanel p1 = addPanel(1, 1, 1, 1);

	private JTextArea text = p1.addTextArea("", 1, 1, 1, 1);

	private JButton bttn = p1.addButton("OK", 2, 1, 1, 1), hold = p1.addButton("Pause", 3, 1, 1, 1);
	/*
	 * ActionListener aL = new ActionListener() {
	 * 
	 * @Override public void actionPerformed(ActionEvent e) { dispose(); } };
	 */
	Timer t = new Timer(2000, new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			dispose();
		}
	});
	{
		t.setRepeats(false);
	}

	/*
	 * public void run() {
	 * 
	 * long l = Long.MAX_VALUE; for (int i = 1; i <= 30000000; i++) { //
	 * System.out.println(i); l/=i; }
	 * 
	 * try { Thread.sleep(750); } catch (InterruptedException e) {
	 * e.printStackTrace(); } finally {
	 * 
	 * dispose(); } }
	 */
	@Override
	public void buttonClicked(JButton buttonObj) {
		if (buttonObj == bttn)
			dispose();
		if (buttonObj == hold) {
			if (t.isRunning()) {
				t.stop();
			} else {
				t.start();
			}
		}
	}

	public CorrectMessage(JFrame arg0, Component c, int time, Color col, String msg) {
		this(arg0, c, 250, 150, time, col, msg);
	}

	public CorrectMessage(JFrame arg0, Component c, int width, int height, int time, Color col, String msg) {
		super(arg0);
		setTitle("Correct");
		getContentPane().setBackground(col);
		setSize(width, height);
		setLocationRelativeTo(c);
		double y = (299 * col.getRed() + 587 * col.getGreen() + 114 * col.getBlue()) / 1000;
//		System.out.println(y >= 128 ? Color.black : Color.white);
		t.setInitialDelay(time / 2 * Settings.getSlowTxt());

		Font boldFont = new Font(text.getFont().getName(), y >= 128 ? Font.PLAIN : Font.BOLD, text.getFont().getSize());
		text.setText(msg);
		text.setEditable(false);
//		text.setHorizontalAlignment(JLabel.CENTER);
		text.setBackground(getContentPane().getBackground());
//		if(col.get)
		text.setForeground(y >= 128 ? Color.black : Color.white);
//		text.setBorder(BorderFactory.createLineBorder(col));
//		text.setOpaque(false);
		text.setFocusable(false);
		text.setFont(boldFont);

		bttn.setFocusable(false);
		hold.setFocusable(false);

		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				int c = e.getKeyCode();
				if (c == KeyEvent.VK_ESCAPE || c == KeyEvent.VK_ENTER)
					buttonClicked(bttn);
				else if (c == KeyEvent.VK_SPACE)
					buttonClicked(hold);

			}
		});

		t.start();

//		addWindowFocusListener(new WindowFocusListener() {
//
//			@Override
//			public void windowLostFocus(WindowEvent e) {
//			}
//
//			@Override
//			public void windowGainedFocus(WindowEvent e) {
//				try {
//					Thread.sleep(time);
//				} catch (InterruptedException ie) {
//					ie.printStackTrace();
//				}
//				dispose();
//			}
//		});

		p1.setOpaque(false);
		setVisible(true);
//		run();
//		run();

		/*
		 * try { Thread.sleep(1000); } catch (InterruptedException e) {
		 * e.printStackTrace(); }
		 */
	}
}