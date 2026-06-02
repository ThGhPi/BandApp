package com.thghpi.bandapp.band_api.service;
import com.thghpi.bandapp.band_api.dto.SurveyDto;

import java.util.List;
import org.springframework.lang.NonNull;

public interface SurveyService {
    List<SurveyDto> getRecent();
    List<SurveyDto> getPrevious(Long pageNumber);
    SurveyDto getById(@NonNull Long id);
    List<SurveyDto> getAll();

    SurveyDto save(SurveyDto surveyDto);
    List<SurveyDto> saveAll(@NonNull List<SurveyDto> surveyDtos);

    void deleteById(@NonNull Long id);
    void deleteAll(@NonNull List<SurveyDto> surveyDtos);
	void checkSurveyClosure(SurveyDto surveyDto);
	void checkSurveysClosure(List<SurveyDto> surveyDtos);
}
