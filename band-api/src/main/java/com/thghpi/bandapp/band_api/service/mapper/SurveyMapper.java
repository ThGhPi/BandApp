package com.thghpi.bandapp.band_api.service.mapper;
import com.thghpi.bandapp.band_api.dto.SurveyDto;
import com.thghpi.bandapp.band_api.entity.Survey;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    componentModel = "spring",
    uses = { ChoiceMapper.class }
)
public interface SurveyMapper {
    
    @Mapping(target = "totalVotes", expression = "java(survey.getTotalVotes())")
    @Mapping(target = "closed", expression = "java(survey.isClosed())")
    SurveyDto toDto(Survey survey);
    
    Survey toEntity(SurveyDto survey);
}
