package pl.polpress.util;

import java.io.File;

public class Directory {
	public boolean validate(String path) {
		File file = new File(path);
		if (file.exists()) {
			if (!file.isDirectory()) {
				return false;
			}
		} else {
			return file.mkdirs(); 
		}
		return true;
	}
}
