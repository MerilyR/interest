package interest.handler;

import interest.service.ServiceListener;

import java.sql.Timestamp;
import java.util.Date;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class MessageHandler implements ServiceListener{

	
	
	/**
	 * IMPLEMENTED METHODS
	 */
	
	public void getMessage(String message) {
		Date date = new Date();
		System.out.println("Message received at " + new Timestamp(date.getTime()) + ":");
		System.out.println(" [x] Received '"+message+"'");
		
		decode(message);
	}

	private void decode(String message) {
		
		System.out.println(" [x] Decoding message");
		
		JSONParser jParser = new JSONParser();
		JSONObject jObject;
		try {
			jObject = (JSONObject) jParser.parse(message);
			
			System.out.println( "[-sum-] " + jObject.get("sum"));
			System.out.println( "[-days-] " + jObject.get("days"));
		
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	
}
