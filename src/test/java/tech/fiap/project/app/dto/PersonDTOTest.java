package tech.fiap.project.app.dto;

import org.junit.jupiter.api.Test;
import tech.fiap.project.domain.entity.Role;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class PersonDTOTest {

	@Test
	void testPersonDTO() {
		Role role = Role.USER;
		DocumentDTO documentDTO = new DocumentDTO();

		PersonDTO personDTO = new PersonDTO();
		personDTO.setId(1L);
		personDTO.setEmail("test@example.com");
		personDTO.setName("John Doe");
		personDTO.setPassword("password123");
		personDTO.setRole(role);
		personDTO.setDocument(Collections.singletonList(documentDTO));

		assertNotNull(personDTO);
		assertEquals(1L, personDTO.getId());
		assertEquals("test@example.com", personDTO.getEmail());
		assertEquals("John Doe", personDTO.getName());
		assertEquals("password123", personDTO.getPassword());
		assertEquals(role, personDTO.getRole());
		assertEquals(1, personDTO.getDocument().size());
		assertEquals(documentDTO, personDTO.getDocument().get(0));
	}

	@Test
	void testEmptyPersonDTO() {
		PersonDTO personDTO = new PersonDTO();
		personDTO.setDocument(Collections.emptyList());

		assertNull(personDTO.getId());
		assertNull(personDTO.getEmail());
		assertNull(personDTO.getName());
		assertNull(personDTO.getPassword());
		assertNull(personDTO.getRole());
		assertTrue(personDTO.getDocument().isEmpty());
	}

}
