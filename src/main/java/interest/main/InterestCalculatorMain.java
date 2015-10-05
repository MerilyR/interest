package interest.main;

import interest.handler.MessageHandler;
import interest.server.ServerChannel;
import interest.service.Publisher;
import interest.service.Subscriber;
import interest.service.impl.MessagePublisher;
import interest.service.impl.MessageSubscriber;

public class InterestCalculatorMain{

	public static void main(String[] args) {
		
		ServerChannel sChannel = createChannel();	
		sChannel.createChannel();
		Subscriber subscriber = createSubscriber(sChannel);
		Publisher publisher = createPublisher(sChannel);
		MessageHandler msgHandler = createMessageHandler(publisher);
		subscriber.addListener(msgHandler);
		subscriber.startService();
		
	}

	private static MessageHandler createMessageHandler(Publisher publisher) {
		MessageHandler msgHandler = new MessageHandler();
		msgHandler.setPublisher(publisher);
		
		msgHandler.setToken("themerru");
		
		return msgHandler;
	}

	private static Subscriber createSubscriber(ServerChannel sChannel) {
		Subscriber s = new MessageSubscriber (sChannel);
		
		s.setQueue ("interest-queue");
		s.setNrOfMessages(0);
		
		return s;
	}
	
	private static Publisher createPublisher(ServerChannel sChannel) {
		Publisher p = new MessagePublisher(sChannel);
		
		p.setQueue("solved-interest-queue");
		
		return p;
	}

	private static ServerChannel createChannel() {
		
		String host = "impact.ccat.eu";
		String user = "myjar";
		String pass = "myjar";
		
		return new ServerChannel(host, user, pass);
	}

	
}
