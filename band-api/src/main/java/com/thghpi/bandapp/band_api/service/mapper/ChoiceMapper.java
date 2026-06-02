package com.thghpi.bandapp.band_api.service.mapper;
import com.thghpi.bandapp.band_api.dto.ChoiceDto;
import com.thghpi.bandapp.band_api.entity.Choice;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    componentModel = "spring"
)
public interface ChoiceMapper {
    
    @Mapping(target = "votes", expression = "java(choice.getVotes())")
    @Mapping(target = "surveyId", expression = "java(choice.getSurvey().getId())")
    ChoiceDto toDto(Choice choice);

    @Mapping(target = "persons", ignore = true)
    @Mapping(target = "survey", ignore = true)
    Choice toEntity(ChoiceDto choice);
}
