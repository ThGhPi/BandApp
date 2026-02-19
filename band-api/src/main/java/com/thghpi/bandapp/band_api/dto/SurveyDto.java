package com.thghpi.bandapp.band_api.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SurveyDto {
    private Long id;
    private String question;
    private LocalDate scheduledEnd;
    private Boolean multiplicity;
    private Boolean isClosed;
    private Long totalVotes;
    private List<ChoiceDto> choices = new ArrayList<ChoiceDto>();
}
