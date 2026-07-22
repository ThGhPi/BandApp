package com.thghpi.bandapp.band_api.service;
import com.thghpi.bandapp.band_api.dto.PersonDto;
import com.thghpi.bandapp.band_api.dto.PersonRoleDto;

import java.util.Set;
import java.util.List;
import org.springframework.lang.NonNull;

public interface PersonService {

    // CREATE methods are not needed for this entity as it needs authentication management and is handled by the AuthenticationServiceImpl.

    // READ methods
    PersonDto getById(@NonNull Long id);
    List<PersonDto> getAll();
    List<PersonDto> getByGroupId(@NonNull Long groupId);

    // UPDATE methods
	List<PersonRoleDto> updateMany(List<PersonRoleDto> personDtos);
    Set<Long> checkIdsForUpdate(List<PersonRoleDto> personDtos);

    // DELETE methods
    void deleteOne(@NonNull Long id);
    void deleteMany(List<PersonDto> personDtos);
}
