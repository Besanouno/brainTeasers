package pl.polpress.pdf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.HashMap;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import pl.polpress.Main;
import pl.polpress.util.Logger;
import pl.polpress.util.Parser;
import pl.polpress.wordPuzzle.WordPuzzle;
import pl.polpress.wordPuzzle.WordPuzzleGeneratorImpl;
import pl.polpress.wordPuzzle.net.WordsDownloaderImpl;

public class JRPrinter {
	private final Logger logger = Logger.createLogger();

	public boolean print() {
		try {
			this.generatePdf(this.createPuzzle(), "output/puzzle.pdf");
		} catch (FileNotFoundException | NullPointerException | URISyntaxException | JRException e) {
			this.logger.logError(new String[] { "<PuzzlePdfPrinter.print>", e.getMessage(), e.getClass().getName() });
			return false;
		}
		return true;
	}

	private WordPuzzle createPuzzle() {
		WordPuzzle puzzle;
		String[] words = this.downloadWords();
		this.logger.logInfo(new String[] { "Try to create puzzles..." });
		while ((puzzle = new WordPuzzleGeneratorImpl().generatePuzzle()) == null) {
		}
		this.logger.logInfo(new String[] { "Puzzles created" });
		return puzzle;
	}

	private String[] downloadWords() {
		String[] words;
		this.logger.logInfo(new String[] { "Try to download words" });
		int counter = 0;
		while ((words = new WordsDownloaderImpl().downloadWords()) == null) {
			if (++counter != 100)
				continue;
		}
		this.logger.logInfo(new String[] { "Words downloaded" });
		return words;
	}

	private void generatePdf(WordPuzzle puzzle, String path)
			throws JRException, FileNotFoundException, URISyntaxException {
		String patternFile = "src/main/resources/puzzle22.jasper";
		String outputFile = String.valueOf(path) + ".pdf";
		this.logger.logInfo(new String[] { "Try to generate pdf" });
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(puzzle.getBoardRows());
		parameters.put("puzzle", (Object) dataSource);
		parameters.put("words", new Parser().getPrintableWordsList(puzzle.getWordsToFind()));
		parameters.put("image", Main.class.getResourceAsStream("src/main/resources/wzor.png"));
		JasperPrint reportPrint = JasperFillManager.fillReport( patternFile, parameters,
				(JRDataSource) new JREmptyDataSource());
		FileOutputStream outputStream = new FileOutputStream(new File(outputFile));
		JasperExportManager.exportReportToPdfStream((JasperPrint) reportPrint, (OutputStream) outputStream);
		this.logger.logInfo(new String[] { "Pdf generated" });
	}
}
