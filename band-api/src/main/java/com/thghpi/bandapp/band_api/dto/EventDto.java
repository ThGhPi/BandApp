package com.thghpi.bandapp.band_api.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EventDto {
    private Long id;
    private String title;
    private LocalDate date;
    private LocalTime start;
    private LocalTime end;
    private LocalTime rdv;
    private String details;
    private List<ProgrammeDto> programme = new ArrayList<ProgrammeDto>();
    private EventTypeDto type;
    private PlaceDto place;
    private GroupDto group;
    private OrganisationDto organiser;
}
