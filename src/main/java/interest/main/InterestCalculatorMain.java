package interest.main;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import interest.exception.ExceptionHandler;
import interest.handler.MessageHandler;
import interest.server.ServerChannel;
import interest.service.Publisher;
import interest.service.Subscriber;
import interest.service.impl.MessagePublisher;
import interest.service.impl.MessageSubscriber;

public class InterestCalculatorMain{

	private static final String CONFIG_FILE = "config.txt";
	
	private static String HOST;
	private static String USER;
	private static String PASSWORD;
	private static int MESSAGES;
	private static String TOKEN;
	private static String INPUT;
	private static String OUTPUT;
	
	
	public static void main(String[] args) {
		
		
		
		readConfig(CONFIG_FILE);
		
		ServerChannel sChannel = createChannel();	
		sChannel.createChannel();
		Subscriber subscriber = createSubscriber(sChannel);
		Publisher publisher = createPublisher(sChannel);
		MessageHandler msgHandler = createMessageHandler(publisher);
		subscriber.addListener(msgHandler);
		subscriber.startService();
		
	}
	
	private static void readConfig (String fileName) {

		JSONParser jParser = new JSONParser();
		JSONObject jObject = null;
		
		try {
			jObject = (JSONObject) jParser.parse(new FileReader(fileName));
			System.out.println(jObject.toJSONString());
			HOST = jObject.get("Server").toString();
			USER = jObject.get("User").toString();
			PASSWORD = jObject.get("Password").toString();
			MESSAGES = Integer.parseInt(jObject.get("Messages").toString());
			TOKEN = jObject.get("Token").toString();
			INPUT = jObject.get("Input").toString();
			OUTPUT = jObject.get("Output").toString();
		
		} catch (ParseException e) {
			ExceptionHandler.log(e);
		} catch (FileNotFoundException e) {
			ExceptionHandler.log(e);
		} catch (IOException e) {
			ExceptionHandler.log(e);
		}
		
	}

	private static MessageHandler createMessageHandler(Publisher publisher) {
		MessageHandler msgHandler = new MessageHandler();
		msgHandler.setPublisher(publisher);
		
		msgHandler.setToken(TOKEN);
		
		return msgHandler;
	}

	private static Subscriber createSubscriber(ServerChannel sChannel) {
		Subscriber s = new MessageSubscriber (sChannel);
		
		s.setQueue (INPUT);
		s.setNrOfMessages(MESSAGES);
		
		return s;
	}
	
	private static Publisher createPublisher(ServerChannel sChannel) {
		Publisher p = new MessagePublisher(sChannel);
		
		p.setQueue(OUTPUT);
		
		return p;
	}

	private static ServerChannel createChannel() {
		
		return new ServerChannel(HOST, USER, PASSWORD);
	}

	
}
