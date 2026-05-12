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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final PersonMapper mapper;
    private final PersonRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    
    /**
     * Saves a new person to the database after encoding their password.
     * The method takes a PersonDto object, converts it to a Person entity,
     * encodes the password using the PasswordEncoder and then saves it to the repository.
     * Finally, it converts the saved entity back to a PersonDto and returns it.
     * @param input the data transfer object containing the person's information
     * @return the saved person's data transfer object
     */
    @Override
    public PersonDto save(PersonDto input) {
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
                input.getTrialPassword()
            )
        );
        return jwtService.generateToken(
            repository.findByUsername(input.getUsername())
                .orElseThrow()
        );
    }

    /**
     * Changes the password for the authenticated person.
     * @param personList the list of person data transfer objects
     * @return the updated person's data transfer object
     */
    @Override
    public PersonDto changePassword(List<PersonDto> personList) {
        authenticate(personList.getFirst());
        Person person = mapper.toEntity(getAuthenticatedPerson());
        person.setPassword(passwordEncoder.encode(personList.getLast().getTrialPassword()));
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
}