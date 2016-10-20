package pl.polpress.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class Config {
	private static Properties properties = new Properties();
	private static final String CONFIG_FILEPATH = "properties.txt";

	static {
		try {
			init();
		} catch (Exception e) {
		}
	}

	public static String getString(String key) {
		return properties.getProperty(key);
	}

	public static void setString(String key, String value) {
		properties.setProperty(key, value);
		try {
			properties.store(new FileWriter(new File(CONFIG_FILEPATH)), null);
		} catch (IOException e) {

		}
	}

	private static void init() throws FileNotFoundException, IOException {
		if (!isFileExist(CONFIG_FILEPATH)) {
			createConfigFile();
		}
		properties.load(new FileReader(new File(CONFIG_FILEPATH)));
	}

	private static boolean isFileExist(String filepath) {
		return new File(filepath).exists();
	}

	private static void createConfigFile() {
		try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(CONFIG_FILEPATH, true))) {
			bufferedWriter.write("errors=false" + System.lineSeparator());
			bufferedWriter.write("info=false" + System.lineSeparator());
			bufferedWriter.flush();
		} catch (IOException e) {
		}
	}
}
