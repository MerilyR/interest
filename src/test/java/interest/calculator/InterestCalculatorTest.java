package interest.calculator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import interest.exception.ExceptionHandler;

import java.math.BigDecimal;

import org.junit.Test;

public class InterestCalculatorTest extends InterestCalculator {

	@Test
	public void testBigDecimal() {
		System.out.println("--- Testing BigDecimal class compared to double ---");
		
		double d = 0.2;
		BigDecimal bd = new BigDecimal (d);
		System.out.println("Double values are not exact:");
		System.out.println("double != BigDecimal");
		System.out.println(d + " != " + bd);
		assertTrue(Double.toString(d).compareTo(bd.toString())!=0);
		
		d = 0.1d + 0.1d + 0.1d + 0.1d + 0.1d + 0.1d + 0.1d + 0.1d + 0.1d + 0.1d;
		bd = new BigDecimal(d);
		System.out.println("Adding double values is not exact:");
		System.out.println("result of adding 0.1d ten times:");
		System.out.println(d + " != " + bd);
		assertTrue(Double.toString(d).compareTo(bd.toString())!=0);
		
		System.out.println(1 + " != " + d);
		assertTrue (Double.compare(1, d) == 1);	//bigger
		
		System.out.println("--- End of Test ---");
		System.out.println("");
	}
	
	@Test
	public void testPartOfAmount() {

		System.out.println("--- Testing calculating part of amount ---");
		
		assertTrue (Double.compare(getPartOfAmount(15, 100.00), 15.00)==0);
		assertTrue (Double.compare(getPartOfAmount(20, 20.00), 	4.00)==0);
		assertTrue (Double.compare(getPartOfAmount(20, 1.00), 	0.2)==0);
		assertTrue (Double.compare(getPartOfAmount(20, 1.23), 	0.25)==0);
		assertTrue (Double.compare(getPartOfAmount(20, 1.22), 	0.24)==0);
		
		try {
			getPartOfAmount(-15, 100.00);
		}
		catch (IllegalArgumentException e) {

			ExceptionHandler.log(e);
			assert true;
		}
		
		try {
			getPartOfAmount(15, -100.00);
		}
		catch (IllegalArgumentException e) {

			ExceptionHandler.log(e);
			assert true;
		}
		
		System.out.println("--- End of Test ---");
		System.out.println("");
		
	}	
	
	@Test
	public void testPercentageForDay() {
		

		System.out.println("--- Testing calculating percentage for specified day ---");
		
		assertEquals(4, getPercentageForDayNr(1));
		assertEquals(4, getPercentageForDayNr(2));
		assertEquals(1, getPercentageForDayNr(3));
		assertEquals(4, getPercentageForDayNr(4));
		assertEquals(2, getPercentageForDayNr(5));
		assertEquals(2, getPercentageForDayNr(10));
		assertEquals(3, getPercentageForDayNr(15));
		assertEquals(1, getPercentageForDayNr(27));
		assertEquals(2, getPercentageForDayNr(100));
		try {
			getPercentageForDayNr(0);
		}
		catch (IllegalArgumentException e) {
			ExceptionHandler.log(e);
			assert true;
		}
		
		System.out.println("--- End of Test ---");
		System.out.println("");
	}

	@Test
	public void testInterestForDay() {
		
		System.out.println("--- Testing calculating interest for specified day ---");
		
		assertTrue (Double.compare(getInterestForDayNr(1, 123), 4.92)==0);
		assertTrue (Double.compare(getInterestForDayNr(2, 123), 4.92)==0);
		assertTrue (Double.compare(getInterestForDayNr(3, 123), 1.23)==0);
		assertTrue (Double.compare(getInterestForDayNr(4, 123), 4.92)==0);
		assertTrue (Double.compare(getInterestForDayNr(5, 123), 2.46)==0);
		
		System.out.println("--- End of Test ---");
		System.out.println("");
	}
	
