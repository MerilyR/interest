package interest.service;

import java.sql.Timestamp;
import java.util.Date;

public class MessagePublisher implements Publisher {

	/**
	 * IMPLEMENTED METHODS
	 */
		
	public boolean publish(String message) {
		
		System.out.println();
		System.out.println("$Publisher> ---Publishing message ---");

		Date date = new Date();
		System.out.println("$Publisher> Message published at " + new Timestamp(date.getTime()) + ":");
		System.out.println("$Publisher>  [x] Published '"+message+"'");
		
		return true;
	}

}
