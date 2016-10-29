package pl.polpress.wordPuzzle;

import pl.polpress.exceptions.GeneratingException;
import pl.polpress.pdf.JRPrinter;
import pl.polpress.wordPuzzle.net.WordsDownloader;
import pl.polpress.wordPuzzle.net.WordsDownloaderImpl;

public class PuzzlePrintGenerator {
	public void create(int count) {
		int successful = 0;
		while (successful < count) {
			if (new JRPrinter().print()) {
				successful++;
			}

		}
	}

	private WordPuzzle generate() {
		WordPuzzleGenerator generator = new WordPuzzleGeneratorImpl();
		try {
			return generator.generatePuzzle(downloadWords());
		} catch (GeneratingException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	private String[] downloadWords() {
		WordsDownloader downloader = new WordsDownloaderImpl();
		String[] words;
		int counter = 0;
		while ((words = downloader.downloadWords()) == null) {
			counter++;
			if (counter == 10) {
				// TODO : Wyswietl blad
				System.out.println("Blad pobierania wyrazów");
			}
		}
		;
		return words;
	}
}
