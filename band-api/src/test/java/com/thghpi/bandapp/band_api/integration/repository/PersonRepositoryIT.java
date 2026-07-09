package com.thghpi.bandapp.band_api.integration.repository;
import com.thghpi.bandapp.band_api.entity.Group;
import com.thghpi.bandapp.band_api.entity.Person;
import com.thghpi.bandapp.band_api.entity.enumeration.Role;
import com.thghpi.bandapp.band_api.entity.enumeration.GroupType;
import com.thghpi.bandapp.band_api.repository.GroupRepository;
import com.thghpi.bandapp.band_api.repository.PersonRepository;
import com.thghpi.bandapp.band_api.integration.AbstractIntegrationTest;
import com.thghpi.bandapp.band_api.configuration.FixedClockConfiguration;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.context.annotation.Import;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Integration tests for the PersonRepository interface and implementation by JPA et hibernate.
 * These tests verify the behavior of data persistence and ACIDity during interaction with database,
 * including saving a person, retrieving persons by id, and handling invalid input.
 * The tests use autowired repositories for persons and choices entities to test the relations with surveys.
 * @throws Exception if any request to database or any assertion fail.
 */
@Import(FixedClockConfiguration.class)
public class PersonRepositoryIT extends AbstractIntegrationTest {
    /** GroupRepository instance to test relationships with persons */
    @Autowired
    private GroupRepository groupRepository;
    /** PersonRepository instance to test person-related operations */
    @Autowired
    private PersonRepository repository;
    /** Clock instance to manage time in tests */
    @Autowired
    private Clock clock;

    /** Clean the database before each test */
    @BeforeEach
    void cleanDatabase() {
        repository.deleteAll();
        groupRepository.deleteAll();
    }

    /**
     * Test the findByUsername method of PersonRepository.
     */
    @Test
    void findByUsernameTest() {
        Person person = repository.save(Objects.requireNonNull(
           new Person(
                null, "John", "Doe", "johndoe",
                "john.doe@example.com", "encodedPassword",
                Role.MEMBER, null, null, null,
                null, null, null
            ) 
        ));
        Optional<Person> foundPerson = repository.findByUsername("johndoe");
        assertTrue(foundPerson.isPresent());
        assertEquals(person.getId(), foundPerson.get().getId());

        assertTrue(repository.findByUsername("nonexistent").isEmpty());
    }

    /**
     * Test the existsByEmail method of PersonRepository.
     */
    @Test
    void existsByEmailTest() {
        repository.save(Objects.requireNonNull(
           new Person(
                null, "John", "Doe", "johndoe",
                "john.doe@example.com", "encodedPassword",
                Role.MEMBER, null, null, null,
                null, null, null
            ) 
        ));
        assertTrue(repository.existsByEmail("john.doe@example.com"));
        assertFalse(repository.existsByEmail("nonexistent@example.com"));
    }

    /**
     * Test the existsByUsername method of PersonRepository.
     */
    @Test
    void existsByUsernameTest() {
        repository.save(Objects.requireNonNull(
           new Person(
                null, "John", "Doe", "johndoe",
                "john.doe@example.com", "encodedPassword",
                Role.MEMBER, null, null, null,
                null, null, null
            ) 
        ));
        assertTrue(repository.existsByUsername("johndoe"));
        assertFalse(repository.existsByUsername("nonexistent"));
    }

    /**
     * Test the findByGroups method of PersonRepository.
     */
    @Test
    void findByGroupsTest() {
        List<Group> groups = groupRepository.saveAll(Objects.requireNonNull(List.of(
            new Group(null, "Clarinettes", LocalDate.ofInstant(Instant.now(clock), ZoneId.systemDefault()), GroupType.SECTION, null, null, null, null, null, null),
            new Group(null, "Saxophones", LocalDate.ofInstant(Instant.now(clock), ZoneId.systemDefault()), GroupType.SECTION, null, null, null, null, null, null)
        )));
        List<Person> group1Persons = repository.saveAll(Objects.requireNonNull(List.of(
            new Person(
                null, "John", "Doe",
                "johndoe", "john.doe@example.com",
                "encodedPassword", Role.MEMBER, null,
                null, null, List.of(groups.getFirst()), null, null
            ),
            new Person(
                null, "Jane", "Smith",
                "janesmith", "jane.smith@example.com",
                "encodedPassword", Role.MEMBER, null,
                null, null, List.of(groups.getFirst()), null, null
            )
        )));
        List<Person> foundPersons = repository.findByGroups(groups.getFirst());
        assertEquals(group1Persons.size(), foundPersons.size());
        assertTrue(foundPersons.containsAll(group1Persons));

        assertEquals(0, repository.findByGroups(groups.getLast()).size());
    }
}