package pl.polpress.pdf;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.HashMap;

import javax.imageio.ImageIO;

import net.sf.jasperreports.engine.DefaultJasperReportsContext;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import pl.polpress.util.Logger;
import pl.polpress.util.Parser;
import pl.polpress.wordPuzzle.WordPuzzle;
import pl.polpress.wordPuzzle.WordPuzzleGeneratorImpl;
import pl.polpress.wordPuzzle.net.WordsDownloaderImpl;

public class JRPrinter {
	private final Logger logger = Logger.createLogger();

	public boolean print() {
		try {
			// this.generatePdf(this.createPuzzle(), "output/puzzle.pdf");
			this.exportPdf(this.createPuzzle(), "output/puzzle.pdf");
		} catch (FileNotFoundException | NullPointerException | URISyntaxException | JRException e) {
			this.logger.logError(new String[] { "<PuzzlePdfPrinter.print>", e.getMessage(), e.getClass().getName() });
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

	private void exportPdf(WordPuzzle puzzle, String path) throws JRException, URISyntaxException, IOException {
		String patternFile = "src/main/resources/puzzle22.jasper";
		String outputFile = "output/puzzleImg.png";
		this.logger.logInfo(new String[] { "Try to generate pdf" });
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(puzzle.getBoardRows());
		parameters.put("puzzle", (Object) dataSource);
		parameters.put("words", new Parser().getPrintableWordsList(puzzle.getWordsToFind()));
		JasperPrint reportPrint = JasperFillManager.fillReport(patternFile, parameters, new JREmptyDataSource());
		FileOutputStream outputStream = new FileOutputStream(new File(outputFile));
		DefaultJasperReportsContext.getInstance();
		JasperPrintManager printManager = JasperPrintManager.getInstance(DefaultJasperReportsContext.getInstance());
		BufferedImage renderedImage = (BufferedImage) printManager.printPageToImage(reportPrint, 0, 3f);
		ImageIO.write(renderedImage, "png", outputStream);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		ImageIO.write(renderedImage, "png", os);
		InputStream is = new ByteArrayInputStream(os.toByteArray());
		createImgPdf(is);
		this.logger.logInfo(new String[] { "Pdf generated" });
	}

	private void createImgPdf(InputStream inputStream) throws JRException, FileNotFoundException {
		String patternFile = "src/main/resources/imagePdf.jrxml";
		String outputFile = "output/newPuzzle2.pdf";
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("image", inputStream);
		JasperPrint reportPrint = JasperFillManager.fillReport(JasperCompileManager.compileReport(patternFile),
				parameters, new JREmptyDataSource());
		FileOutputStream outputStream = new FileOutputStream(new File(outputFile));
		JasperExportManager.exportReportToPdfStream(reportPrint, outputStream);
	}

	private void generatePdf(WordPuzzle puzzle, String path)
			throws JRException, FileNotFoundException, URISyntaxException {
		String patternFile = "src/main/resources/puzzle22.jasper";
		String outputFile = String.valueOf(path);
		this.logger.logInfo(new String[] { "Try to generate pdf" });
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(puzzle.getBoardRows());
		parameters.put("puzzle", (Object) dataSource);
		parameters.put("words", new Parser().getPrintableWordsList(puzzle.getWordsToFind()));

		JasperPrint reportPrint = JasperFillManager.fillReport(patternFile, parameters, new JREmptyDataSource());

		FileOutputStream outputStream = new FileOutputStream(new File(outputFile));
		JasperExportManager.exportReportToPdfStream(reportPrint, outputStream);

		this.logger.logInfo(new String[] { "Pdf generated" });
	}
}
