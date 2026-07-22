package com.thghpi.bandapp.band_api.integration.controller;
import com.thghpi.bandapp.band_api.entity.Group;
import com.thghpi.bandapp.band_api.entity.Person;
import com.thghpi.bandapp.band_api.entity.enumeration.GroupType;
import com.thghpi.bandapp.band_api.entity.enumeration.Role;
import com.thghpi.bandapp.band_api.dto.PersonDto;
import com.thghpi.bandapp.band_api.dto.PersonRoleDto;
import com.thghpi.bandapp.band_api.dto.request.LoginRequest;
import com.thghpi.bandapp.band_api.repository.GroupRepository;
import com.thghpi.bandapp.band_api.repository.PersonRepository;
import com.thghpi.bandapp.band_api.integration.AbstractIntegrationTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import com.jayway.jsonpath.JsonPath;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * PersonControllerIT is class to test integration when solliciting the PersonController
 * It tests person reading (all, by groups and by id), update and deletion by an admin (unit and multiple ones)
 */
@AutoConfigureMockMvc
public class PersonControllerIT extends AbstractIntegrationTest {
    /** The repository used to create persons when needed */
    @Autowired
    private PersonRepository repository;
    /** The repository for group entities */
    @Autowired
    private GroupRepository groupRepository;
    /** MockMvc instance used to perform HTTP requests in the tests. */
    @Autowired
    private MockMvc mockMvc;
    /** The object mapper to generate json body from java dtos */
    @Autowired
    private ObjectMapper objectMapper;
    /** The password encoder used for password constency in the database - use the corresponding Bean of the api */
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    /**
     * Clean database before each test in the test container
     * to make sure there is no data interferences between tests.
     */
    @BeforeEach
    void cleanDatabase() {
        repository.deleteAll();
        groupRepository.deleteAll();
    }

