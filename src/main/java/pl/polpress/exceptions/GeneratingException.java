package pl.polpress.exceptions;

public class GeneratingException extends Exception {
	private final String message;

	public GeneratingException() {
		this("");
	}

	public GeneratingException(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}
}
