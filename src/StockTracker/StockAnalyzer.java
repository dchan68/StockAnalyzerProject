package StockTracker;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import fileUtilities.FileWriting;

//Got the idea to retrieve Stock price from https://www.youtube.com/watch?v=UVqjMbYlCFs&t=310s. Aside from connecting
//to URL and retrieve stock price, the rest was my own idea
public class StockAnalyzer {

	public static void main(String[] args) throws IOException{
		final String[] SYM = {"MOGO", "SHOP", "ABST", "PPL", "ENB"};
		final String fileName = "StockReports.txt";
		List<String> StockPrice = new ArrayList<>();
		List<String> ChangeInStockPriceInDollars = new ArrayList<>();
		List<String> ChangeInStockPriceInPercentage = new ArrayList<>();
		WebConnector wb = new WebConnector();
		FileWriting fw = new FileWriting();
		
		StockPrice = wb.gatherStockPrice(SYM);
		ChangeInStockPriceInDollars = wb.gatherChangeInStockPriceInDollars(SYM);
		ChangeInStockPriceInPercentage = wb.gatherChangeInStockPriceInPercentage(SYM);
		System.out.println(StockPrice);
		System.out.println(ChangeInStockPriceInDollars);
		System.out.println(ChangeInStockPriceInPercentage);
		fw.createFile(fileName);
		fw.writeToFile(SYM, fileName, StockPrice, ChangeInStockPriceInDollars, ChangeInStockPriceInPercentage);
		
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
