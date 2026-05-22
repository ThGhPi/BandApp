package com.thghpi.bandapp.band_api.service.connection;
import com.thghpi.bandapp.band_api.dto.PersonDto;

import java.util.List;
import org.springframework.lang.NonNull;

public interface AuthenticationService {
    PersonDto save(PersonDto input);

    String authenticate(PersonDto input);

    PersonDto getAuthenticatedPerson();

    PersonDto changePassword(List<PersonDto> personList);

    PersonDto updateAuthenticatedPerson(Long id, PersonDto personDto);

    void deleteAuthenticatedPerson(@NonNull Long id);

    List<PersonDto> saveAll(List<PersonDto> personDtos);
}
