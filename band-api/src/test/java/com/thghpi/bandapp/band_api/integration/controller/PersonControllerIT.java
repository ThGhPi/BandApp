package com.thghpi.bandapp.band_api.integration.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.Objects;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import com.thghpi.bandapp.band_api.entity.Person;
import com.thghpi.bandapp.band_api.entity.enumeration.Role;
import com.thghpi.bandapp.band_api.integration.AbstractIntegrationTest;
import com.thghpi.bandapp.band_api.repository.PersonRepository;

public class PersonControllerIT extends AbstractIntegrationTest {
    @Autowired
    private PersonRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnPersonWithTheRightId() throws Exception {

        Person person = repository.save(Objects.requireNonNull(
            new Person(null, "Doe", "John",
            "johndoe", "john.doe@example.com",
            encoder.encode("Password123!"), Role.ADMIN,
            null, null, null, null,
            null, null)
        ));

        mockMvc.perform(
            get("/band-api/person/" + person.getId())
            .header("Autorization", "Bearer " + token )
        );


    }
    // TODO: Implement integration tests for PersonController
}
