package com.thghpi.bandapp.band_api.service;
import com.thghpi.bandapp.band_api.dto.SurveyDto;

import java.util.List;

public interface SurveyService {
    List<SurveyDto> getRecent();
    List<SurveyDto> getOld(Long pageNumber);
}
