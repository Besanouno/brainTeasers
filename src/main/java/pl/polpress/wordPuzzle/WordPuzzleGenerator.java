package pl.polpress.wordPuzzle;

import pl.polpress.exceptions.GeneratingException;

public interface WordPuzzleGenerator {
	WordPuzzle generatePuzzle();
	WordPuzzle generatePuzzle(String[] words) throws GeneratingException;
}
