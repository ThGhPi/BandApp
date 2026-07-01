package com.thghpi.bandapp.band_api.service;
import com.thghpi.bandapp.band_api.service.exception.NotFoundMessage;
import com.thghpi.bandapp.band_api.service.exception.BadCUException;
import com.thghpi.bandapp.band_api.service.exception.BadCUMessage;
import com.thghpi.bandapp.band_api.service.exception.NotFoundException;
import com.thghpi.bandapp.band_api.entity.Survey;
import com.thghpi.bandapp.band_api.dto.SurveyDto;
import com.thghpi.bandapp.band_api.dto.SurveyPageDto;
import com.thghpi.bandapp.band_api.repository.SurveyRepository;
import com.thghpi.bandapp.band_api.service.mapper.SurveyMapper;

import java.util.List;
import java.util.Objects;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.lang.NonNull;
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
    private final SurveyMapper mapper;
    private final SurveyRepository repository;

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
     * Retrieves the surveys closed before the given date from the database with pagination.
     * @param LocalDate date the page number of the surveys to retrieve.
     * @return a SurveyPageDto with five most recent survey older than the given date.
     */
    @Override
    public SurveyPageDto getPrevious(LocalDate date) {
        Page<Survey> surveyPage = 
            repository.findByScheduledEndBeforeOrderByScheduledEndDesc(
                date, PageRequest.of(0,5)
            );
        return new SurveyPageDto(
            surveyPage.getContent().stream()
                .map(mapper::toDto)
                .toList(),
            surveyPage.hasNext()
        );
    }

    /**
     * Retrieves the survey with the given id from the database.
     * @param Longid the id of the survey to retrieve.
     * @return the SurveyDto of the retrieved survey.
     * @throws NotFoundException if the id is not found in database
     */
    @Override
    public SurveyDto getById(@NonNull Long id) {
        return mapper.toDto(
            repository.findById(id)
                .orElseThrow(() -> new NotFoundException(new NotFoundMessage(id, Survey.class)))
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
        checkSurveyData(surveyDto);
        Survey survey = mapper.toEntity(surveyDto);
        checkSurveyClosure(survey);
        return mapper.toDto(
            repository.save(
                Objects.requireNonNull(survey)
            )
        );
    }

    /**
     * Saves all the given surveys in the database.
     * @param List<SurveyDto> surveyDtos the list of SurveyDto to save.
     * @return the list of saved SurveyDto objects.
     */
    @Override
    public List<SurveyDto> saveAll(@NonNull List<SurveyDto> surveyDtos) {
        checkSurveysData(surveyDtos);
        List<Survey> surveys = Objects.requireNonNull(
            surveyDtos.stream()
                .map(mapper::toEntity)
                .toList()
            );
        checkSurveysClosure(surveys);
        return repository.saveAll(surveys)
            .stream()
            .map(mapper::toDto)
            .toList();
    }

    /**
     * Deletes the survey with the given id from the database.
     * @param Long id the id of the survey to delete.
     */
    @Override
    public void deleteById(@NonNull Long id) {
        repository.deleteById(id);
    }

    /**
     * Deletes all surveys in the given list from the database.
     * @param List<SurveyDto> surveyDtos the list of SurveyDto to delete.
     */
    @Override
    public void deleteAll(@NonNull List<SurveyDto> surveyDtos) {
        repository.deleteAll(Objects.requireNonNull(
            surveyDtos.stream()
                .map(mapper::toEntity)
                .toList()
        ));
    }

    /**
     * Checks if there are closed survey in the provided list and throws an exception there are.
     * @param SurveyDto surveyDto the SurveyDto to check.
     * @throws IllegalArgumentException if there are closed surveys, with the list of closed survey IDs.
     */
    @Override
    public void checkSurveysClosure(List<Survey> surveys) {
        List<Long> ids = surveys.stream()
            .filter(Survey::isClosed)
            .map(Survey::getId)
            .toList();
        if (!ids.isEmpty()) {
            if (ids.contains(null)) {
                throw new BadCUException(new BadCUMessage(
                    true, Survey.class, "that are already closed",
                    null, "The scheduled end date must be after today"
                ));
            }
            throw new BadCUException(new BadCUMessage(
                false, Survey.class, "that are already closed",
                ids, "are already closed"
            ));
        }
    }

    /**
     * Checks if the given survey is closed and throws an exception if it is.
     * @param SurveyDto surveyDto the SurveyDto to check.
     * @throws IllegalArgumentException if the survey is closed with it's ID.
     */
    @Override
    public void checkSurveyClosure(Survey survey) {
        if (survey.isClosed()) {
            if (survey.getId() == null) {
                throw new BadCUException(new BadCUMessage(
                    true, Survey.class, "that are already closed",
                    null, "The scheduled end date must be after today"
                ));
            }
            throw new BadCUException(new BadCUMessage(
                false, Survey.class, "that is already closed",
                List.of(survey.getId()), " is already closed"
            ));
        }
    }

    /**
     * Checks if the given survey has invalid data and throws an exception if it does.
     * @param SurveyDto survey the SurveyDto to check.
     * @throws BadCUException if the survey question isn't conform (blank or exceeding 255 in length) with it's ID.
     */
    @Override
    public void checkSurveyData(SurveyDto survey) {
        if (survey.getQuestion().isBlank() || survey.getQuestion().length() > 255) {
            if (survey.getId() == null) {
                throw new BadCUException(new BadCUMessage(
                    true, Survey.class, "with invalid data",
                    null, "The question must not be blank nor exceed 255 characters"
                ));
            }
            throw new BadCUException(new BadCUMessage(
                false, Survey.class, "with invalid data",
                List.of(survey.getId()), "must not be blank nor exceed 255 characters"
            ));
        }
    }

    /**
     * Checks if there are surveys with invalid data in the provided list.
     * @param List<SurveyDto> surveys the list of SurveyDto to check.
     * @throws BadCUException if there are surveys with invalid data, with the list of invalid survey IDs.
     */
    @Override
    public void checkSurveysData(List<SurveyDto> surveys) {
        List<Long> ids = surveys.stream()
            .filter(survey -> survey.getQuestion().isBlank() || survey.getQuestion().length() > 255)
            .map(SurveyDto::getId)
            .toList();
        if (!ids.isEmpty()) {
            if (ids.contains(null)) {
                throw new BadCUException(new BadCUMessage(
                    true, Survey.class, "with invalid data",
                    null, "The question must not be blank nor exceed 255 characters"
                ));
            }
            throw new BadCUException(new BadCUMessage(
                false, Survey.class, "with invalid data",
                ids, "must not have their question blank nor exceeding 255 characters"
            ));
        }
    }
}
