package com.thghpi.bandapp.band_api.service;
import com.thghpi.bandapp.band_api.dto.ChoiceDto;

import java.util.List;

public interface ChoiceSercice {
    ChoiceDto getById(Long id);
    List<ChoiceDto> getBySurveyId(Long surveyId);

    ChoiceDto save(ChoiceDto choiceDto);
    List<ChoiceDto> saveAll(List<ChoiceDto> choiceDtos);

    void deleteById(Long id);
    void deleteAll(List<ChoiceDto> choiceDtos);
}
