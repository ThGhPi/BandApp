package com.thghpi.bandapp.band_api.repository;
import com.thghpi.bandapp.band_api.entity.Event;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {

}
