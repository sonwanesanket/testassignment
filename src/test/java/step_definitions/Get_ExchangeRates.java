package step_definitions;

import dependency_injection.ContextDI;
import io.cucumber.java8.En;
import utility.UrlRepository;

import static io.restassured.RestAssured.*;
import org.testng.Assert;
import assertions.ExchangeRatesValidations;

/* 
 * Class contains steps for latest and past data feature files
 * Wile logging please use white space in prefix suffix aslong with --> and :: please refer 
 * any log for better under standing of same
*/
public class Get_ExchangeRates implements En {
	// local variables
	public static final String endpoint = "/api/";
	ExchangeRatesValidations exchangeRatesValidations;
	String date;

	// constructor
	public Get_ExchangeRates(ContextDI contextDI) {

		date = null;
		String uri = UrlRepository.getEnvURLProperties().get("get_exchange_rate").toString();

		Given("Rates API for Foreign Exchange rates", () -> {
			contextDI.setRequestSpecification(given().baseUri(uri));

		});

		When("I hit URL with date as {string}", (String date) -> {
			this.date = date;
			contextDI.log(" -->URL:: " + uri + endpoint + date);
			contextDI.setResponse(contextDI.getRequestSpecification().when().get(endpoint + date), true);

		});

		When("I hit URL with query parameter {string} as {string} with date as {string}",
				(String queryParam, String queryParamValue, String date) -> {
					this.date = date;
					contextDI.log(" -->URL:: " + uri + endpoint + date);
					contextDI.log(" -->Query Parameters:: " + queryParam + "=" + queryParamValue);
					contextDI.setResponse(contextDI.getRequestSpecification().when()
							.queryParam(queryParam, queryParamValue).get(endpoint + date), true);
				});

		When("I hit URL with query parameters {string} as {string} and {string} as {string} with date as {string}",
				(String queryParam1, String queryParamValue1, String queryParam2, String queryParamValue2,
						String date) -> {
					this.date = date;
					contextDI.log(" -->URL:: " + uri + endpoint + date);
					contextDI.log(" -->Query Parameters:: " + queryParam1 + "=" + queryParamValue1 + "," + queryParam2
							+ "=" + queryParam2);

					contextDI.setResponse(
							contextDI.getRequestSpecification().when().queryParam(queryParam1, queryParamValue1)
									.queryParam(queryParam2, queryParamValue2).get(endpoint + date),
							true);

				});

		When("I hit incorrect URL {string} with date as {string}", (String url, String date) -> {
			// Write code here that turns the phrase above into concrete actions
			
			contextDI.log(" -->URL:: " + url + date);
			contextDI.getRequestSpecification().baseUri(url);
			contextDI.setResponse(when().get(date), true);

			
		});

		Then("API returns the response with status code as {int}", expectedStatusCode -> {
			// Write code here that turns the phrase above into concrete actions
			// statusCode is Integer object need to cast to int in assert method
			contextDI.getRequestSpecification().then().statusCode(200);
			Assert.assertEquals(contextDI.getResponse().getStatusCode(), expectedStatusCode, "");

		});

		Then("All exchange rates are returned against base {string}", (String base) -> {
			// Write code here that turns the phrase above into concrete actions
			exchangeRatesValidations = new ExchangeRatesValidations(contextDI.getResponse().getBody());
			exchangeRatesValidations.assertResponse(base, date);
		});

		Then("Response should contain rate for {string} against base {string}", (String symbols, String base) -> {
			// Write code here that turns the phrase above into concrete actions
			exchangeRatesValidations = new ExchangeRatesValidations(contextDI.getResponse().getBody());
			exchangeRatesValidations.assertResponse(symbols, base, date);
		});

	}

}
