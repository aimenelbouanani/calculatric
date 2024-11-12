package acceptance;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.web.client.RestTemplate;
import static org.junit.Assert.assertEquals;

/* Step definitions for calculator.feature */
public class StepDefinitions {

    private String server = System.getProperty("calculator.url");
    private RestTemplate restTemplate = new RestTemplate();
    private String a;
    private String b;
    private String result;

    @Given("^I have two numbers (.*) and (.*)$")  // Supprimez le signe ":" pour correspondre au fichier .feature
    public void i_have_two_numbers(String a, String b) throws Throwable {
        this.a = a;
        this.b = b;
    }

    @When("^I sum them with the calculator$") // Assurez-vous que l'étape correspond exactement à celle du fichier .feature
    public void the_calculator_sums_them() throws Throwable {
        String url = String.format("%s/sum?a=%s&b=%s", server, a, b);
        result = restTemplate.getForObject(url, String.class);
    }

    @Then("^I should receive (.*) as the result$") // Adaptez l'annotation pour correspondre
    public void i_should_receive_as_the_result(String expectedResult) throws Throwable {
        assertEquals(expectedResult, result);
    }
}

