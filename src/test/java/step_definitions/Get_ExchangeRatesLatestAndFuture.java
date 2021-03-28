package step_definitions;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.testng.Assert;

import assertions.ExchangeRatesValidations;
import dependency_injection.ContextDI;
import io.cucumber.java8.En;
import io.restassured.response.Response;
import utility.UrlRepository;

public class Get_ExchangeRatesLatestAndFuture implements En {
	Response latestDateResponse, futureDateResponse;
	String futureDate;

	public Get_ExchangeRatesLatestAndFuture(ContextDI contextDI) {
		String uri = UrlRepository.getEnvURLProperties().get("get_exchange_rate").toString();
		When("I hit URL with latest and future dates", () -> {
			// Write code here that turns the phrase above into concrete actions
			contextDI.log(" -->URL1:: " + uri + Get_ExchangeRates.endpoint + "latest");
			latestDateResponse = contextDI.getRequestSpecification().when().get(Get_ExchangeRates.endpoint + "latest");
			contextDI.log(" -->Latest date response:: " + latestDateResponse.asString());
			futureDate = LocalDate.now().plusDays(10).toString();
			contextDI.log(" -->URL2:: " + uri + Get_ExchangeRates.endpoint + futureDate);
			futureDateResponse = contextDI.getRequestSpecification().when()
					.get(Get_ExchangeRates.endpoint + futureDate);
			contextDI.log(" -->future date response:: " + futureDateResponse.asString());

		});

		Then("Both apis returns the response with status code as {int}", statusCode -> {
			// Write code here that turns the phrase above into concrete actions
			Assert.assertEquals(latestDateResponse.getStatusCode(), statusCode);
			Assert.assertEquals(futureDateResponse.getStatusCode(), statusCode);
		});
		Then("validate future date rates matches current date", () -> {
			// Write code here that turns the phrase above into concrete actions
			ExchangeRatesValidations latestRatesValidations = new ExchangeRatesValidations(
					latestDateResponse.getBody());
			ExchangeRatesValidations futureRatesValidations = new ExchangeRatesValidations(
					latestDateResponse.getBody());
			latestRatesValidations.compareSameAs(futureRatesValidations, true, true, false);

		});

	}

}
