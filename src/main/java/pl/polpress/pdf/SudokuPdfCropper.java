package pl.polpress.pdf;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;

public class SudokuPdfCropper {
	public void cutPdf(String inputPath, String outputPath, String defaultName) throws IOException {
		File inputFile = new File(inputPath);
		PDDocument inputDoc = PDDocument.load(inputFile);
		outputPath = outputPath + File.separator;
		for (int i = 0; i < inputDoc.getNumberOfPages(); i++) {
			PDPage page = inputDoc.getPage(i);
			String filename = outputPath + defaultName + i + ".pdf";
			saveCroppedSudoku(filename, page);
		}
		inputDoc.close();
	}

	private PDRectangle getCutRectangle() {
		PDRectangle rectangle = new PDRectangle();
		rectangle.setLowerLeftX(34); // lewy bok, im wieksza wartosc tym bardziej idziemy w prawo
		rectangle.setLowerLeftY(107); // dol, im wieksza wartosc tym bardziej
										// uciety jest dol
		rectangle.setUpperRightX(578);
		rectangle.setUpperRightY(650); // dol, im wieksza wartosc tym mniej
										// ucieta jest góra
		return rectangle;
	}
	
	private void saveCroppedSudoku(String outputPath, PDPage page) throws IOException {
		File outputFile = new File(outputPath); 
		PDDocument outputDoc = new PDDocument();
		cutPage(page);
		outputDoc.addPage(page);
		outputFile.delete();
		outputDoc.save(outputFile);
		outputDoc.close();
	}
	
	private void cutPage(PDPage page) {
		PDRectangle rectangle = getCutRectangle();
		page.setMediaBox(rectangle);
		page.setCropBox(rectangle);
	}

}