package com.thghpi.bandapp.band_api.dto;
import com.thghpi.bandapp.band_api.entity.enumeration.Key;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InstrumentDto {
    private Long id;
    private String name;
    private Key key;
    private List<PersonDto> persons = new ArrayList<PersonDto>();
}
