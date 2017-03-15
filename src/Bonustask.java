import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Bonustask {	
	/* SPN-test vars */
	private static String x_solution = "0001001010001111"; //16 lang
	private static String k = "00010001001010001000110000000000"; //32 lang
	private static String y = "1010111010110100"; //16 lang
	/* end of SPN-test vars */
	
	public static void main(String[] args) {
		SPN.calc_spn_params();
		SPN.calc_spn_reversed_params();
		
		//y = loadFile();
		String x = SPN.spn_decode(y, k);
		System.out.println("k:         " + k);
		System.out.println("y:         " + y);
		System.out.println("Lösung:    " + x_solution);
		System.out.println("Berechnet: " + x);
	}
	
	private static String loadFile() {
		// TODO Auto-generated method stub
		return "";
	}
	
	public static String xor(String s1, String s2) {
		if(s1.length() != s2.length()) {
			System.out.println("Not same length");
			return "";
		}
		StringBuilder result = new StringBuilder();
		for(int i = 0; i < s1.length(); i++) {
			if(s1.charAt(i) == s2.charAt(i)) {
				result.append("0");
			} else {
				result.append("1");
			}
		}
		return result.toString();
	}

}
