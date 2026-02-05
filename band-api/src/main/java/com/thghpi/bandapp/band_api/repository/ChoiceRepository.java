package com.thghpi.bandapp.band_api.repository;
import com.thghpi.bandapp.band_api.entity.Choice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChoiceRepository extends JpaRepository<Choice, Long> {

}
