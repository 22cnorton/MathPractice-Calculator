package CalculatorWindows;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.FocusTraversalPolicy;
import java.awt.Image;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.GrayFilter;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import BreezySwing.GBPanel;

/**
 * a calculator to add and subtract US bills and coins
 * 
 * @author 22cnorton
 *
 */
public class CoinsCalc extends CalculatorWindow {
	private static final long serialVersionUID = -145553732950639834L;

	private GBPanel p1 = addPanel(1, 1, 1, 1), p2 = addPanel(2, 1, 1, 1), p3 = p1.addPanel(3, 4, 1, 1);

	private JButton penny = p1.addButton("", 1, 1, 1, 1);
	private JButton nickel = p1.addButton("", 2, 1, 1, 1);
	private JButton dime = p1.addButton("", 1, 2, 1, 1);
	private JButton quarter = p1.addButton("", 2, 2, 1, 1);
	private JButton halfDollar = p1.addButton("", 2, 3, 1, 1);
	private JButton dollar = p1.addButton("", 1, 3, 1, 1);
	private JButton twoDollar = p1.addButton("", 2, 4, 1, 1);
	private JButton fiveDollar = p1.addButton("", 1, 4, 1, 1);
	private JButton tenDollar = p1.addButton("", 2, 5, 1, 1);
	private JButton twentyDollar = p1.addButton("", 1, 5, 1, 1);
	private JButton fiftyDollar = p1.addButton("", 2, 6, 1, 1);
	private JButton hundredDollar = p1.addButton("", 1, 6, 1, 1);

	private JButton[] buttons = { penny, dime, dollar, fiveDollar, twentyDollar, hundredDollar, nickel, quarter,
			halfDollar, twoDollar, tenDollar, fiftyDollar };

	private JTextField totalField = p2.addTextField("", 1, 1, 1, 1);
	private int total = 0;
	private JButton clear = p2.addButton("Clear", 2, 1, 1, 1), exit = p2.addButton("Exit", 3, 1, 1, 1);

	private JCheckBox subtract = p3.addCheckBox("Subtract", 2, 4, 1, 1);

	public void buttonClicked(JButton buttonObj) {// button clicked method
		if (buttonObj == clear) {// clear button resets values to zero
			totalField.setText("$0.00");
			total = 0;
		} else if (buttonObj == exit) {// exit button closes this window
			dispose();
		} else {// checks which button was clicked and alters the total by that amount
			int amount = 0;

			if (buttonObj == penny)
				amount = 1;
			else if (buttonObj == nickel)
				amount = 5;
			else if (buttonObj == dime)
				amount = 10;

			else if (buttonObj == quarter)
				amount = 25;
			else if (buttonObj == halfDollar)
				amount = 50;

			else if (buttonObj == dollar)
				amount = 100;
			else if (buttonObj == twoDollar)
				amount = 200;
			else if (buttonObj == fiveDollar)
				amount = 500;
			else if (buttonObj == tenDollar)
				amount = 1000;
			else if (buttonObj == twentyDollar)
				amount = 2000;
			else if (buttonObj == fiftyDollar)
				amount = 5000;
			else if (buttonObj == hundredDollar)
				amount = 10000;

			try {
				if (subtract.isSelected() && total < amount)// money value always stays positive
					throw new NumberFormatException("Money Must be Positive");

				if (subtract.isSelected()) {// subtracts from the total by the selected amount
					total -= amount;
				} else {
					total += amount;// adds to the total by the selected amount
				}
				if (total % 100 < 10) {// makes sure the the output is formated correctly
					totalField.setText("$" + total / 100 + ".0" + total % 100);
				} else
					totalField.setText("$" + total / 100 + "." + total % 100);
			} catch (NumberFormatException e) {
				if (e.getMessage().equals("Money Must be Positive"))// error if the money goes negative
					messageBox("Cannot have negative money");
			}
		}
	}

