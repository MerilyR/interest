package interest.service;

public interface Publisher {
	boolean publish(String message);
	void setQueue(String queue);
}
