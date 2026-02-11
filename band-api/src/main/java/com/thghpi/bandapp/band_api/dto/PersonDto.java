package com.thghpi.bandapp.band_api.dto;
import com.thghpi.bandapp.band_api.entity.enumeration.Role;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PersonDto {
    private Long id;
    private String lastname;
    private String firstname;
    private String username;
    private String email;
    private String trialPassword;
    private Role role;
    private String phoneNumber;
    private PlaceDto address;
    private List<GroupDto> groups = new ArrayList<GroupDto>();
    private List<InstrumentDto> instruments = new ArrayList<InstrumentDto>();
    private List<ChoiceDto> choices = new ArrayList<ChoiceDto>();
}
