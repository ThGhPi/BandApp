package com.thghpi.bandapp.band_api.repository;
import com.thghpi.bandapp.band_api.entity.Choice;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ChoiceRepository extends JpaRepository<Choice, Long> {

    List<Choice> findAllBySurveyId(Long surveyId);

    List<Choice> saveAll(List<Choice> choices);

    void deleteAll(List<Choice> choices);
}
