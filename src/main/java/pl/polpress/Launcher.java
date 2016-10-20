package pl.polpress;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import pl.polpress.util.Logger;

public class Launcher {
	private final Logger logger = Logger.createLogger();
	
	public void start(Stage stage) {
		try {
			run(stage);
		} catch(IOException e) {
			logger.logError("[Launcher.start]", e.getMessage());
			e.printStackTrace();
		}
	}
	
	private void run(Stage stage) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/pl/polpress/view/MainView.fxml"));
		AnchorPane rootLayout = (AnchorPane) loader.load();
		stage.centerOnScreen();
		stage.setTitle("Wykreslanka");
		stage.setScene(new Scene(rootLayout));
		stage.show();
	}
}
