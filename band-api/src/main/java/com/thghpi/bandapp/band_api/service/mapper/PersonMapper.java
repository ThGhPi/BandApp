package com.thghpi.bandapp.band_api.service.mapper;
import com.thghpi.bandapp.band_api.entity.Person;
import com.thghpi.bandapp.band_api.dto.PersonDto;
import com.thghpi.bandapp.band_api.dto.PersonRoleDto;
import com.thghpi.bandapp.band_api.dto.request.RegisterRequest;
import com.thghpi.bandapp.band_api.dto.response.ProfileResponse;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper(
    componentModel = "spring",
    uses = {
        ChoiceMapper.class
    })
public interface PersonMapper {

    // --- DTO --> Entity ---
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "address", ignore = true)
    @Mapping(target = "groups", ignore = true)
    @Mapping(target = "instruments", ignore = true)
    @Mapping(target = "choices", ignore = true)
    Person toEntity(PersonDto personDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "address", ignore = true)
    @Mapping(target = "groups", ignore = true)
    @Mapping(target = "instruments", ignore = true)
    @Mapping(target = "choices", ignore = true)
    Person toEntity(RegisterRequest personDto);

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "address", ignore = true)
    @Mapping(target = "groups", ignore = true)
    @Mapping(target = "instruments", ignore = true)
    @Mapping(target = "choices", ignore = true)
    Person toEntity(PersonRoleDto personDto);

    /**
     * method to update an existing entity using a given dto
     * @param dto the PersonRoleDto with the new information
     * @param entity the entity to update
     */
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "address", ignore = true)
    @Mapping(target = "groups", ignore = true)
    @Mapping(target = "instruments", ignore = true)
    @Mapping(target = "choices", ignore = true)
    void updatePersonFromRoleDto(
        PersonRoleDto dto, @MappingTarget Person entity
    );

    // --- Entity --> DTO ---
    @Mapping(target = "address", ignore = true)
    @Mapping(target = "instruments", ignore = true)
    PersonDto toDto(Person person);
    
    PersonRoleDto toRoleDto(Person person);

    @Mapping(target = "address", expression = "java(null)") // TODO : Change when implementing Place feature
    @Mapping(target = "instruments", ignore = true) // TODO : Change when implementing Instrument feature
    @Mapping(target = "groups", ignore = true) // TODO : Change when implementing Group feature
    ProfileResponse toProfile(Person person);
}
