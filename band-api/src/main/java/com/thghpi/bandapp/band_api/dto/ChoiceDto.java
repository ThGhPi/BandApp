package com.thghpi.bandapp.band_api.dto;

import com.thghpi.bandapp.band_api.entity.Choice;

/**
 * ChoiceDto a record used as data transfer object
 * for the {@link Choice} entity
 * 
 * @param id         the id of the choice in the database
 * @param title      the short proposition for this choice
 * @param complement an eventual complement or text for a link
 * @param url        the address of the link attached to the choice
 * @param surveyId   the id of the survey it is attached to
 * @param votes      the number of person having chosen this choice
 * @param chosen     a boolean indicating if the current user has chosen this
 *                   choice
 */
public record ChoiceDto(
        Long id,
        String title,
        String complement,
        String url,
        Long surveyId,
        Long votes,
        Boolean chosen
) {
    /**
     * Constructor for ChoiceDto without the 'chosen' field.
     * This constructor is useful when the 'chosen' field is not relevant or not
     * available.
     * 
     * @param id         the id of the choice in the database
     * @param title      the short proposition for this choice
     * @param complement an eventual complement or text for a link
     * @param url        the address of the link attached to the choice
     * @param surveyId   the id of the survey it is attached to
     * @param votes      the number of person having chosen this choice
     */
    public ChoiceDto(Long id, String title, String complement, String url, Long surveyId, Long votes) {
        this(id, title, complement, url, surveyId, votes, null);
    }
}
