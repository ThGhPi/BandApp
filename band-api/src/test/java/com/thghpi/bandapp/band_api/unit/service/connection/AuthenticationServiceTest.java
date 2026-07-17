package com.thghpi.bandapp.band_api.unit.service.connection;
import com.thghpi.bandapp.band_api.dto.PersonDto;
import com.thghpi.bandapp.band_api.entity.Person;
import com.thghpi.bandapp.band_api.entity.enumeration.Role;
import com.thghpi.bandapp.band_api.repository.PersonRepository;
import com.thghpi.bandapp.band_api.service.mapper.PersonMapper;
import com.thghpi.bandapp.band_api.service.connection.JwtServiceImpl;
import com.thghpi.bandapp.band_api.service.connection.PasswordChecker;
import com.thghpi.bandapp.band_api.service.exception.ExistenceConflictException;
import com.thghpi.bandapp.band_api.service.connection.AuthenticationServiceImpl;

import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;

import java.util.Objects;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * Unit tests for the {@link AuthenticationService} class.
 * AuthenticationServiceTest
 */
@ExtendWith(MockitoExtension.class)
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
    private AuthenticationServiceImpl service;

    private PersonDto input;
    private PersonDto output = new PersonDto(
            1L, "John", "Doe", "johndoe",
            "john.doe@example.com", null,
            Role.MEMBER, null, null, null,
            null, null, null
        );
    private Person person = new Person(
            null, "John", "Doe", "johndoe",
            "john.doe@example.com", "encodedPassword",
            Role.MEMBER, null, null, null,
            null, null, null
        );

    /**
     * Reset the input dto and the entituy person used by the tests.
     */
    @BeforeEach
    void setUp() {
        input = new PersonDto(
            null, "John", "Doe", "johndoe",
            "john.doe@example.com", "Password123!",
            Role.MEMBER, null, null, null,
            null, null, null
        );
        person.setId(null);
    }
    
    /**
     * Test the comportment of save method when there are no problems
     */
    @Test
    void shouldSaveValidNewPerson() {
        when(passwordEncoder.encode(input.getTrialPassword())).thenReturn("encodedPassword");
        when(mapper.toDto(repository.save(Objects.requireNonNull(person)))).thenReturn(output);
        when(mapper.toEntity(input)).thenReturn(person);
        when(repository.existsByUsername(input.getUsername())).thenReturn(false);
        when(repository.existsByEmail(input.getEmail())).thenReturn(false);
        doNothing().when(passwordChecker).checkPasswordStrength(input.getTrialPassword());
        
        PersonDto result = assertDoesNotThrow(() -> service.save(input));
        assertEquals(output, result);
    }

    /**
     * Test the comportment of save method when there are already existing username or email in database
     */
    @Test
    void shouldRefuseRegistrationWithInvalidData() {
        when(repository.existsByUsername(input.getUsername())).thenReturn(true);
        doNothing().when(passwordChecker).checkPasswordStrength(input.getTrialPassword());
        
        ExistenceConflictException thrown = assertThrows(ExistenceConflictException.class, () -> service.save(input));
        assertEquals(
            "Can't create Person. " + input.getUsername() + " already exists in database.",
            thrown.getMessage()
        );
    }
}
