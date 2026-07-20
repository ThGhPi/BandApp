package com.thghpi.bandapp.band_api.controller;
import com.thghpi.bandapp.band_api.dto.PersonDto;
import com.thghpi.bandapp.band_api.dto.request.LoginRequest;
import com.thghpi.bandapp.band_api.dto.request.RegisterRequest;
import com.thghpi.bandapp.band_api.dto.response.LoginResponse;
import com.thghpi.bandapp.band_api.dto.response.ProfileResponse;
import com.thghpi.bandapp.band_api.service.connection.AuthenticationServiceImpl;
import com.thghpi.bandapp.band_api.service.exception.BadCUException;
import com.thghpi.bandapp.band_api.service.exception.BadCUMessage;

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
     * For POST request on endpoint band-api/auth/register,
     * Takes the body of the request as a PersonDto for endpoint band-api/auth/register,
     * and returns the registered PersonDto when the registration process is successful.
     * @param toRegisterPersonDto the PersonDto to register, taken from the body of the request.
     * @return the registered PersonDto if the registration process is successful, otherwise an error response.
     */
    @PostMapping("/register")
    public ResponseEntity<PersonDto> register(@RequestBody RegisterRequest toRegisterPersonDto) {
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
    public ResponseEntity<List<PersonDto>> registerMany(@RequestBody List<RegisterRequest> personDtos) {
        List<PersonDto> registeredPersons = authService.registerAll(personDtos);
        return ResponseEntity
            .status(HttpStatus.CREATED.value())
            .body(registeredPersons);
    }
    

    /**
     * For POST request on endpoint band-api/auth/login,
     * Takes the body of the request as a PersonDto for endpoint band-api/auth/login,
     * and returns a JWT token when the authentication process is successful.
     * @param toAuthPerson the PersonDto for authentication, taken from the body of the request.
     * @return the JWT token if the authentication process is successful, otherwise an error response.
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest toAuthPerson) {
        String jwtToken = authService.authenticate(toAuthPerson);
        LoginResponse loginResponse = new LoginResponse(jwtToken);

        return ResponseEntity.ok(loginResponse);
    }

    /**
     * For PUT request on endpoint band-api/auth/me,
     * Takes the body of the request as a list of PersonDto for endpoint band-api/auth/me,
     * and returns an no content ResponseEntity when the password renewal process is successful.
     * @param personList the list of PersonDto for password renewal, taken from the body of the request.
     * @return a no content ResponseEntity if the password renewal process is successful, otherwise an error response.
     */
    @PutMapping("/me")
    public ResponseEntity<Void> renewPassword(@RequestBody List<LoginRequest> personList) {
        authService.changePassword(personList);
        return ResponseEntity.noContent().build();
    }
    
    /**
     * For GET request on endpoint band-api/auth/me,
     * Answer GET request for endpoint band-api/auth/me by returning the data of the authenticated user.
     * @return the PersonDto of the currently authenticated user if successful, else an error response.
     */
    @GetMapping("/me")
    public ResponseEntity<ProfileResponse> getProfil() {
        ProfileResponse currentPerson = authService.getAuthenticatedPersonProfile();
        return ResponseEntity.ok(currentPerson);
    }

    /**
     * For PUT request on endpoint band-api/auth/me/{id}, takes the id of the authenticated user as a path variable
     * and the updated data as a PersonDto in the body of the request,
     * and returns the updated PersonDto when the update process is successful.
     * @param id the id of the authenticated user, taken as a path variable.
     * @param personDto the updated PersonDto, taken from the body of the request.
     * @return the updated PersonDto if the update process is successful, otherwise an error response.
     * @throws BadCUException whe, the path id and the given personDto id mismatch.
     */
    @PutMapping("/me/{id}")
    public ResponseEntity<ProfileResponse> updateProfil(@PathVariable Long id, @RequestBody PersonDto personDto) {
        if (id != personDto.id()) {
            throw new BadCUException(new BadCUMessage(
                false, personDto.getClass(), "with mismatched IDs", null, null
            ));
        }
        ProfileResponse updatedPerson = authService.updateAuthenticatedPerson(id, personDto);
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
