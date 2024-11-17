package acceptance;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

/* Point d'entrée pour les tests d'acceptation */
@RunWith(Cucumber.class)
@CucumberOptions(
    features = "classpath:feature", // Chemin vers le dossier contenant les fichiers .feature
    plugin = {"pretty", "html:target/cucumber-reports.html"}, // Génère un rapport HTML
    glue = "acceptance" // Chemin vers les définitions des étapes
)
public class AcceptanceTest { }

