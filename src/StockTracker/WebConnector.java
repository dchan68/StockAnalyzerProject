package StockTracker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WebConnector {
	final int indexNumOfStockPriceFromGoogleFinance = 0;
	final int indexNumOfStockPriceChangeInDollarsFromGoogleFinance = 1;
	final int indexNumOfStockPriceChangeInPercentageFromGoogleFinance = 2;
	
	public BufferedReader establishConnection(String s) throws IOException {
		BufferedReader buff;	
		URL url = new URL("https://www.google.com/finance/quote/"+s+":TSE");
		URLConnection urlConn = url.openConnection();
		InputStreamReader inStream = new InputStreamReader(urlConn.getInputStream());
		buff = new BufferedReader(inStream);
		return buff;
	}
	
	public List<String> gatherStockPrice(String[] SYM) throws IOException{
		
		ArrayList<String> StockPrice = new ArrayList<>();
		List<String> parsedData = new ArrayList<>();
		String parsedDataString;
		for (String s : SYM) {
			
			BufferedReader buff = establishConnection(s);
			String line = buff.readLine();	
			parsedData = findDataFromFile(s, line, buff, parsedData);

			System.out.println("price of " + s + ":");
			parsedDataString = parsedData.toString();
			System.out.println(filterStockInfo(parsedDataString, indexNumOfStockPriceFromGoogleFinance));
			StockPrice.add(filterStockInfo(parsedDataString, indexNumOfStockPriceFromGoogleFinance));

		}
		
		return StockPrice;
	}
	
	public List<String> gatherChangeInStockPriceInDollars(String[] SYM) throws IOException{
		ArrayList<String> StockPriceChange = new ArrayList<>();
		List<String> parsedData = new ArrayList<>();
		String parsedDataString;
		
		for (String s : SYM) {		
			BufferedReader buff = establishConnection(s);
			String line = buff.readLine();	
			parsedData = findDataFromFile(s, line, buff, parsedData);

			System.out.println("price change of " + s + ":");
			parsedDataString = parsedData.toString();			
			System.out.println(filterStockInfo(parsedDataString, indexNumOfStockPriceChangeInDollarsFromGoogleFinance));
			StockPriceChange.add(filterStockInfo(parsedDataString, indexNumOfStockPriceChangeInDollarsFromGoogleFinance));
		}
		return StockPriceChange;
	}
	
	public List<String> findDataFromFile(String s, String line, BufferedReader buff, List<String> parsedData) throws IOException {
		
		String priceChange = "not found";
		while(line !=null) {
			if(line.contains("[\""+s+"\",")) {
				int target = line.indexOf("[\""+s+"\",");
				int deci = line.indexOf(".", target);
				int start = deci;
				while (line.charAt(start) != '[') {
					start--;
				}
				priceChange = line.substring(start + 1, deci + 31);
				parsedData = Arrays.asList(priceChange.split(","));
			}

			line = buff.readLine();
		}
		return parsedData;
	}
	
	public String filterStockInfo(String parsedDataString, int index){
		String[] parts = parsedDataString.split(",");	
		if (parts[index].startsWith("[")) 
			parts[index] = parts[index].replace("[","");
		else if (parts[index].endsWith("]"))
			parts[index] = parts[index].replace("]","");
		return parts[index];	
	}
}
