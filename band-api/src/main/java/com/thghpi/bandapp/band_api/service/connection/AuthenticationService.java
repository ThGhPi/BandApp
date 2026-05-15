package com.thghpi.bandapp.band_api.service.connection;

import java.util.List;

import com.thghpi.bandapp.band_api.dto.PersonDto;

public interface AuthenticationService {
    PersonDto save(PersonDto input);

    String authenticate(PersonDto input);

    PersonDto getAuthenticatedPerson();

    PersonDto changePassword(List<PersonDto> personList);

    PersonDto updateAuthenticatedPerson(Long id, PersonDto personDto);

    void deleteAuthenticatedPerson(Long id);

    List<PersonDto> saveAll(List<PersonDto> personDtos);
}
