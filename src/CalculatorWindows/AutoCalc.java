package CalculatorWindows;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JTextField;

import BreezySwing.GBDialog;
import Questions.Question;

@Deprecated
public class AutoCalc extends GBDialog {
	private static final long serialVersionUID = -7388705988745215928L;

	private ArrayList<JTextField> fields = new ArrayList<>();

	public AutoCalc(Class<? extends Question> question, JFrame arg0) {
		super(arg0);

//		Class<?> q = question.getClass();
		ArrayList<Field> vars = new ArrayList<>();
		Class<?> c = question;
		do {
			vars.addAll(Arrays.asList(c.getDeclaredFields()));
			c = c.getSuperclass();
		} while (c != null);

		for (int f = 0; f < vars.size(); f++) {
			java.lang.reflect.Type genericType = vars.get(f).getGenericType();
			if (((Class<?>) genericType).isPrimitive()) {
				fields.add(addTextField("", f + 1, 1, 1, 1));
				fields.get(f).setName(vars.get(f).getName());
			}
			System.out.println(fields.get(f).getName());
		}

//		System.out.println(Arrays.asList(q.getDeclaredFields()));
		setVisible(true);
	}
}
