package interest.server;

import interest.exception.ExceptionHandler;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;

public class ServerChannel {

	private Connection connection;
	private Channel channel;
	
	private final String HOST;
	private final String USER;
	private final String PASSWORD;
	
	public ServerChannel(String host, String user, String password) {
		this.HOST = host;
		this.USER = user;
		this.PASSWORD = password;
	}
	
	public boolean createChannel() {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(HOST);
		factory.setUsername(USER);
		factory.setPassword(PASSWORD);
		
		try {
			connection = factory.newConnection();
			channel = connection.createChannel();
			System.out.println("~Channel> ---Connection channel created---");
			return true;
			
		} catch (IOException e) {
			ExceptionHandler.log(e);
			return false;
		} catch (TimeoutException e) {
			ExceptionHandler.log(e);
			return false;
		}
	}
	
	public Channel getChannel() {
		return channel;
	}
	
	private String consumerTag;
	
	public void subscribe(String queue, Consumer consumer) {
		if (consumerTag != null)
			unsubscribe(queue);		
		try {			
			consumerTag = getChannel().basicConsume(queue, true, consumer);
		} catch (IOException e) {
			ExceptionHandler.log(e);
		}
	}

	public void unsubscribe(String queue) {
		try {
			getChannel().basicCancel(consumerTag);
			consumerTag = null;
		} catch (IOException e) {
			ExceptionHandler.log(e);
		}
	}
	
	String publishQueue = "";
	
	public void prepareToPublish(String queue) {
		
		publishQueue = queue;

	}

	public void publish(String message) {
		
		if (publishQueue.equals(""))
			ExceptionHandler.log(new IOException("~Channel> Queue to publish to has not been set!"));
		try {
			AMQP.BasicProperties properties = new AMQP.BasicProperties();
			properties = properties.builder().contentType("application/json").build();
			getChannel().basicPublish("", publishQueue, properties, message.getBytes(Charset.forName("UTF-8")));
		} catch (IOException e) {
			ExceptionHandler.log(e);
		}
		
	}
	
	
	
}
