package com.thghpi.bandapp.band_api.service.mapper;
import com.thghpi.bandapp.band_api.dto.SurveyDto;
import com.thghpi.bandapp.band_api.entity.Survey;

import org.mapstruct.Mapper;

@Mapper(
    componentModel = "spring",
    uses = { ChoiceMapper.class }
)
public interface SurveyMapper {
    
    SurveyDto toDto(Survey survey);
    
    Survey toEntity(SurveyDto survey);
}
