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
	
	//connects to the given URL and so we can later receive all the HTML code from google finance for each stock symbol
	public BufferedReader establishConnection(String s) throws IOException {
		BufferedReader buff;	
		URL url = new URL("https://www.google.com/finance/quote/"+s+":TSE");
		URLConnection urlConn = url.openConnection();
		InputStreamReader inStream = new InputStreamReader(urlConn.getInputStream());
		buff = new BufferedReader(inStream);
		return buff;
	}
	
	//Gets the stock price for each SYM (symbol)
	public List<String> gatherStockPrice(String[] SYM) throws IOException{
		
		ArrayList<String> StockPrice = new ArrayList<>();
		List<String> parsedData = new ArrayList<>();
		String parsedDataString;
		for (String s : SYM) {
			//retrieves every line of HTML code from google finance for each SYM and stores it in String line
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

			System.out.println("price change of " + s + " in Dollars:");
			parsedDataString = parsedData.toString();			
			System.out.println(filterStockInfo(parsedDataString, indexNumOfStockPriceChangeInDollarsFromGoogleFinance));
			StockPriceChange.add(filterStockInfo(parsedDataString, indexNumOfStockPriceChangeInDollarsFromGoogleFinance));
		}
		return StockPriceChange;
	}
	
	public List<String> gatherChangeInStockPriceInPercentage(String[] SYM) throws IOException{
		ArrayList<String> StockPriceChange = new ArrayList<>();
		List<String> parsedData = new ArrayList<>();
		String parsedDataString;
		
		for (String s : SYM) {
			BufferedReader buff = establishConnection(s);
			String line = buff.readLine();	
			parsedData = findDataFromFile(s, line, buff, parsedData);
			
			System.out.println("price change of " + s + " in Percentage:");
			parsedDataString = parsedData.toString();
			System.out.println(filterStockInfo(parsedDataString, indexNumOfStockPriceChangeInPercentageFromGoogleFinance));
			StockPriceChange.add(filterStockInfo(parsedDataString, indexNumOfStockPriceChangeInPercentageFromGoogleFinance));
		}		
		return StockPriceChange;
	}
	
	//receives all lines of HTML code and finds a specific data from the HTML code, such as one below
	//ex: ["SHOP","TSE"],"Shopify Inc",0,"CAD",[837.01,-0.090026855,-0.01075474,2,3,3]
	public List<String> findDataFromFile(String s, String line, BufferedReader buff, List<String> parsedData) throws IOException {
		
		String priceChange = "not found";
		while(line !=null) {
			if(line.contains("[\""+s+"\",")) { //To find our desired code, we want to start with finding, ex: ["SHOP",
				int target = line.indexOf("[\""+s+"\",");  //store the location of, ex: ["SHOP",
				int deci = line.indexOf(".", target);  //finds the first decimal point, ex the dec from 837.01
				int start = deci;
				while (line.charAt(start) != '[') {
					start--;
				}
				priceChange = line.substring(start + 1, deci + 31); //tries to get all the digits within array as best it can but not perfect. Ex we can retrieve837,-0.090026855,-0.01075474,2,3,3], OR 11.79,-0.40999985,-3.3606546,2,2, 	
				parsedData = Arrays.asList(priceChange.split(",")); //Splits each element gathered from above code line, separate each element by their commas, and stores it into List<String>
			}

			line = buff.readLine();
		}
		return parsedData;
	}
	
	//receives an array such as [837.01,-0.090026855,-0.01075474,2,3,3], and takes out the element needed based on the index.
	//Ex: index 0 is the price which is $837.01, index 1 is change in price in $, index 2 is change in price in %
	public String filterStockInfo(String parsedDataString, int index){
		String[] parts = parsedDataString.split(",");	
		if (parts[index].startsWith("[")) 
			parts[index] = parts[index].replace("[","");  
		else if (parts[index].endsWith("]"))
			parts[index] = parts[index].replace("]","");
		return parts[index];	
	}
}
