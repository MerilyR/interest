package interest.server;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

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
			System.out.println("---Connection channel created---");
			return true;
			
		} catch (IOException e) {
			printException(e);
			return false;
		} catch (TimeoutException e) {
			printException(e);
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
			printException(e);
		}
	}

	public void unsubscribe(String queue) {
		try {
			getChannel().basicCancel(consumerTag);
			consumerTag = null;
		} catch (IOException e) {
			printException(e);
		}
	}
	
	
	private void printException(Exception e) {
		System.out.println("--- Exception caught --- ");
		System.out.println(e.getClass());
		System.out.println(e.getMessage());
		System.out.println(e.getStackTrace()[0]);
		//e.printStackTrace();
	}
	
}
