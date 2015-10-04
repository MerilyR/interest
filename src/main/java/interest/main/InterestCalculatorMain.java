package interest.main;

import interest.handler.MessageHandler;
import interest.server.ServerChannel;
import interest.service.Subscriber;

public class InterestCalculatorMain{

	public static void main(String[] args) {
		
		ServerChannel sChannel = createChannel();	
		sChannel.createChannel();
		Subscriber subscriber = createSubscriber(sChannel);
		subscriber.addListener(new MessageHandler());
		subscriber.startService();
		
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
