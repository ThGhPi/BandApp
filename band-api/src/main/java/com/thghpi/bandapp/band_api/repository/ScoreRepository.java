package com.thghpi.bandapp.band_api.repository;
import com.thghpi.bandapp.band_api.entity.Score;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScoreRepository extends JpaRepository<Score,Long> {

}
