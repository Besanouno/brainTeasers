package pl.polpress.util;

import java.util.Collections;
import java.util.List;

public class Parser {
	public String getPrintableWordsList(List<String> words) {
		Collections.sort(words);
		String result = "";
		for (int i = 0; i < words.size()-1; i++) {
			result += words.get(i) + ", "; 
		}
		result += words.get(words.size()-1);
		return result;
	}
}
