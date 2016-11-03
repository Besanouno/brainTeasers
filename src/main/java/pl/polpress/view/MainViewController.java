package pl.polpress.view;

import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import pl.polpress.pdf.JRPrinter;
import pl.polpress.util.Config;

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
		int successful = 0;
		while (successful < count) {
			if (new JRPrinter().print(tfDirectory.getText() + File.separator + successful + ".pdf")) {
				successful++;
			}

		}
	}
}
