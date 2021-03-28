package assertions;

import java.util.List;

import org.testng.Assert;
import org.testng.asserts.SoftAssert;

public class BaseAssertion {
	SoftAssert softAssert;

	public BaseAssertion() {
		// TODO Auto-generated constructor stub
		softAssert = new SoftAssert();
	}

	public void assertAll() {
		softAssert.assertAll();
	}

	public void assertStatusCode(int expectedStatusCode, int actualStatusCode) {

	}

	public void assertStatusCodes(List<Integer> expectedStatusCodes, List<Integer> actualStatusCodes) {
		Assert.assertEquals(actualStatusCodes, expectedStatusCodes, "");
	}

}
