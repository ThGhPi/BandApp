package com.thghpi.bandapp.band_api.controller;
import com.thghpi.bandapp.band_api.dto.PersonDto;
import com.thghpi.bandapp.band_api.dto.LoginResponse;
import com.thghpi.bandapp.band_api.service.connection.AuthenticationServiceImpl;

import org.springframework.lang.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import lombok.RequiredArgsConstructor;

/**
 * Controller class for handling authentication-related requests,
 * including user registration, login, password renewal, and profile management.
 * It uses the AuthenticationServiceImpl to perform the necessary operations and returns appropriate responses.
 * AuthenticationController
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/band-api/auth")
public class AuthenticationController {
    private final AuthenticationServiceImpl authService;

    /**
     * Takes the body of the request as a PersonDto for endpoint band-api/auth/register,
     * and returns the registered PersonDto when the registration process is successful.
     * @param toRegisterPersonDto the PersonDto to register, taken from the body of the request.
     * @return the registered PersonDto if the registration process is successful, otherwise an error response.
     */
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PersonDto> register(@RequestBody PersonDto toRegisterPersonDto) {
        PersonDto registeredPersonDto = authService.save(toRegisterPersonDto);
        return ResponseEntity
            .status(HttpStatus.CREATED.value())
            .body(registeredPersonDto);
    }

    /**
     * For POST request on endpoint band-api/auth/register/many,
     * takes a list of PersonDto from the body of the request,
     * and returns a list of registered PersonDto when the registration process is successful.
     * @param personDtos the list of PersonDto to register, taken from the body of the request.
     * @return the list of registered PersonDto if the registration process is successful, otherwise an error response.
     */
    @PostMapping("/register/many")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<List<PersonDto>> registerMany(@RequestBody List<PersonDto> personDtos) {
        List<PersonDto> registeredPersons = authService.saveAll(personDtos);
        return ResponseEntity
            .status(HttpStatus.CREATED.value())
            .body(registeredPersons);
    }
    

    /**
     * Takes the body of the request as a PersonDto for endpoint band-api/auth/login,
     * and returns a JWT token when the authentication process is successful.
     * @param toAuthPerson the PersonDto for authentication, taken from the body of the request.
     * @return the JWT token if the authentication process is successful, otherwise an error response.
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody PersonDto toAuthPerson) {
        String jwtToken = authService.authenticate(toAuthPerson);
        LoginResponse loginResponse = new LoginResponse(jwtToken);

        return ResponseEntity.ok(loginResponse);
    }

    /**
     * Takes the body of the request as a list of PersonDto for endpoint band-api/auth/me,
     * and returns an no content ResponseEntity when the password renewal process is successful.
     * @param personList the list of PersonDto for password renewal, taken from the body of the request.
     * @return a no content ResponseEntity if the password renewal process is successful, otherwise an error response.
     */
    @PutMapping("/me")
    public ResponseEntity<Void> renewPassword(@RequestBody List<PersonDto> personList) {
        authService.changePassword(personList);
        ResponseEntity<Void> response = ResponseEntity.noContent().build();
        System.out.println(response.getStatusCode());
        throw new RuntimeException("Does work as expected" + response.getStatusCode());
        // return response;
    }
    
    /**
     * Answer GET request for endpoint band-api/auth/me by returning the data of the authenticated user.
     * @return the PersonDto of the currently authenticated user if successful, else an error response.
     */
    @GetMapping("/me")
    public ResponseEntity<PersonDto> getProfil() {
        PersonDto currentPerson = authService.getAuthenticatedPerson();
        return ResponseEntity.ok(currentPerson);
    }

    /**
     * For PUT request on endpoint band-api/auth/me/{id}, takes the id of the authenticated user as a path variable
     * and the updated data as a PersonDto in the body of the request,
     * and returns the updated PersonDto when the update process is successful.
     * @param id the id of the authenticated user, taken as a path variable.
     * @param personDto the updated PersonDto, taken from the body of the request.
     * @return the updated PersonDto if the update process is successful, otherwise an error response.
     */
    @PutMapping("/me/{id}")
    public ResponseEntity<PersonDto> updateProfil(@PathVariable Long id, @RequestBody PersonDto personDto) {
        PersonDto updatedPerson = authService.updateAuthenticatedPerson(id, personDto);
        return ResponseEntity.ok(updatedPerson);
    }

    /**
     * For DELETE request on endpoint band-api/auth/me/{id}, takes the id of the authenticated user as a path variable,
     * and deletes the authenticated user's account when the deletion process is successful.
     * @param id the id of the authenticated user, taken as a path variable.
     * @return a no content response if the deletion process is successful, otherwise an error response.
     */
    @DeleteMapping("/me/{id}")
    public ResponseEntity<Void> deleteProfil(@PathVariable @NonNull Long id) {
        authService.deleteAuthenticatedPerson(id);
        return ResponseEntity.noContent().build();
    }
}
