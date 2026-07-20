package com.thghpi.bandapp.band_api.integration.controller;
import com.thghpi.bandapp.band_api.entity.Person;
import com.thghpi.bandapp.band_api.entity.enumeration.Role;
import com.thghpi.bandapp.band_api.dto.request.LoginRequest;
import com.thghpi.bandapp.band_api.repository.PersonRepository;
import com.thghpi.bandapp.band_api.integration.AbstractIntegrationTest;

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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * PersonControllerIT is class to test integration when solliciting the PersonController
 * It tests person reading (all, by groups and by id), update and deletion by an admin (unit and multiple ones)
 */
@AutoConfigureMockMvc
public class PersonControllerIT extends AbstractIntegrationTest {
    /** The repository used to create persons when needed */
    @Autowired
    private PersonRepository repository;
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
    }

    @Test
    void shouldReturnPersonWithTheRightId() throws Exception {
        Person person = savePerson(null);

        String token = login(person, null);

        mockMvc.perform(
            get("/band-api/person/" + person.getId())
            .header("Autorization", "Bearer " + token )
        );


    }

        /**
     * Reusable method that returns a saved Person using the repository
     * and default passworld "Passworld123!" when the argument password is null.
     * @param String password can be null
     * @return the saved person with the given password or default passworld
     */
    private Person savePerson(String password) {
        return repository.save(new Person(
            null, "Doe", "John",
            "johndoe", "john.doe@example.com",
            passwordEncoder.encode(password != null ? password : "Password123!"),
            Role.MEMBER, null, null, null,
            null, null, null
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
