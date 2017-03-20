import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
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
		String[] yBlocks = makeBlocks(y, k.length() / 2);
		System.out.println("Anzahl Zeichen: " + yBlocks.length);
		System.out.println("Blocklength: " + yBlocks[0].length());

		String[] blocksToDecode = Arrays.copyOfRange(yBlocks, 1, yBlocks.length);
		String yMinus1 = yBlocks[0];
		
		String x_Calculated = CTR.ctr_decode_mode(blocksToDecode, k, yMinus1);
		String CTR_decoded_clean = x_Calculated.substring(0, x_Calculated.lastIndexOf('1'));

		System.out.println("Calc x: " + CTR_decoded_clean);
		System.out.println("\n"+binaryToAscii(CTR_decoded_clean));
		System.out.println("\nTest y: " + y);

		String y_Calculated = CTR.ctr_encode_mode(makeBlocks(x_Calculated, 16), k, yMinus1);
		System.out.println("Calc y: " + y_Calculated);

		if (y_Calculated.equals(y)) {
			System.out.println("ALL SYSTEMS RUN");
		}
	}

	private static String binaryToAscii(String cTR_decoded_clean) {
		String text = "";
		String[] byteBlocks = makeBlocks(cTR_decoded_clean, 8);
		for (String string : byteBlocks) {
			text += (char) Integer.parseInt(binaryPadding(string,8),2);
		}
		return text;
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

	// makes the adding for a l long binary number
	public static String binaryPadding(String s, int l) {
		String padding = "";
		for (int i = 0; i < l; i++)
			padding += "0";

		String result = padding + s;
		// take the right-most l digits
		return result.substring(result.length() - l, result.length());
	}
}
