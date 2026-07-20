package com.thghpi.bandapp.band_api.service.connection;
import com.thghpi.bandapp.band_api.entity.Person;
import com.thghpi.bandapp.band_api.dto.PersonDto;
import com.thghpi.bandapp.band_api.dto.request.LoginRequest;
import com.thghpi.bandapp.band_api.dto.request.RegisterRequest;
import com.thghpi.bandapp.band_api.dto.response.ProfileResponse;
import com.thghpi.bandapp.band_api.repository.PersonRepository;
import com.thghpi.bandapp.band_api.service.mapper.PersonMapper;
import com.thghpi.bandapp.band_api.service.exception.ExistenceConflictMessage;
import com.thghpi.bandapp.band_api.service.exception.FailedPasswordChangeException;
import com.thghpi.bandapp.band_api.service.exception.NotAuthenticatedException;
import com.thghpi.bandapp.band_api.service.exception.NotFoundException;
import com.thghpi.bandapp.band_api.service.exception.NotFoundMessage;
import com.thghpi.bandapp.band_api.service.exception.ExistenceConflictException;

import java.util.List;
import java.util.Objects;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 * AuthenticationServiceImpl
 */
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final PersonMapper mapper;
    private final JwtServiceImpl jwtService;
    private final PersonRepository repository;
    private final PasswordChecker passwordChecker;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    /**
     * Saves a new person to the database after encoding their password.
     * The method takes a PersonDto object, converts it to a Person entity,
     * encodes the password using the PasswordEncoder
     * and then saves it to the repository.
     * Finally, it converts the saved entity back to a PersonDto and returns it.
     * @param input the data transfer object containing the person's information
     * @return the saved person's data transfer object
     */
    @Override
    public PersonDto save(RegisterRequest input) {
        passwordChecker.checkPasswordStrength(input.trialPassword());
        checkUsernameAndEmailUsage(input.username(), input.email(), null);
        Person person = mapper.toEntity(input);
        person.setPassword(passwordEncoder.encode(input.trialPassword()));
        return mapper.toDto(repository.save(person));
    }

    
    /**
     * Authenticates a person using their username and password.
     * @param input the data transfer object containing the person's authentication information
     * @return a JWT token if authentication is successful
     */
    @Override
    public String authenticate(LoginRequest input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.username(),
                        input.trialPassword()));
        return jwtService.generateToken(
                repository.findByUsername(input.username())
                        .orElseThrow());
    }

    /**
     * Changes the password for the authenticated person.
     * Checks the strength of the new password using the PasswordChecker
     * and then encodes it and saves the updated person entity to the repository.
     * @param List<PersonDto> personList the list of person data transfer objects
     * @throws FailedPasswordChangeException when the given initial password isn't correct
     */
    @Override
    public void changePassword(List<LoginRequest> personList) {
        Person person = getAuthenticatedPerson();
        if (!passwordEncoder.matches(
                personList.getFirst().trialPassword(),
                person.getPassword()
        )) {
            throw new FailedPasswordChangeException("Couldn't update password. There is a mismatch.");
        }
        final String newPassword = personList.getLast().trialPassword();
        passwordChecker.checkPasswordStrength(newPassword);
        person.setPassword(passwordEncoder.encode(newPassword));
        repository.save(person);
    }

    /**
     * Retrieves the authenticated person's information.
     * @return the authenticated person's data transfer object
     * @throws NotFoundException when the authenticated person is not found by id in the database
     */
    @Override
    public ProfileResponse getAuthenticatedPersonProfile() {
        Long profileId = getAuthenticatedPerson().getId();
        return mapper.toProfile(
            repository.findPersonWithAllById(profileId)
                .orElseThrow(() -> new NotFoundException(
                    new NotFoundMessage(profileId, Person.class)
                ))
        );
    }

    /**
     * Updates the authenticated person's information.
     * The method checks if the authenticated user's ID matches the ID provided in
     * the path variable.
     * @param Long id the ID of the person to update
     * @param PersonDto personDto the updated person data transfer object
     * @return the updated person's data transfer object
     */
    @Override
    public ProfileResponse updateAuthenticatedPerson(Long id, PersonDto personDto) {
        Person oldPerson = checkAuthenticatedPerson(id);
        checkUsernameAndEmailUsage(
            personDto.username(), personDto.email(), id
        );
        Person updatedPerson = mapper.toEntity(personDto);
        updatedPerson.setId(id);
        updatedPerson.setPassword(oldPerson.getPassword());
        return mapper.toProfile(repository.save(updatedPerson));
    }
    
    /**
     * Deletes the authenticated person's account.
     * The method checks if the authenticated user's ID matches the ID provided
     * before deleting the account from the repository.
     * @param Long id the ID of the person to delete
     */
    @Override
    public void deleteAuthenticatedPerson(@NonNull Long id) {
        checkAuthenticatedPerson(id);
        repository.deleteById(id);
    }
    
    /**
     * Saves all provided person data transfer objects.
     * Checks the strength of each person's password using the PasswordChecker
     * and then encodes it before saving to the repository.
     * @param List<RegisterRequest> personDtos the list of person data transfer objects to register
     * @return the list of saved person data transfer objects if successful, otherwise throws an error
     */
    @Override
    public List<PersonDto> registerAll(List<RegisterRequest> personDtos) {
        personDtos.forEach(dto -> {
            passwordChecker.checkPasswordStrength(dto.trialPassword());
            checkUsernameAndEmailUsage(dto.username(), dto.email(), null);
        });
        return repository.saveAll(
            Objects.requireNonNull(
                personDtos.stream()
                .map(dto -> {
                    Person person = mapper.toEntity(dto);
                    person.setPassword(passwordEncoder.encode(dto.trialPassword()));
                    return person;
                })
                .toList()
            )).stream()
                .map(mapper::toDto)
                .toList();
    }

    /**
     * Verifies that the authenticated person's ID matches the provided ID.
     * If the IDs do not match, an IllegalArgumentException is thrown.
     * @param Long id the ID to verify against the authenticated person's ID
     * @throws NotAuthenticatedException if the authenticated person's ID does not match the provided ID
     */
    @Override
    public Person checkAuthenticatedPerson(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Person person = repository.findByUsername(authentication.getName()).orElseThrow();
        if (!person.getId().equals(id)) {
            throw new NotAuthenticatedException(
                "Person with ID " + id + " is not your current profile."
            );
        }
        return person;
    }

    /**
     * Checks if the provided username and email are already in use.
     * If either the username or email already exists in the repository,
     * a BadCUException is thrown with an appropriate message.
     * @param String username the username to check for uniqueness
     * @param String email the email to check for uniqueness
     * @throws ExistenceConflictException if the username or email already exists in the repository
     */
    @Override
    public void checkUsernameAndEmailUsage(String username, String email, Long id) {
        if (id != null) {
            if (repository.existsByUsernameAndIdNot(username, id)) {
                throw new ExistenceConflictException(new ExistenceConflictMessage(
                    false, Person.class, null,
                    List.of(id), username + " already exists in database"
                ));
            }
            if (repository.existsByEmailAndIdNot(email, id)) {
                throw new ExistenceConflictException(new ExistenceConflictMessage(
                    false, Person.class, null,
                    List.of(id), email + " already exists in database"
                ));
            }
            
        } else {
            if (repository.existsByUsername(username)) {
                throw new ExistenceConflictException(new ExistenceConflictMessage(
                    true, Person.class, null,
                    null, username + " already exists in database"
                ));
            }
            if (repository.existsByEmail(email)) {
                throw new ExistenceConflictException(new ExistenceConflictMessage(
                    true, Person.class, null,
                    null, email + " already exists in database"
                ));
            }
        }
    }

    private Person getAuthenticatedPerson() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return repository.findByUsername(authentication.getName()).orElseThrow();
    }
}