package pl.polpress.wordPuzzle;

import java.util.List;
import java.util.function.BiConsumer;

public interface Board {
	void forEachTableField(BiConsumer<Integer, Integer> action);
	List<Row> getRows();
	int getHeight();
	int getWidth();
	void set(int y, int x, char value);
	char get(int y, int x);
	boolean areCoordinatesInsideBounds(int y, int x);
	boolean isEmpty(int y, int x);
}
