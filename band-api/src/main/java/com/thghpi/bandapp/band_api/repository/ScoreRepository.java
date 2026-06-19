package com.thghpi.bandapp.band_api.repository;
import com.thghpi.bandapp.band_api.entity.Score;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ScoreRepository extends JpaRepository<Score,Long> {

}
