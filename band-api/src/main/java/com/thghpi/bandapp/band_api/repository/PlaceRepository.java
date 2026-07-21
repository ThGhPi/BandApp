package com.thghpi.bandapp.band_api.repository;
import com.thghpi.bandapp.band_api.entity.Person;
import com.thghpi.bandapp.band_api.entity.Place;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Place,Long> {

    Optional<Place> findByPerson(Person authenticatedPerson);

}