	public CoinsCalc(JFrame arg0) {// constructor
		super(arg0, "Coinculator", 1200, 500, arg0);
		p1.setOpaque(false);
		p2.setOpaque(false);
		p3.setOpaque(false);

		totalField.setBackground(getContentPane().getBackground());// sets the field to not be editable
		totalField.setEditable(false);
		totalField.setHorizontalAlignment(JLabel.CENTER);
		totalField.setForeground(Color.white);
		totalField.setText("$0.00");

		subtract.setForeground(Color.white);

		final String STR = "Coins/pictures/";// the image address that is the same for all the coins

		final String[] END = { "penny.png", "dime.png", "oneDollar.png", "fiveDollar.png", "twentyDollar.png",
				"hundredDollar.png", "nickel.png", "quarter.png", "halfDollar.png", "twoDollar.png", "tenDollar.png",
				"fiftyDollar.png" };

		for (int i = 0; i < buttons.length; i++) {
			buttons[i].setIcon(new ImageIcon(STR + END[i]));
		}

		FocusTraversalPolicy fTP = new FocusTraversalPolicy() {

			@Override
			public Component getLastComponent(Container aContainer) {
				return clear;
			}

			@Override
			public Component getFirstComponent(Container aContainer) {
				return penny;
			}

			@Override
			public Component getDefaultComponent(Container aContainer) {
				return penny;
			}

			@Override
			public Component getComponentBefore(Container aContainer, Component aComponent) {
				if (Arrays.asList(buttons).contains(aComponent)) {

					if (aComponent == penny)
						return clear;
					/*
					 * else if (aComponent == nickel) return hundredDollar;
					 */ else
						return buttons[Arrays.asList(buttons).indexOf(aComponent) - 1];
				} else if (aComponent == subtract)
					return fiftyDollar;
				else if (aComponent == clear)
					return subtract;
				return null;
			}

			@Override
			public Component getComponentAfter(Container aContainer, Component aComponent) {
				if (Arrays.asList(buttons).contains(aComponent)) {
					/*
					 * if (aComponent == penny) return dime; else
					 */ if (aComponent == fiftyDollar)
						return subtract;
					else
						return buttons[Arrays.asList(buttons).indexOf(aComponent) + 1];
				} else if (aComponent == subtract)
					return clear;
				else if (aComponent == clear)
					return penny;
				return null;
			}
		};

		hundredDollar.setFocusTraversalPolicy(getFocusTraversalPolicy());

		KeyAdapter key = new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				int c = e.getKeyCode();
				if (c == KeyEvent.VK_ESCAPE) {
					buttonClicked((e.getModifiersEx() == KeyEvent.SHIFT_DOWN_MASK) ? clear : exit);
				}
				if (c == KeyEvent.VK_BACK_QUOTE) {
					subtract.setSelected(!subtract.isSelected());
				}
			}
		};
		FocusListener foc = new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				JButton c = (JButton) e.getComponent();
				int i = 0;
				while (i < buttons.length) {
					if (buttons[i] == c)
						break;
					i++;
				}
				try {
					c.setIcon(new ImageIcon(ImageIO.read(new File(STR + END[i]))));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}

			@Override
			public void focusGained(FocusEvent e) {
				if (e.getComponent() instanceof JButton) {
					JButton c = (JButton) e.getComponent();
					int i = 0;
					while (i < buttons.length) {
						if (buttons[i] == c)
							break;
						i++;
					}
					try {
						Image img = ImageIO.read(new File(STR + END[i]));
						Image tmp = GrayFilter.createDisabledImage(img);
						c.setIcon(new ImageIcon(tmp));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}

			}
		};

		setFocusTraversalPolicy(fTP);

		subtract.addKeyListener(key);
		clear.addKeyListener(key);
		for (JButton b : buttons) {// causes the buttons to only appear as the images
			b.setBorder(BorderFactory.createEmptyBorder());
			b.setContentAreaFilled(false);
			b.setForeground(Color.white);
			b.addKeyListener(key);
			b.addFocusListener(foc);
		}
	}
}