package com.thghpi.bandapp.band_api.service;
import com.thghpi.bandapp.band_api.entity.Choice;
import com.thghpi.bandapp.band_api.dto.ChoiceDto;
import com.thghpi.bandapp.band_api.service.mapper.ChoiceMapper;
import com.thghpi.bandapp.band_api.repository.ChoiceRepository;
import com.thghpi.bandapp.band_api.service.exception.NotFoundMessage;
import com.thghpi.bandapp.band_api.service.exception.NotFoundException;

import java.util.Set;
import java.util.Objects;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@Transactional
@RequiredArgsConstructor
public class ChoiceServiceImpl implements ChoiceService {
    
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
                .orElseThrow(() -> new NotFoundException(new NotFoundMessage(id, Choice.class)))
            );
    }

    /**
     * Retrieves all choices associated with a specific survey ID.
     * @param Long surveyId the ID of the survey for which to retrieve choices
     * @return a set of ChoiceDto objects corresponding to the specified survey ID
     */
    @Override
    public Set<ChoiceDto> getBySurveyId(Long surveyId) {
        return repository.findAllBySurveyId(surveyId)
            .stream()
            .map(mapper::toDto)
            .collect(Collectors.toSet());
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
        return mapper.toDto(repository.save(Objects.requireNonNull(
            mapper.toEntity(this.checkLinkComplement(choiceDto))
        )));
    }

    /**
     * Saves multiple choices to the database.
     * @param Set<ChoiceDto> choiceDtos the list of data transfer objects containing the choice information to save
     * @return the set of saved ChoiceDto objects
     */
    @Override
    public Set<ChoiceDto> saveAll(@NonNull Set<ChoiceDto> choiceDtos) {
        return repository.saveAll(
            Objects.requireNonNull(
            choiceDtos.stream()
                .map(this::checkLinkComplement)
                .map(mapper::toEntity)
                .toList()
            )).stream()
            .map(mapper::toDto)
            .collect(Collectors.toSet());
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
     * @param Set<ChoiceDto> choiceDtos the set of data transfer objects containing the choice information to delete
     */
    @Override
    public void deleteAll(@NonNull Set<ChoiceDto> choiceDtos) {
        repository.deleteAll(
            Objects.requireNonNull(
            choiceDtos.stream()
                .map(mapper::toEntity)
                .toList()
        ));
    }

    /**
     * Checks if a choice has a URL and no complement, and sets the complement accordingly.
     * @param ChoiceDto choiceDto the data transfer object containing the choice information to check
     * @return the updated ChoiceDto
     */
    @Override
    public ChoiceDto checkLinkComplement(ChoiceDto choiceDto) {
        if (
            !(choiceDto.getUrl() == null || choiceDto.getUrl().isBlank()) &&
             (choiceDto.getComplement() == null || choiceDto.getComplement().isBlank())
            ) {
            choiceDto.setComplement("Cliquez ici pour suivre le lien");
        }
        return choiceDto;
    }
}
