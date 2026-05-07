package com.thghpi.bandapp.band_api.controller;
import com.thghpi.bandapp.band_api.dto.PersonDto;
import com.thghpi.bandapp.band_api.dto.LoginResponse;
import com.thghpi.bandapp.band_api.service.connection.AuthenticationServiceImpl;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/band-api/auth")
public class AuthenticationController {
    private final AuthenticationServiceImpl authService;

    /**
     * Takes the body of the request as a PersonDto for endpoint band-api/auth/register,
     * and returns the registered PersonDto when the registration process is successful.
     * @param toRegisterPersonDto the PersonDto to register, taken from the body of the request
     * @return the registered PersonDto if the registration process is successful, otherwise an error response
     */
    @PostMapping("/register")
    public ResponseEntity<PersonDto> register(@RequestBody PersonDto toRegisterPersonDto) {
        PersonDto registeredPersonDto = authService.save(toRegisterPersonDto);
        return ResponseEntity.ok(registeredPersonDto);
    }

    /**
     * Takes the body of the request as a PersonDto for endpoint band-api/auth/login,
     * and returns a JWT token when the authentication process is successful.
     * @param toAuthPerson the PersonDto for authentication, taken from the body of the request
     * @return the JWT token if the authentication process is successful, otherwise an error response
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody PersonDto toAuthPerson) {
        String jwtToken = authService.authenticate(toAuthPerson);
        LoginResponse loginResponse = new LoginResponse(jwtToken);

        return ResponseEntity.ok(loginResponse);
    }

    /**
     * Takes the body of the request as a list of PersonDto for endpoint band-api/auth/me,
     * and returns the PersonDto with the user information when the password renewal process is successful.
     * @param personList the list of PersonDto for password renewal, taken from the body of the request
     * @return the updated PersonDto if the password renewal process is successful, otherwise an error response
     */
    @PutMapping("/me")
    public ResponseEntity<PersonDto> renewPassword(@RequestBody List<PersonDto> personList) {
        PersonDto currentPerson = authService.changePassword(personList);
        
        return ResponseEntity.ok(currentPerson);
    }
    
    /**
     * Returns the PersonDto of the currently authenticated user for endpoint band-api/auth/me.
     * @return the PersonDto of the currently authenticated user if the retrieval process is successful, otherwise an error response
     */
    @GetMapping("/me")
    public ResponseEntity<PersonDto> getProfil() {
        PersonDto currentPerson = authService.getAuthenticatedPerson();
        return ResponseEntity.ok(currentPerson);
    }
    
}
