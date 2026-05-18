package com.thghpi.bandapp.band_api.service;
import com.thghpi.bandapp.band_api.dto.SurveyDto;
import com.thghpi.bandapp.band_api.repository.SurveyRepository;
import com.thghpi.bandapp.band_api.service.mapper.SurveyMapper;

import java.util.List;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of the SurveyService interface.
 * This class provides methods to manage surveys,
 * including retrieving recent and previous surveys,
 * getting a survey by id, saving and deleting surveys.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class SurveyServiceImpl implements SurveyService {
    private final SurveyRepository repository;
    private final SurveyMapper mapper;

    /**
     * Retrieves the most recent surveys from the database (less than a month old).
     * @return a list of the most recent SurveyDto objects.
     */
    @Override
    public List<SurveyDto> getRecent() {
        LocalDate date = LocalDate.now().minusMonths(1);
        return repository.findRecent(date)
            .stream()
            .map(mapper::toDto)
            .toList();
    }

    /**
     * Retrieves the previous surveys from the database (more than a month old).
     * @param Long pageNumber the page number of the surveys to retrieve.
     * @return a list of the previous SurveyDto objects.
     */
    @Override
    public List<SurveyDto> getPrevious(Long pageNumber) {
        LocalDate date = LocalDate.now().minusMonths(1);
        return repository.findOld(date, pageNumber*5)
            .stream()
            .map(mapper::toDto)
            .toList();
    }

    /**
     * Retrieves the survey with the given id from the database.
     * @param Longid the id of the survey to retrieve.
     * @return the SurveyDto of the retrieved survey.
     */
    @Override
    public SurveyDto getById(Long id) {
        return mapper.toDto(
            repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Survey with ID " + id + " not found"))
            );
    }

    /**
     * Retrieves all surveys from the database.
     * @return a list of all SurveyDto objects in the database.
     */
    @Override
    public List<SurveyDto> getAll() {
        return repository.findAll()
            .stream()
            .map(mapper::toDto)
            .toList();
    }

    /**
     * Saves the given survey in the database.
     * @param SurveyDto surveyDto the SurveyDto to save.
     * @return the saved SurveyDto object.
     */
    @Override
    public SurveyDto save(SurveyDto surveyDto) {
        return mapper.toDto(
            repository.save(mapper.toEntity(surveyDto))
        );
    }

    /**
     * Saves all the given surveys in the database.
     * @param List<SurveyDto> surveyDtos the list of SurveyDto to save.
     * @return the list of saved SurveyDto objects.
     */
    @Override
    public List<SurveyDto> saveAll(List<SurveyDto> surveyDtos) {
        return repository.saveAll(
            surveyDtos.stream()
                .map(mapper::toEntity)
                .toList())
            .stream()
            .map(mapper::toDto)
            .toList();
    }

    /**
     * Deletes the survey with the given id from the database.
     * @param Long id the id of the survey to delete.
     */
    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    /**
     * Deletes all surveys in the given list from the database.
     * @param List<SurveyDto> surveyDtos the list of SurveyDto to delete.
     */
    @Override
    public void deleteAll(List<SurveyDto> surveyDtos) {
        repository.deleteAll(
            surveyDtos.stream()
                .map(mapper::toEntity)
                .toList()
        );
    }
}
