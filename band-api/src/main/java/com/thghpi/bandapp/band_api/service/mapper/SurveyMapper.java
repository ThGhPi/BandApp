package com.thghpi.bandapp.band_api.service.mapper;
import com.thghpi.bandapp.band_api.dto.SurveyDto;
import com.thghpi.bandapp.band_api.entity.Person;
import com.thghpi.bandapp.band_api.entity.Survey;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.InjectionStrategy;

import java.time.Clock;

import org.mapstruct.AfterMapping;
import org.mapstruct.MappingTarget;

@Mapper(
    componentModel = "spring",
    uses = { ChoiceMapper.class },
    injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface SurveyMapper {
    
    @Mapping(target = "id", source = "survey.id")
    @Mapping(target = "choices", source = "survey.choices")
    @Mapping(target = "closed", expression = "java((survey != null ? survey.isClosed(clock) : null))")
    @Mapping(target = "totalVotes", expression = "java((survey != null ? survey.getTotalVotes() : null))")
    SurveyDto toDto(Survey survey, Person currentUser, Clock clock);
        
    Survey toEntity(SurveyDto survey);

    @AfterMapping
    default void linkChoices(@MappingTarget Survey survey) {
        survey.getChoices()
            .forEach(choice -> choice.setSurvey(survey));
    }
}