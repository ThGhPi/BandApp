package com.thghpi.bandapp.band_api.unit.service;
import com.thghpi.bandapp.band_api.entity.Survey;
import com.thghpi.bandapp.band_api.dto.SurveyDto;
import com.thghpi.bandapp.band_api.repository.SurveyRepository;
import com.thghpi.bandapp.band_api.service.SurveyServiceImpl;
import com.thghpi.bandapp.band_api.service.CurrentUserService;
import com.thghpi.bandapp.band_api.service.exception.BadCUException;
import com.thghpi.bandapp.band_api.service.mapper.SurveyMapper;

import java.util.List;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * Unit tests for the {@link SurveyServiceImpl} class, focusing on the validation of survey closure logic.
 * These tests ensure that the service correctly identifies closed surveys based on their scheduled end date
 * and that it properly handles attempts to create or update closed surveys.
 * The tests cover both single survey validation and bulk survey validation scenarios.
 */
@ExtendWith(MockitoExtension.class)
public class SurveyServiceTest {
    /** The SurveyMapper instance to be mocked */
    @Mock
    private SurveyMapper mapper;
    /** The CurrentUserService instance to be mocked */
    @Mock
    private CurrentUserService authService;
    /** The SurveyRepository instance to be mocked */
    @Mock
    private SurveyRepository repository;
    /** The SurveyServiceImpl instance to be tested, with mocked dependencies injected */
    private SurveyServiceImpl service;
    
    /** A fixed clock instance for testing purposes, replacing the bean clock */
    private static final Clock FIXED_CLOCK = Clock.fixed(
            Instant.parse("2026-07-03T12:00:00Z"),
            ZoneOffset.UTC
        );

    /**
     * Sets up the test environment before each test method is executed.
     * This method is used to stub the clock methods to return a fixed instant and zone, ensuring consistent test results.
     */
    @BeforeEach
    void setUp() {
        service = new SurveyServiceImpl(FIXED_CLOCK, mapper, repository, authService);
    }

    /**
     * Tests that the service correctly rejects attempts to create a single survey with invalid data.
    */
   @Test
    void shouldRejectInvalidSurvey() {
        SurveyDto closedSurveyDto = new SurveyDto(
            null,
            "",
            LocalDate.now(FIXED_CLOCK).minusDays(1),
            true,
            null,
            null,
            null
        );
        Survey entity = new Survey();
        entity.setQuestion("Question ?");
        entity.setScheduledEnd(LocalDate.now(FIXED_CLOCK).minusDays(1));
        entity.setMultiplicity(true);

        BadCUException thrown = assertThrows(
            BadCUException.class,
            () -> service.checkSurveysClosure(List.of(entity))
        );
        assertEquals(
            "Can't create Survey that are already closed. The scheduled end date must be after today.",
            thrown.getMessage()
        );
        BadCUException thrown2 = assertThrows(
            BadCUException.class,
            () -> service.checkSurveysData(List.of(closedSurveyDto))
        );
        assertEquals(
            "Can't create Survey with invalid data. The question must not be blank nor exceed 255 characters.",
            thrown2.getMessage()
        );
    }

    /**
     * Tests that the service correctly accepts attempts to create a single open survey.
     */
    @Test
    void shouldAcceptOpenSurvey() {
        Survey openSurvey = new Survey(
            null,
            "Question ?",
            LocalDate.now(FIXED_CLOCK),
            true,
            null
        );
        assertDoesNotThrow(() -> service.checkSurveysClosure(List.of(openSurvey)));
    }

    /**
     * Tests that the service correctly rejects attempts to update : 
     * - multiple closed surveys,
     * - multiple surveys with invalid data.
     */
    @Test
    void shouldRejectInvalidSurveys() {
        Survey closedSurvey1 = new Survey(
            2L,
            "Question QuestionQuestionQuestionQuestionQuestionQuestionQuestionQuestionQuestionQuestionQuestionQuestionQuestionQuestionQuestionQuestionQuestionQuestionQuestionQuestionQuestionQuestionQuestionQuestionQuestionQuestionQuestionQuestionQuestionQuestionQuestionQuestion ?",
            LocalDate.now(FIXED_CLOCK).minusDays(1),
            false,
            null
        );
        Survey closedSurvey2 = new Survey(
            1L,
            "Question ?",
            LocalDate.now(FIXED_CLOCK).minusDays(1),
            true,
            null
        );
        BadCUException thrown1 = assertThrows(
            BadCUException.class,
            () -> service.checkSurveysClosure(
                List.of(closedSurvey1, closedSurvey2)
            )
        );
        assertEquals(
            "Can't update Survey that are already closed : Survey with IDs [2, 1] are already closed.",
            thrown1.getMessage()
        );
        SurveyDto dto1 = new SurveyDto(
            2L,
            "Question QuestionQuestionQuestionQuestionQuestionQuestionQuestionQuestionQuestionQuestionQuestionQuestionQuestionQuestionQuestionQuestionQuestionQuestionQuestionQuestionQuestionQuestionQuestionQuestionQuestionQuestionQuestionQuestionQuestionQuestionQuestionQuestion ?",
            LocalDate.now(FIXED_CLOCK).minusDays(1),
            false,
            null,
            null,
            null
        );
        SurveyDto dto2 = new SurveyDto(
            1L,
            "",
            LocalDate.now(FIXED_CLOCK).minusDays(1),
            false,
            null,
            null,
            null
        );
        BadCUException thrown2 = assertThrows(
            BadCUException.class,
            () -> service.checkSurveysData(
                List.of(dto1, dto2)
            )
        );
        assertEquals(
            "Can't update Survey with invalid data : Survey with IDs [2, 1] must not have their question blank nor exceeding 255 characters.",
            thrown2.getMessage()
        );
    }

    /**
     * Tests that the service correctly accepts attempts to update multiple open surveys.
     */
    @Test
    void shouldAcceptOpenSurveys() {
        Survey openSurvey1 = new Survey(
            2L,
            "Question ?",
            LocalDate.now(FIXED_CLOCK).plusDays(1),
            false,
            null
        );
        Survey openSurvey2 = new Survey(
            1L,
            "Question ?",
            LocalDate.now(FIXED_CLOCK).plusDays(1),
            true,
            null
        );
        assertDoesNotThrow(() -> service.checkSurveysClosure(
                List.of(openSurvey1, openSurvey2)
            )
        );
    }
}
