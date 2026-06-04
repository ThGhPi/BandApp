package com.thghpi.bandapp.band_api.unit.entity;
import com.thghpi.bandapp.band_api.entity.Choice;
import com.thghpi.bandapp.band_api.entity.Person;
import com.thghpi.bandapp.band_api.entity.Survey;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

/**
 * Tests for the Survey entity, focusing on the isClosed and getTotalVotes methods.
 * These tests ensure that the Survey correctly determines its closed status based on the closing date
 */
public class SurveyTest {
    /**
     * Tests the isClosed method of the Survey entity. It creates three surveys with different closing dates
     * and asserts that the method correctly identifies whether each survey is closed or not.
     */
    @Test
    void shouldDetermineClosedStatus() {
        Survey survey1 = new Survey(
                1L,
                "Question ?",
                LocalDate.now().plusDays(1),
                true,
                List.of()
            );
        assertFalse(survey1.isClosed());
        Survey survey2 = new Survey(
                2L,
                "Question ?",
                LocalDate.now().minusDays(1),
                true,
                List.of()
            );
        assertTrue(survey2.isClosed());
        Survey survey3 = new Survey(
                3L,
                "Question ?",
                LocalDate.now(),
                true,
                List.of()
            );
        assertFalse(survey3.isClosed());
    }

    /**
     * Tests the getTotalVotes method of the Survey entity. It creates a survey with two choices, each having a different number of votes,
     * and asserts that the method correctly calculates the total number of persons having voted for the survey.
     */
    @Test
    void shouldCalculateTotalVotes() {
        Survey survey = new Survey(
                1L,
                "Question ?",
                LocalDate.now().plusDays(1),
                true,
                List.of()
            );
        assertEquals(0L, survey.getTotalVotes());
        Choice choice1 = new Choice(
                1L,
                "Choice 1",
                null,
                null,
                survey,
                null
            );
        Choice choice2 = new Choice(
                2L,
                "Choice 2",
                null,
                null,
                survey,
                null
            );
        survey.setChoices(List.of(choice1, choice2));
        assertEquals(0L, survey.getTotalVotes());
        Person person1 = new Person(
            1L,
            "Taylor",
            "Alice",
            "aliceT",
            "alice.taylor@example.com",
            "blank",
            null, null, null, null, null, null,
            List.of(choice1, choice2)
        );
        Person person2 = new Person(
            2L,
            "Smith",
            "Bob",
            "bobS",
            "bob.smith@example.com",
            "blank",
            null, null, null, null, null, null,
            List.of(choice1)
        );
        choice1.setPersons(List.of(person1, person2));
        choice2.setPersons(List.of(person1));
        assertEquals(2L, survey.getTotalVotes());
    }
}
