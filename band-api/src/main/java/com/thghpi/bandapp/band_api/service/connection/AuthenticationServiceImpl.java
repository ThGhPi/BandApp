package com.thghpi.bandapp.band_api.service.connection;
import com.thghpi.bandapp.band_api.dto.PersonDto;
import com.thghpi.bandapp.band_api.entity.Person;
import com.thghpi.bandapp.band_api.service.mapper.PersonMapper;
import com.thghpi.bandapp.band_api.repository.PersonRepository;

import java.util.List;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

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
    public PersonDto save(PersonDto input) {
        passwordChecker.checkPasswordStrength(input.getTrialPassword());
        Person person = mapper.toEntity(input);
        person.setPassword(passwordEncoder.encode(input.getTrialPassword()));
        return mapper.toDto(repository.save(person));
    }

    /**
     * Authenticates a person using their username and password.
     * @param input the data transfer object containing the person's authentication information
     * @return a JWT token if authentication is successful
     */
    @Override
    public String authenticate(PersonDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getUsername(),
                        input.getTrialPassword()));
        return jwtService.generateToken(
                repository.findByUsername(input.getUsername())
                        .orElseThrow());
    }

    /**
     * Changes the password for the authenticated person.
     * Checks the strength of the new password using the PasswordChecker
     * and then encodes it and saves the updated person entity to the repository.
     * @param List<PersonDto> personList the list of person data transfer objects
     * @return the updated person's data transfer object
     */
    @Override
    public PersonDto changePassword(List<PersonDto> personList) {
        authenticate(personList.getFirst());
        Person person = mapper.toEntity(getAuthenticatedPerson());
        String newPassword = personList.getLast().getTrialPassword();
        passwordChecker.checkPasswordStrength(newPassword);
        person.setPassword(passwordEncoder.encode(newPassword));
        return mapper.toDto(repository.save(person));
    }

    /**
     * Retrieves the authenticated person's information.
     * @return the authenticated person's data transfer object
     */
    @Override
    public PersonDto getAuthenticatedPerson() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Person person = repository.findByUsername(authentication.getName()).orElseThrow();
        return mapper.toDto(person);
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
    public PersonDto updateAuthenticatedPerson(Long id, PersonDto personDto) {
        checkAuthenticatedPerson(id);
        Person updatedPerson = mapper.toEntity(personDto);
        updatedPerson.setId(id);
        return mapper.toDto(repository.save(updatedPerson));
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
     * @param List<PersonDto> personDtos the list of person data transfer objects to save
     * @return the list of saved person data transfer objects if successful, otherwise throws an error
     */
    @Override
    public List<PersonDto> saveAll(List<PersonDto> personDtos) {
        personDtos.forEach(dto -> {
            passwordChecker.checkPasswordStrength(dto.getTrialPassword());
        });
        return repository.saveAll(
            personDtos.stream()
                .map(dto -> {
                    Person person = mapper.toEntity(dto);
                    person.setPassword(passwordEncoder.encode(dto.getTrialPassword()));
                    return person;
                })
                .toList()
            ).stream()
                .map(mapper::toDto)
                .toList();
    }

    /**
     * Verifies that the authenticated person's ID matches the provided ID.
     * If the IDs do not match, an IllegalArgumentException is thrown.
     * @param Long id the ID to verify against the authenticated person's ID
     */
    private void checkAuthenticatedPerson(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Person person = repository.findByUsername(authentication.getName()).orElseThrow();
        if (!person.getId().equals(id)) {
            throw new IllegalArgumentException("You can only update your own profile.");
        }
    }
}