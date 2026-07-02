package com.thghpi.bandapp.band_api.dto;
import com.thghpi.bandapp.band_api.entity.enumeration.Role;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.Data;
import lombok.AllArgsConstructor;
import org.springframework.lang.Nullable;

@Data
@AllArgsConstructor
public class PersonDto {
    @Nullable
    private Long id;
    @Nullable
    private String lastname;
    @Nullable
    private String firstname;
    private String username;
    @Nullable
    private String email;
    @Nullable
    private String trialPassword;
    @Nullable
    private Role role;
    private LocalDate birthday;
    @Nullable
    private String phoneNumber;
    @Nullable
    private PlaceDto address;
    private List<GroupDto> groups = new ArrayList<GroupDto>();
    private List<InstrumentDto> instruments = new ArrayList<InstrumentDto>();
    private Set<Long> choiceIds = new HashSet<Long>();
}
