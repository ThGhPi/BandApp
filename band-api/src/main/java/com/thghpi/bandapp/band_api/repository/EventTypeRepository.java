package com.thghpi.bandapp.band_api.repository;
import com.thghpi.bandapp.band_api.entity.EventType;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EventTypeRepository extends JpaRepository<EventType, Long> {

}
