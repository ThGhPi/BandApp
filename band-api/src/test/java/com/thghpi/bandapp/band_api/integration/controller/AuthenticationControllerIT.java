package com.thghpi.bandapp.band_api.integration.controller;
import com.thghpi.bandapp.band_api.dto.PersonDto;
import com.thghpi.bandapp.band_api.entity.Person;
import com.thghpi.bandapp.band_api.entity.enumeration.Role;
import com.thghpi.bandapp.band_api.repository.PersonRepository;
import com.thghpi.bandapp.band_api.integration.AbstractIntegrationTest;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Objects;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * AuthenticationControllerIT is class to test integration when solliciting the AuthenticationController
 * It tests login, registration and profil management (password change, update, reading and deletion by the user on his own profil)
 */
@AutoConfigureMockMvc
public class AuthenticationControllerIT extends AbstractIntegrationTest {
    /** MockMvc instance used to perform HTTP requests in the tests. */
    @Autowired
    private MockMvc mockMvc;
    /** The password encoder used for password constency in the database - use the corresponding Bean of the api */
    @Autowired
    private PasswordEncoder passwordEncoder;
    /** The object mapper to generate json body from java dtos */
    @Autowired
    ObjectMapper objectMapper;
    /** The repository used to create persons when needed */
    @Autowired
    private PersonRepository repository;
    
    /**
     * Clean database before each test in the test container
     * to make sure there is no data interferences between tests.
     */
    @BeforeEach
    public void cleanDatabase() {
        repository.deleteAll();
    }

    /**
     * Test for registering a person with already used data.
     * Tested endpoint : POST /band-api/auth/register
     * @throws Exception when test fail
     */
    @Test
    public void shouldRefuseExistingEmailOrUsernameRegistration() throws Exception {
        String usernameJohn = "johndoe";
        String usernameAlice = "alicesmith";
        String emailJohn = "john.doe@example.com";
        String emailAlice = "alice.smith@example.com";

        repository.save(Objects.requireNonNull(new Person(
            null, "John", "Doe",
            usernameJohn, emailJohn, "encodedPassword",
            Role.MEMBER, null, null, null,
            null, null, null
        )));

        PersonDto toRegisterAliceDto = new PersonDto(
            null, "Alice", "Smith",
            usernameJohn, emailAlice, "Password123!",
            Role.MEMBER, null, null, null,
            null, null, null
        );
        
        mockMvc.perform(
            post("/band-api/auth/register")
            .contentType("application/json")
            .content(Objects.requireNonNull(
                objectMapper.writeValueAsString(toRegisterAliceDto)
            ))
        ).andDo(print())
            .andExpect(status().isConflict())
            .andExpect(jsonPath("$.message").value(
                "Can't create Person. Username already exists in database."
            ));
        
        PersonDto toRegisterAliceDto2 = new PersonDto(
            null, "Alice", "Smith",
            usernameAlice, emailJohn, "Password123!",
            Role.MEMBER, null, null, null,
            null, null, null
        );
        
        mockMvc.perform(
            post("/band-api/auth/register")
            .contentType("application/json")
            .content(Objects.requireNonNull(
                objectMapper.writeValueAsString(toRegisterAliceDto2)
            ))
        ).andExpect(status().isConflict())
            .andExpect(jsonPath("$.message").value(
                "Can't create Person. Email already exists in database."
            ));
    }

