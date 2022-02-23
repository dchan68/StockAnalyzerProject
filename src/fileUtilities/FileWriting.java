package fileUtilities;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.Formatter;
import java.util.List;

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
	
	public void writeToFile(String[] SYM, String fileName, List<String> StockPrice, List<String> ChangeInStockPriceInDollars, List<String> ChangeInStockPriceInPercentage) throws IOException {

		java.util.Date date = new java.util.Date();
		try(FileWriter fw = new FileWriter(fileName, true);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter out = new PrintWriter(bw)){
			out.println("------------------------------------------------------------------");
			out.println("Stock info for " + date);
			out.printf("%-15s%-18s%-20s%-20s\n", "Symbol", "Price", "Change in $", "Change in %");
			out.println("------------------------------------------------------------------");
			for (int i = 0;i<SYM.length;i++) {
				out.printf("%-14s %-16s %-19s %-20s\n", SYM[i], StockPrice.get(i), ChangeInStockPriceInDollars.get(i), ChangeInStockPriceInPercentage.get(i));
			}
			out.println("------------------------------------------------------------------");
			} catch (IOException e) {
				System.err.println(e);
			}
	}
}
