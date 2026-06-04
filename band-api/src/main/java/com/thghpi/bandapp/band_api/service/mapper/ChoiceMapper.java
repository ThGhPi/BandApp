package com.thghpi.bandapp.band_api.service.mapper;
import com.thghpi.bandapp.band_api.dto.ChoiceDto;
import com.thghpi.bandapp.band_api.entity.Choice;
import com.thghpi.bandapp.band_api.entity.Survey;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(
    componentModel = "spring"
)
public interface ChoiceMapper {
    
    @Mapping(target = "votes", expression = "java(choice.getVotes())")
    @Mapping(target = "surveyId", expression = "java(choice.getSurvey() != null ? choice.getSurvey().getId() : null)")
    ChoiceDto toDto(Choice choice);

    @Mapping(target = "persons", ignore = true)
    @Mapping(target = "survey", source = "surveyId", qualifiedByName = "mapSurveyIdToSurvey")
    Choice toEntity(ChoiceDto choice);

    /**
     * Maps a surveyId to a Survey entity with only the id field set.
     * This is used to correctly set the survey association when mapping a ChoiceDto to a Choice entity.
     * @param surveyId the id of the survey to map to a Survey entity
     * @return a Survey entity with the id field set to the provided surveyId, or null if the surveyId is null
     */
    @Named("mapSurveyIdToSurvey")
    default Survey mapSurveyIdToSurvey(Long surveyId) {
        if (surveyId == null) {
            return null;
        }
        Survey survey = new Survey();
        survey.setId(surveyId);
        return survey;
    }
}
