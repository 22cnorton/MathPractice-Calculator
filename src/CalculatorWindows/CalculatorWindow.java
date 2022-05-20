package CalculatorWindows;

import java.awt.Color;
import java.awt.Component;

import javax.swing.*;

import BreezySwing.*;

public abstract class CalculatorWindow extends GBDialog {

	private static final long serialVersionUID = -1425929754832586412L;

	public void setLocationRelativeTo(Component c) {
		super.setLocationRelativeTo(c);
	}

	public CalculatorWindow(JFrame arg0, String title) {
		this(arg0, title, Color.DARK_GRAY, 500, 350, arg0);
	}

	public CalculatorWindow(JFrame arg0, String title, Color bgColor, int width, int height, Component relative) {
		super(arg0);
		setTitle(title);
		getContentPane().setBackground(bgColor);
		setSize(width, height);
		setLocationRelativeTo(relative);
	}

	public CalculatorWindow(JFrame arg0, String title, int width, int height, Component relative) {
		this(arg0, title, Color.darkGray, width, height, relative);
	}
}