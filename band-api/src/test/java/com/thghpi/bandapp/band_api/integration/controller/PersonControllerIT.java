package com.thghpi.bandapp.band_api.integration.controller;
import com.thghpi.bandapp.band_api.dto.PersonDto;
import com.thghpi.bandapp.band_api.entity.Person;
import com.thghpi.bandapp.band_api.entity.enumeration.Role;
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
    /** The password encoder used for password constency in the database - use the corresponding Bean of the api */
    @Autowired
    private PasswordEncoder encoder;
    /** MockMvc instance used to perform HTTP requests in the tests. */
    @Autowired
    private MockMvc mockMvc;
    /** The object mapper to generate json body from java dtos */
    @Autowired
    private ObjectMapper objectMapper;
    
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
        String password = "Password123!";
        Person person = repository.save(Objects.requireNonNull(
            new Person(null, "Doe", "John",
            "johndoe", "john.doe@example.com",
            encoder.encode(password), Role.ADMIN,
            null, null, null, null,
            null, null)
        ));

        PersonDto loginDto = new PersonDto(
            null, null, null, person.getUsername(),
            null, password, null, null, null,
            null, null, null, null
        );

        MvcResult loginResult = mockMvc.perform(
        post("/band-api/auth/login")
            .contentType("application/json")
            .content(Objects.requireNonNull(
                objectMapper.writeValueAsString(loginDto)
            ))
        ).andExpect(status().isOk())
            .andReturn();

        String token = JsonPath.read(
            loginResult.getResponse().getContentAsString(),
            "$.token"
        );

        mockMvc.perform(
            get("/band-api/person/" + person.getId())
            .header("Autorization", "Bearer " + token )
        );


    }
    // TODO: Implement integration tests for PersonController
}
