package pl.polpress.wordPuzzle;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class BoardImpl implements Board {
	private final char EMPTY_CELL = ' ';
	private final int height;
	private final int width;
	private char[][] table;

	public BoardImpl(int height, int width) {
		if (height <= 0 || width <= 0)
			throw new RuntimeException();
		this.height = height;
		this.width = width;
		table = new char[height][width];
		forEachTableField(this::clear);
	}

	public void forEachTableField(BiConsumer<Integer, Integer> action) {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				action.accept(y, x);
			}
		}
	}
	
	public List<Row> getRows() {
		List<Row> rows = new ArrayList<>();
		for (int i = 0; i < getHeight(); i++) {
			rows.add(new Row(table[i]));
		}
		return rows;	
	}
	
	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public void set(int y, int x, char value) {
		table[y][x] = value;
	}

	public char get(int y, int x) {
		return table[y][x];
	}

	public boolean areCoordinatesInsideBounds(int y, int x) {
		return (y >= 0 && y < height && x >= 0 && x < width);
	}

	public boolean isEmpty(int y, int x) {
		return table[y][x] == EMPTY_CELL;
	}

	private void clear(int y, int x) {
		table[y][x] = EMPTY_CELL;
	}
}