	@Test
	public void testInterest() {

		System.out.println("--- Testing calculating interest for specified sum for specified number of days ---");
		
		assertTrue(Double.compare(getInterest(123, 5), 18.45)==0);
		assertTrue(Double.compare(getInterest(123, 1), 4.92)==0);
		
		//examples from example queue
		assertTrue(Double.compare(getInterest(225, 25), 157.50)==0);
		assertTrue(Double.compare(getInterest(233, 2), 18.64)==0);
		assertTrue(Double.compare(getInterest(182, 28), 143.78)==0);
		
		//examples from task explained document
		assertTrue(Double.compare(getInterest(343, 25), 240.10)==0);
		assertTrue(Double.compare(getInterest(465, 22), 292.95)==0);
		
		try {
			getInterest(0, 5);
			assert false;
		}
		catch (IllegalArgumentException e) {
			ExceptionHandler.log(e);
			assert true;
		}		
		
		try {
			getInterest(123, 0);
			assert false;
		}
		catch (IllegalArgumentException e) {
			ExceptionHandler.log(e);
			assert true;
		}
		
		//trying with negative values
		try {
			getInterest(123, -1);
			assert false;
		}
		catch (IllegalArgumentException e) {
			ExceptionHandler.log(e);
			assert true;
		}
		
		try {
			getInterest(-1, 20);
			assert false;
		}
		catch (IllegalArgumentException e) {
			ExceptionHandler.log(e);
			assert true;
		}
		
		try {
			getInterest(-1, -1);
			assert false;
		}
		catch (IllegalArgumentException e) {
			ExceptionHandler.log(e);
			assert true;
		}
		
		//trying with big values
		System.out.println();
		System.out.println("--Testing big values [stress-testing]:");
		
		long startTime = System.currentTimeMillis();
		
		System.out.println(getInterest (12345678, 1));
		
		long endTime = System.currentTimeMillis();
		System.out.println("When sum is 8-digit it took " +((endTime - startTime)/1000.0)+" seconds");
		System.out.println();
				
		startTime = System.currentTimeMillis();
		System.out.println(getInterest (123, 1234));
		
		endTime = System.currentTimeMillis();
		System.out.println("When days is 4-digit it took " +((endTime - startTime)/1000.0)+" seconds");
		System.out.println();
			
		startTime = System.currentTimeMillis();
		System.out.println(getInterest (12345678, 12345));
		
		endTime = System.currentTimeMillis();
		System.out.println("When days is 5-digit it took " +((endTime - startTime)/1000.0)+" seconds");
		System.out.println();
			
		startTime = System.currentTimeMillis();
		System.out.println(getInterest (12345678, 123456));
		
		endTime = System.currentTimeMillis();
		System.out.println("When days is 6-digit it took " +((endTime - startTime)/1000.0)+" seconds");
		System.out.println();
			
		startTime = System.currentTimeMillis();
		System.out.println(getInterest (12345678, 1234567));
		
		endTime = System.currentTimeMillis();
		System.out.println("When days is 7-digit it took " +((endTime - startTime)/1000.0)+" seconds");
		System.out.println();
			
		startTime = System.currentTimeMillis();
		System.out.println(getInterest (12345678, 12345678));
		
		endTime = System.currentTimeMillis();
		System.out.println("When days is 8-digit it took " +((endTime - startTime)/1000.0)+" seconds");
		System.out.println();
			
		startTime = System.currentTimeMillis();
		System.out.println(getInterest (12345678, 12345678));
		
		endTime = System.currentTimeMillis();
		System.out.println("When both are 8-digit it took " +((endTime - startTime)/1000.0)+" seconds");
		System.out.println();
		
		startTime = System.currentTimeMillis();
		System.out.println(getInterest (123, 123456789));
		
		endTime = System.currentTimeMillis();
		System.out.println("When days is 9-digit it took " +((endTime - startTime)/1000.0)+" seconds");
		System.out.println();
		
//		startTime = System.currentTimeMillis();
//		System.out.println(getInterest (123, 1234567890));
//		
//		endTime = System.currentTimeMillis();
//		System.out.println("When days is 10-digit it took " +((endTime - startTime)/1000.0)+" seconds");
//		System.out.println();
				
		System.out.println("--- End of Test ---");
		System.out.println("");
		
	}	
}