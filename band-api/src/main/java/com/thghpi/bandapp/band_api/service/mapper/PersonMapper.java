package com.thghpi.bandapp.band_api.service.mapper;
// import com.thghpi.bandapp.band_api.entity.Choice;
import com.thghpi.bandapp.band_api.entity.Person;
import com.thghpi.bandapp.band_api.dto.PersonDto;
import com.thghpi.bandapp.band_api.dto.PersonRoleDto;
import com.thghpi.bandapp.band_api.dto.request.RegisterRequest;
import com.thghpi.bandapp.band_api.dto.response.ProfileResponse;

// import java.util.Objects;
// import java.util.Set;
// import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
// import org.mapstruct.Named;


@Mapper(
    componentModel = "spring",
    uses = {
        // PlaceMapper.class,
        // GroupMapper.class,
        // InstrumentMapper.class,
        ChoiceMapper.class})
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

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "address", ignore = true)
    @Mapping(target = "groups", ignore = true)
    @Mapping(target = "instruments", ignore = true)
    @Mapping(target = "choices", ignore = true)
    Person toEntity(PersonRoleDto personDto);

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
