package com.thghpi.bandapp.band_api.service;
import com.thghpi.bandapp.band_api.dto.ChoiceDto;
import com.thghpi.bandapp.band_api.service.mapper.ChoiceMapper;
import com.thghpi.bandapp.band_api.repository.ChoiceRepository;

import java.util.List;
import lombok.RequiredArgsConstructor;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ChoiceServiceImpl implements ChoiceSercice {
    
    private final ChoiceMapper mapper;
    private final ChoiceRepository repository;

    /**
     * Retrieves a choice by its ID. When unsuccessful, it throws an exception.
     * @param Long id the ID of the choice to retrieve
     * @return the ChoiceDto corresponding to the specified ID
     * @throws IllegalArgumentException if no choice with the specified ID is found
     */
    @Override
    public ChoiceDto getById(@NonNull Long id) {
        return mapper.toDto(
            repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Choice with ID " + id + " not found"))
            );
    }

    /**
     * Retrieves all choices associated with a specific survey ID.
     * @param Long surveyId the ID of the survey for which to retrieve choices
     * @return a list of ChoiceDto objects corresponding to the specified survey ID
     */
    @Override
    public List<ChoiceDto> getBySurveyId(Long surveyId) {
        return repository.findAllBySurveyId(surveyId)
            .stream()
            .map(mapper::toDto)
            .toList();
    }

    /**
     * Saves a new choice to the database. It takes a ChoiceDto as input,
     * converts it to a Choice entity, saves it to the repository, and then
     * converts the saved entity back to a ChoiceDto before returning it.
     * @param ChoiceDto choiceDto the data transfer object containing the choice information to save
     * @return the saved ChoiceDto
     */
    @Override
    public ChoiceDto save(ChoiceDto choiceDto) {
        return mapper.toDto(repository.save(mapper.toEntity(choiceDto)));
    }

    /**
     * Saves multiple choices to the database.
     * @param List<ChoiceDto> choiceDtos the list of data transfer objects containing the choice information to save
     * @return the list of saved ChoiceDto objects
     */
    @Override
    public List<ChoiceDto> saveAll(@NonNull List<ChoiceDto> choiceDtos) {
        return repository.saveAll(
            choiceDtos.stream()
                .map(mapper::toEntity)
                .toList())
            .stream()
            .map(mapper::toDto)
            .toList();
    }

    /**
     * Deletes a choice by its ID. If the choice with the specified ID does not exist, it throws an exception.
     * @param Long id the ID of the choice to delete
     */
    @Override
    public void deleteById(@NonNull Long id) {
        repository.deleteById(id);
    }

    /**
     * Deletes multiple choices from the database.
     * @param List<ChoiceDto> choiceDtos the list of data transfer objects containing the choice information to delete
     */
    @Override
    public void deleteAll(List<ChoiceDto> choiceDtos) {
        repository.deleteAll(
            choiceDtos.stream()
                .map(mapper::toEntity)
                .toList()
        );
    }

}
