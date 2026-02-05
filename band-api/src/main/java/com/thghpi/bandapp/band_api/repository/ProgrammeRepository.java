package com.thghpi.bandapp.band_api.repository;
import com.thghpi.bandapp.band_api.entity.Programme;
import com.thghpi.bandapp.band_api.entity.product_key.ProgrammePK;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgrammeRepository extends JpaRepository<Programme,ProgrammePK> {

}
