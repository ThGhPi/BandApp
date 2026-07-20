package com.thghpi.bandapp.band_api.dto.response;
import com.thghpi.bandapp.band_api.dto.SurveyDto;

import java.util.List;

/**
 * SurveyPageResponse record used as a data transfer object
 * for listing older surveys {@link SurveyDto} by page when requested
 * @param surveys the list of survey dtos requested
 * @param hasNext true if there exists older surveys, otherwise false
 */
public record SurveyPageResponse(
    List<SurveyDto> surveys,
    Boolean hasNext
) { }
