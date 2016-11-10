package pl.polpress.view;

import java.io.File;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pl.polpress.Main;
import pl.polpress.pdf.SudokuPdfCropper;
import pl.polpress.util.Config;
import pl.polpress.util.Directory;

public class SudokuController {
	@FXML
	private TextField tfOutputDirectory; 
	@FXML
	private Button btnOutputDirectorySetter;
	@FXML
	private ImageView ivOutputDirectorySetterIcon;
	@FXML
	private Label lblWrongDirectory; 
	@FXML
	private TextField tfSourceFile;
	@FXML
	private Button btnSourceFileSetter;
	@FXML
	private ImageView ivSourceFileSetterIcon;
	@FXML
	private Label lblWrongSourceFile;
	@FXML
	private TextField tfDefaultName; 
	@FXML
	private Button btnGenerate; 
	@FXML
	private Label lblInfo;
	 
	private final String INFO_GENERATED = "Wygenerowano!";
	private final String INFO_ERROR = "Nastąpił nieoczekiwany błąd, spróbuj ponownie!";
	
	private Stage stage;
	
	public void setStage(Stage stage) {
		this.stage = stage;
		this.stage.setOnCloseRequest((e) -> {
			saveSettings(); 
		});
	}
	@FXML
	private void initialize() {
		tfDefaultName.setText(Config.getString("default_name_s"));
		initDirectory(); 
		activateBlockadeOfIllegalCharactersInFilename(); 
	}
	
	private void initDirectory() {
		tfOutputDirectory.setText(Config.getString("default_dir_s"));
		ivOutputDirectorySetterIcon.setImage(new Image(Main.class.getResourceAsStream("/folder.png")));
		ivSourceFileSetterIcon.setImage(new Image(Main.class.getResourceAsStream("/folder.png")));
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

	@FXML
	private void setOutputDirectory() {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setTitle("Wybierz katalog"); 
		File file = directoryChooser.showDialog(stage);  
		tfOutputDirectory.setText(file == null? "" : file.getPath());
	}

	private void saveSettings() { 
		Config.setString("default_dir_s", tfOutputDirectory.getText());
		Config.setString("default_name_s", tfDefaultName.getText());
		Config.setString("source_file_s", tfSourceFile.getText());
	} 
	
	@FXML
	private void setSourceFile() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Wybierz plik źródłowy");
		File file = fileChooser.showOpenDialog(stage);
		tfSourceFile.setText(file.getAbsolutePath());
	}
	
	@FXML
	private void run() {
		if (!validateInput()) {
			return;
		}
		try {
			new SudokuPdfCropper().cutPdf(tfSourceFile.getText(), tfOutputDirectory.getText() + File.separator, tfDefaultName.getText());
		} catch (IOException e) {
			lblInfo.setText(INFO_ERROR);
		}
		lblInfo.setText(INFO_GENERATED);
	}
	
	private boolean validateInput() {
		boolean isSourceFileProperly = !tfSourceFile.getText().isEmpty() && new File(tfSourceFile.getText()).exists();
		lblWrongSourceFile.setVisible(!isSourceFileProperly);
		boolean isOutputDirectoryProperly = !tfOutputDirectory.getText().isEmpty() && new Directory().validate(tfOutputDirectory.getText());
		lblWrongDirectory.setVisible(isOutputDirectoryProperly);
		return isSourceFileProperly && isOutputDirectoryProperly;
	}

}
