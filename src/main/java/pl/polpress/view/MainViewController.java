package pl.polpress.view;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import pl.polpress.Main;

public class MainViewController {
	@FXML
	private AnchorPane sudokuTabPage;
	@FXML
	private AnchorPane puzzleTabPage;

	private SudokuController sudokuController;
	private WordPuzzleController puzzleController;

	@FXML
	private void initialize() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/pl/polpress/view/SudokuView.fxml"));
			sudokuTabPage = (AnchorPane) loader.load();
			sudokuController = loader.getController();

			loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/pl/polpress/view/PuzzleView.fxml"));
			puzzleTabPage = (AnchorPane) loader.load();
			puzzleController = loader.getController();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setStage(Stage stage) {
	}
}
