package com.thghpi.bandapp.band_api.integration.controller;
import com.thghpi.bandapp.band_api.entity.Survey;
import com.thghpi.bandapp.band_api.dto.ChoiceDto;
import com.thghpi.bandapp.band_api.dto.SurveyDto;
import com.thghpi.bandapp.band_api.repository.SurveyRepository;
import com.thghpi.bandapp.band_api.integration.AbstractIntegrationTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.Set;
import java.util.List;
import java.util.Objects;
import java.time.LocalDate;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Integration tests for the SurveyController class.
 * These tests verify the behavior of the SurveyController endpoints,
 * including creating a survey, retrieving surveys, and handling invalid input.
 * The tests use MockMvc to perform HTTP requests and assert the responses.
 * @throws Exception if any of the HTTP requests fail or if the assertions fail.
 */
@AutoConfigureMockMvc
public class SurveyControllerIT extends AbstractIntegrationTest {
    /**
     * MockMvc instance used to perform HTTP requests in the tests.
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * To generate proper json for testing creation and update.
     */
    @Autowired
    ObjectMapper objectMapper;

    /**
     * SurveyRepository instance used to interact with the survey data in the tests.
     */
    @Autowired
    private SurveyRepository repository;

    /**
     * Clean database before each test in the test container
     * to make sure there is no data interferences between tests.
     */
    @BeforeEach
    void cleanDatabase() {
        repository.deleteAll();
    }

    /**
     * Test for creating a survey with valid data.
     * Tested endpoint : POST /band-api/surveys
     * @throws Exception when test fail
     */
    @Test
    void shouldCreateSurvey() throws Exception {
        ChoiceDto choice1 = new ChoiceDto(
            null,
            "Red",
            null,
            null,
            null,
            null
        );
        ChoiceDto choice2 = new ChoiceDto(
            null,
            "Blue",
            null,
            null,
            null,
            null
        );
        ChoiceDto choice3 = new ChoiceDto(
            null,
            "Green",
            null,
            null,
            null,
            null
        );
        SurveyDto dto1 = new SurveyDto(
            null,
            "Favorite color ?",
            LocalDate.now().plusMonths(1),
            false,
            null,
            null,
            Set.of(choice1, choice2, choice3)
        );

        mockMvc.perform(post("/band-api/surveys")
                .contentType("application/json")
                .content(Objects.requireNonNull(objectMapper.writeValueAsString(dto1))))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.question").value("Favorite color ?"))
            .andExpect(jsonPath("$.scheduledEnd").value(LocalDate.now().plusMonths(1).toString()))
            .andExpect(jsonPath("$.multiplicity").value(false))
            .andExpect(jsonPath("$.totalVotes").value(0))
            .andExpect(jsonPath("$.closed").value(false))
            .andExpect(jsonPath("$.choices").isArray())
            .andExpect(jsonPath("$.choices[0].id").value("1"))
            .andExpect(jsonPath("$.choices[0].votes").value(0))
            .andExpect(jsonPath("$.choices[1].id").value("2"))
            .andExpect(jsonPath("$.choices[2].id").value("3"))
            .andExpect(jsonPath("$.choices[3]").doesNotExist());
    }

    /**
     * Test for creating a survey with invalid data,
     * such as an empty question, a past scheduled end date, or no choices.
     * Tested endpoint : POST /band-api/surveys
     * @throws Exception when test fail
     */
    @Test
    void shouldNotCreateSurveyWithInvalidData() throws Exception {
        ChoiceDto choice1 = new ChoiceDto(
            null,
            "red",
            null,
            null,
            null,
            null
        );
        ChoiceDto choice2 = new ChoiceDto(
            null,
            "blue",
            null,
            null,
            null,
            null
        );
        ChoiceDto choice3 = new ChoiceDto(
            null,
            "green",
            null,
            null,
            null,
            null
        );
        SurveyDto dto = new SurveyDto(
            null,
            "",
            LocalDate.now().plusMonths(1),
            false,
            null,
            null,
            Set.of(choice1, choice2, choice3)
        );
        String jsonSurvey1 = Objects.requireNonNull(objectMapper.writeValueAsString(dto));

        mockMvc.perform(post("/band-api/surveys")
                .contentType("application/json")
                .content(jsonSurvey1))
            .andExpect(status().isBadRequest());
        
        dto.setQuestion("Favorite color ?");
        dto.setScheduledEnd(LocalDate.now().minusYears(1));
        String jsonSurvey2 = Objects.requireNonNull(objectMapper.writeValueAsString(dto));

        mockMvc.perform(post("/band-api/surveys")
                .contentType("application/json")
                .content(jsonSurvey2))
            .andExpect(status().isBadRequest());
    }

