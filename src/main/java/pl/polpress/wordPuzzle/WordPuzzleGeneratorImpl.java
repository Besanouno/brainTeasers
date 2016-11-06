package pl.polpress.wordPuzzle;

import java.util.Arrays;
import java.util.Random;

public class WordPuzzleGeneratorImpl implements WordPuzzleGenerator {
	private Random random = new Random();
	private int height;
	private int width; 
 
	@Override
	public WordPuzzle generatePuzzle(String[] words) {
		WordPuzzle puzzle;
		while ((puzzle = createPuzzle(words)) == null) {
		}
		return puzzle;
	}
 
	public WordPuzzle createPuzzle(String[] words) {
		WordPuzzle puzzle = createBoardWithProperlyDimensions(countLettersInWords(words));
		Arrays.sort(words, (x, y) -> y.length() - x.length());
		for (String word : words) {
			if (!insertWord(word, puzzle)) {
				return null;
			}
		}
		puzzle.fillEmptyFields();
		return puzzle;
	}
	
	private int countLettersInWords(String[] words) {
		int sumOfLetters = 0;
		for (String word : words) {
			sumOfLetters += word.length();
		}
		return sumOfLetters;
	}

	private WordPuzzle createBoardWithProperlyDimensions(int lettersNumber) {
		if (lettersNumber > 130 && lettersNumber <= 142) {
			height = 12;
			width = 20;
		} else if (lettersNumber > 142 && lettersNumber <= 156) {
			height = 12;
			width = 21;
		} else if (lettersNumber > 156 && lettersNumber <= 162) {
			height = 12;
			width = 22;
		} else if (lettersNumber > 162 && lettersNumber <= 171) {
			height = 13;
			width = 22;
		} else if (lettersNumber > 171 && lettersNumber <= 190) {
			height = 13;
			width = 23;
		} else {
			height = 12;
			width = 22;
		}
		width = 22;
		return new WordPuzzleImpl(height, width);
	}

	private int randomCoordinateY() {
		return random.nextInt(height);
	}

	private int randomCoordinateX() {
		return random.nextInt(width);
	}

	private boolean insertWord(String word, WordPuzzle puzzle) {
		Direction direction = Direction.getRandom();
		int counter = 0;
		while (!puzzle.insertWord(word, randomCoordinateY(), randomCoordinateX(), direction)) {
			counter++;
			if (counter == 250) {
				return false;
			}
		}
		return true;
	}
}
