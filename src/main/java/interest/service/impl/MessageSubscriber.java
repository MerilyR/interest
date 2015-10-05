package interest.service.impl;

import interest.exception.ExceptionHandler;
import interest.server.ServerChannel;
import interest.service.ServiceListener;
import interest.service.Subscriber;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class MessageSubscriber implements Subscriber {

	ServerChannel sChannel;	
	public MessageSubscriber(ServerChannel sChannel) {
		this.sChannel = sChannel;
	}

	private void publish(String message) {
		for (ServiceListener listener: listeners)
			try {
				listener.getMessage(message);
			} catch (Exception e) {
				ExceptionHandler.log(e);
			}
	}
	
	private void handleReceivedMessages (byte[] body)
			throws UnsupportedEncodingException {
		
		String message = new String(body, "UTF-8");
		Date date = new Date();
		System.out.println();
		System.out.println("$Subscriber> ---Receiving message---");
		System.out.println("$Subscriber> Message received at " + new Timestamp(date.getTime()) + ":");
		System.out.println("$Subscriber>  [x] Received '"+message+"'");
		
		publish(message);
		messagesReceived ++;
		
		if (messages > 0 && messagesReceived == messages)
			stopService();
		
	}	
	
	/**
	 * IMPLEMENTED METHODS
	 */
	
	private String queue;
	public void setQueue(String queue) {
		this.queue = queue;
	}
	
	private int messages;
	private int messagesReceived;	
	public void setNrOfMessages(int messages) {
		this.messages = messages;
		this.messagesReceived = 0;
	}
	
	List<ServiceListener> listeners = new ArrayList<ServiceListener>();
	public void addListener (ServiceListener listener) {
		listeners.add(listener);
	}	
	public boolean removeListener (ServiceListener listener) {
		return listeners.remove(listener);
	}
	
	/**
	 * SERVICE METHODS
	 */

	private Consumer consumer;

	public void startService() {		
		consumer = new DefaultConsumer(sChannel.getChannel()) {
			@Override
			public void handleDelivery(String consumerTag,
					Envelope envelope, BasicProperties properties,
					byte[] body) throws IOException {
				handleReceivedMessages(body);
			}
		};
		sChannel.subscribe(queue, consumer);
		System.out.println("$Subscriber> ---Subscription started---");
		System.out.println("$Subscriber> ---Subscribed to queue > " + queue);
		System.out.println("$Subscriber> ---Receiving limited to " + messages + " messages--- ");
		System.out.println("$Subscriber>  [*] Waiting for messages: ");	
		
	}
	
	public void stopService() {
	    sChannel.unsubscribe(queue);
	    System.out.println();
		System.out.println("$Subscriber>  Unsubscribed from queue > " + queue);
	}
	
}