    /**
     * Test for retrieving old surveys with pagination.
     * This test creates several surveys with past scheduled end dates and one survey with a future scheduled end date.
     * It then performs GET requests to the /band-api/surveys/page/{pageNumber}
     * endpoint to retrieve the old surveys in pages and asserts the responses.
     * @throws Exception when test fail
     */
    @Test
    void shouldReturnOldSurveys() throws Exception {
        Survey survey1 = Survey.builder()
            .question("Favorite color ?")
            .scheduledEnd(LocalDate.now().minusMonths(2))
            .multiplicity(false)
            .build();
        Survey survey2 = Survey.builder()
            .question("Favorite pet ?")
            .scheduledEnd(LocalDate.now().minusMonths(3))
            .multiplicity(false)
            .build();
        Survey survey3 = Survey.builder()
            .question("Favorite instrument ?")
            .scheduledEnd(LocalDate.now().minusMonths(4))
            .multiplicity(false)
            .build();
        Survey survey4 = Survey.builder()
            .question("Favorite food ?")
            .scheduledEnd(LocalDate.now().minusMonths(5))
            .multiplicity(false)
            .build();
        Survey survey5 = Survey.builder()
            .question("Favorite drink ?")
            .scheduledEnd(LocalDate.now().minusMonths(6))
            .multiplicity(false)
            .build();
        Survey survey6 = Survey.builder()
            .question("Favorite town ?")
            .scheduledEnd(LocalDate.now().minusMonths(7))
            .multiplicity(false)
            .build();
        Survey survey7 = Survey.builder()
            .question("Favorite season ?")
            .scheduledEnd(LocalDate.now().plusMonths(1))
            .multiplicity(false)
            .build();
        
        repository.saveAll(Objects.requireNonNull(
            List.of(survey1, survey2, survey3, survey4, survey5, survey6, survey7)
        ));

        mockMvc.perform(get("/band-api/surveys/before/" + LocalDate.now().minusMonths(1)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.surveys").isArray())
            .andExpect(jsonPath("$.surveys[0].question").value("Favorite color ?"))
            .andExpect(jsonPath("$.surveys[1].question").value("Favorite pet ?"))
            .andExpect(jsonPath("$.surveys[2].question").value("Favorite instrument ?"))
            .andExpect(jsonPath("$.surveys[3].question").value("Favorite food ?"))
            .andExpect(jsonPath("$.surveys[4].question").value("Favorite drink ?"))
            .andExpect(jsonPath("$.surveys[5]").doesNotExist())
            .andExpect(jsonPath("$.hasNext").value(true));
        
        mockMvc.perform(get("/band-api/surveys/before/" + LocalDate.now().minusMonths(6)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.surveys").isArray())
            .andExpect(jsonPath("$.surveys[0].question").value("Favorite town ?"))
            .andExpect(jsonPath("$.surveys[1]").doesNotExist())
            .andExpect(jsonPath("$.hasNext").value(false));
        
        mockMvc.perform(get("/band-api/surveys/before/" + LocalDate.now().minusMonths(7)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.surveys").isArray())
            .andExpect(jsonPath("$.surveys[0]").doesNotExist());
    }

    /**
     * Test for retrieving current surveys.
     * This test creates several surveys with future scheduled end dates
     * and one survey with a past scheduled end date.
     * Tested endpoint : GET /band-api/surveys 
     * @throws Exception when test fail
     */
    @Test
    void shouldReturnCurrentSurveys() throws Exception {
        Survey survey1 = Survey.builder()
            .question("Favorite color ?")
            .scheduledEnd(LocalDate.now().plusMonths(1))
            .multiplicity(false)
            .build();
        Survey survey2 = Survey.builder()
            .question("Favorite pet ?")
            .scheduledEnd(LocalDate.now().minusDays(20))
            .multiplicity(false)
            .build();
        Survey survey3 = Survey.builder()
            .question("Favorite town ?")
            .scheduledEnd(LocalDate.now().minusYears(1))
            .multiplicity(false)
            .build();
        
        repository.saveAll(Objects.requireNonNull(
            List.of(survey1, survey2, survey3)
        ));

        mockMvc.perform(get("/band-api/surveys"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$[0].question").value("Favorite color ?"))
            .andExpect(jsonPath("$[1].question").value("Favorite pet ?"))
            .andExpect(jsonPath("$[2]").doesNotExist());
    }

    /**
     * Test for retrieving all surveys.
     * This test creates several surveys with future scheduled end dates
     * and one survey with a past scheduled end date.
     * Tested endpoint : GET /band-api/surveys/all
     * @throws Exception when test fail
     */
    @Test
    void shouldReturnAllSurveys() throws Exception {
        Survey survey1 = Survey.builder()
            .question("Favorite color ?")
            .scheduledEnd(LocalDate.now().plusMonths(1))
            .multiplicity(false)
            .build();
        Survey survey2 = Survey.builder()
            .question("Favorite pet ?")
            .scheduledEnd(LocalDate.now().plusMonths(1))
            .multiplicity(false)
            .build();
        Survey survey3 = Survey.builder()
            .question("Favorite instrument ?")
            .scheduledEnd(LocalDate.now().minusYears(1))
            .multiplicity(false)
            .build();
        
        repository.saveAll(Objects.requireNonNull(
            List.of(survey1, survey2, survey3)
        ));

        mockMvc.perform(get("/band-api/surveys/all"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$[0].question").value("Favorite color ?"))
            .andExpect(jsonPath("$[1].question").value("Favorite pet ?"))
            .andExpect(jsonPath("$[2].question").value("Favorite instrument ?"))
            .andExpect(jsonPath("$[3]").doesNotExist());
    }

    /**
     * Test for retrieving a survey by its ID.
     * This test creates a survey, and try to retrive it by it's ID.
     * Tested endpoint : GET /band-api/surveys/{id}
     * @throws Exception when test fail
     */
    @Test
    void shouldReturnSurveyById() throws Exception {
        Survey survey = Survey.builder()
            .question("Favorite color ?")
            .scheduledEnd(LocalDate.now().plusMonths(1))
            .multiplicity(false)
            .build();
        
        survey = repository.save(Objects.requireNonNull(survey));

        mockMvc.perform(get("/band-api/surveys/" + survey.getId().toString()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.question").value("Favorite color ?"))
            .andExpect(jsonPath("$.scheduledEnd").value(LocalDate.now().plusMonths(1).toString()))
            .andExpect(jsonPath("$.multiplicity").value(false))
            .andExpect(jsonPath("$.totalVotes").value(0))
            .andExpect(jsonPath("$.closed").value(false))
            .andExpect(jsonPath("$.choices").isArray())
            .andExpect(jsonPath("$.choices").isEmpty());
    }

    /**
     * Test for retrieving a non-existing survey by its ID.
     * This test tries to retrieve a survey that does not exist.
     * Tested endpoint : GET /band-api/surveys/{id}
     * @throws Exception when test fail
     */
    @Test
    void shouldReturnNotFoundForNonExistingSurvey() throws Exception {
        mockMvc.perform(get("/band-api/surveys/9999"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.message")
                .value("Survey with ID 9999 not found"));
    }

    /**
     * Test the correct implementation of updating a survey
     * This test tries to update a survey  by changing it's question and adding choices
     * Tested endpoint : PUT /band-api/surveys/{id}
     * @throws Exception when an expectationis not fullfilled
     */
    @Test
    void shouldUpdateSurvey() throws Exception {
        Survey survey = Survey.builder()
            .question("Favorite color ?")
            .scheduledEnd(LocalDate.now().plusMonths(1))
            .multiplicity(false)
            .build();
        
        survey = repository.save(Objects.requireNonNull(survey));

        ChoiceDto choice1 = new ChoiceDto(
            null,
            "Dog",
            null,
            null,
            survey.getId(),
            null
        );
        ChoiceDto choice2 = new ChoiceDto(
            null,
            "Cat",
            null,
            null,
            survey.getId(),
            null
        );
        ChoiceDto choice3 = new ChoiceDto(
            null,
            "Fish",
            null,
            null,
            survey.getId(),
            null
        );
        SurveyDto dtoForUpdate = new SurveyDto(
            null,
            "Favorite pet ?",
            LocalDate.now().plusMonths(2),
            true,
            null,
            null,
            Set.of(choice1, choice2, choice3)
        );
        String jsonForUpdate = Objects.requireNonNull(objectMapper.writeValueAsString(dtoForUpdate));

        mockMvc.perform(put("/band-api/surveys/" + survey.getId().toString())
                .contentType("application/json")
                .content(jsonForUpdate))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(survey.getId()))
            .andExpect(jsonPath("$.question").value("Favorite pet ?"))
            .andExpect(jsonPath("$.scheduledEnd").value(LocalDate.now().plusMonths(2).toString()))
            .andExpect(jsonPath("$.multiplicity").value(true))
            .andExpect(jsonPath("$.totalVotes").value(0))
            .andExpect(jsonPath("$.closed").value(false))
            .andExpect(jsonPath("$.choices").isArray())
            .andDo(print())
            .andExpect(jsonPath("$.choices[*].title").value(containsInAnyOrder("Dog", "Cat", "Fish")))
            .andExpect(jsonPath("$.choices[0].votes").value(0));
    }
}
