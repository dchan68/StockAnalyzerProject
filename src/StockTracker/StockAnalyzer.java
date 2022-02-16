package StockTracker;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import fileUtilities.FileWriting;


public class StockAnalyzer {

	public static void main(String[] args) throws IOException{
		final String[] SYM = {"MOGO", "SHOP", "ABST", "PPL", "ENB"};
		final String fileName = "StockCollections.txt";
		ArrayList<String> StockPrice = new ArrayList<>();
		ArrayList<String> ChangeInStock = new ArrayList<>();
		WebConnector wb = new WebConnector();
		FileWriting fw = new FileWriting();
		
		StockPrice = wb.gatherStockPrice(SYM);

		System.out.println(StockPrice);
		//fw.createFile(fileName);
		//fw.writeToFile(SYM, fileName);
		
	}
}
