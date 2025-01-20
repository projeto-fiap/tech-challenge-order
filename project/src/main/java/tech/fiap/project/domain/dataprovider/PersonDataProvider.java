package tech.fiap.project.domain.dataprovider;

import tech.fiap.project.app.dto.PersonDTO;
import tech.fiap.project.domain.entity.Person;

import java.util.List;
import java.util.Optional;

public interface PersonDataProvider {

	List<Person> retrieveAll();

	Optional<PersonDTO> retrieveByCpf(String cpf);

	Optional<Person> retrieveById(Long id);

	Person save(Person person);

	void delete(Person person);

	Person update(Person person);

	void delete(Long id);

}
