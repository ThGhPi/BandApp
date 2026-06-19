package com.thghpi.bandapp.band_api.dto;

import java.util.List;

public record SurveyPageDto(
    List<SurveyDto> surveys,
    Boolean hasNext
) { }
