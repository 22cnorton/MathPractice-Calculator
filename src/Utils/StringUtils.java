package Utils;

public class StringUtils {
	public static String capitalize(String s) {
		s=s.replaceAll("_", " ");
		int i=s.indexOf(" ");
//		if(i<0) {
//			return s.substring(0,1).toUpperCase()+s.substring(1).toLowerCase();
//		}
		String str="";
		while(i>=0) {
			str+=s.substring(0,1).toUpperCase()+s.substring(1,i).toLowerCase()+" ";
			s=s.substring(i+1);
			i=s.indexOf(" ");
		}
		return str+ s.substring(0,1).toUpperCase()+s.substring(1).toLowerCase();
	}
}
