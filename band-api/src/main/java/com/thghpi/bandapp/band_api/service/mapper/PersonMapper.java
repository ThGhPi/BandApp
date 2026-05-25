package com.thghpi.bandapp.band_api.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.thghpi.bandapp.band_api.dto.PersonDto;
import com.thghpi.bandapp.band_api.entity.Person;

@Mapper(
    componentModel = "spring",
    uses = {
        // PlaceMapper.class,
        // GroupMapper.class,
        // InstrumentMapper.class,
        ChoiceMapper.class})
public interface PersonMapper {

    @Mapping(target = "password", ignore = true)
    Person toEntity(PersonDto personDto);

    @Mapping(target = "trialPassword", ignore = true)
    PersonDto toDto(Person person);
}
