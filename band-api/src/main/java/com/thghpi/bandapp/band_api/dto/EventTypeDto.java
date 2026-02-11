package com.thghpi.bandapp.band_api.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EventTypeDto {
    private Long id;
    private String name;
    private List<EventDto> events = new ArrayList<EventDto>();
}
