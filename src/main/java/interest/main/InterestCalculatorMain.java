package interest.main;

import interest.handler.MessageHandler;
import interest.server.ServerChannel;
import interest.service.MessagePublisher;
import interest.service.Subscriber;

public class InterestCalculatorMain{

	public static void main(String[] args) {
		
		ServerChannel sChannel = createChannel();	
		sChannel.createChannel();
		Subscriber subscriber = createSubscriber(sChannel);
		MessageHandler msgHandler = createMessageHandler();
		subscriber.addListener(msgHandler);
		subscriber.startService();
		
	}

	private static MessageHandler createMessageHandler() {
		MessageHandler msgHandler = new MessageHandler();
		
		msgHandler.setPublisher(new MessagePublisher());
		msgHandler.setToken("themerru");
		
		return msgHandler;
	}

	private static Subscriber createSubscriber(ServerChannel sChannel) {
		Subscriber s = new Subscriber (sChannel);
		
		s.setQueue ("interest-queue");
		s.setNrOfMessages(10);
		
		return s;
	}

	private static ServerChannel createChannel() {
		
		String host = "impact.ccat.eu";
		String user = "myjar";
		String pass = "myjar";
		
		return new ServerChannel(host, user, pass);
	}

	
}
