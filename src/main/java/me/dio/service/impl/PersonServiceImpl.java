package me.dio.service.impl;

import me.dio.domain.model.Person;
import me.dio.domain.repository.PersonRepository;
import me.dio.service.PersonService;
import me.dio.service.exception.BusinessException;
import me.dio.service.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.Optional.ofNullable;

@Service
public class PersonServiceImpl implements PersonService {

    private static final Long UNCHANGEABLE_PERSON_ID = 1L;

    private final PersonRepository personRepository;

    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Transactional(readOnly = true)
    public List<Person> findAll() {
        return this.personRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Person findById(Long id) {
        return this.personRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Transactional
    public Person create(Person personToCreate) {
        ofNullable(personToCreate).orElseThrow(() -> new BusinessException("Person to create must not be null."));
        ofNullable(personToCreate.getName()).orElseThrow(() -> new BusinessException("Person name must not be null."));
        ofNullable(personToCreate.getBirthday()).orElseThrow(() -> new BusinessException("Person birthday must not be null."));

        this.validateChangeableId(personToCreate.getId(), "created");
        if (personRepository.existsByName(personToCreate.getName())) {
            throw new BusinessException("This person already exists.");
        }
        return this.personRepository.save(personToCreate);
    }

    @Transactional
    public Person update(Long id, Person personToUpdate) {
        this.validateChangeableId(id, "updated");
        Person dbPerson = this.findById(id);
        
        dbPerson.setName(personToUpdate.getName());
        dbPerson.setBirthday(personToUpdate.getBirthday());
        dbPerson.setNickname(personToUpdate.getNickname());
        dbPerson.setPartner(personToUpdate.getPartner());
        dbPerson.setPhone(personToUpdate.getPhone());
        
        return this.personRepository.save(dbPerson);
    }

    @Transactional
    public void delete(Long id) {
        this.validateChangeableId(id, "deleted");
        Person dbPerson = this.findById(id);
        this.personRepository.delete(dbPerson);
    }

    private void validateChangeableId(Long id, String operation) {
        if (UNCHANGEABLE_PERSON_ID.equals(id)) {
            throw new BusinessException("User with ID %d can not be %s.".formatted(UNCHANGEABLE_PERSON_ID, operation));
        }
    }
}

