package tech.fiap.project.infra.configuration.authorization;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class KeycloakJwtAuthenticationConverterTest {

	@InjectMocks
	private KeycloakJwtAuthenticationConverter converter;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this); // Inicializa os mocks
	}

	@Test
	void testCreateRole_shouldFormatRoleCorrectly() {
		// Teste para garantir que o método createRole funciona corretamente
		String role = converter.createRole("admin");
		assertEquals("ROLE_ADMIN", role);

		role = converter.createRole("user", "manager");
		assertEquals("ROLE_USER_MANAGER", role);
	}

}
