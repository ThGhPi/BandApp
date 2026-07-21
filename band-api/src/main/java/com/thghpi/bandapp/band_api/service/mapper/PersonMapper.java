package com.thghpi.bandapp.band_api.service.mapper;
// import com.thghpi.bandapp.band_api.entity.Choice;
import com.thghpi.bandapp.band_api.entity.Person;
import com.thghpi.bandapp.band_api.dto.PersonDto;
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

    // --- Entity --> DTO ---
    @Mapping(target = "address", ignore = true)
    @Mapping(target = "instruments", ignore = true)
    PersonDto toDto(Person person);

    @Mapping(target = "address", expression = "java(null)")
    @Mapping(target = "instruments", ignore = true)
    @Mapping(target = "groups", ignore = true)
    ProfileResponse toProfile(Person person);

    // /**
    //  * Maps a setof choices to a set of choice ids.
    //  * This is used to get the votes of a person when mapping a Person entity to a PersonDto.
    //  * @param surveyId the id of the survey to map to a Survey entity
    //  * @return a Survey entity with the id field set to the provided surveyId, or null if the surveyId is null
    //  */
    // @Named("mapChoicesToChoiceIds")
    // default Set<Long> mapChoicesToChoiceIds(Set<Choice> choices) {
    //     if (choices.isEmpty()) {
    //         return Set.of();
    //     }
        
    //     return choices.stream()
    //         .filter(Objects::nonNull)
    //         .map(Choice::getId)
    //         .filter(Objects::nonNull)
    //         .collect(Collectors.toSet());
    // }
}
