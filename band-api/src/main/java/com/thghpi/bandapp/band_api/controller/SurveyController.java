package com.thghpi.bandapp.band_api.controller;
import com.thghpi.bandapp.band_api.dto.SurveyDto;
import com.thghpi.bandapp.band_api.dto.SurveyPageDto;
import com.thghpi.bandapp.band_api.service.SurveyServiceImpl;

import java.util.List;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;

import org.springframework.lang.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



/**
 * Controller class for the Survey entity, handling HTTP requests related to surveys.
 * It provides endpoints for creating, retrieving, updating, and deleting surveys.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/band-api/surveys")
public class SurveyController {
    private final SurveyServiceImpl service;
    
    /**
     * For endpoint band-api/surveys/{id} GET request,
     * returns the SurveyDto of the survey with the given id.
     * @param id the id of the survey to retrieve, taken from the path variable of the request.
     * @return the SurveyDto of the survey with the given id if successful, otherwise an error response.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SurveyDto> getSurvey(@PathVariable @NonNull Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    /**
     * For endpoint band-api/surveys/all GET request,
     * returns the list of all surveys.
     * @return a list of SurveyDto for all the surveys in the database.
     */
    @GetMapping("/all")
    public ResponseEntity<List<SurveyDto>> getAllSurveys() {
        return ResponseEntity.ok(service.getAll());
    }

    /**
     * For endpoint band-api/surveys GET request,
     * returns the list of recent surveys.
     * @return a list of SurveyDto for the recent surveys.
     */
    @GetMapping
    public ResponseEntity<List<SurveyDto>> getCurrentSurveys() {
        return ResponseEntity.ok(service.getRecent());
    }

    /**
     * For endpoint band-api/surveys/before/{date} GET request,
     * returns the list of older surveys.
     * @param pageNumber the page number of the surveys to retrieve, taken from the path variable of the request.
     * @return a list of SurveyDto for the older surveys.
     */
    @GetMapping("/before/{date}")
    public ResponseEntity<SurveyPageDto> getOlderSurveys(@PathVariable LocalDate date) {
        return ResponseEntity.ok(service.getPrevious(date));
    }

    /**
     * For endpoint band-api/surveys POST request,
     * creates a new survey in the database.
     * @param surveyDto the SurveyDto of the survey to create, taken from the body of the request.
     * @return the SurveyDto of the created survey if successful, otherwise an error response.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<SurveyDto> createSurvey(@RequestBody SurveyDto surveyDto) {
        return ResponseEntity.ok(service.save(surveyDto));
    }

    /**
     * For endpoint band-api/surveys/several POST request,
     * creates several new surveys in the database.
     * @param surveys the list of SurveyDto of the surveys to create, taken from the body of the request.
     * @return the list of SurveyDto of the created surveys if successful, otherwise an error response.
     */
    @PostMapping("/several")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<List<SurveyDto>> createSeveral(@RequestBody @NonNull List<SurveyDto> surveys) {        
        return ResponseEntity.ok(service.saveAll(surveys));
    }
    
    /**
     * For endpoint band-api/surveys/{id} PUT request,
     * updates the survey with the given id in the database with the given SurveyDto.
     * @param id the id of the survey to update, taken from the path variable of the request.
     * @param surveyDto the SurveyDto with the updates, taken from the body of the request.
     * @return the SurveyDto of the updated survey if successful, otherwise an error response.
     */
    @PutMapping("/{id}")
    public ResponseEntity<SurveyDto> updateSurvey(@PathVariable Long id, @RequestBody SurveyDto surveyDto) {
        surveyDto.setId(id);
        return ResponseEntity.ok(service.save(surveyDto));
    }

    /**
     * For endpoint band-api/surveys PUT request,
     * updates several surveys in the database with the given list of SurveyDto.
      * Check that all SurveyDto in the list have an id, otherwise throws an exception.
     * @param surveys the list of SurveyDto with the updates, taken from the body of the request.
     * @return the list of updated SurveyDto if the update process is successful, otherwise an error response.
     * @throws IllegalArgumentException when finding a SurveyDto in the list without an id.
     */
    @PutMapping
    public ResponseEntity<List<SurveyDto>> updateSeveral(@RequestBody @NonNull List<SurveyDto> surveys) {
        for (SurveyDto survey : surveys) {
            if (survey.getId() == null) {
                throw new IllegalArgumentException("All surveys must have an ID for update");
            }
        }
        return ResponseEntity.ok(service.saveAll(surveys));
    }

    /**
     * For endpoint band-api/surveys/{id} DELETE request,
     * deletes the survey with the given id from the database.
     * @param id the id of the survey to delete, taken from the path variable of the request.
     * @return a no content response if the deletion process is successful, otherwise an error response.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSurvey(@PathVariable @NonNull Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * For endpoint band-api/surveys DELETE request,
     * deletes the list of surveys with the given list of SurveyDto from the database.
     * @param surveys the list of SurveyDto to delete, taken from the body of the request.
     * @return a no content response if the deletion process is successful, otherwise an error response.
     * @throws IllegalArgumentException when finding a SurveyDto in the list without an id.
     */
    @DeleteMapping
    public ResponseEntity<Void> deleteSeveral(@RequestBody @NonNull List<SurveyDto> surveys) {
        for (SurveyDto surveyDto : surveys) {
            if (surveyDto.getId() == null) {
                throw new IllegalArgumentException("All surveys must have an ID for deletion");
            }
        }
        service.deleteAll(surveys);
        return ResponseEntity.noContent().build();
    }
}
