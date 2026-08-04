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
import java.time.ZoneId;

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
 * Unit Tests for the SurveyMapper class, which maps between Survey entities and SurveyDto objects.
 * Tests the mapping of the closed and totalVotes fields, as well as the mapping of choices using the ChoiceMapper.
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
     SurveyMapperImpl.class,
     ChoiceMapperImpl.class
    })
public class SurveyMapperTest {
    @Autowired
    private SurveyMapper mapper;

    private final Clock fixedClock = Clock.fixed(Instant.now(), ZoneId.systemDefault());

    /**
     * Tests the mapping of a Survey entity to a SurveyDto, including the calculation of the closed and totalVotes fields,
     * and the mapping of choices using the ChoiceMapper.
     */
    @Test
    void shouldMapSurveyToDto() {
        Survey survey1 = new Survey(
                1L,
                "Question ?",
                LocalDate.now().plusDays(1),
                true,
                Set.of()
            );
        survey1.setChoices(Set.of(
                new Choice(
                        1L,
                        "Choice 1",
                        null,
                        null,
                        survey1,
                        null),
                new Choice(
                        2L,
                        "Choice 2",
                        null,
                        null,
                        survey1,
                        null
                    )
                )
            );
        SurveyDto surveyDto1 = mapper.toDto(survey1, null, fixedClock);
        assertNotNull(surveyDto1);
        assertFalse(Objects.requireNonNull(surveyDto1.closed()));
        assertEquals(0L, surveyDto1.totalVotes());
        Survey survey2 = new Survey(
                2L,
                "Question ?",
                LocalDate.now().minusDays(1),
                true,
                Set.of()
            );
        SurveyDto surveyDto2 = mapper.toDto(survey2, null, fixedClock);
        assertTrue(Objects.requireNonNull(surveyDto2.closed()));
        assertEquals(0L, surveyDto2.totalVotes());
        assertEquals(2, surveyDto1.choices().size());
    }
}
