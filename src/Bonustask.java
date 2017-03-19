import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Bonustask {
	private static String x;
	private static String k;
	private static String y;

	public static void main(String[] args) {
		SPN.calc_spn_params();
		SPN.calc_spn_reversed_params();

		// test();
		launch_ctr();

	}

	private static void launch_ctr() {
		k = "00111010100101001101011000111111";
		y = loadFile("data/chiffre.txt");
		String[] yBlocks = makeBlocks(y, 16);
		
		String CTR_decoded = CTR.ctr_decode_mode(yBlocks, k, yBlocks[yBlocks.length - 1]);
		System.out.println("Calc x:" + CTR_decoded);

		System.out.println("\nTest y:"+y);

		String CTR_encoded = CTR.ctr_encode_mode(makeBlocks(CTR_decoded, 16), k, yBlocks[yBlocks.length - 1]);
		System.out.println("Calc y:" + CTR_encoded);

		if (CTR_encoded.equals(y)) {
			System.out.println("ALL SYSTEMS RUN");
		}
	}

	private static void test() {
		/* SPN-test vars */
		x = "0001001010001111";// 16 lang
		k = "00010001001010001000110000000000";// 32 lang
		y = "1010111010110100"; // 16 lang
		/* end of SPN-test vars */

		String y_calc = SPN.spn_encode(x, k);
		String x_calc = SPN.spn_decode(y, k);

		System.out.println("k:           " + k);
		System.out.println("y:           " + y);
		System.out.println("y Berechnet: " + y_calc);

		System.out.println("x Lösung:    " + x);
		System.out.println("x Berechnet: " + x_calc);

		if (x_calc.equals(x) && y_calc.equals(y)) {
			System.out.println("\nTEST SUCCESSFUL");
		} else
			System.out.println("\nTEST NOT SUCCESSFUL");
	}

	public static String[] makeBlocks(String y, int length) {
		String[] blocks = new String[y.length() / length];
		for (int i = 0; i < (y.length() / length); i++) {
			blocks[i] = y.substring(i * length, length * i + length);
		}
		return blocks;
	}

	private static String loadFile(String path) {
		String line = null;
		try {
			line = Files.readAllLines(Paths.get(path)).get(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return line;
	}

	public static String xor(String s1, String s2) {
		if (s1.length() != s2.length()) {
			System.out.println("Not same length");
			return "";
		}
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < s1.length(); i++) {
			if (s1.charAt(i) == s2.charAt(i)) {
				result.append("0");
			} else {
				result.append("1");
			}
		}
		return result.toString();
	}

}
