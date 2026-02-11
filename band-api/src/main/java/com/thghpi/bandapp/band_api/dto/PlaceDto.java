package com.thghpi.bandapp.band_api.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlaceDto {
    private Long id;
    private String name;
    private String address;
    private String addressDetails;
    private Double latitude;
    private Double longitude;
    private Long capacity;
    private List<PersonDto> persons = new ArrayList<PersonDto>();
    private List<EventDto> events = new ArrayList<EventDto>();
    private List<OrganisationDto> organisations = new ArrayList<OrganisationDto>();
    private FileInfoDto fileInfo;
    private CityDto city;
    private PlaceTypeDto type;
}
