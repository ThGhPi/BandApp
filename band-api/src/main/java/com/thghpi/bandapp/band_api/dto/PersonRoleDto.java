package com.thghpi.bandapp.band_api.dto;
import com.thghpi.bandapp.band_api.entity.enumeration.Role;

import java.time.LocalDate;

/**
 * PersonRole record used as a data transfer object
 * when the role of a {@link Person} needs management by an ADMIN
 * visibility : own and admin
 * @param id the id in database for the person
 * @param lastname the stated lastname of the person
 * @param firstname the stated firstnmae of the person
 * @param username the used name for connection
 * @param email the registered email of the person
 * @param role the role defined for the app use
 * @param birthday the birth date of the person
 * @param phoneNumber the phone number of the person
 */
public record PersonRoleDto(
    Long id,
    String lastname,
    String firstname,
    String username,
    String email,
    Role role,
    LocalDate birthday,
    String phoneNumber
) { }
