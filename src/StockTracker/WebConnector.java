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
	
	public BufferedReader establishConnection(String s) throws IOException {
		BufferedReader buff;
		
		URL url = new URL("https://www.google.com/finance/quote/"+s+":TSE");
		URLConnection urlConn = url.openConnection();
		InputStreamReader inStream = new InputStreamReader(urlConn.getInputStream());
		buff = new BufferedReader(inStream);
		return buff;
	}
	
	public ArrayList<String> gatherStockPrice(String[] SYM) throws IOException{
		
		ArrayList<String> StockPrice = new ArrayList<>();
		for (String s : SYM) {
			
			BufferedReader buff = establishConnection(s);
			String price = "not found";

			String line = buff.readLine();	
			while(line !=null) {
				//System.out.println(line);
				if(line.contains("[\""+s+"\",")) {
					int target = line.indexOf("[\""+s+"\",");
					int deci = line.indexOf(".", target);
					int start = deci;
					while (line.charAt(start) != '[') {
						start--;

					}
					price = line.substring(start + 1, deci + 3);
				}

				line = buff.readLine();

			}
			System.out.println("price of " + s + ":");
			System.out.println(price);
			
			StockPrice.add(price);
		}
		
		return StockPrice;
	}
	
	public List<String> gatherChangeInStockPrice(String[] SYM) throws IOException{
		ArrayList<String> StockPriceChange = new ArrayList<>();
		List<String> parsedData = new ArrayList<>();
		String parsedDataString;
		String lineToParse;
		for (String s : SYM) {
			
			BufferedReader buff = establishConnection(s);
			String priceChange = "not found";

			String line = buff.readLine();	
			while(line !=null) {
				//System.out.println(line);
				if(line.contains("[\""+s+"\",")) {
					int target = line.indexOf("[\""+s+"\",");
					
					int deci = line.indexOf(".", target);
					int start = deci;
					while (line.charAt(start) != '[') {
						start--;
					}
					lineToParse = line.substring(start + 1, deci + 31);
					
					parsedData = Arrays.asList(lineToParse.split(","));
				}

				line = buff.readLine();

			}
			System.out.println("price change of " + s + ":");
			parsedDataString = parsedData.toString();			
			System.out.println(filterStockPriceChange(parsedDataString));
			StockPriceChange.add(filterStockPriceChange(parsedDataString));
		}
		return StockPriceChange;
	}
	
	public String filterStockPriceChange(String parsedDataString){
		String[] parts = parsedDataString.split(",");	
		return parts[1];	
	}
}
