package com.thghpi.bandapp.band_api.service;
import com.thghpi.bandapp.band_api.dto.SurveyDto;

import java.util.List;

public interface SurveyService {
    List<SurveyDto> getRecent();
    List<SurveyDto> getPrevious(Long pageNumber);
    SurveyDto getById(Long id);
    List<SurveyDto> getAll();

    SurveyDto save(SurveyDto surveyDto);
    List<SurveyDto> saveAll(List<SurveyDto> surveyDtos);

    void deleteById(Long id);
    void deleteAll(List<SurveyDto> surveyDtos);
}
