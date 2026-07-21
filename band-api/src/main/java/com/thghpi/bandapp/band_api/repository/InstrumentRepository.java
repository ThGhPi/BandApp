package com.thghpi.bandapp.band_api.repository;
import com.thghpi.bandapp.band_api.entity.Instrument;
import com.thghpi.bandapp.band_api.entity.Person;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InstrumentRepository extends JpaRepository<Instrument, Long> {

    List<Instrument> findAllByPersons(Person authenticatedPerson);

}
