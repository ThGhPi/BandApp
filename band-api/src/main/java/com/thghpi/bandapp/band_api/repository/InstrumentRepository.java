package com.thghpi.bandapp.band_api.repository;
import com.thghpi.bandapp.band_api.entity.Instrument;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstrumentRepository extends JpaRepository<Instrument, Long> {

}
