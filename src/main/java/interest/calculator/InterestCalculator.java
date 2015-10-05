package interest.calculator;

import java.math.BigDecimal;


public class InterestCalculator {

	
	/**
	 * Calculates the total sum of interest for specified number of days for the sum specified
	 * 
	 * Each day is calculated individually and then calculated interest is summed up
	 * 
	 * @param sum - amount that interest is calculated from
	 * @param days - number of days the interest is calculated for 
	 * @returns the total sum of interest
	 * 
	 * @throws IllegalArgumentException when number of days is negative
	 * 
	 * Note: returns 0 when number of days is 0
	 */
	public static double getInterest( long sum, long days ) {

		if (days < 1)
			throw new IllegalArgumentException ("%Calculator> Number of days cannot be less than 1!");
		
		BigDecimal interest = new BigDecimal("0");
		
		for (int day = 1; day <= days; day ++) {	
			interest = interest.add(new BigDecimal("" + getInterestForDayNr ( day, sum )));
		}
		
		System.out.println("%Calculator> ---------------------------");
		System.out.println("%Calculator> Calculating interest:");
		System.out.println("%Calculator> Amount = 		"+sum);
		System.out.println("%Calculator> Number of days = 	"+days);
		System.out.println("%Calculator> Total interest = 	"+interest);
		System.out.println("%Calculator> ---------------------------");
		
		return interest.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	protected static double getInterestForDayNr (int indexOfDay, double sum) {
		return getPartOfAmount(getPercentageForDayNr(indexOfDay), sum);
	}
	
	protected static int getPercentageForDayNr ( int indexOfDay ) {	
		if (indexOfDay < 1)
			throw new IllegalArgumentException ("%Calculator> Index of day cannot be smaller than 1!");			
		if ( indexOfDay %3 != 0 && indexOfDay %5 != 0 )
			return 4;
		else {
			int percentage = 0;
			if (indexOfDay %3 == 0)
				percentage += 1;
			if (indexOfDay % 5 == 0)
				percentage += 2;
			return percentage;
		}
	}

	protected static double getPartOfAmount ( double percentage, double amount ) {
		
		if (percentage < 0)
			throw new IllegalArgumentException ("%Calculator> Percentage cannot be negative!");
		if (amount <= 0)
			throw new IllegalArgumentException ("%Calculator> Amount cannot be negative or 0!");
		
		double partOfAmount = percentage/100*amount;
		BigDecimal bigDecimal = new BigDecimal (String.valueOf(partOfAmount));		
		return bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();		
	}
	
}
