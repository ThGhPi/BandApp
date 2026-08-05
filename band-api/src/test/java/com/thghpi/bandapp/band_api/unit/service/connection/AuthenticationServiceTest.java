package com.thghpi.bandapp.band_api.unit.service.connection;
import com.thghpi.bandapp.band_api.dto.PersonDto;
import com.thghpi.bandapp.band_api.dto.request.RegisterRequest;
import com.thghpi.bandapp.band_api.entity.Person;
import com.thghpi.bandapp.band_api.entity.enumeration.Role;
import com.thghpi.bandapp.band_api.repository.PersonRepository;
import com.thghpi.bandapp.band_api.service.mapper.PersonMapper;
import com.thghpi.bandapp.band_api.service.connection.JwtServiceImpl;
import com.thghpi.bandapp.band_api.service.connection.PasswordChecker;
import com.thghpi.bandapp.band_api.service.exception.ExistenceConflictException;
import com.thghpi.bandapp.band_api.service.connection.AuthenticationService;
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
 * AuthenticationServiceTest class of
 * Unit tests for the {@link AuthenticationService} class.
 */
@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {
    /** mock of a PersonMapper */
    @Mock
    private PersonMapper mapper;
    /** mock of a JwtServiceImpl */
    @Mock
    private JwtServiceImpl jwtService;
    /** mock of a PersonRepository */
    @Mock
    private PersonRepository repository;
    /** mock of a PasswordChecker */
    @Mock
    private PasswordChecker passwordChecker;
    /** mock of a PasswordEncoder */
    @Mock
    private PasswordEncoder passwordEncoder;
    /** mock of an AuthenticationManager */
    @Mock
    private AuthenticationManager authenticationManager;
    /** AuthenticationServiceImpl to be tested, injected with mocks for it's fields */
    @InjectMocks
    private AuthenticationServiceImpl service;

    /** A dto used as an input for testing */
    private RegisterRequest input;
    /** A dto used as output for testing */
    private PersonDto output = new PersonDto(
            1L, "John", "Doe", "johndoe",
            "john.doe@example.com", null,
            null, null, null
        );
    private Person person = Person.builder()
            .id(null)
            .firstname("John")
            .lastname("Doe")
            .username("johndoe")
            .email("john.doe@example.com")
            .password("encodedPassword")
            .role(Role.MEMBER)
            .build();

    /**
     * Reset the input dto and the entituy person used by the tests.
     */
    @BeforeEach
    void setUp() {
        input = new RegisterRequest(
            "John", "Doe","johndoe", "john.doe@example.com",
            "Password123!", null, null,
            null, null
        );
        person.setId(null);
    }
    
    /**
     * Test the comportment of save method when there are no problems
     */
    @Test
    void shouldSaveValidNewPerson() {
        when(passwordEncoder.encode(input.trialPassword())).thenReturn("encodedPassword");
        when(mapper.toDto(repository.save(Objects.requireNonNull(person)))).thenReturn(output);
        when(mapper.toEntity(input)).thenReturn(person);
        when(repository.existsByUsername(input.username())).thenReturn(false);
        when(repository.existsByEmail(input.email())).thenReturn(false);
        doNothing().when(passwordChecker).checkPasswordStrength(input.trialPassword());
        
        PersonDto result = assertDoesNotThrow(() -> service.save(input));
        assertEquals(output, result);
    }

    /**
     * Test the comportment of save method when there are already existing username or email in database
     */
    @Test
    void shouldRefuseRegistrationWithInvalidData() {
        when(repository.existsByUsername(input.username())).thenReturn(true);
        doNothing().when(passwordChecker).checkPasswordStrength(input.trialPassword());
        
        ExistenceConflictException thrown = assertThrows(ExistenceConflictException.class, () -> service.save(input));
        assertEquals(
            "Can't create Person. " + input.username() + " already exists in database.",
            thrown.getMessage()
        );
    }
}
