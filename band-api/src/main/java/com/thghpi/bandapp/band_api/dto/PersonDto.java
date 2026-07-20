package com.thghpi.bandapp.band_api.dto;

import java.util.ArrayList;
import java.util.List;

import com.thghpi.bandapp.band_api.entity.Person;

import java.time.LocalDate;

/**
 * PersonDto record used as a data transfer object
 * when the summary of the informations about a {@link Person} is requested
 * and when receiving a request for profile update
 * visibility : all members
 * @param id the id in database for the person
 * @param lastname the stated lastname of the person
 * @param firstname the stated firstnmae of the person
 * @param username the name used for connection
 * @param email the registered email of the person
 * @param birthday the birth date of the person
 * @param phoneNumber the phone number of the person
 * @param address the person registered address
 * @param instruments the music instruments played by the person
 */
public record PersonDto (
    Long id,
    String lastname,
    String firstname,
    String username,
    String email,
    LocalDate birthday,
    String phoneNumber,
    PlaceDto address,
    List<InstrumentDto> instruments
) {
    public PersonDto(
        Long id,
        String lastname,
        String firstname,
        String username,
        String email,
        LocalDate birthday,
        String phoneNumber,
        PlaceDto address
    ) {
        this(
            id, lastname, firstname,
            username, email, birthday,
            phoneNumber, address, new ArrayList<InstrumentDto>()
        );
    }
}
