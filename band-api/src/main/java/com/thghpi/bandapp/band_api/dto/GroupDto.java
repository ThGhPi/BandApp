package com.thghpi.bandapp.band_api.dto;
import com.thghpi.bandapp.band_api.entity.enumeration.GroupType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GroupDto {
    private Long id;
    private String name;
    private LocalDate creationDate;
    private GroupType groupType;
    private String goal;
    private String details;
    private LocalDate scheduledEnd;
    private List<ScoreDto> scores = new ArrayList<ScoreDto>();
    private List<EventDto> events = new ArrayList<EventDto>();
    private List<PersonDto> persons = new ArrayList<PersonDto>();
    private List<ChoiceDto> choices = new ArrayList<ChoiceDto>();
}
