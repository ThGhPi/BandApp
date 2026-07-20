package com.thghpi.bandapp.band_api.dto.response;
import com.thghpi.bandapp.band_api.dto.PlaceDto;
import com.thghpi.bandapp.band_api.dto.GroupDto;
import com.thghpi.bandapp.band_api.dto.InstrumentDto;
import com.thghpi.bandapp.band_api.entity.Person;
import com.thghpi.bandapp.band_api.entity.enumeration.Role;

import java.util.Set;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.time.LocalDate;

/**
 * ProfileResponse record used as a data transfer object
 * when the profile of a {@link Person} is requested
 * visibility : own and admin
 * @param id the id in database for the person
 * @param lastname the stated lastname of the person
 * @param firstname the stated firstnmae of the person
 * @param username the used name for connection
 * @param email the registered email of the person
 * @param role the role defined for the app use
 * @param birthday the birth date of the person
 * @param phoneNumber the phone number of the person
 * @param address the address of the person
 * @param groups list of groups the person belong to
 * @param instruments list of instruments played by the person
 * @param choiceIds the set of choice ids voted by the person
 */
public record ProfileResponse(
    Long id,
    String lastname,
    String firstname,
    String username,
    String email,
    Role role,
    LocalDate birthday,
    String phoneNumber,
    PlaceDto address,
    List<GroupDto> groups,
    List<InstrumentDto> instruments,
    Set<Long> choiceIds
) {
    public ProfileResponse(
        Long id,
        String lastname,
        String firstname,
        String username,
        String email,
        Role role,
        LocalDate birthday,
        String phoneNumber,
        PlaceDto address
    ) {
        this(
            id, lastname, firstname, username,
            email, role, birthday, phoneNumber,
            address, new ArrayList<GroupDto>(),
            new ArrayList<InstrumentDto>(),
            new LinkedHashSet<Long>()
        );
    }
}
