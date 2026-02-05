package com.thghpi.bandapp.band_api.repository;
import com.thghpi.bandapp.band_api.entity.City;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

}
