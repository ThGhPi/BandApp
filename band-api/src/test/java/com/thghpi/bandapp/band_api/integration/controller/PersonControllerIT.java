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

@AutoConfigureMockMvc
public class PersonControllerIT extends AbstractIntegrationTest {
    @Autowired
    private PersonRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

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
