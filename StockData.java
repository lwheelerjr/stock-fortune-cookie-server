import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;
import java.util.ArrayList;


public class StockData {

	String symbol = "";
	ArrayList<Quote> quotes = null;

	public StockData() {
	}

	public StockData(String symbol, ArrayList<Quote> quotes) {
		this.symbol = symbol;
		this.quotes = quotes;
	}	

	public static StockData fromJSON(String stockSymbol, String json) {
		ArrayList<Quote> newQuotes = new ArrayList();

		try {
			JSONObject obj = (JSONObject) new JSONTokener(json).nextValue();
			JSONObject query = (JSONObject) obj.get("query");
			Integer count = (Integer) query.get("count");
			JSONObject results = (JSONObject) query.get("results");
			JSONArray quotes = (JSONArray) results.get("quote");

            if (count > 0) {
                for (int i = 0; i<quotes.length(); ++i) {
                  JSONObject jobject = quotes.getJSONObject(i);

                  newQuotes.add(Quote.fromJSONObject(jobject));
                }
            }

			return new StockData(stockSymbol, newQuotes);

		} catch (JSONException je) {
			je.printStackTrace();
		}

  		return null;
	}


	public String getSymbol() {
		return symbol;
	}



	public String toString() {
		String string = "StockData:\n" + "   Symbol = " + symbol + "\n" +
		"   Quotes:\n";
		for(Quote quote : quotes) {
			string = string + "   " + quote.toString() + "\n";
		}
		return string;
	}



}