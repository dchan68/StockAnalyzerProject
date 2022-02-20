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
		List<String> StockPrice = new ArrayList<>();
		List<String> ChangeInStockPrice = new ArrayList<>();
		WebConnector wb = new WebConnector();
		FileWriting fw = new FileWriting();
		
		StockPrice = wb.gatherStockPrice(SYM);
		ChangeInStockPrice = wb.gatherChangeInStockPriceInDollars(SYM);
		System.out.println(StockPrice);
		System.out.println(ChangeInStockPrice);
		//fw.createFile(fileName);
		//fw.writeToFile(SYM, fileName);
		
//		BufferedReader buff;
//		
//		URL url = new URL("https://www.google.com/finance/quote/SHOP:TSE");
//		URLConnection urlConn = url.openConnection();
//		InputStreamReader inStream = new InputStreamReader(urlConn.getInputStream());
//		buff = new BufferedReader(inStream);
//		
//		String line = buff.readLine();
//		while(line !=null) {
//			System.out.println(line);
//			line = buff.readLine();
//		}
		
	}
}
