import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SPN {
	private static int r = 4;
	private static int n = 4;
	private static int m = 4;
	
	private static Map<String,String> sBox;
	private static Map<String,String> inverted_sBox;
	private static Map<Integer,Integer> bitPermutation;

	public static void calc_spn_params() {
		sBox = new HashMap<>();
		sBox.put("0000", "1110"); //0 - E
		sBox.put("0001", "0100"); //1 - 4
		sBox.put("0010", "1101"); //2 - D
		sBox.put("0011", "0001"); //3 - 1
		sBox.put("0100", "0010"); //4 - 2
		sBox.put("0101", "1111"); //5 - F
		sBox.put("0110", "1011"); //6 - B
		sBox.put("0111", "1000"); //7 - 8
		sBox.put("1000", "0011"); //8 - 3
		sBox.put("1001", "1010"); //9 - A
		sBox.put("1010", "0110"); //A - 6
		sBox.put("1011", "1100"); //B - C
		sBox.put("1100", "0101"); //C - 5
		sBox.put("1101", "1001"); //D - 9
		sBox.put("1110", "0000"); //E - 0
		sBox.put("1111", "0111"); //F - 7
		
		bitPermutation = new HashMap<>();
		bitPermutation.put(0, 0);
		bitPermutation.put(1, 4);
		bitPermutation.put(2, 8);
		bitPermutation.put(3, 12);
		bitPermutation.put(4, 1);
		bitPermutation.put(5, 5);
		bitPermutation.put(6, 9);
		bitPermutation.put(7, 13);
		bitPermutation.put(8, 2);
		bitPermutation.put(9, 6);
		bitPermutation.put(10, 10);
		bitPermutation.put(11, 14);
		bitPermutation.put(12, 3);
		bitPermutation.put(13, 7);
		bitPermutation.put(14, 11);
		bitPermutation.put(15, 15);
	}
	
	public static void calc_spn_reversed_params() {
		inverted_sBox = new HashMap<>();
		Set<String> keys = sBox.keySet();
		for (String key : keys) {
			inverted_sBox.put(sBox.get(key), key);
		}
	}
	
	private static String calc_inverted_roundkey(String k, int round) {
		if(round == 0 || round == r) {
			return calc_roundkey(k, r-round);
		}
		return permutate(calc_roundkey(k, r-round));
	}
	
	private static String permutate(String s) {
		StringBuilder result = new StringBuilder(s);
		for(int i = 0; i < n*m; i++) {
			if(bitPermutation.get(i) > i) {
				char temp = result.charAt(i);
				result.setCharAt(i, result.charAt(bitPermutation.get(i)));
				result.setCharAt(bitPermutation.get(i), temp);
			}
		}
		return result.toString();
	}
	
	private static String calc_roundkey(String k, int round) {
		return k.substring(4*round, 4*round+16);
	}
	
	private static String sbox_inverse(String y) {
		StringBuilder replaced = new StringBuilder(y);
		for(int i = 0; i < n; i++) {
			replaced.replace(i*m, i*m+m, inverted_sBox.get(replaced.substring(i*m, i*m+m)));
		}
		return replaced.toString();
	}
	
	public static String spn_decode(String y, String k) {
		// initialer Weissschritt
		String result = Bonustask.xor(y, calc_inverted_roundkey(k, 0));
		for(int i = 1; i < r; i++) {
			// S-inverse
			result = sbox_inverse(result);
			// permutate
			result = permutate(result);
			// xor mit invertedroundkey
			result = Bonustask.xor(result, calc_inverted_roundkey(k, i));
		}
		// s-inverse
		result = sbox_inverse(result);
		// Rundenschlüsseladdition
		result = Bonustask.xor(result, calc_inverted_roundkey(k, r));
		return result;
	}
	
}
