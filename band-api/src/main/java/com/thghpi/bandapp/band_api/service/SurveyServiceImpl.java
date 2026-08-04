package com.thghpi.bandapp.band_api.service;
import com.thghpi.bandapp.band_api.entity.Person;
import com.thghpi.bandapp.band_api.entity.Survey;
import com.thghpi.bandapp.band_api.dto.SurveyDto;
import com.thghpi.bandapp.band_api.dto.request.VoteRequest;
import com.thghpi.bandapp.band_api.dto.response.SurveyPageResponse;
import com.thghpi.bandapp.band_api.repository.SurveyRepository;
import com.thghpi.bandapp.band_api.service.mapper.SurveyMapper;
import com.thghpi.bandapp.band_api.service.exception.BadCUMessage;
import com.thghpi.bandapp.band_api.service.exception.BadCUException;
import com.thghpi.bandapp.band_api.service.exception.NotFoundMessage;
import com.thghpi.bandapp.band_api.service.exception.NotFoundException;

import java.util.List;
import java.util.Objects;
import java.time.Clock;
import java.time.Instant;
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
    private final Clock clock;
    private final SurveyMapper mapper;
    private final SurveyRepository repository;
    private final CurrentUserService authService;

    /**
     * Retrieves the most recent surveys from the database (less than a month old).
     * @return a list of the most recent SurveyDto objects.
     */
    @Override
    public List<SurveyDto> getRecent() {
        LocalDate date = LocalDate.from(Instant.now(clock)).minusMonths(1);
        Person currentUser = authService.getAuthenticatedPerson();
        return repository.findRecent(date)
            .stream()
            .map(survey -> mapper.toDto(survey, currentUser, clock))
            .toList();
    }

    /**
     * Retrieves the surveys closed before the given date from the database with pagination.
     * @param LocalDate date the page number of the surveys to retrieve.
     * @return a SurveyPageDto with five most recent survey older than the given date.
     */
    @Override
    public SurveyPageResponse getPrevious(LocalDate date) {
        Person currentUser = authService.getAuthenticatedPerson();
        Page<Survey> surveyPage = 
            repository.findByScheduledEndBeforeOrderByScheduledEndDesc(
                date, PageRequest.of(0,5)
            );
        return new SurveyPageResponse(
            surveyPage.getContent().stream()
                .map(survey -> mapper.toDto(survey, currentUser, clock))
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
            , authService.getAuthenticatedPerson(), clock);
    }

    /**
     * Retrieves all surveys from the database.
     * @return a list of all SurveyDto objects in the database.
     */
    @Override
    public List<SurveyDto> getAll() {
        return repository.findAll()
            .stream()
            .map(survey -> mapper.toDto(survey, null, clock))
            .toList();
    }

    /**
     * Saves the given survey in the database.
     * @param SurveyDto surveyDto the SurveyDto to save.
     * @return the saved SurveyDto object.
     */
    @Override
    public SurveyDto save(SurveyDto surveyDto) {
        checkSurveysData(List.of(surveyDto));
        Survey survey = mapper.toEntity(surveyDto);
        checkSurveysClosure(List.of(survey));
        survey.getChoices().forEach(choice -> Objects.requireNonNull(choice).checkLinkComplement());
        return mapper.toDto(
            repository.save(
                Objects.requireNonNull(survey)
            ), null, clock
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
        surveys.forEach(
            survey -> survey.getChoices()
                .forEach(choice -> Objects.requireNonNull(choice).checkLinkComplement())
            );
        return repository.saveAll(surveys)
            .stream()
            .map(survey -> mapper.toDto(survey, null, clock))
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
     * Adds a vote to the survey for the authenticated person.
     * @param VoteRequest voteRequest the request containing the survey and choice IDs.
     * @return the updated SurveyDto object.
     */
    @Override
    public SurveyDto addVote(VoteRequest voteRequest) {
        // Get the authenticated person
        Person currentUser = authService.getAuthenticatedPerson();

        // Retrieve the survey from the database
        Survey survey = repository.findById(Objects.requireNonNull(voteRequest.surveyId()))
            .orElseThrow(() -> new NotFoundException(new NotFoundMessage(voteRequest.surveyId(), Survey.class)));
        
        checkSurveysClosure(List.of(survey)); // Check if the survey is closed before adding the vote
        survey.addVote(voteRequest.choiceId(), currentUser); // Add the vote to the survey
        return mapper.toDto(repository.save(survey), currentUser, clock);
    }

    /**
     * Removes a vote from the survey for the authenticated person.
     * @param VoteRequest voteRequest the request containing the survey and choice IDs.
     * @return the updated SurveyDto object.
     */
    @Override
    public SurveyDto removeVote(VoteRequest voteRequest) {
        Person currentUser = authService.getAuthenticatedPerson(); // Get the authenticated person

        // Retrieve the survey from the database
        Survey survey = repository.findById(Objects.requireNonNull(voteRequest.surveyId()))
            .orElseThrow(() -> new NotFoundException(new NotFoundMessage(voteRequest.surveyId(), Survey.class)));
        
        checkSurveysClosure(List.of(survey)); // Check if the survey is closed before removing the vote
        survey.removeVote(voteRequest.choiceId(), currentUser); // Remove the vote from the survey
        return mapper.toDto(repository.save(survey), currentUser, clock);
    }

    /**
     * Checks if there are closed survey in the provided list and throws an exception there are.
     * @param SurveyDto surveyDto the SurveyDto to check.
     * @throws IllegalArgumentException if there are closed surveys, with the list of closed survey IDs.
     */
    @Override
    public void checkSurveysClosure(List<Survey> surveys) {
        List<Long> ids = surveys.stream()
            .filter(survey -> Objects.requireNonNull(survey).isClosed(clock))
            .map(survey -> { // throw the error if it's for creation i.e. there are null ids
                if (survey.getId() == null) {
                    throw new BadCUException(new BadCUMessage(
                        true, Survey.class, "that are already closed",
                        null, "The scheduled end date must be after today"
                    ));
                }
                return survey.getId();
            })
            .toList();
        if (!ids.isEmpty()) {
            throw new BadCUException(new BadCUMessage(
                false, Survey.class, "that are already closed",
                ids, "are already closed"
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
            .filter(survey -> survey.question().isBlank() || survey.question().length() > 255)
            .map(survey -> { // throw the error if it's for creation i.e. there are null ids
                if (survey.id() == null) {
                    throw new BadCUException(new BadCUMessage(
                        true, Survey.class, "with invalid data",
                        null, "The question must not be blank nor exceed 255 characters"
                    ));
                }
                return survey.id();
            })
            .toList();
        if (!ids.isEmpty()) {
            throw new BadCUException(new BadCUMessage(
                false, Survey.class, "with invalid data",
                ids, "must not have their question blank nor exceeding 255 characters"
            ));
        }
    }
}
