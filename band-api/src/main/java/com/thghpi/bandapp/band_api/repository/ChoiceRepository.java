package com.thghpi.bandapp.band_api.repository;
import com.thghpi.bandapp.band_api.entity.Choice;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ChoiceRepository extends JpaRepository<Choice, Long> {

    List<Choice> findAllBySurveyId(Long surveyId);
}
