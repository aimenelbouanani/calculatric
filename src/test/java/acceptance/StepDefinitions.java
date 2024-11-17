package acceptance;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;

/* Step definitions for calculator.feature */
public class StepDefinitions {

    // Récupère l'URL du serveur depuis la propriété système
    private String server = System.getProperty("calculatric.url"); // Correction du nom de la propriété
    private RestTemplate restTemplate = new RestTemplate(); // Client HTTP pour envoyer les requêtes
    private String a;
    private String b;
    private String result;

    @Given("^I have two numbers (.*) and (.*)$")
    public void i_have_two_numbers(String a, String b) throws Throwable {
        this.a = a;
        this.b = b;
    }

    @When("^I sum them with the calculator$")
    public void the_calculator_sums_them() throws Throwable {
        // Construire l'URL pour la somme
        String url = String.format("%s/sum?a=%s&b=%s", server, a, b);
        result = restTemplate.getForObject(url, String.class); // Effectue une requête GET pour récupérer le résultat
    }

    @Then("^I should receive (.*) as the result$")
    public void i_should_receive_as_the_result(String expectedResult) throws Throwable {
        assertEquals(expectedResult, result); // Vérifie que le résultat attendu correspond
    }
}

