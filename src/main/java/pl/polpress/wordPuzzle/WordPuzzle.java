package pl.polpress.wordPuzzle;

import java.util.List;

public interface WordPuzzle {
	boolean insertWord(String word, int x, int y, Direction direction);
	void fillEmptyFields();
	List<Row> getBoardRows();
	List<String> getWordsToFind();
	String getName();
}
