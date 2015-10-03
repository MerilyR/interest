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
	public static double getInterest( double sum, int days ) {

		if (days < 0)
			throw new IllegalArgumentException ("Number of days cannot be negative!");
		
		BigDecimal interest = new BigDecimal("0");
		for (int day = 1; day <= days; day ++) {	
			interest = interest.add(new BigDecimal("" + getInterestForDayNr ( day, sum )));			
		}
		
		System.out.println("Calculating interest:");
		System.out.println("Amount = 		"+sum);
		System.out.println("Number of days = 	"+days);
		System.out.println("Total interest = 	"+interest);
		System.out.println("---------------------------");
		
		return interest.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	protected static double getInterestForDayNr (int indexOfDay, double sum) {
		return getPartOfAmount(getPercentageForDayNr(indexOfDay), sum);
	}
	
	protected static int getPercentageForDayNr ( int indexOfDay ) {	
		if (indexOfDay < 1)
			throw new IllegalArgumentException ("Index of day cannot be smaller than 1!");			
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
			throw new IllegalArgumentException ("Percentage cannot be negative!");
		if (amount < 0)
			throw new IllegalArgumentException ("Amount cannot be negative!");
		
		double partOfAmount = percentage/100*amount;
		BigDecimal bigDecimal = new BigDecimal (String.valueOf(partOfAmount));
		return bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(); 
		
	}
	
}
