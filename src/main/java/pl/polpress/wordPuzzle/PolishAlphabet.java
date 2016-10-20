package pl.polpress.wordPuzzle;

import java.util.Random;

public class PolishAlphabet {
	private final static char[] alphabet = { 'A', 'Ą', 'B', 'C', 'Ć', 'D', 'E', 'Ę', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
			'Ł', 'M', 'N', 'O', 'Ó', 'P', 'Q', 'R', 'S', 'Ś', 'T', 'U', 'V', 'W', 'Y', 'X', 'Z', 'Ź', 'Ż' };
	private static Random random = new Random();

	public static char getRandomChar() {
		return alphabet[random.nextInt(alphabet.length)];
	}
}
