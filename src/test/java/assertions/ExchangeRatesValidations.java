package assertions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.TimeZone;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import io.restassured.response.ResponseBody;
import models.exchange_rate.GetExchangeRateResponse;

/* 
* Class for latest and past data Exchange Rates validations
* no need to check key and value as not null as we are using hash table
*  which dosen't contain key and value as null
*/
public class ExchangeRatesValidations extends BaseAssertion {
	ResponseBody responseBody;
	GetExchangeRateResponse exchangeRateResponse;
	// For EUR base 32 exchange rates are returned in response
	private static final int countOfCountriesforEUR_Base = 32;
	//For Non EUR base 33 exchange rates are returned
	private static final int countOfCountriesNonEUR_Base = 33;
	static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	public static final String todaysDate;
	private int statusCode;

	static {
		format.setTimeZone(TimeZone.getTimeZone("UTC"));
		todaysDate = format.format(new Date());
	}

	public ExchangeRatesValidations(ResponseBody responseBody) {
		exchangeRateResponse = responseBody.as(GetExchangeRateResponse.class);

	}


	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	

	public void assertResponse(String base, String date) {
		// exchangeRateResponse.getRates().size();
		int countOfCountries;
        if(base.equalsIgnoreCase("EUR")) 
        	countOfCountries=countOfCountriesforEUR_Base;
        else
        	countOfCountries=countOfCountriesNonEUR_Base;
		softAssert.assertEquals(exchangeRateResponse.getRates().size(), countOfCountries,
				"Validation of count of " + countOfCountries + " countries" + " in response:: ");
		softAssert.assertEquals(exchangeRateResponse.getBase(), base, "Validation of expected base " + base + ":: ");
		validateDate(date);
		softAssert.assertAll();

	}

	public void assertResponse(String symbols, String base, String date) {
		// TODO Auto-generated method stub
		softAssert.assertEquals(exchangeRateResponse.getRates().size(), 1,
				"Validation of count of 1 country in response ::");
		softAssert.assertEquals(exchangeRateResponse.getBase(), base, "Validation of expected base " + base + ":: ");
		validateDate(date);
		softAssert.assertAll();
	}

	public void validateDate(String date) {
		/*
		 * long numberOfDays = ChronoUnit.DAYS.between(LocalDate.parse(todaysDate),
		 * LocalDate.parse(exchangeRateResponse.getDate()));
		 */
		if (date.trim().equalsIgnoreCase("latest")) {
			String expectedResponseDate=getExpectedResponseRateDateForGivenDate(todaysDate);	
			softAssert.assertEquals(exchangeRateResponse.getDate(),expectedResponseDate, "Validation of expected latest date "+expectedResponseDate+ ":: ");
			
		} else {
			String expectedResponseDate=getExpectedResponseRateDateForGivenDate(date);	
			softAssert.assertEquals(exchangeRateResponse.getDate(), expectedResponseDate,
					"Validation of expected date " + expectedResponseDate + " ::");
		}
		
	}

	
	
	public String getExpectedResponseRateDateForGivenDate(String date) {
		String responseRateDate;
		String dayOfWeek= LocalDate.parse(date).getDayOfWeek().toString();
	    if(dayOfWeek.equalsIgnoreCase("SATURDAY")) {
	    	 
	    	responseRateDate= LocalDate.parse(date).minusDays(1).toString();
	    }
	    else if(dayOfWeek.equalsIgnoreCase("SUNDAY")) {
	    	responseRateDate= LocalDate.parse(date).minusDays(2).toString();
	    }
	    else {
	    	responseRateDate=date;
	    }
	 
	     return responseRateDate;
		
	}
	
	public void compareSameAs(ExchangeRatesValidations exchangeRatesValidations, boolean compareRates,
			boolean compareBase, boolean compareDate) {
		// TODO Auto-generated method stub
		boolean performAssert = false;
		if (compareRates) {
			Hashtable<String, Double> sourceRates = this.exchangeRateResponse.getRates();
			Hashtable<String, Double> targetRates = exchangeRatesValidations.exchangeRateResponse.getRates();
			softAssert.assertEquals(sourceRates, targetRates, "Validatation of rates of 2 responses for 2 dates:: ");
			performAssert = true;
		}
		if (compareBase) {
			String sourceBase = this.exchangeRateResponse.getBase();
			String targetBase = exchangeRatesValidations.exchangeRateResponse.getBase();
			softAssert.assertEquals(sourceBase, targetBase, "Validation of Base of 2 response for 2 dates:: ");
			performAssert = true;
		}
		if (compareDate) {
			String sourceDate = this.exchangeRateResponse.getDate();
			String targetDate = exchangeRatesValidations.exchangeRateResponse.getDate();
			softAssert.assertEquals(sourceDate, targetDate, "Validation of Dates of 2 responses:: ");
			performAssert = true;
		}
		if (performAssert) {
			softAssert.assertAll();
		}
	}

}
