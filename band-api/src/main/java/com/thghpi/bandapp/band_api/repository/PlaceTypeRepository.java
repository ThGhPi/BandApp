package com.thghpi.bandapp.band_api.repository;
import com.thghpi.bandapp.band_api.entity.PlaceType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceTypeRepository extends JpaRepository<PlaceType,Long> {

}
