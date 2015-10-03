package interest.user;

import interest.service.Receiver;
import interest.service.ServiceListener;

import java.sql.Timestamp;
import java.util.Date;

public class ServiceUser{

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Receiver receiver = new Receiver();
		
		receiver.setHost ("impact.ccat.eu") ;
		receiver.setUser ("myjar");
		receiver.setPass ("myjar");
		receiver.setQueue ("interest-queue");
		receiver.setNrOfMessages(10);
		
		receiver.addListener(new ServiceListener() {
			
			public void getMessage(String message) {
				Date date = new Date();
				System.out.println("Message received at " + new Timestamp(date.getTime()) + ":");
				System.out.println(" [x] Received '"+message+"'");
			}
		});
		
		receiver.startService();
		
	}

}
