package com.thghpi.bandapp.band_api.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.lang.Nullable;

import lombok.Data;
import lombok.AllArgsConstructor;

/**
 * DTO représentant un sondage.
 * Contient les mêmes champs que l'entité Survey,
 * à l'exception de :
 * - closed : calculé à partir de la date de fin du sondage
 * - totalVotes : calculé à partir des choix du sondage
 */
@Data
@AllArgsConstructor
public class SurveyDto {
    @Nullable
    private Long id;
    private String question;
    private LocalDate scheduledEnd;
    private Boolean multiplicity;

    @Nullable
    private Boolean closed;

    @Nullable
    private Long totalVotes;

    private List<ChoiceDto> choices = new ArrayList<ChoiceDto>();
}
