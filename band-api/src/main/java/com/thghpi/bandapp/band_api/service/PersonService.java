package com.thghpi.bandapp.band_api.service;

import java.util.List;

import com.thghpi.bandapp.band_api.dto.PersonDto;

public interface PersonService {

    // CREATE methods are not needed for this entity as it needs authentication management and is handled by the AuthenticationServiceImpl.

    // READ methods
    PersonDto getById(Long id);
    List<PersonDto> getAll();
    List<PersonDto> getByGroupId(Long groupId);

    // UPDATE methods
	List<PersonDto> updateMany(List<PersonDto> personDtos);

    // DELETE methods
    void deleteOne(Long id);
    void deleteMany(List<PersonDto> personDtos);
}
