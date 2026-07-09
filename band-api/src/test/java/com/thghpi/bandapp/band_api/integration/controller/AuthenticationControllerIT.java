package com.thghpi.bandapp.band_api.integration.controller;
import com.thghpi.bandapp.band_api.dto.PersonDto;
import com.thghpi.bandapp.band_api.entity.Person;
import com.thghpi.bandapp.band_api.entity.enumeration.Role;
import com.thghpi.bandapp.band_api.repository.PersonRepository;
import com.thghpi.bandapp.band_api.integration.AbstractIntegrationTest;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Objects;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@AutoConfigureMockMvc
public class AuthenticationControllerIT extends AbstractIntegrationTest {
    @Autowired
    private PersonRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    
    @BeforeEach
    public void cleanDatabase() {
        repository.deleteAll();
    }

    @Test
    public void shouldRefuseExistingEmailOrUsernameRegistration() throws Exception {
        repository.save(Objects.requireNonNull(new Person(
            null, "John", "Doe",
            "johndoe", "john.doe@example.com",
            "encodedPassword", Role.MEMBER, null,
            null, null, null, null, null
        )));

        PersonDto toRegisterPersonDto = new PersonDto(
            null, "Jane", "Smith",
            "johndoe", "jane.smith@example.com",
            "Password123!", Role.MEMBER, null,
            null, null, null, null, null
        );
        
        mockMvc.perform(
            post("/band-api/auth/register")
            .contentType("application/json")
            .content(Objects.requireNonNull(objectMapper.writeValueAsString(toRegisterPersonDto)))
        ).andDo(print())
            .andExpect(status().isConflict())
            .andExpect(jsonPath("$.message").value(
                "Can't create Person. Username already exists in database."
            ));
        
        PersonDto toRegisterPersonDto2 = new PersonDto(
            null, "Jane", "Smith",
            "janesmith", "john.doe@example.com",
            "Password123!", Role.MEMBER, null,
            null, null, null, null, null
        );
        
        mockMvc.perform(
            post("/band-api/auth/register")
            .contentType("application/json")
            .content(Objects.requireNonNull(objectMapper.writeValueAsString(toRegisterPersonDto2)))
        ).andDo(print())
            .andExpect(status().isConflict())
            .andExpect(jsonPath("$.message").value(
                "Can't create Person. Email already exists in database."
            ));
    }

    @Test
    public void shouldRegisterUserSuccessfully() throws Exception {
        PersonDto toRegisterPersonDto = new PersonDto(
            null, "Alice", "Johnson",
            "alicejohnson", "alice.johnson@example.com",
            "Password123!", Role.MEMBER, null,
            null, null, null, null, null
        );

        mockMvc.perform(
            post("/band-api/auth/register")
            .contentType("application/json")
            .content(Objects.requireNonNull(objectMapper.writeValueAsString(toRegisterPersonDto)))
        ).andExpect(status().isCreated())
            .andExpect(jsonPath("$.username").value("alicejohnson"));
    }

    @Test
    public void shouldAuthenticateUserSuccessfully() throws Exception {
        Person person = new Person(
            null, "Bob", "Williams",
            "bobwilliams", "bob.williams@example.com",
            passwordEncoder.encode("Password!123"), Role.MEMBER, null,
            null, null, null, null, null
        );
        person = repository.save(Objects.requireNonNull(person));

        mockMvc.perform(
            post("/band-api/auth/login")
            .contentType("application/json")
            .content(Objects.requireNonNull(objectMapper.writeValueAsString(new PersonDto(
                null, "Bob", "Williams",
                "bobwilliams", "bob.williams@example.com",
                "Password!123", Role.MEMBER, null,
                null, null, null, null, null
            ))))
        ).andExpect(status().isOk())
            .andExpect(jsonPath("$.token").exists());
    }

    @Test
    public void shouldRefuseAuthentication() throws Exception {
        Person person = new Person(
            null, "Bob", "Williams",
            "bobwilliams", "bob.williams@example.com",
            passwordEncoder.encode("Password123!"), Role.MEMBER, null,
            null, null, null, null, null
        );
        repository.save(Objects.requireNonNull(person));

        PersonDto trial1Dto = new PersonDto(
            null, "Bob", "Williams",
            "bobwilliams", "bob.williams@example.com",
            "wrongPassword", Role.MEMBER, null,
            null, null, null, null, null
        );

        mockMvc.perform(
            post("/band-api/auth/login")
            .contentType("application/json")
            .content(Objects.requireNonNull(objectMapper.writeValueAsString(new PersonDto(
                null, "Bob", "Williams",
                "bobwilliams", "bob.williams@example.com",
                "wrongPassword", Role.MEMBER, null,
                null, null, null, null, null
            ))))
        ).andExpect(status().isForbidden())
            .andExpect(jsonPath("$.message").value("Wrong Password"));
    }


    
}
