package com.thghpi.bandapp.band_api.repository;
import com.thghpi.bandapp.band_api.entity.Group;
import com.thghpi.bandapp.band_api.entity.Person;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person,Long> {

    Optional<Person> findByUsername(String username); // for authentication
    
    /**
     * Alternate method to load a person
     * with it's relationship with instruments and place
     * @param id the id of the person to load
     * @return a person with it's address and list of played instruments loaded
     */
    @EntityGraph(attributePaths = {
        "address",
        "instruments"
    })
    Optional<Person> findPersonWithAddressAndInstrumentsById(Long id);

    /**
     * Alternate method to load a person
     * with it's relationship with instruments and place
     * @param id the id of the person to load
     * @return a person with it's address, list of played instruments, set of choices and list of groups it belong to loaded
     */
    @EntityGraph(attributePaths = {
        "address",
        "instruments",
        "groups",
        "choices"
    })
    Optional<Person> findPersonWithAllById(Long id);

    List<Person> findByGroups(Group group);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmailAndIdNot(String email, Long id);

    boolean existsByUsernameAndIdNot(String username, Long id);
}
