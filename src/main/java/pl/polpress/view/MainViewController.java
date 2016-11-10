package pl.polpress.view;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class MainViewController extends AnchorPane { 
	@FXML
	private AnchorPane sudokuView; 
	@FXML
	private SudokuController sudokuViewController;
	@FXML
	private AnchorPane puzzleView;
	@FXML
	private WordPuzzleController puzzleViewController;
	
	@FXML
	private void initialize() {
	}

	public void setStage(Stage stage) {  
		sudokuViewController.setStage(stage);
		puzzleViewController.setStage(stage);
		
	}
}
