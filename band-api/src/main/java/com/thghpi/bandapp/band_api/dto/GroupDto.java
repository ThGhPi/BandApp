package com.thghpi.bandapp.band_api.dto;
import com.thghpi.bandapp.band_api.entity.enumeration.GroupType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * GroupDto a record used as data transfer data object
 * for the {@link Group} entity
 * @param id the id of the group in database
 * @param name the name given to the group
 * @param creationDate the date of creation of the group in the app
 * @param groupType either a section or a work-group
 * @param goal what it wish to accomplish
 * @param details a description of what the people in the group do together
 * @param scheduledEnd the date the group is supposed to have finished it's mission
 * @param personIds the list of the ids of the persons participating in the group
 * @param eventId the list of ids of events specific to this group only
 * @param scoreIds the list of ids of scores accessible only to this group
 */
public record GroupDto (
    Long id,
    String name,
    LocalDate creationDate,
    GroupType groupType,
    String goal,
    String details,
    LocalDate scheduledEnd,
    List<Long> personIds,
    List<Long> eventId,
    List<Long> scoreIds
) {
    /**
     * Constructor for generating automatically
     * empty lists of id for relations
     * @see GroupDto
     */
    public GroupDto(
        Long id,
        String name,
        LocalDate creationDate,
        GroupType groupType,
        String goal,
        String details,
        LocalDate scheduledEnd
    ) {
        this(
            id, name, creationDate, groupType,
            goal, details, scheduledEnd,
            new ArrayList<Long>(),
            new ArrayList<Long>(),
            new ArrayList<Long>()
        );
    }

    /**
     * Constructor to be used before creation,
     * when the Group has yet to be saved in database
     * Set id to null
     * @see GroupDto
     */
    public GroupDto(
        String name,
        LocalDate creationDate,
        GroupType groupType,
        String goal,
        String details,
        LocalDate scheduledEnd
    ) {
        this(
            null, name, creationDate, groupType,
            goal, details, scheduledEnd
        );
    }
}
