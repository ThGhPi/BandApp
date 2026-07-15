package com.thghpi.bandapp.band_api.service;
import com.thghpi.bandapp.band_api.dto.PersonDto;

import java.util.List;
import org.springframework.lang.NonNull;

public interface PersonService {

    // CREATE methods are not needed for this entity as it needs authentication management and is handled by the AuthenticationServiceImpl.

    // READ methods
    PersonDto getById(@NonNull Long id);
    List<PersonDto> getAll();
    List<PersonDto> getByGroupId(@NonNull Long groupId);

    // UPDATE methods
	List<PersonDto> updateMany(List<PersonDto> personDtos);
    void checkIdsForUpdate(List<PersonDto> personDtos);

    // DELETE methods
    void deleteOne(@NonNull Long id);
    void deleteMany(List<PersonDto> personDtos);
}
