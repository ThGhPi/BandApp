package com.thghpi.bandapp.band_api.unit.service.connection;
import com.thghpi.bandapp.band_api.dto.PersonDto;
import com.thghpi.bandapp.band_api.entity.Person;
import com.thghpi.bandapp.band_api.entity.enumeration.Role;
import com.thghpi.bandapp.band_api.repository.PersonRepository;
import com.thghpi.bandapp.band_api.service.connection.AuthenticationService;
import com.thghpi.bandapp.band_api.service.connection.JwtServiceImpl;
import com.thghpi.bandapp.band_api.service.connection.PasswordChecker;
import com.thghpi.bandapp.band_api.service.mapper.PersonMapper;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
 * Unit tests for the {@link AuthenticationService} class.
 * AuthenticationServiceTest
 */
public class AuthenticationServiceTest {
    @Mock
    private PersonMapper mapper;
    @Mock
    private JwtServiceImpl jwtService;
    @Mock
    private PersonRepository repository;
    @Mock
    private PasswordChecker passwordChecker;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AuthenticationManager authenticationManager;
    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    void shouldSaveValidNewPerson() {
        PersonDto input = new PersonDto(
            null, "John", "Doe", "johndoe",
            "john.doe@example.com", "Password123!",
            Role.MEMBER, null, null, null,
            null, null, null
        );
        PersonDto output = new PersonDto(
            1L, "John", "Doe", "johndoe",
            "john.doe@example.com", null,
            Role.MEMBER, null, null, null,
            null, null, null
        );
        Person person = new Person(
            null, "John", "Doe", "johndoe",
            "john.doe@example.com", "Password123!",
            Role.MEMBER, null, null, null,
            null, null, null
        );
        when(passwordEncoder.encode(input.getTrialPassword())).thenReturn("encodedPassword");
        when(mapper.toDto(repository.save(person))).thenReturn(output);
        when(mapper.toEntity(input)).thenReturn(person);
        when(repository.existsByUsername(input.getUsername())).thenReturn(false);
        when(repository.existsByEmail(input.getEmail())).thenReturn(false);
        doNothing().when(passwordChecker).checkPasswordStrength(input.getTrialPassword());

        PersonDto result = assertDoesNotThrow(() -> authenticationService.save(input));
        assertEquals(output, result);
    }

    @Test
    void shouldRefuseRegistrationWithInvalidData() {
        // TODO: Implement test for refusing registration with invalid data
    }

    @Test
    void shouldChangePasswordForAuthenticatedPerson() {
        // TODO: Implement test for changing password for authenticated person
    }

    @Test
    void shouldRefusePasswordChangeForUnauthenticatedPerson() {
        // TODO: Implement test for refusing password change for unauthenticated person
    }

    @Test
    void shouldUpdateAuthenticatedPerson() {
        // TODO: Implement test for updating authenticated person
    }

    @Test
    void shouldRefuseUpdateForUnauthenticatedPerson() {
        // TODO: Implement test for refusing update for unauthenticated person
    }

    @Test
    void shouldDeleteAuthenticatedPerson() {
        // TODO: Implement test for deleting authenticated person
    }

    @Test
    void shouldRefuseDeletionForUnauthenticatedPerson() {
        // TODO: Implement test for refusing deletion for unauthenticated person
    }
}
