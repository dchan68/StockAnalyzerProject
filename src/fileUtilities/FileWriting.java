package fileUtilities;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileWriting {
	public void createFile(String fileName) {
	    try {
	    	File myFile = new File(fileName);
	    	if(!myFile.exists()) {
	    		myFile.createNewFile();
	    	}else {
	    		System.out.println("File already exists");
	    	}
	      } catch (IOException e) {
	        System.out.println("An error occurred.");
	        e.printStackTrace();
	    }	
	}
	
	public void writeToFile(String[] SYM, String fileName) {

	}
}
