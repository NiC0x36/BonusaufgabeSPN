public class CTR {
	/*
	 * Zerlege y in Blöcke der Länge l: y = y_-1y_0y_1 ... y_n-1
	 * 
	 * xi = E((y_-1 + i) mod 2^l, k) XOR yi (i = 0, ..., n-1)
	 * 
	 * Gib x = x_0x_1 ... x_n-1 zurück.
	 */
	public static String ctr_decode_mode(String[] blocksToDecode, String k, String yMinus1) {
		String result = "";
		int l = yMinus1.length();

		// xi = E((y_-1 + i) mod 2^l, k) XOR yi (i = 0, ..., n-1)
		for (int i = 0; i < blocksToDecode.length; i++) {
			result += Bonustask.xor(ctr_E(yMinus1, i, k), blocksToDecode[i]);
		}
		return result;
	}

	public static String ctr_encode_mode(String[] blocksToEncode, String k, String yMinus1) {
		String result = "";
		int l = blocksToEncode[0].length();

		// xi = E((y_-1 + i) mod 2^l, k) XOR x_i (i = 0, ..., n-1)
		for (int i = 0; i < blocksToEncode.length; i++) {
			result += Bonustask.xor(ctr_E(yMinus1, i, k), blocksToEncode[i]);
		}
		
		return yMinus1+result;
	}

	private static String ctr_E(String yMinus1, int i, String k) {
		// (y_-1 + i)
		int numberToMod = Integer.parseInt(yMinus1, 2) + i;

		// 2^l
		int l = yMinus1.length();
		int lPowerOfTwo = (int) Math.pow(2, l);

		// (y_-1 + i) mod 2^l
		String c = Bonustask.binaryPadding(Integer.toBinaryString(numberToMod % lPowerOfTwo), l);

		// E((y_-1 + i) mod 2^l, k)
		String result = SPN.spn_encode(c, k);

		return result;
	}

}
