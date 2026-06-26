package com.thghpi.bandapp.band_api.service;
import com.thghpi.bandapp.band_api.dto.ChoiceDto;

import java.util.Set;

import org.springframework.lang.NonNull;

public interface ChoiceService {
    ChoiceDto getById(@NonNull Long id);
    Set<ChoiceDto> getBySurveyId(Long surveyId);

    ChoiceDto save(ChoiceDto choiceDto);
    Set<ChoiceDto> saveAll(@NonNull Set<ChoiceDto> choiceDtos);

    void deleteById(@NonNull Long id);
    void deleteAll(@NonNull Set<ChoiceDto> choiceDtos);
    ChoiceDto checkLinkComplement(ChoiceDto choiceDto);
}
