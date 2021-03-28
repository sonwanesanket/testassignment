package dependency_injection;

import io.cucumber.java8.Scenario;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

/* 
 * Class is used for dependency injection i.e, for sharing objects across different classes.
 * this object will be created for each scenario before it run hence we can perform 
 * dependency injection for scenario steps  if they are present in same/different class. 
*/
public class ContextDI {

	private RequestSpecification requestSpecification;
	private Response response;
	private Scenario scenario;

	// constructor will be called before executing each scenario (steps)
	public ContextDI() {

	}

	public RequestSpecification getRequestSpecification() {
		return requestSpecification;
	}

	public void setRequestSpecification(RequestSpecification requestSpecification) {
		this.requestSpecification = requestSpecification;
	}

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response, boolean logResponse) {
		this.response = response;
		if (logResponse) {
			this.log(" -->statusCode:: " + response.getStatusCode());
			this.log(" -->responseBody:: " + response.asString());
		}
	}

	public Scenario getScenario() {
		return scenario;
	}

	public void setScenario(Scenario scenario) {
		this.scenario = scenario;
	}

	public void log(String s) {
		scenario.log(s);
	}

}
