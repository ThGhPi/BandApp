package com.thghpi.bandapp.band_api.service.connection;
import com.thghpi.bandapp.band_api.dto.PersonDto;
import com.thghpi.bandapp.band_api.dto.request.LoginRequest;
import com.thghpi.bandapp.band_api.dto.request.RegisterRequest;
import com.thghpi.bandapp.band_api.dto.response.ProfileResponse;
import com.thghpi.bandapp.band_api.entity.Person;

import java.util.List;
import org.springframework.lang.NonNull;

public interface AuthenticationService {
    // AUTHENTICATION
    String authenticate(LoginRequest input);

    // CREATE - new member or admin only
    PersonDto save(RegisterRequest input);
    List<PersonDto> registerAll(List<RegisterRequest> personDtos);

    // READ - authenticated person only
    ProfileResponse getAuthenticatedPersonProfile();

    // UPDATE - authenticated person only
    void changePassword(List<LoginRequest> personList);
    ProfileResponse updateAuthenticatedPerson(Long id, PersonDto personDto);

    // DELETE - authenticated person only
    void deleteAuthenticatedPerson(@NonNull Long id);

    // CHECK - for registration and update
    Person checkAuthenticatedPerson(Long id);
    void checkUsernameAndEmailUsage(String username, String email, Long id);

}
