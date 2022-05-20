package Main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

public class Settings {
	/*
	 * private static int lowerBound, upperBound, qNum, numType; private static char
	 * type; private static boolean firstLarger, fractionAnswer;
	 */

//	public static HashMap<Integer, Integer> diffScale = new HashMap<Integer, Integer>(); 

	private static HashMap<String, String> settings;
	private static boolean devMode = false;

	// added at the beginning of week 2, makes it easier to check against what the
	// problem type is
	/*
	 * private static final int[] nums; static { nums = new int[] { 0, 1, 2, 3, 4,
	 * 5, 6, 7, 8, 9, 10, 11, 12, 13 }; }
	 */
	/*
	 * public static final int NORM = 0, MONEY = 1, FRACTION = 2, COINS = 3, TIME =
	 * 4, SEQUENCE = 5, SIMPLIFY_FRACTION = 6, DATE = 7, RECTANGLE = 8, CIRCLE = 9,
	 * TRIANGLE = 10, TRAPEZOID = 11, PERCENT = 12, CHANGE = 13;
	 * 
	 * private static final String[] typeNames = { "Numbers", "Money", "Fraction",
	 * "Coins", "Time", "Sequence", "Simplify Fraction", "Date", "Rectangle",
	 * "Circle", "Triangle", "Trapezoid", "Percent", "Change" };
	 */

	/*
	 * private static final HashMap<Integer, String> val; static { val = new
	 * HashMap<>(); for (int i = 0; i < ProblemTypes.values().length; i++) {
	 * val.put(ProblemTypes.values()[i].val, ProblemTypes.values()[i].name); //
	 * System.out.println(ProblemTypes.values()[i].name); }
	 * 
	 * 
	 * val.put("Numbers", 0); val.put("Money", 1); val.put("Fraction", 2);
	 * val.put("Coins", 3); val.put("Time", 4); val.put("Sequence", 5);
	 * val.put("Simplify Fraction", 6);
	 * 
	 * // System.out.println(val); }
	 */

	/*
	 * public static String getType(int key) { return
	 * ProblemTypes.values()[key].name; }
	 * 
	 * public static int getNumOfTypes() { return ProblemTypes.values().length; }
	 */

	/*
	 * public Settings() { lowerBound = 1; upperBound = 10; type = '+'; qNum = 10;
	 * numType = 0; firstLarger = true; fractionAnswer = false; }
	 * 
	 * public Settings(int lB, int uB, char t, int qN, int numType) { lowerBound =
	 * lB; upperBound = uB; type = t; qNum = qN; Settings.numType = numType;
	 * firstLarger = true; fractionAnswer = false; }
	 * 
	 * static Object getDoub() { return 4.5; }
	 */

	/*
	 * static void readFiles() { settings = new HashMap<>(); try { // var t=10;
	 * FileInputStream fis = new FileInputStream("settings.properties"); Properties
	 * prop = new Properties(); prop.load(fis); fis.close(); settings.put("Op",
	 * prop.getProperty("Op")); settings.put("NumType",
	 * prop.getProperty("NumType")); settings.put("Reps", prop.getProperty("Reps"));
	 * settings.put("MinBound", prop.getProperty("MinBound"));
	 * settings.put("MaxBound", prop.getProperty("MaxBound"));
	 * settings.put("FirstLarger", prop.getProperty("FirstLarger"));
	 * settings.put("FractionAnswer", prop.getProperty("FractionAnswer"));
	 * 
	 * } catch (FileNotFoundException fnfe) { fnfe.printStackTrace(); } catch
	 * (IOException ioe) { ioe.printStackTrace(); }
	 * 
	 * }
	 */
	private static final String[] NAMES;
	static {
		NAMES = new String[] { "MinBound", "MaxBound", "Reps", "NumType", "Op", "FirstLarger", "FractionAnswer",
				"SlowTxt", "Timed", "TimedSpeed" };
	}

	private static final File SETTINGS_FILE = new File("settings.properties");

