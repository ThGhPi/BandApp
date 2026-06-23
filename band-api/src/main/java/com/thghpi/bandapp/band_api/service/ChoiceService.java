package com.thghpi.bandapp.band_api.service;
import com.thghpi.bandapp.band_api.dto.ChoiceDto;

import java.util.List;
import org.springframework.lang.NonNull;

public interface ChoiceService {
    ChoiceDto getById(@NonNull Long id);
    List<ChoiceDto> getBySurveyId(Long surveyId);

    ChoiceDto save(ChoiceDto choiceDto);
    List<ChoiceDto> saveAll(@NonNull List<ChoiceDto> choiceDtos);

    void deleteById(@NonNull Long id);
    void deleteAll(@NonNull List<ChoiceDto> choiceDtos);
    ChoiceDto checkLinkComplement(ChoiceDto choiceDto);
}
