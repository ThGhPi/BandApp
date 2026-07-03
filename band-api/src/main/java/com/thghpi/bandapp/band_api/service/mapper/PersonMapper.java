package com.thghpi.bandapp.band_api.service.mapper;

import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.thghpi.bandapp.band_api.dto.PersonDto;
import com.thghpi.bandapp.band_api.entity.Choice;
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
    @Mapping(target = "address", ignore = true)
    @Mapping(target = "groups", ignore = true)
    @Mapping(target = "instruments", ignore = true)
    @Mapping(target = "choices", ignore = true)
    Person toEntity(PersonDto personDto);


    @Mapping(target = "trialPassword", ignore = true)
    @Mapping(target = "address", ignore = true)
    @Mapping(target = "groups", ignore = true)
    @Mapping(target = "instruments", ignore = true)
    @Mapping(target = "choiceIds", source = "choices", qualifiedByName = "mapChoicesToChoiceIds")
    PersonDto toDto(Person person);

    /**
     * Maps a setof choices to a set of choice ids.
     * This is used to get the votes of a person when mapping a Person entity to a PersonDto.
     * @param surveyId the id of the survey to map to a Survey entity
     * @return a Survey entity with the id field set to the provided surveyId, or null if the surveyId is null
     */
    @Named("mapChoicesToChoiceIds")
    default Set<Long> mapChoicesToChoiceIds(Set<Choice> choices) {
        if (choices.isEmpty()) {
            return Set.of();
        }
        
        return choices.stream()
            .filter(choiceDto -> choiceDto != null && choiceDto.getId() != null)
            .map(Choice::getId)
            .collect(Collectors.toSet());
    }
}
