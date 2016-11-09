package pl.polpress.view;

import java.io.File;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import pl.polpress.Main;
import pl.polpress.pdf.JRPrinter;
import pl.polpress.util.Config;
import pl.polpress.util.Directory;
import pl.polpress.wordPuzzle.WordPuzzle;
import pl.polpress.wordPuzzle.WordPuzzleGeneratorImpl;
import pl.polpress.wordPuzzle.net.WordsDownloaderImpl;

public class WordPuzzleController {
	@FXML
	private TextField tfOutputDirectory;
	@FXML
	private Button btnOutputDirectorySetter;
	@FXML
	private ImageView ivOutputDirectorySetterIcon;
	@FXML
	private Label lblWrongDirectory;
	@FXML
	private TextField tfAmount;
	@FXML
	private TextField tfDefaultName;
	@FXML
	private Button btnGenerate;
	@FXML
	private Button btnStop;
	@FXML
	private ProgressBar pbGeneratingProgress;
	@FXML
	private Label lblProgressInfo;

	private final String CAPTION_WAIT = "Czekaj... trwa generowanie wykreślanek";

	private Stage stage;
	private boolean end = false;

	public void setStage(Stage stage) {
		this.stage = stage;
		this.stage.setOnCloseRequest((e) -> {
			saveSettings();
			end = true;
		});
	}

	@FXML
	private void initialize() {
		tfDefaultName.setText(Config.getString("default_name"));
		initDirectory();
		initAmount();
		activateBlockadeOfIllegalCharactersInFilename();
		activateBlockadeOfNonDigitsInAmount();
	}

	private void initDirectory() {
		tfOutputDirectory.setText(Config.getString("default_dir"));
		ivOutputDirectorySetterIcon.setImage(new Image(Main.class.getResourceAsStream("/folder.png")));
	}

	private void initAmount() {
		tfAmount.setText(Config.getString("amount"));
		reinitializeEmptyAmountField();
	}

	private void activateBlockadeOfIllegalCharactersInFilename() {
		tfDefaultName.textProperty().addListener((ov, oldValue, newValue) -> {
			if (containsIllegalChar(newValue)) {
				tfDefaultName.setText(oldValue);
			}
		});
	}

	private boolean containsIllegalChar(String value) {
		return value.contains("%") || value.contains("*") || value.contains(".") || value.contains(",")
				|| value.contains("{") || value.contains("}") || value.contains("/") || value.contains("\\")
				|| value.contains(":") || value.contains(">") || value.contains("<") || value.contains("?")
				|| value.contains("|") || value.contains("\"");
	}

	private void activateBlockadeOfNonDigitsInAmount() {
		tfAmount.textProperty().addListener((ov, oldValue, newValue) -> {
			if (newValue.length() == 0) {
				return;
			}
			char last = newValue.charAt(newValue.length() - 1);
			if (last < 48 || last > 57) {
				tfAmount.setText(oldValue);
			}
		});
	}

	private void reinitializeEmptyAmountField() {
		if ("".equals(tfAmount.getText())) {
			tfAmount.setText("0");
		}
	}

	@FXML
	private void setOutputDirectory() {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setTitle("Wybierz katalog");
		File file = directoryChooser.showDialog(stage);
		tfOutputDirectory.setText(file.getPath());
	}

	@FXML
	private void run() {
		end = false;
		btnGenerate.setVisible(false);
		btnStop.setVisible(true);
		cleanWindow();
		if (!validateDirectory())
			return;
		reinitializeEmptyAmountField();
		generatePuzzle(Integer.parseInt(tfAmount.getText()), tfOutputDirectory.getText() + File.separator);
	}

	private void cleanWindow() {
		lblWrongDirectory.setVisible(false);
	}

	private boolean validateDirectory() {
		boolean validateResult = new Directory().validate(tfOutputDirectory.getText());
		if (!validateResult) {
			lblWrongDirectory.setVisible(true);
		}
		return validateResult;
	}

	@FXML
	private void stop() {
		end = true;
		btnGenerate.setVisible(true);
		btnStop.setVisible(false);
	}

	private void saveSettings() {
		Config.setString("amount", tfAmount.getText());
		Config.setString("default_dir", tfOutputDirectory.getText());
		Config.setString("default_name", tfDefaultName.getText());
	}

	private void generatePuzzle(int count, String path) {
		initGeneratingView();
		new Thread(() -> {
			int successful = 0;
			while (successful < count) {
				String filename = path + tfDefaultName.getText() + successful + ".pdf";
				if (new JRPrinter().print(createPuzzle(), filename)) {
					successful++;
					updateGeneratingView(successful, count);
				}
				if (end) {
					break;
				}
			}
			endGeneratingView(successful);
		}).start();
	}

	private void initGeneratingView() {
		lblProgressInfo.setText(CAPTION_WAIT);
		lblProgressInfo.setVisible(true);
		btnGenerate.setVisible(false);
		btnStop.setVisible(true);
		pbGeneratingProgress.setVisible(true);
		pbGeneratingProgress.setProgress(0);
	}

	private void updateGeneratingView(int successful, int totalAmount) {
		Platform.runLater(() -> {
			pbGeneratingProgress.setProgress((double) successful / totalAmount);
			lblProgressInfo.setText("Wygenerowano " + successful + "/" + totalAmount + " wykreślanek");
		});
	}

	private void endGeneratingView(int successful) {
		Platform.runLater(() -> {
			lblProgressInfo.setText("Koniec, wygenerowano " + successful + " wykreślanek");
			btnGenerate.setVisible(true);
			btnStop.setVisible(false);
		});
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
