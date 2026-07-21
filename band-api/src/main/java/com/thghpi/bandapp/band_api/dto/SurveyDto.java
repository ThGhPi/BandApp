package com.thghpi.bandapp.band_api.dto;

import java.util.Set;
import java.time.LocalDate;
import java.util.LinkedHashSet;

/**
 * SurveyDto a record used as data transfer object
 * for the {@link Survey} entity
 * @param id the id of the survey in database
 * @param question the question to be answered
 * @param scheduledEnd the date after which the survey is closed
 * @param multiplicity whether only one or several choices can be voted for by a person
 * @param closed whether the survey is closed
 * @param totalVotes the count of person having already answered the survey (by voting for at least one choice)
 * @param choices the set of choices proposed in the survey
 */
public record SurveyDto (
    Long id,
    String question,
    LocalDate scheduledEnd,
    Boolean multiplicity,
    Boolean closed,
    Long totalVotes,
    Set<ChoiceDto> choices
) {
    /**
     * Constructor for generating automatically
     * empty lists of choices
     * @see SurveyDto
     */
    public SurveyDto(
        Long id,
        String question,
        LocalDate scheduledEnd,
        Boolean multiplicity,
        Boolean closed,
        Long totalVotes
    ) {
        this(
            id, question, scheduledEnd,
            multiplicity, closed, totalVotes,
            new LinkedHashSet<ChoiceDto>()
        );
    }

    /**
     * Constructor for setting to null the calculated fields
     * when there is a choice set
     * @see SurveyDto
     */
    public SurveyDto(
        Long id,
        String question,
        LocalDate scheduledEnd,
        Boolean multiplicity,
        Set<ChoiceDto> choices
    ) {
        this(
            id, question, scheduledEnd,
            multiplicity, null, null,
            choices
        );
    }

    /**
     * Constructor for setting to null the calculated fields
     * @see SurveyDto
     */
    public SurveyDto(
        Long id,
        String question,
        LocalDate scheduledEnd,
        Boolean multiplicity) {
        this(
            id, question, scheduledEnd,
            multiplicity, null, null
        );
    }

    /**
     * Constructor to be used before creation when there is a set of choices,
     * when the instrument has yet to be saved in database
     * Set id to null
     * @see SurveyDto
     */
    public SurveyDto(
        String question,
        LocalDate scheduledEnd,
        Boolean multiplicity,
        Set<ChoiceDto> choices
    ) {
        this(
            null, question, scheduledEnd,
            multiplicity, choices
        );
    }

    /**
     * Constructor to be used before creation,
     * when the instrument has yet to be saved in database
     * Set id to null
     * @see SurveyDto
     */
    public SurveyDto(
        String question,
        LocalDate scheduledEnd,
        Boolean multiplicity
    ) {
        this(
            null, question, scheduledEnd,
            multiplicity
        );
    }
    
}
