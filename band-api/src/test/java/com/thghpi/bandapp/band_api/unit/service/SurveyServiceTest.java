package com.thghpi.bandapp.band_api.unit.service;
import com.thghpi.bandapp.band_api.entity.Survey;
import com.thghpi.bandapp.band_api.dto.SurveyDto;
import com.thghpi.bandapp.band_api.repository.SurveyRepository;
import com.thghpi.bandapp.band_api.service.SurveyServiceImpl;
import com.thghpi.bandapp.band_api.service.mapper.SurveyMapper;

import java.util.List;
import java.time.LocalDate;

import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * Unit tests for the SurveyServiceImpl class, focusing on the validation of survey closure logic.
 * These tests ensure that the service correctly identifies closed surveys based on their scheduled end date
 * and that it properly handles attempts to create or update closed surveys.
 * The tests cover both single survey validation and bulk survey validation scenarios.
 */
@ExtendWith(MockitoExtension.class)
public class SurveyServiceTest {
    @Mock
    private SurveyMapper mapper;
    @Mock
    private SurveyRepository repository;

    @InjectMocks
    private SurveyServiceImpl service;

    /**
     * Tests that the service correctly rejects attempts to create a single closed survey.
     */
    @Test
    void shouldRejectClosedSurvey() {
        SurveyDto closedSurveyDto = new SurveyDto(
            null,
            "Question ?",
            LocalDate.now().minusDays(1),
            true,
            null,
            null,
            null
        );
        Survey entity = new Survey();
        entity.setQuestion(closedSurveyDto.getQuestion());
        entity.setScheduledEnd(LocalDate.now().minusDays(1));
        entity.setMultiplicity(true);

        when(mapper.toEntity(closedSurveyDto))
            .thenReturn(entity);
        IllegalArgumentException thrown = assertThrows(
            IllegalArgumentException.class,
            () -> service.save(closedSurveyDto)
        );
        assertEquals(
            "Can't create closed survey. The scheduled end date must be after today.",
            thrown.getMessage()
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
            LocalDate.now(),
            true,
            null
        );
        assertDoesNotThrow(() -> service.checkSurveyClosure(openSurvey));
    }

    /**
     * Tests that the service correctly rejects attempts to update multiple closed surveys.
     */
    @Test
    void shouldRejectClosedSurveys() {
        Survey closedSurvey1 = new Survey(
            2L,
            "Question ?",
            LocalDate.now().minusDays(1),
            false,
            null
        );
        Survey closedSurvey2 = new Survey(
            1L,
            "Question ?",
            LocalDate.now().minusDays(1),
            true,
            null
        );
        IllegalArgumentException thrown = assertThrows(
            IllegalArgumentException.class,
            () -> service.checkSurveysClosure(
                List.of(closedSurvey1, closedSurvey2)
            )
        );
        assertEquals(
            "Can't update closed surveys : surveys with ids : [2, 1] are already closed.",
            thrown.getMessage()
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
            LocalDate.now().plusDays(1),
            false,
            null
        );
        Survey openSurvey2 = new Survey(
            1L,
            "Question ?",
            LocalDate.now().plusDays(1),
            true,
            null
        );
        assertDoesNotThrow(() -> service.checkSurveysClosure(
                List.of(openSurvey1, openSurvey2)
            )
        );
    }
}
