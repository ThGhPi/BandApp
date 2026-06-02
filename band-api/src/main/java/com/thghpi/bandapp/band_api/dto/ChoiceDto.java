package com.thghpi.bandapp.band_api.dto;

import org.springframework.lang.Nullable;

import lombok.Data;
import lombok.AllArgsConstructor;

/**
 * DTO représentant un choix dans un sondage.
 * Contient les mêmes champs que l'entité Choice,
 * à l'exception de :
 * - votes : calculé à partir des personnes ayant voté pour ce choix
 * - surveyId : l'identifiant du sondage auquel ce choix est associé, pour éviter les références circulaires avec le SurveyDto
 */
@Data
@AllArgsConstructor
public class ChoiceDto {
    @Nullable
    private Long id;
    private String title;
    
    @Nullable
    private Long surveyId;
    @Nullable
    private String complement;
    @Nullable
    private String url;
    @Nullable
    private Long votes;
}
