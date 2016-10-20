package pl.polpress.wordPuzzle;

import java.util.ArrayList;
import java.util.List;

public class WordPuzzleImpl implements WordPuzzle {
	public WordPuzzleImpl(int height, int width) throws NegativeArraySizeException {
		board = new BoardImpl(height, width);
	}

	private BoardImpl board;
	private List<String> wordsToFind = new ArrayList<>();

	@Override
	public boolean insertWord(String word, final int y, final int x, Direction direction) {
		word = word.toUpperCase();
		Coordinates coordinatesProvider = CoordinatesProviderFactory.createProviderDependsOnWordDirection(y, x,
				direction);
		if (canInsert(word, coordinatesProvider)) {
			insert(word, coordinatesProvider);
			return true;
		}
		return false;
	}
	
	@Override
	public void fillEmptyFields() {
		board.forEachTableField(this::fillFieldWithRandomLetterIfIsEmpty);
	}
	
	private void fillFieldWithRandomLetterIfIsEmpty(int y, int x) {
		if (board.isEmpty(y, x)) {
			board.set(y, x, PolishAlphabet.getRandomChar());
		}
	}

	@Override
	public List<Row> getBoardRows() {
		return board.getRows();
	}
	
	@Override
	public List<String> getWordsToFind() {
		return wordsToFind;
	}

	private boolean canInsert(String word, Coordinates coordinatesProvider) {
		for (int i = 0; i < word.length(); i++) {
			int y = coordinatesProvider.getY(i);
			int x = coordinatesProvider.getX(i);
			if (!board.areCoordinatesInsideBounds(y, x) || board.isEmpty(y,x) && board.get(y,x) != word.charAt(i)) {
				return false;
			}
		}
		return true;
	}

	private void insert(String word, Coordinates coordinatesProvider) {
		for (int i = 0; i < word.length(); i++) {
			int y = coordinatesProvider.getY(i);
			int x = coordinatesProvider.getX(i);
			board.set(y, x, word.charAt(i));
		}
		wordsToFind.add(word.toLowerCase());
	}
}
