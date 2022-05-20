package Utils;

public class StringUtils {
	public static String properCase(String s) {
		s = s.trim();
		int spaceIndex = s.indexOf('\s');
		if (spaceIndex < 0) {
			return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
		}
		return s.substring(0, 1).toUpperCase() + s.substring(1, spaceIndex + 1).toLowerCase()
				+ properCase(s.substring(spaceIndex + 1));
	}

	public static String reverseProperCase(String s) {
		s = s.trim();
		int spaceIndex = s.indexOf('\s');
		if (spaceIndex < 0) {
			return s.substring(0, 1).toLowerCase() + s.substring(1).toUpperCase();
		}
		return s.substring(0, 1).toLowerCase() + s.substring(1, spaceIndex + 1).toUpperCase()
				+ reverseProperCase(s.substring(spaceIndex + 1));
	}
}