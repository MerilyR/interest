package interest.service.impl;

import interest.server.ServerChannel;
import interest.service.Publisher;

import java.sql.Timestamp;
import java.util.Date;

public class MessagePublisher implements Publisher {

	ServerChannel sChannel;	
	public MessagePublisher(ServerChannel sChannel) {
		this.sChannel = sChannel;
	}
	
	/**
	 * IMPLEMENTED METHODS
	 */
	
	private String queue;
	public void setQueue(String queue) {
		this.queue = queue;
	}
		
	public boolean publish(String message) {
		
		System.out.println();
		System.out.println("$Publisher> ---Publishing message ---");

		Date date = new Date();
		System.out.println("$Publisher> Message published at " + new Timestamp(date.getTime()) + ":");
		System.out.println("$Publisher>  [x] Published '"+message+"'");
		
		sChannel.prepareToPublish(queue);
		sChannel.publish(message);
		
		return true;
	}

}
