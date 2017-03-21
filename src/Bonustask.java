import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class Bonustask {
	private static String x;
	private static String k;
	private static String y;

	public static void main(String[] args) {
		SPN.calc_spn_params();
		SPN.calc_spn_reversed_params();

		launch_ctr();
	}

	// starts the ctr modes
	private static void launch_ctr() {
		k = "00111010100101001101011000111111";
		y = loadFile("data/chiffre.txt");
		String[] yBlocks = makeBlocks(y, k.length() / 2);

		String[] blocksToDecode = Arrays.copyOfRange(yBlocks, 1, yBlocks.length);
		String yMinus1 = yBlocks[0];

		x = CTR.ctr_decode_mode(blocksToDecode, k, yMinus1);
		String CTR_decoded_clean = x.substring(0, x.lastIndexOf('1'));

		System.out.println(binaryToAscii(CTR_decoded_clean));

		// To check if y_Calculated equals to y (loaded)
		// String y_Calculated = CTR.ctr_encode_mode(makeBlocks(x_Calculated,
		// 16), k, yMinus1);
	}

	// Calculates a binary number as string to a ascii coding
	private static String binaryToAscii(String binString) {
		String text = "";
		String[] byteBlocks = makeBlocks(binString, 8);
		for (String string : byteBlocks) {
			text += (char) Integer.parseInt(string, 2);
		}
		return text;
	}

	//Makes the blocks of a binary string to given length and returns them as array
	public static String[] makeBlocks(String y, int length) {
		String[] blocks = new String[y.length() / length];
		for (int i = 0; i < (y.length() / length); i++) {
			blocks[i] = y.substring(i * length, length * i + length);
		}
		return blocks;
	}

	//loads the file with the y
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
