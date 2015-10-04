package interest.handler;

import interest.calculator.InterestCalculator;
import interest.exception.ExceptionHandler;
import interest.service.Publisher;
import interest.service.ServiceListener;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class MessageHandler implements ServiceListener{

	private String token = "unknown";
	
	public void setToken(String token) {
		this.token = token;
	}
	
	private Publisher publisher;
	
	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}
	
	/**
	 * IMPLEMENTED METHODS
	 */
	
	public void getMessage(String message) throws Exception{
		try {
			handle (decode(message) );
		} catch (IOException e) {
			System.out.println("$MessageHandler> *******************************");
			System.out.println("$MessageHandler> "+e.getMessage());
			System.out.println("$MessageHandler> No new message can be generated or published!");
			System.out.println("$MessageHandler> *******************************");
			throw new Exception(e.getMessage());
		}
	}

	private JSONObject decode(String message) {

		
		System.out.println("$MessageHandler>  [x] Decoding message");
		
		JSONParser jParser = new JSONParser();
		JSONObject jObject = null;
		try {
			jObject = (JSONObject) jParser.parse(message);
			
			System.out.println( "$MessageHandler>  [-sum-] " + jObject.get("sum"));
			System.out.println( "$MessageHandler>  [-days-] " + jObject.get("days"));
		
		} catch (ParseException e) {
			ExceptionHandler.handle(e);
		}
		
		return jObject;
	}
	
	private void handle (JSONObject jObject) throws IOException {
		if (jObject == null) {
			System.err.println("$MessageHandler> <<<<< SOMETHING WENT WRONG !?! >>>>>");
		}
		
		if (jObject.get("sum") == null)
			throw new IOException ("Invalid unput data: 'sum' cannot be null");
		if (jObject.get("days") == null)
			throw new IOException ("Invalid unput data: 'days' cannot be null");
		
		double sum = Double.parseDouble(jObject.get("sum").toString());
		int days = Integer.parseInt(jObject.get("days").toString());
		double interest;
		try {
			interest = InterestCalculator.getInterest(sum, days);
		}
		catch (IllegalArgumentException e) {
			throw new IOException ("Invalid input data: " + e.getMessage());
		}
		
		
		double totalSum = (new BigDecimal(sum + "").add(new BigDecimal(interest + ""))).doubleValue();
		
		encode (sum, days, interest, totalSum);
		
	}
	
	private void encode(double sum, int days, double interest, double totalSum)	{
		
		Map<String, Object> msgObject = new LinkedHashMap<String, Object>();	
		
		msgObject.put("sum", sum);
		msgObject.put("days", days);
		msgObject.put("interest", interest);
		msgObject.put("totalSum", totalSum);
		msgObject.put("token", token);
				
		publisher.publish(JSONValue.toJSONString(msgObject));
	}
	
	
}
