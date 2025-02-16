package tech.fiap.project;

import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import tech.fiap.project.infra.configuration.Configuration;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		classes = { Configuration.class, ServletWebServerFactoryAutoConfiguration.class })
@ActiveProfiles("integration-test")
@TestPropertySource(properties = { "KEYCLOAK_BASE_URL=http://localhost:29000" })
class ProjectApplicationTests {

	@Autowired
	private ApplicationContext applicationContext;

	@Test
	public void testContextLoads() {
		assertNotNull(applicationContext, "O contexto da aplicação não foi carregado.");
	}

}