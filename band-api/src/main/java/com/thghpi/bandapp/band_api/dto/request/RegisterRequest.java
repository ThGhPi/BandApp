package com.thghpi.bandapp.band_api.dto.request;
import com.thghpi.bandapp.band_api.dto.PlaceDto;
import com.thghpi.bandapp.band_api.dto.InstrumentDto;

import java.util.List;
import java.time.LocalDate;

/**
 * RegisterRequest record used as a data transfer object
 * for registration requests 
 * @param lastname the stated lastname of the person
 * @param firstname the stated firstnmae of the person
 * @param username the name used for connection
 * @param email the registered email of the person
 * @param trialPassword the future password of the person
 * @param birthday the birth date of the person
 * @param phoneNumber the phone number of the person
 * @param address the address of the given person
 * @param instruments the list of instruments played by the person
 * @see Person
 */
public record RegisterRequest(
    String lastname,
    String firstname,
    String username,
    String email,
    String trialPassword,
    LocalDate birthday,
    String phoneNumber,
    PlaceDto address,
    List<InstrumentDto> instruments
) { }
