package com.thghpi.bandapp.band_api.service;
import com.thghpi.bandapp.band_api.entity.Survey;
import com.thghpi.bandapp.band_api.dto.SurveyDto;
import com.thghpi.bandapp.band_api.dto.request.VoteRequest;
import com.thghpi.bandapp.band_api.dto.response.SurveyPageResponse;

import java.time.LocalDate;
import java.util.List;
import org.springframework.lang.NonNull;

public interface SurveyService {
    List<SurveyDto> getRecent();
    SurveyPageResponse getPrevious(LocalDate date);
    SurveyDto getById(@NonNull Long id);
    List<SurveyDto> getAll();

    SurveyDto save(SurveyDto surveyDto);
    List<SurveyDto> saveAll(@NonNull List<SurveyDto> surveyDtos);

    void deleteById(@NonNull Long id);
    void deleteAll(@NonNull List<SurveyDto> surveyDtos);
	void checkSurveysClosure(List<Survey> surveys);
    void checkSurveysData(List<SurveyDto> surveys);
    SurveyDto addVote(VoteRequest voteRequest);
    SurveyDto removeVote(VoteRequest voteRequest);
}
