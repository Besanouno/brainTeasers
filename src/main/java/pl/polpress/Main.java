package pl.polpress;

import javafx.application.Application;
import javafx.stage.Stage;
import pl.polpress.pdf.JRPrinter;

public class Main extends Application {
	public static void main(String[] args) {
		new JRPrinter().print();
		System.out.println("już");
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		new Launcher().start(primaryStage);
	}
}
