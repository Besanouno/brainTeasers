package pl.polpress.wordPuzzle.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pl.polpress.util.Logger;

public class WordsDownloaderImpl implements WordsDownloader {
	private Socket socket;
	private String[] words; 
    private final String PAGE_WITH_WORDS = "http://telepress24.com/losowe/";
	private final Logger logger = Logger.createLogger();

	@Override
	public String[] downloadWords() {
		if (connect(PAGE_WITH_WORDS)) {
			try {
				findWordsInPage();
			} catch (IOException e) {
				logger.logError("<WordsDownloaderImpl.downloadWords>", e.getMessage());
			}
		}
		return words;
	}
	
	private boolean connect(String urlAddress) {
		URL website;
		try {
			website = new URL(urlAddress);
		} catch (MalformedURLException e) { 
			logger.logError("<WordsDownloaderImpl.connect>", e.getMessage());
			return false;
		}
		try {
			socket = new Socket(website.getHost(), 80);
			socket.setKeepAlive(false);
			socket.setSoTimeout(5000);
			sendRequest(website.getHost(), website.getPath(), 80);
		} catch (IOException e) {
			logger.logError("<WordsDownloaderImpl.connect>", e.getMessage());
			return false;
		}
		return true;
	}

	private void sendRequest(String address, String path, int port) throws IOException {
		OutputStream out = socket.getOutputStream();
		PrintWriter writer = new PrintWriter(new OutputStreamWriter(out, "UTF-8"));
		writer.println("GET /" + path + " HTTP/1.1");
		writer.println("Host: " + address);
		writer.println("Connection: close");
		writer.println();
		writer.flush();
	}

	private void findWordsInPage() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
		String singleLine;
		while ((singleLine = reader.readLine()) != null) {
			if (findWordsInLine(singleLine)) {
				break;
			}
		}
	}

	private boolean findWordsInLine(String text) {
		String regex = ".*id=\"rzeczowniki\">([^<]*)<.*";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(text);
		if (matcher.find()) {
			String result = matcher.group(1); 
			words = result.split(",");
			return words == null;
		}
		return false;
	}
}
