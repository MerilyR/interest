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
		final String outputMessage = "{\"sum\":123.0,\"days\":5,\"interest\":18.45,\"totalSum\":141.45,\"token\":\"test_token\"}";
		
		handler.setPublisher(new Publisher() {
			public boolean publish(String message) {
				
				System.out.println("Expected> "+outputMessage);
				System.out.println("Actual  > "+message);
				
				assertTrue(message.equals(outputMessage));
				return true;
			}
		});
		
		try {
			handler.getMessage(inputMessage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
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
