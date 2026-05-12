package com.thghpi.bandapp.band_api.service.mapper;

import org.mapstruct.Mapper;

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

    Person toEntity(PersonDto personDto);

    PersonDto toDto(Person person);

}
