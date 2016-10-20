package pl.polpress.wordPuzzle;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class WordPuzzleImpl implements WordPuzzle {
	public WordPuzzleImpl(int height, int width) throws NegativeArraySizeException {
		table = new Character[height][width];
		forEachTableField(this::initField);
	}

	private void initField(int x, int y) {
		table[x][y] = EMPTY_CELL;
	}

	private final static char EMPTY_CELL = ' ';
	private Character[][] table;
	private List<String> wordsToFind = new ArrayList<>();

	@Override
	public List<Row> getBoardRows() {
		List<Row> rows = new ArrayList<>();
		for (int i = 0; i < getHeight(); i++) {
			rows.add(new Row(table[i]));
		}
		return rows;
	}

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
		forEachTableField(this::fillFieldWithRandomLetterIfIsEmpty);
	}
	
	private void fillFieldWithRandomLetterIfIsEmpty(int x, int y) {
		if (table[x][y] == EMPTY_CELL) {
			table[x][y] = PolishAlphabet.getRandomChar();
		}
	}

	@Override
	public List<String> getWordsToFind() {
		return wordsToFind;
	}

	private boolean canInsert(String word, Coordinates coordinatesProvider) {
		for (int i = 0; i < word.length(); i++) {
			int x = coordinatesProvider.getX(i);
			int y = coordinatesProvider.getY(i);
			if (!areCoordinatesInsideBounds(y, x) || table[y][x] != EMPTY_CELL && table[y][x] != word.charAt(i)) {
				return false;
			}
		}
		return true;
	}

	private boolean areCoordinatesInsideBounds(int y, int x) {
		return (y >= 0 && y < getHeight() && x >= 0 && x < getWidth());
	}

	private int getHeight() {
		return table.length;
	}

	private int getWidth() {
		return (getHeight() == 0) ? 0 : table[0].length;
	}

	private void insert(String word, Coordinates coordinatesProvider) {
		for (int i = 0; i < word.length(); i++) {
			table[coordinatesProvider.getY(i)][coordinatesProvider.getX(i)] = word.charAt(i);
		}
		wordsToFind.add(word.toLowerCase());
	}

	private void forEachTableField(BiConsumer<Integer, Integer> action) {
		for (int i = 0; i < getHeight(); i++) {
			for (int j = 0; j < getWidth(); j++) {
				action.accept(i, j);
			}
		}
	}
}
