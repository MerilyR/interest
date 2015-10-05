package interest.handler;

import static org.junit.Assert.*;
import interest.service.Publisher;

import org.junit.Before;
import org.junit.Test;

public class MessageHandlerTest {

	final String TOKEN = "test_token";
	MessageHandler handler = new MessageHandler();
	
	@Before
	public void setUp() throws Exception {
		handler.setToken(TOKEN);
	}

	@Test
	public void test_correct() {
		
		System.out.println("--- Testing with correct input values --- ");
		
		final String inputMessage = "{\"sum\":123,\"days\":5}";
		final String outputMessage = "{\"sum\":123,\"days\":5,\"interest\":18.45,\"totalSum\":141.45,\"token\":\"test_token\"}";
		
		handler.setPublisher(new Publisher() {
			public boolean publish(String message) {
				
				System.out.println("Expected> "+outputMessage);
				System.out.println("Actual  > "+message);
				
				assertTrue(message.equals(outputMessage));
				return true;
			}

			public void setQueue(String queue) {
				
			}
		});
		
		try {
			handler.getMessage(inputMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		final String inputMessage2 = "{\"sum\":383,\"days\":26}";
		final String outputMessage2 = "{\"sum\":383,\"days\":26,\"interest\":283.42,\"totalSum\":666.42,\"token\":\"test_token\"}";
		
		handler.setPublisher(new Publisher() {
			public boolean publish(String message) {
				
				System.out.println("Expected> "+outputMessage2);
				System.out.println("Actual  > "+message);
				
				assertTrue(message.equals(outputMessage2));
				return true;
			}

			public void setQueue(String queue) {
				
			}
		});
		
		try {
			handler.getMessage(inputMessage2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		final String inputMessage3 = "{\"sum\":824,\"days\":8}";
		final String outputMessage3 = "{\"sum\":824,\"days\":8,\"interest\":197.76,\"totalSum\":1021.76,\"token\":\"test_token\"}";
		
		handler.setPublisher(new Publisher() {
			public boolean publish(String message) {
				
				System.out.println("Expected> "+outputMessage3);
				System.out.println("Actual  > "+message);
				
				assertTrue(message.equals(outputMessage3));
				return true;
			}

			public void setQueue(String queue) {
				
			}
		});
		
		try {
			handler.getMessage(inputMessage3);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		final String inputMessage4 = "{\"sum\":938,\"days\":235108086}";
		final String outputMessage4 = "{\"sum\":938,\"days\":235108086,\"interest\":6321899682.56,\"totalSum\":6321900620.56,\"token\":\"test_token\"}";
		
		handler.setPublisher(new Publisher() {
			public boolean publish(String message) {
				
				System.out.println("Expected> "+outputMessage4);
				System.out.println("Actual  > "+message);
				
				assertTrue(message.equals(outputMessage4));
				return true;
			}

			public void setQueue(String queue) {
				
			}
		});
		
		try {
			handler.getMessage(inputMessage4);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		final String inputMessage5 = "{\"sum\":387,\"days\":11}";
		final String outputMessage5 = "{\"sum\":387,\"days\":11,\"interest\":119.97,\"totalSum\":506.97,\"token\":\"test_token\"}";
		
		handler.setPublisher(new Publisher() {
			public boolean publish(String message) {
				
				System.out.println("Expected> "+outputMessage5);
				System.out.println("Actual  > "+message);
				
				assertTrue(message.equals(outputMessage5));
				return true;
			}

			public void setQueue(String queue) {
				
			}
		});
		
		try {
			handler.getMessage(inputMessage5);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("--- End of test ---");
		System.out.println();
	}
	
	@Test
	public void test_incorrect() {
		
		System.out.println("--- Testing with incorrect input values --- ");
		
		final String inputMessage = "{\"sum\":123,\"days\":-5}";
		
		handler.setPublisher(new Publisher() {
			public boolean publish(String message) {
				assert false;
				return true;
			}

			public void setQueue(String queue) {
				
			}
		});
		
		try {
			handler.getMessage(inputMessage);
			assert false;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			assert true;
		}
		
		final String inputMessage2 = "{\"sum\":-123,\"days\":5}";
		
		try {
			handler.getMessage(inputMessage2);
			assert false;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			assert true;
		}
		
		final String inputMessage3 = "{\"sum\":0,\"days\":5}";
		
		try {
			handler.getMessage(inputMessage3);
			assert false;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			assert true;
		}
		
		final String inputMessage4 = "{\"sum\":123,\"days\":0}";
		
		try {
			handler.getMessage(inputMessage4);
			assert false;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			assert true;
		}
		
		System.out.println("--- End of test ---");
		System.out.println();
	}

	@Test
	public void test_null() {
		
		System.out.println("--- Testing with null values --- ");
		
		final String inputMessage = "{\"sum\":123,\"days\":null}";
		
		handler.setPublisher(new Publisher() {
			public boolean publish(String message) {
				assert false;
				return true;
			}

			public void setQueue(String queue) {
				
			}
		});
		
		try {
			handler.getMessage(inputMessage);
			assert false;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			assert true;
		}
		
		final String inputMessage2 = "{\"sum\":null,\"days\":5}";
		try {
			handler.getMessage(inputMessage2);
			assert false;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			assert true;
		}
		
		final String inputMessage3 = "{\"sum\":null,\"days\":null}";
		try {
			handler.getMessage(inputMessage3);
			assert false;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			assert true;
		}
		
		final String inputMessage4 = "{\"sum\":null}";
		try {
			handler.getMessage(inputMessage4);
			assert false;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			assert true;
		}
		
		final String inputMessage5 = "{\"days\":null}";
		try {
			handler.getMessage(inputMessage5);
			assert false;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			assert true;
		}
		
		final String inputMessage6 = "{}";
		try {
			handler.getMessage(inputMessage6);
			assert false;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			assert true;
		}
		
		System.out.println("--- End of test ---");
		System.out.println();
	}
	
}
