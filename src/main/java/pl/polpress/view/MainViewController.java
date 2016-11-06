package pl.polpress.view;

import java.io.File;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import pl.polpress.Main;
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
	private Button btnDirectorySetter;
	@FXML
	private ImageView ivDirectorySetterIcon;
	@FXML
	private TextField tfName;
	@FXML
	private Slider slAmount;

	private final String CAPTION_GENERATE = "Generuj";
	private final String CAPTION_STOP = "Zatrzymaj";
	private final String CAPTION_WAIT = "Czekaj... trwa generowanie wykreślanek";

	private Stage stage;
	private boolean end = false;

	@FXML
	private void initialize() {
		tfName.setText(Config.getString("default_name"));
		initDirectoryField();
		initAmountField();
		ivDirectorySetterIcon.setImage(new Image(Main.class.getResourceAsStream("/folder.png")));

		tfName.textProperty().addListener((ov, oldValue, newValue) -> {
			if (containsIllegalChar(newValue)) {
				tfName.setText(oldValue);
			}
		});

		slAmount.valueProperty().addListener((ov, oldValue, newValue) -> {
			tfAmount.setText(Integer.toString((int) slAmount.getValue()));
		});

		tfAmount.textProperty().addListener((ov, oldValue, newValue) -> {
			char last = newValue.charAt(newValue.length() - 1);
			if (last < 48 || last > 57) {
				tfAmount.setText(oldValue);
			}
		});
	} 
	private boolean containsIllegalChar(String value) {
		return value.contains("%") || value.contains("*") || value.contains(".") || value.contains(",")
				|| value.contains("{") || value.contains("}") || value.contains("/") || value.contains("\\")
				|| value.contains(":") || value.contains(">") || value.contains("<") || value.contains("?")
				|| value.contains("|") || value.contains("\"");
	}

	public void setStage(Stage stage) {
		this.stage = stage;
		this.stage.setOnCloseRequest((e) -> {
			saveSettings();
			end = true;
		});
	}

	private void initDirectoryField() {
		tfDirectory.setText(Config.getString("default_dir"));
	}

	private void initAmountField() {
		tfAmount.setText(Config.getString("amount"));
		slAmount.setValue(Integer.parseInt(tfAmount.getText()));
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
	private void setDirectory() {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setTitle("Wybierz katalog");
		File file = directoryChooser.showDialog(stage);
		tfDirectory.setText(file.getPath());
	}

	@FXML
	private void generate() {
		if (btnRun.getText().equals(CAPTION_STOP)) {
			end = true;
		} else {
			cleanWindow();
			if (!validateAmount())
				return;
			if (!validateDirectory())
				return;
			generatePuzzle();
		}
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
		Config.setString("default_name", tfName.getText());
	}

	private void generatePuzzle() {
		reinitializeEmptyAmountField();
		int count = Integer.parseInt(tfAmount.getText());
		generatePuzzle(count, tfDirectory.getText() + File.separator);
	}

	private void generatePuzzle(int count, String path) {
		lblWait.setText(CAPTION_WAIT);
		lblWait.setVisible(true);
		btnRun.setText(CAPTION_STOP);
		new Thread(() -> {
			int successful = 0;
			while (successful < count) {
				String filename = path + tfName.getText() + successful + ".pdf";
				if (new JRPrinter().print(createPuzzle(), filename)) {
					successful++;
				}
				if (end) {
					break;
				}
			}
			int finallySuccessful = successful;
			end = false;
			Platform.runLater(() -> {
				lblWait.setText("Wygenerowano " + finallySuccessful + " wykreślanek");
				btnRun.setText(CAPTION_GENERATE);
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
