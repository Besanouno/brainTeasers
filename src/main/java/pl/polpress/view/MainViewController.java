package pl.polpress.view;

import java.io.File;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import pl.polpress.pdf.JRPrinter;
import pl.polpress.util.Config;
import pl.polpress.wordPuzzle.WordPuzzle;
import pl.polpress.wordPuzzle.WordPuzzleGeneratorImpl;
import pl.polpress.wordPuzzle.net.WordsDownloaderImpl;

public class MainViewController {
	@FXML
	private TextField tfDirectory;
	@FXML
	private TextField tfAmount;
	@FXML
	private Button btnRun;
	@FXML
	private Label lblMax;
	@FXML
	private Label lblDirectory;
	@FXML
	private Label lblPositive;
	@FXML
	private Label lblWait;

	@FXML
	private void initialize() {
		initDirectoryField();
		initAmountField();
	}

	private void initDirectoryField() {
		tfDirectory.setText(Config.getString("default_dir"));
	}

	private void initAmountField() {
		tfAmount.setText(Config.getString("amount"));
		reinitializeEmptyAmountField();
		addDigitsFilterToAmountField();
	}

	private void reinitializeEmptyAmountField() {
		if ("".equals(tfAmount.getText())) {
			tfAmount.setText("0");
		}
	}

	private void addDigitsFilterToAmountField() {
		tfAmount.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("\\d*")) {
				tfAmount.setText(newValue.replaceAll("[^\\d]", ""));
			}
		});
	}

	@FXML
	private void generate() {
		cleanWindow();
		if (!validateAmount())
			return;
		if (!validateDirectory())
			return;
		saveSettings();
		generatePuzzle();
	}

	private void cleanWindow() {
		lblMax.setVisible(false);
		lblDirectory.setVisible(false);
		lblPositive.setVisible(false);
	}

	private boolean validateAmount() {
		int amount = Integer.parseInt(tfAmount.getText());
		if (amount > 100) {
			lblMax.setVisible(true);
			return false;
		} else if (amount <= 0) {
			lblPositive.setVisible(true);
			return false;
		}
		return true;
	}

	private boolean validateDirectory() {
		File file = new File(tfDirectory.getText());
		if (file.exists()) {
			if (!file.isDirectory()) {
				lblDirectory.setVisible(true);
				return false;
			}
		} else {
			return file.mkdirs();
		}
		return true;
	}

	private void saveSettings() {
		Config.setString("amount", tfAmount.getText());
		Config.setString("default_dir", tfDirectory.getText());
	}

	private void generatePuzzle() {
		reinitializeEmptyAmountField();
		int count = Integer.parseInt(tfAmount.getText()); 
		generatePuzzle(count, tfDirectory.getText() + File.separator);
	}

	private void generatePuzzle(int count, String path) { 
		lblWait.setVisible(true);
		btnRun.setDisable(true);
		new Thread(() -> {
			int successful = 0;
			while (successful < count) {
				String filename = path + successful + ".pdf";
				if (new JRPrinter().print(createPuzzle(), filename)) {
					successful++;
				}
			}
			Platform.runLater(() -> {
				lblWait.setText("Wygenerowano " + count + " wykreślanek");
				btnRun.setDisable(false);
			});
		}).start();
	}

	private WordPuzzle createPuzzle() {
		String[] words = this.downloadWords();
		return new WordPuzzleGeneratorImpl().generatePuzzle(words);
	}

	private String[] downloadWords() {
		String[] words;
		int counter = 0;
		while ((words = new WordsDownloaderImpl().downloadWords()) == null) {
			if (++counter != 100)
				continue;
		}
		return words;
	}
}
