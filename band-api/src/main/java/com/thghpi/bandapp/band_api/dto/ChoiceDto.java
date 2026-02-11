package com.thghpi.bandapp.band_api.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChoiceDto {
    private Long id;
    private String title;
    private String complement;
    private String url;
    private SurveyDto survey;
    private List<PersonDto> persons = new ArrayList<PersonDto>();
}
