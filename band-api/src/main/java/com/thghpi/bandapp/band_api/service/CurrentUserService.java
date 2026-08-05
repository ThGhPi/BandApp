package com.thghpi.bandapp.band_api.service;

import com.thghpi.bandapp.band_api.entity.Person;
import com.thghpi.bandapp.band_api.repository.PersonRepository;

import lombok.RequiredArgsConstructor;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Service to retrieve the currently authenticated user.
 * CurrentUserService provides a method to get the authenticated Person from the database
 * based on the username obtained from the Spring Security context.
 */
@Service
@RequiredArgsConstructor
public class CurrentUserService {
    /** The repository for accessing person data. */
    private final PersonRepository repository;

    /**
     * Retrieves the currently authenticated Person from the database.
     * @return the authenticated Person object.
     * @throws NoSuchElementException if the authenticated user is not found in the database.
     */
    public Person getAuthenticatedPerson() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Person authenticatedPerson = repository.findByUsername(authentication.getName())
                .orElseThrow(() -> new NoSuchElementException(
                        "Person with username " + authentication.getName() + " wasn't found in database."));
        return authenticatedPerson;
    }
}
