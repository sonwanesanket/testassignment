package step_definitions;

import com.aventstack.extentreports.model.Report;

import dependency_injection.ContextDI;
import io.cucumber.java8.En;
import io.cucumber.java8.Scenario;

/* 
 *  Class for initializing Scenario Object
*/
public class Hooks implements En {
	// Hooks(ContextDI )
	tech.grasshopper.pdf.pojo.cucumber.Scenario scn;

	public Hooks(ContextDI contextDI) {
		Before((Scenario scenario) -> {
			contextDI.setScenario(scenario);
		});
		After((Scenario scenario) -> {

			contextDI.setScenario(null);
		});

	}

}
