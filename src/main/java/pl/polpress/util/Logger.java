package pl.polpress.util;

import logLibrary.FileLogger;

public class Logger {
	private FileLogger logger = new FileLogger("logs");
	private boolean errors;
	private boolean info;

	private Logger() {
		errors = "true".equals(Config.getString("errors"));
		info = "true".equals(Config.getString("info"));
	}
	
	public static Logger createLogger() {
		return new Logger();
	}
	
	public void logError(String... errorStatement) {
		if (errors) {
			logger.errorStatement(errorStatement);
		}
	}
	
	public void logInfo(String... infoStatement) {
		if (info) {
			logger.infoStatement(infoStatement);
		}
	}
}
