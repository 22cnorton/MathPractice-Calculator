package Main;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import BreezySwing.GBDialog;

/**
 * Confirmation box, if the user presses yes the dlgCloseIndicator equals yes,
 * otherwise no
 *
 * @author 22cnorton
 *
 */
public class ConfirmationDialog extends GBDialog {

	private static final long serialVersionUID = 1L;
	private JTextField text = addTextField("Are You Sure?", 1, 1, 2, 1);
	private JButton yes = addButton("Confirm", 2, 1, 1, 1), no = addButton("Cancel", 2, 2, 1, 1);

	@Override
	public void buttonClicked(JButton buttonObj) {// button clicked method
		if (buttonObj == yes) {// yes button is selected
			setDlgCloseIndicator("Yes");
			dispose();
		} else if (buttonObj == no) {// no button is selected
			setDlgCloseIndicator("No");
			dispose();
		}
	}

	public ConfirmationDialog(JFrame arg0, Component c, String str) {// constructor
		super(arg0);
		getContentPane().setBackground(Color.red);
		setSize(200, 100);
		setTitle("Confirmation");
		setLocationRelativeTo(c);

		text.setText(str);

		// sets the text to be unable to be interacted with
		text.setOpaque(false);
		text.setEditable(false);
		text.setForeground(Color.white);
		text.setBorder(null);
		text.setHorizontalAlignment(SwingConstants.CENTER);
		text.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				int c = e.getKeyCode();
				if (c == KeyEvent.VK_ENTER || c == KeyEvent.VK_SPACE) {
					buttonClicked(yes);
				} else if (c == KeyEvent.VK_ESCAPE)
					buttonClicked(no);
			}
		});

		yes.setFocusable(false);
		no.setFocusable(false);
	}
}