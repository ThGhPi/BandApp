package com.thghpi.bandapp.band_api.service.connection;
import com.thghpi.bandapp.band_api.dto.PersonDto;

import java.util.List;
import org.springframework.lang.NonNull;

public interface AuthenticationService {
    // AUTHENTICATION
    String authenticate(PersonDto input);

    // CREATE - new member or admin only
    PersonDto save(PersonDto input);
    List<PersonDto> saveAll(List<PersonDto> personDtos);

    // READ - authenticated person only
    PersonDto getAuthenticatedPerson();

    // UPDATE - authenticated person only
    void changePassword(List<PersonDto> personList);
    PersonDto updateAuthenticatedPerson(Long id, PersonDto personDto);

    // DELETE - authenticated person only
    void deleteAuthenticatedPerson(@NonNull Long id);

}
