package runner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import utility.UrlRepository;

/* 
 * Class is used for running project you can also use pom.xml file using goal as mvn test
 * In Cucumber 6 we have to specify tags in () instead of {} as {} is not supported for tags
 * Project Genrates 3 reports 1)Spark Report 2) PDF report 3) Deafult Cucumber html Report
 * First two reports are present in Project Report folder and 3rd report is present in target folder
 * To publish cucumber report set publish to true (here we can share report url directly to client).
*/

@CucumberOptions(features = { "classpath:features" }, glue = "step_definitions", plugin = {

		 "html:target/cucumber.html",
		"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
		// above plugin is use to genrate html and pdf report

              }, 
                dryRun = false
                // , publish = true
		        //, tags = ("@test")
		)
public class TestRunner extends AbstractTestNGCucumberTests {
	
	  @DataProvider(parallel = true)
	    public Object[][] scenarios() {
	        return super.scenarios();
	    }

}
