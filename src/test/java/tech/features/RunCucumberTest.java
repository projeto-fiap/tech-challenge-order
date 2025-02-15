package tech.features;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features", glue = "tech.features",
		plugin = { "pretty", "html:target/cucumber-report.html" })
public class RunCucumberTest {

}
