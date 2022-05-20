package CalculatorWindows;

import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.GrayFilter;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import BreezySwing.GBDialog;
import BreezySwing.GBPanel;

public class ChangeCalcAnswerWindow extends GBDialog {

	private static final long serialVersionUID = 5428914031317179255L;

	private GBPanel p1 = addPanel(1, 1, 1, 1), p2 = addPanel(2, 1, 1, 1);

	private JTextField[] nums, names;
	private JLabel[] pics;
	private Image[] imgs;
	private JSeparator[] seps;

	private JButton ok = p2.addButton("OK", 1, 1, 1, 1);

	public void buttonClicked(JButton buttonObj) {
		dispose();
	}

	public ChangeCalcAnswerWindow(JFrame arg0, Component c, String[][] str) throws IOException {
		super(arg0);
//		this.str = str;

		setTitle("Answer");
		getContentPane().setBackground(Color.darkGray);
		setSize(400, str.length * 70 + 50);
		setLocationRelativeTo(c);

		p1.setOpaque(false);
		p2.setBackground(Color.gray);

		nums = new JTextField[str.length];
		names = new JTextField[str.length];
		pics = new JLabel[str.length];
		imgs = new Image[str.length];
		seps = new JSeparator[str.length - 1];

		for (int i = 0; i < str.length; i++) {
			nums[i] = p1.addTextField(str[i][0], (i + 1) * 2 - 1, 1, 1, 1);
			names[i] = p1.addTextField(str[i][1], (i + 1) * 2 - 1, 2, 1, 1);
			if (i < seps.length)
				seps[i] = p1.addSeparator(true, (i + 1) * 2, 1, 3, 1);
//			pics[i] = p1.addLabel("", i + 1, 3, 1, 1);
			File f = new File(str[i][2]);
			Image img = ImageIO.read(f);
			Image resizedImage = img.getScaledInstance(img.getWidth(null) / 2, img.getHeight(null) / 2,
					Image.SCALE_AREA_AVERAGING);
			imgs[i] = resizedImage;
		}
		for (JTextField j : nums) {
			j.setBackground(getContentPane().getBackground());
			j.setEditable(false);
			j.setHorizontalAlignment(SwingConstants.RIGHT);
			j.setForeground(Color.white);
			j.setFocusable(false);
		}
		for (JTextField j : names) {
			j.setBackground(getContentPane().getBackground());
			j.setEditable(false);
			j.setHorizontalAlignment(SwingConstants.LEFT);
			j.setForeground(Color.white);
			j.setFocusable(false);
		}
		Border b = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray);
		Border b1 = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.white);
		for (int i = 0; i < str.length; i++) {
			Image img;
			if (Integer.parseInt(str[i][0]) == 0) {
				names[i].setBorder(b);
				nums[i].setBorder(b);
				names[i].setForeground(Color.gray);
				nums[i].setForeground(Color.gray);

				img = GrayFilter.createDisabledImage(imgs[i]);

			} else {
				names[i].setBorder(b1);
				nums[i].setBorder(b1);
				names[i].setBackground(Color.black);
				nums[i].setBackground(Color.black);
				img = imgs[i];
			}
			if (i < seps.length) {
				seps[i].setBackground(Color.lightGray);
				seps[i].setForeground(getContentPane().getBackground());
			}
			pics[i] = p1.addImageLabel(img, (i + 1) * 2 - 1, 3, 1, 1);
		}
		ok.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_ESCAPE)
					buttonClicked(ok);

			}
		});
	}
}
