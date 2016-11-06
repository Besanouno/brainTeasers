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

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import pl.polpress.Main;
import pl.polpress.util.Logger;
import pl.polpress.util.Parser;
import pl.polpress.wordPuzzle.WordPuzzle;

public class JRPrinter {
	private final Logger logger = Logger.createLogger();

	public boolean print(WordPuzzle puzzle, String path) {
		try { 
			exportUsingPng(puzzle, path); 
		} catch (NullPointerException | URISyntaxException | JRException | IOException e) {
			logger.logError("[JRPrinter.print] ", e.getClass().getName(), e.getMessage());
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private void exportUsingPng(WordPuzzle puzzle, String path) throws JRException, URISyntaxException, IOException {
		String patternFile =  "/" + puzzle.getName() + ".jasper";
		
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(puzzle.getBoardRows());
		parameters.put("puzzle", (Object) dataSource);
		parameters.put("words", new Parser().getPrintableWordsList(puzzle.getWordsToFind()));
		parameters.put("image", Main.class.getResourceAsStream("/wzor.png"));
		JasperPrint reportPrint = JasperFillManager.fillReport(Main.class.getResourceAsStream(patternFile), parameters, new JREmptyDataSource());
		createPdf(reportPrint, path, 6); 
	}

	private void createPdf(JasperPrint report, String path, float zoom) throws IOException, JRException {
		BufferedImage renderedImage = (BufferedImage) JasperPrintManager.printPageToImage(report, 0, zoom);

		/* Zapisywanie png
		 String outputFile = "output/puzzleImg.png";
		FileOutputStream outputStream = new FileOutputStream(new File(outputFile));
		ImageIO.write(renderedImage, "png", outputStream);
		*/
		createPdfContainingPng(getImageInputStream(renderedImage), path);
	} 
	
	private InputStream getImageInputStream(BufferedImage image) throws IOException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		ImageIO.write(image, "png", os);
		return new ByteArrayInputStream(os.toByteArray());
	}

	private void createPdfContainingPng(InputStream image, String outputName)
			throws FileNotFoundException, JRException {
		String patternFile = "/imagePdf.jasper";
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("image", image);
		JasperPrint reportPrint = JasperFillManager.fillReport(Main.class.getResourceAsStream(patternFile),
				parameters, new JREmptyDataSource());
		FileOutputStream outputStream = new FileOutputStream(new File(outputName));
		JasperExportManager.exportReportToPdfStream(reportPrint, outputStream);
	} 
}
