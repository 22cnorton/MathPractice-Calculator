package Main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

public class Settings {

	private static HashMap<String, String> settings;
	private static boolean devMode = false;

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


	public static ProblemTypes getNumType() {
		try {

			return ProblemTypes.valueOf(settings.get("NumType"));
		} catch (IllegalArgumentException | NullPointerException npe) {
			return ProblemTypes.NUMBERS;
		}
	}


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
