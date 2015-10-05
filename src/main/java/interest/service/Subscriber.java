package interest.service;

public interface Subscriber {
	void addListener(ServiceListener listener);
	boolean removeListener(ServiceListener listener);
	void startService();
	void stopService();
	void setQueue (String queue);
	void setNrOfMessages(int nr);
}
