package interest.exception;

public class ExceptionHandler {

	public static void log(Exception e) {
		System.out.println("#ExceptionHandler> --- Exception caught --- ");
		System.out.println("#ExceptionHandler> "+e.getClass());
		System.out.println("#ExceptionHandler> "+e.getMessage());
		System.out.println("#ExceptionHandler> "+e.getStackTrace()[0]);
//		e.printStackTrace();
	}
	
}
