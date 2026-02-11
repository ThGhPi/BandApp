package com.thghpi.bandapp.band_api.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CityDto {
    private Long id;
    private String name;
    private String postcode;
    private List<PlaceDto> places = new ArrayList<PlaceDto>();
}
