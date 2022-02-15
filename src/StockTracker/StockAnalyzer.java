package StockTracker;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.URL;
import java.net.URLConnection;


public class StockAnalyzer {

	public static void main(String[] args) throws IOException{
		final String SYM = "SHOP";
		URL url = new URL("https://www.google.com/finance/quote/SHOP:TSE");
		URLConnection urlConn = url.openConnection();
		InputStreamReader inStream = new InputStreamReader(urlConn.getInputStream());
		BufferedReader buff = new BufferedReader(inStream);
		String price = "not found";
		
		String line = buff.readLine();
		while(line !=null) {
			//System.out.println(line);
			if(line.contains("[\"SHOP\",")) {
				int target = line.indexOf("[\"SHOP\",");
				int deci = line.indexOf(".", target);
				int start = deci;
				while (line.charAt(start) != '[') {
					start--;
				}
				price = line.substring(start + 1, deci + 3);
			}
			
			line = buff.readLine();
		}
		System.out.println(price);
	}

}
