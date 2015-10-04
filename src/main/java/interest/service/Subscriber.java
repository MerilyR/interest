package interest.service;

import interest.server.ServerChannel;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class Subscriber {

	ServerChannel sChannel;	
	public Subscriber(ServerChannel sChannel) {
		this.sChannel = sChannel;
	}
	
	List<ServiceListener> listeners = new ArrayList<ServiceListener>();
	public void addListener (ServiceListener listener) {
		listeners.add(listener);
	}	
	public boolean removeListener (ServiceListener listener) {
		return listeners.remove(listener);
	}

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
		System.out.println("---Subscription started---");
		System.out.println("---Subscribed to queue > " + queue);
		System.out.println("---Receiving limited to " + messages + " messages --- ");
		System.out.println(" [*] Waiting for messages: ");	
		
	}
	
	protected void stopService() {
	    sChannel.unsubscribe(queue);
		System.out.println("---Unsubscribed from queue > " + queue);
	}

	private void publish(String message) {
		for (ServiceListener listener: listeners)
			listener.getMessage(message);
	}
	
	private void handleReceivedMessages (byte[] body)
			throws UnsupportedEncodingException {
		
		String message = new String(body, "UTF-8");
		publish(message);
		messagesReceived ++;
		
		if (messagesReceived == messages)
			stopService();
		
	}	
}