	static void readFiles() {
		Settings.setSettings(new HashMap<>());
		try {
			SETTINGS_FILE.createNewFile();
			FileInputStream fis = new FileInputStream(SETTINGS_FILE);
			Properties prop = new Properties();
			prop.load(fis);
			fis.close();

			for (String str : NAMES)
				settings.put(str, prop.getProperty(str));

			/*
			 * Settings.getSettings().put("Op", Character.toString(getType()));
			 * Settings.getSettings().put("NumType", prop.getProperty("NumType"));
			 * Settings.getSettings().put("Reps", prop.getProperty("Reps"));
			 * Settings.getSettings().put("MinBound", prop.getProperty("MinBound"));
			 * Settings.getSettings().put("MaxBound", prop.getProperty("MaxBound"));
			 * Settings.getSettings().put("FirstLarger", prop.getProperty("FirstLarger"));
			 * Settings.getSettings().put("FractionAnswer",
			 * prop.getProperty("FractionAnswer")); Settings.getSettings().put("SlowTxt",
			 * prop.getProperty("SlowTxt")); Settings.getSettings().put("Timed",
			 * prop.getProperty("Timed"));
			 */
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	static void writeFiles() {

		Properties prop = new Properties();

		for (String str : NAMES) {
			try {
				prop.put(str, settings.get(str));
			} catch (NullPointerException npe) {
				prop.put(str, "");
			}
		}
//		prop.put("MinBound", settings.get("MinBound"));
//		prop.put("MaxBound", settings.get("MaxBound"));
//		prop.put("Reps", settings.get("Reps"));
//		prop.put("NumType", settings.get("NumType"));
//		prop.put("Op", settings.get("Op"));
//		prop.put("FirstLarger", settings.get("FirstLarger"));
//		prop.put("FractionAnswer", settings.get("FractionAnswer"));
//		prop.put("SlowTxt", settings.get("SlowTxt"));
//		prop.put("Timed", settings.get("Timed"));

		try {
			FileOutputStream outputStrem = new FileOutputStream(SETTINGS_FILE);
			prop.store(outputStrem, "These are the settings for the math practice program");
			outputStrem.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static boolean isTimed() {
		return Boolean.valueOf(settings.get("Timed"));
	}
	
	static void setTimed(boolean timed) {
		settings.put("Timed", Boolean.toString(timed));
	}

	static int getTimedSpeed() {
		try {
			return Integer.parseInt(settings.get("TimedSpeed"));
		} catch (NullPointerException | NumberFormatException npe) {
			return 30;
		}
	}

	static void setTimedSpeed(int speed) {
		settings.put("TimedSpeed", Integer.toString(speed));
	}

	static int getSlowTxt() {
		try {
			return Integer.parseInt(settings.get("SlowTxt"));
		} catch (NullPointerException | NumberFormatException npe) {
			return 4;
		}
	}

	static void setSlowTxt(int slowTxt) {
		settings.put("SlowTxt", Integer.toString(slowTxt));
	}

	public static boolean isDevMode() {
		return devMode;
	}

	public static void setDevMode(boolean devMode) {
		Settings.devMode = devMode;
	}

	public static HashMap<String, String> getSettings() {
		return settings;
	}

	public static void setSettings(HashMap<String, String> settings) {
		Settings.settings = settings;
	}

	/*
	 * static void setAll(int lB, int uB, char t, int qN, int numType) { lowerBound
	 * = lB; upperBound = uB; type = t; qNum = qN; Settings.numType = numType;
	 * firstLarger = true; fractionAnswer = false; }
	 */

	public static ProblemTypes getNumType() {
		try {

			return ProblemTypes.valueOf(settings.get("NumType"));
		} catch (IllegalArgumentException | NullPointerException npe) {
			return ProblemTypes.NUMBERS;
		}
	}

//	static ProblemTypes getProblemType() {
//		return ProblemTypes.values()[1];
//	}

	static void setNumType(ProblemTypes numType) {
		settings.put("NumType", numType.toString());
	}

	public static int getLowerBound() {
		try {
			return Integer.parseInt(settings.get("MinBound"));
		} catch (NullPointerException | NumberFormatException npe) {
			return 1;
		}
	}

	static void setLowerBound(int lowerBound) {
		settings.put("MinBound", Integer.toString(lowerBound));
	}

	public static int getUpperBound() {
		try {
			return Integer.parseInt(settings.get("MaxBound"));
		} catch (NullPointerException | NumberFormatException npe) {
			return 10;
		}
	}

	static void setUpperBound(int upperBound) {
		settings.put("MaxBound", Integer.toString(upperBound));
	}

	public static char getType() {
		try {
			return settings.get("Op").charAt(0);
		} catch (NullPointerException | StringIndexOutOfBoundsException npe) {
			return '+';
		}
	}

	static void setType(char type) {
		settings.put("Op", Character.toString(type));
	}

	static int getqNum() {
		try {
			return Integer.parseInt(settings.get("Reps"));
		} catch (NullPointerException | NumberFormatException npe) {
			return 10;
		}
	}

	static void setqNum(int qNum) {
		settings.put("Reps", Integer.toString(qNum));
	}

	public static boolean isFirstLarger() {
		String isFL = settings.get("FirstLarger");
		return (isFL != null) ? Boolean.valueOf(isFL) : true;
	}

	static void setFirstLarger(boolean firstLarger) {
		settings.put("FirstLarger", Boolean.toString(firstLarger));
	}

	public static boolean isFractionAnswer() {
		return Boolean.valueOf(settings.get("FractionAnswer"));
	}

	static void setFractionAnswer(boolean fractionAnswer) {
		settings.put("FractionAnswer", Boolean.toString(fractionAnswer));
	}

	public String toString() {
		return "Lower Bound: " + getLowerBound() + "\nUpper Bound: " + getUpperBound() + "\nQuestion Type: " + getType()
				+ "\nRepetition: " + getqNum() + "\nFirst Larger: " + isFirstLarger() + "\nAnswer as Fraction: "
				+ isFractionAnswer() + "\nText Boxes Speed: " + getSlowTxt();
	}
}
