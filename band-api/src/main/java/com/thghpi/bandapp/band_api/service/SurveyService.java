package com.thghpi.bandapp.band_api.service;
import com.thghpi.bandapp.band_api.dto.SurveyDto;
import com.thghpi.bandapp.band_api.dto.SurveyPageDto;
import com.thghpi.bandapp.band_api.entity.Survey;

import java.time.LocalDate;
import java.util.List;
import org.springframework.lang.NonNull;

public interface SurveyService {
    List<SurveyDto> getRecent();
    SurveyPageDto getPrevious(LocalDate date);
    SurveyDto getById(@NonNull Long id);
    List<SurveyDto> getAll();

    SurveyDto save(SurveyDto surveyDto);
    List<SurveyDto> saveAll(@NonNull List<SurveyDto> surveyDtos);

    void deleteById(@NonNull Long id);
    void deleteAll(@NonNull List<SurveyDto> surveyDtos);
	void checkSurveyClosure(Survey survey);
	void checkSurveysClosure(List<Survey> surveys);
    void checkSurveyData(SurveyDto survey);
    void checkSurveysData(List<SurveyDto> surveys);
}