    /**
     * Test for registering a person with valid data.
     * Tested endpoint : POST /band-api/auth/register
     * @throws Exception when test fails
     */
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
            .content(Objects.requireNonNull(
                objectMapper.writeValueAsString(toRegisterPersonDto)
            ))
        ).andExpect(status().isCreated())
            .andExpect(jsonPath("$.username").value(toRegisterPersonDto.getUsername()));
    }

    /**
     * Test for successfull login
     * Tested endpoint : POST /band-api/auth/login
     * @throws Exception when test fails
     */
    @Test
    public void shouldLoginUserSuccessfully() throws Exception {
        String rightPassword = "RightPassword123!";
        Person person = new Person(
            null, "Bob", "Williams",
            "bobwilliams", "bob.williams@example.com",
            passwordEncoder.encode(rightPassword), Role.MEMBER, null,
            null, null, null, null, null
        );
        person = repository.save(Objects.requireNonNull(person));
        
        PersonDto trial1Dto = new PersonDto(
            null, null, null,
            person.getUsername(), null,
            rightPassword, null, null,
            null, null, null, null, null
        );

        mockMvc.perform(
            post("/band-api/auth/login")
            .contentType("application/json")
            .content(Objects.requireNonNull(objectMapper.writeValueAsString(trial1Dto)))
        ).andExpect(status().isOk())
            .andExpect(jsonPath("$.token").exists());
    }

    /**
     * Test for failed login
     * Tested endpoint : POST /band-api/auth/login
     * @throws Exception when test fails
     */
    @Test
    public void shouldRefuseAuthenticationLogin() throws Exception {
        String rightPassword = "RightPassword123!";
        String wrongPassword = "WrongPassword123!";
        
        Person person = new Person(
            null, "Bob", "Williams",
            "bobwilliams", "bob.williams@example.com",
            passwordEncoder.encode(rightPassword), Role.MEMBER, null,
            null, null, null, null, null
        );
        repository.save(Objects.requireNonNull(person));

        PersonDto trial1Dto = new PersonDto(
            null, null, null,
            person.getUsername(), null, wrongPassword,
            null, null, null,
            null, null, null, null
        );

        mockMvc.perform(
            post("/band-api/auth/login")
            .contentType("application/json")
            .content(Objects.requireNonNull(
                objectMapper.writeValueAsString(trial1Dto)
            ))
        ).andExpect(status().isForbidden())
            .andExpect(jsonPath("$").doesNotExist());
    }

    /**
     * Test for successfull password change
     * Tested endpoint : PUT /band-api/auth/me
     * @throws Exception when test fails
     */
    @Test
    public void shouldChangePasswordSuccessfully() throws Exception {
        String initialPassword = "InitPassword123!";
        String newPassword = "NewPassword123!";
        Person person = repository.save(Objects.requireNonNull(new Person(
            null, "Doe", "John",
            "johndoe", "john.doe@example.com",
            passwordEncoder.encode(initialPassword), Role.MEMBER,
            null, null, null, null,
            null, null
        )));
        List<PersonDto> passwordChangeDtos = List.of(
            new PersonDto(
                null, null, null, person.getUsername(),
                null, initialPassword, null, null,
                null, null, null,
                null, null
            ),
            new PersonDto(
                null, null, null, person.getUsername(),
                null, newPassword, null, null,
                null, null, null,
                null, null
            )
        );

        MvcResult loginResult = mockMvc.perform(
        post("/band-api/auth/login")
            .contentType("application/json")
            .content(Objects.requireNonNull(
                objectMapper.writeValueAsString(passwordChangeDtos.getFirst())
            ))
        ).andExpect(status().isOk())
            .andReturn();

        String token = JsonPath.read(
            loginResult.getResponse().getContentAsString(),
            "$.token"
        );
        
        mockMvc.perform(
            put("/band-api/auth/me")
            .header("Authorization", "Bearer " + token)
            .contentType("application/json")
            .content(Objects.requireNonNull(
                objectMapper.writeValueAsString(passwordChangeDtos))
            )
        ).andDo(print())
            .andExpect(status().isNoContent());
    }

    /**
     * Test for failed password change becaus of wrong initial password
     * Tested endpoint : PUT /band-api/auth/me
     * @throws Exception when test fails
     */
    @Test
    public void shouldRefusePasswordChange() throws Exception {
        String initialPassword = "InitPassword123!";
        String wrongPassword = "WrongPassword";
        String newPassword = "NewPassword123!";
        Person person = repository.save(Objects.requireNonNull(new Person(
            null, "Doe", "John",
            "johndoe", "john.doe@example.com",
            passwordEncoder.encode(initialPassword), Role.MEMBER,
            null, null, null, null,
            null, null
        )));
        List<PersonDto> passwordChangeDtos = List.of(
            new PersonDto(
                null, null, null, person.getUsername(),
                null, wrongPassword, null, null,
                null, null, null,
                null, null
            ),
            new PersonDto(
                null, null, null, person.getUsername(),
                null, newPassword, null, null,
                null, null, null,
                null, null
            )
        );

        mockMvc.perform(
            put("/band-api/auth/me")
            .contentType("application/json")
            .content(Objects.requireNonNull(
                objectMapper.writeValueAsString(passwordChangeDtos))
            )
        ).andExpect(status().isForbidden())
            .andExpect(jsonPath("$").doesNotExist());
    }
    
    @Test
    void shouldRefuseToProvideProfil() {

    }
}
