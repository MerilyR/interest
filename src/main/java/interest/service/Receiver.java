package interest.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;

public class Receiver {

	List<ServiceListener> listeners = new ArrayList<ServiceListener>();
		
	public void addListener (ServiceListener listener) {
		listeners.add(listener);
	}

	private String host;
	
	public void setHost(String host) {
		this.host = host;
	}

	private String user; 
	
	public void setUser(String user) {
		this.user = user;		
	}

	private String pass; 
	
	public void setPass(String pass) {
		this.pass = pass;
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

	Connection connection;
	Channel channel;
	Consumer consumer;
	
	public void startService() {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(host);
		factory.setUsername(user);
		factory.setPassword(pass);
		
		try {
			connection = factory.newConnection();
			channel = connection.createChannel();
			
			consumer = new DefaultConsumer(channel) {
				@Override
				public void handleDelivery(String consumerTag,
						Envelope envelope, BasicProperties properties,
						byte[] body) throws IOException {
					String message = new String(body, "UTF-8");
					publish(message);
					messagesReceived ++;
					
					if (messagesReceived == messages)
						stopService();
				}
			};
			
			channel.basicConsume(queue, true, consumer);
			System.out.println("---Service started---");
			System.out.println("---Receiving " + messages + " messages --- ");
			System.out.println(" [*] Waiting for messages: ");
			
			
			
		} catch (IOException e) {
			printException(e);
		} catch (TimeoutException e) {
			printException(e);
		}
		
	}
	
	protected void stopService() {
	    try {
	    	channel.close();
			connection.close();
			System.out.println("---Service stopped---");
		} catch (IOException e) {
			printException(e);
		} catch (TimeoutException e) {
			printException(e);
		} 
	}

	private void publish(String message) {
		for (ServiceListener listener: listeners)
			listener.getMessage(message);
	}
	
	private void printException(Exception e) {
		System.out.println("--- Exception caught --- ");
		System.out.println(e.getClass());
		System.out.println(e.getMessage());

		e.printStackTrace();
		}
	
}
