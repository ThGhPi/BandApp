package com.thghpi.bandapp.band_api.dto;

/**
 * ChoiceDto a record used as data transfer object
 * for the {@link Choice} entity
 * @param id the id of the choice in the database
 * @param title the short proposition for this choice
 * @param complement an eventual complement or text for a link
 * @param url the address of the link attached to the choice
 * @param surveyId the id of the survey it is attached to
 * @param votes the number of person having chosen this choice
 */
public record ChoiceDto (
    Long id,
    String title,
    String complement,
    String url,
    Long surveyId,
    Long votes
) { }
