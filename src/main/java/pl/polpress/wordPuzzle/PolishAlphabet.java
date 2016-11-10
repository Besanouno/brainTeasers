package pl.polpress.wordPuzzle;

import java.util.Random;

public class PolishAlphabet {
	private final static char[] alphabet = { 'A', '•', 'B', 'C', '∆', 'D', 'E', ' ', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
			'£', 'M', 'N', 'O', '”', 'P', 'Q', 'R', 'S', 'å', 'T', 'U', 'V', 'W', 'Y', 'X', 'Z', 'Ø', 'è' };
	private static Random random = new Random();

	public static char getRandomChar() {
		return alphabet[random.nextInt(alphabet.length)];
	}
}
