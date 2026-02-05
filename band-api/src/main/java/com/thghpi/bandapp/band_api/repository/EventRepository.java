package com.thghpi.bandapp.band_api.repository;
import com.thghpi.bandapp.band_api.entity.Event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

}