    @Test
    void shouldReturnPersonWithTheRightId() throws Exception {
        Person person = savePerson(null);

        String token = login(person, null);

        mockMvc.perform(
            get("/band-api/persons/" + person.getId())
            .header("Autorization", "Bearer " + token )
        ).andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(person.getId()))
            .andExpect(jsonPath("$.username").exists())
            .andExpect(jsonPath("$.instruments").doesNotExist());
    }

    @Test
    void shouldNotFindPersonWithInexistingId() throws Exception {
        Person person = savePerson(null);

        String token = login(person, null);

        Long searchedId = person.getId() + 2;

        mockMvc.perform(
            get("/band-api/persons/" + searchedId)
            .header("Autorization", "Bearer " + token )
        ).andExpect(status().isNotFound())
            .andExpect(jsonPath("$.message").exists())
            .andExpect(jsonPath("$.message").value("Person with ID " + searchedId + " not found."));
    }

    @Test
    void shouldReturnPersonList() throws Exception {
        Person person = savePerson(null);
        Person person2 = repository.save(Objects.requireNonNull(
            Person.builder()
                .firstname("Alice")
                .lastname("Smith")
                .username("alicesmith")
                .email("alice.smith@example.com")
                .password(passwordEncoder.encode("Password123!"))
                .role(Role.ADMIN)
                .build()
        ));

        String token = login(person, null);

        mockMvc.perform(
            get("/band-api/persons")
            .header("Authorization", "Bearer " + token)
        ).andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$.[0]").exists())
            .andExpect(jsonPath("$.[1]").exists())
            .andExpect(jsonPath("$.[2]").doesNotExist())
            .andExpect(jsonPath("$.[1].username").value(person2.getUsername()));

    }

    @Test
    void shouldReturnPersonOfTheRightGroup() throws Exception {
        Group group1 = groupRepository.save(Objects.requireNonNull(
            Group.builder()
                .name("group1")
                .groupType(GroupType.OTHER)
                .creationDate(LocalDate.now().minusMonths(1))
                .build()
        ));
        Group group2 = groupRepository.save(Objects.requireNonNull(
            Group.builder()
                .name("group2")
                .groupType(GroupType.SECTION)
                .creationDate(LocalDate.now().minusMonths(1))
                .build()
        ));
        Person oldPerson = savePerson(null);
        oldPerson.getGroups().add(group1);
        Person person = repository.save(Objects.requireNonNull(oldPerson));
        Person person2 = repository.save(Objects.requireNonNull(
            Person.builder()
                .firstname("Alice")
                .lastname("Smith")
                .username("alicesmith")
                .email("alice.smith@example.com")
                .password(passwordEncoder.encode("Password123!"))
                .role(Role.ADMIN)
                .groups(List.of(group2))
                .build()
        ));

        String token = login(person, null);

        mockMvc.perform(
            get("/band-api/persons/group/" + group2.getId())
            .header("Authorization", "Bearer " + token)
        ).andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$.[0]").exists())
            .andExpect(jsonPath("$.[1]").doesNotExist())
            .andExpect(jsonPath("$.[0].username").exists())
            .andExpect(jsonPath("$.[0].username").value(person2.getUsername()))
            .andExpect(jsonPath("$.[0].groups").doesNotExist());
    }

    @Test
    void shouldUpdatePersonsSuccessfully() throws Exception {
        Person person = savePerson(null);
        Person person2 = repository.save(Objects.requireNonNull(
            Person.builder()
                .firstname("Alice")
                .lastname("Smith")
                .username("alicesmith")
                .email("alice.smith@example.com")
                .password(passwordEncoder.encode("Password123!"))
                .role(Role.ADMIN)
                .build()
        ));
        String newFirstname = "Jack";
        Role newRole = Role.ARR;
        PersonRoleDto personDto = new PersonRoleDto(
            person.getId(), person.getLastname(),
            newFirstname, person.getUsername(),
            person.getEmail(),
            newRole, null, null
        );

        String token = login(person2, null);

        mockMvc.perform(
            put("/band-api/persons")
            .header("Authorization", "Bearer " + token)
            .contentType("application/json")
            .content(Objects.requireNonNull(
                objectMapper.writeValueAsString(List.of(personDto))
            ))
        ).andExpect(status().isOk())
            .andExpect(jsonPath("$").exists())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$.[0]").exists())
            .andExpect(jsonPath("$.[1]").doesNotExist())
            .andExpect(jsonPath("$.[0].username").exists())
            .andExpect(jsonPath("$.[0].username").value(personDto.username()))
            .andExpect(jsonPath("$.[0].role").exists())
            .andExpect(jsonPath("$.[0].role").value(personDto.role()));
    }

    @Test
    void shouldDeleteSeveralSuccessfully() throws Exception {
        Person person = savePerson(null);
        Person person2 = repository.save(Objects.requireNonNull(
            Person.builder()
                .firstname("Alice")
                .lastname("Smith")
                .username("alicesmith")
                .email("alice.smith@example.com")
                .password(passwordEncoder.encode("Password123!"))
                .role(Role.ADMIN)
                .build()
        ));
        PersonDto personDto1 = new PersonDto(
            person.getId(), person.getLastname(),
            person.getFirstname(), person.getUsername(),
            person.getEmail(), null, null, null, null
        );
        PersonDto personDto2 = new PersonDto(
            person2.getId(), person2.getLastname(),
            person2.getFirstname(), person2.getUsername(),
            person2.getEmail(), null, null, null, null
        );

        String token = login(person2, null);

        mockMvc.perform(
            delete("/band-api/persons")
            .header("Authorization", "Bearer " + token)
            .contentType("application/json")
            .content(Objects.requireNonNull(
                objectMapper.writeValueAsString(List.of(personDto1, personDto2))
            ))
        ).andExpect(status().isNoContent());
    }

    @Test
    void shouldRefuseDeletionWithoutIds() throws Exception {
        Person person = savePerson(null);
        Person person2 = repository.save(Objects.requireNonNull(
            Person.builder()
                .firstname("Alice")
                .lastname("Smith")
                .username("alicesmith")
                .email("alice.smith@example.com")
                .password(passwordEncoder.encode("Password123!"))
                .role(Role.ADMIN)
                .build()
        ));
        PersonDto personDto1 = new PersonDto(
            null, person.getLastname(),
            person.getFirstname(), person.getUsername(),
            person.getEmail(), null, null, null, null
        );
        PersonDto personDto2 = new PersonDto(
            person2.getId(), person2.getLastname(),
            person2.getFirstname(), person2.getUsername(),
            person2.getEmail(), null, null, null, null
        );

        String token = login(person2, null);

        mockMvc.perform(
            delete("/band-api/persons")
            .header("Authorization", "Bearer " + token)
            .contentType("application/json")
            .content(Objects.requireNonNull(
                objectMapper.writeValueAsString(List.of(personDto1, personDto2))
            ))
        ).andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").exists())
            .andExpect(jsonPath("$.message").value("All persons must have an ID for deletion"));
    }

    /**
     * Reusable method that returns a saved Person2 using the repository
     * and default passworld "Passworld123!" when the argument password is null.
     * @param String password can be null
     * @return the saved person with the given password or default passworld
     */
    private Person savePerson(String password) {
        return repository.save(Objects.requireNonNull(
            Person.builder()
            .lastname("Doe")
            .firstname("John")
            .username("johndoe")
            .email("john.doe@example.com")
            .password(passwordEncoder.encode(password != null ? password : "Password123!"))
            .role(Role.MEMBER)
            .build()
        ));
    }

    /**
     * Reusable method to authenticate a person using a given password
     * @param Person person the person to authenticate (it's username will be used)
     * @param String password the password used for authetication trial
     * @return a valid token if successfull, throw Exception otherwise
     */
    private String login(Person person, String password) throws Exception {
        LoginRequest loginDto = new LoginRequest(
            person.getUsername(),
            password != null ? password : "Password123!"
        );

        MvcResult loginResult = mockMvc.perform(
            post("/band-api/auth/login")
            .contentType("application/json")
            .content(Objects.requireNonNull(
                objectMapper.writeValueAsString(loginDto)
            ))
        ).andExpect(status().isOk())
            .andReturn();

        return JsonPath.read(
            loginResult.getResponse().getContentAsString(), "$.token"
        );
    }

    // TODO: Implement integration tests for PersonController
}
