package com.thghpi.bandapp.band_api.unit.service.mapper;
import com.thghpi.bandapp.band_api.dto.SurveyDto;
import com.thghpi.bandapp.band_api.entity.Choice;
import com.thghpi.bandapp.band_api.entity.Survey;
import com.thghpi.bandapp.band_api.service.mapper.ChoiceMapperImpl;
import com.thghpi.bandapp.band_api.service.mapper.SurveyMapper;
import com.thghpi.bandapp.band_api.service.mapper.SurveyMapperImpl;

import java.util.Objects;
import java.util.Set;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


/**
 * SurveyMapperTest a class of
 * Unit Tests for the {@link SurveyMapper} class,
 * which maps between Survey entities and SurveyDto objects.
 * Tests the mapping of the closed and totalVotes fields,
 * as well as the mapping of choices using the {@link ChoiceMapper}.
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
     SurveyMapperImpl.class,
     ChoiceMapperImpl.class
    })
public class SurveyMapperTest {
    /** The mapper for Survey entity to test */
    @Autowired
    private SurveyMapper mapper;

    private static final Clock FIXED_CLOCK = Clock.fixed(
        Instant.parse("2026-07-03T12:00:00Z"),
        ZoneOffset.UTC
    );

    /**
     * Tests the mapping of a Survey entity to a SurveyDto, including the calculation of the closed and totalVotes fields,
     * and the mapping of choices using the ChoiceMapper.
     */
    @Test
    void shouldMapSurveyToDto() {
        Survey survey1 = Survey.builder()
                .id(1L)
                .question("Question ?")
                .scheduledEnd(LocalDate.now(FIXED_CLOCK).plusDays(1))
                .multiplicity(true)
                .build();
        survey1.addChoice(Choice.builder()
                .id(1L)
                .title("Choice 1")
                .survey(survey1)
                .build());
        survey1.addChoice(Choice.builder()
                .id(2L)
                .title("Choice 2")
                .survey(survey1)
                .build());
        SurveyDto surveyDto1 = mapper.toDto(survey1, null, FIXED_CLOCK);
        assertNotNull(surveyDto1);
        assertFalse(Objects.requireNonNull(surveyDto1.closed()));
        assertEquals(0L, surveyDto1.totalVotes());
        Survey survey2 = Survey.builder()
                .id(2L)
                .question("Question ?")
                .scheduledEnd(LocalDate.now(FIXED_CLOCK).minusDays(1))
                .multiplicity(true)
                .choices(Set.of())
                .build();
        SurveyDto surveyDto2 = mapper.toDto(survey2, null, FIXED_CLOCK);
        assertTrue(Objects.requireNonNull(surveyDto2.closed()));
        assertEquals(0L, surveyDto2.totalVotes());
        assertEquals(2, surveyDto1.choices().size());
    }
}
