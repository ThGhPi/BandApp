package com.thghpi.bandapp.band_api.unit.entity;
import com.thghpi.bandapp.band_api.entity.Choice;
import com.thghpi.bandapp.band_api.entity.Person;
import com.thghpi.bandapp.band_api.entity.Survey;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;

import org.junit.jupiter.api.Test;

/**
 * Tests for the Survey entity, focusing on the isClosed and getTotalVotes methods.
 * These tests ensure that the Survey correctly determines its closed status based on the closing date
 */
public class SurveyTest {

    private final Clock fixedClock = Clock.fixed(
            Instant.now(),
            ZoneOffset.UTC
        );

    /**
     * Tests the isClosed method of the Survey entity. It creates three surveys with different closing dates
     * and asserts that the method correctly identifies whether each survey is closed or not.
     */
    @Test
    void shouldDetermineClosedStatus() {
        Survey survey1 = new Survey(
                1L,
                "Question ?",
                LocalDate.now(fixedClock).plusDays(1),
                true,
                Set.of()
            );
        assertFalse(survey1.isClosed(fixedClock));
        Survey survey2 = new Survey(
                2L,
                "Question ?",
                LocalDate.now(fixedClock).minusDays(1),
                true,
                Set.of()
            );
        assertTrue(survey2.isClosed(fixedClock));
        Survey survey3 = new Survey(
                3L,
                "Question ?",
                LocalDate.now(fixedClock),
                true,
                Set.of()
            );
        assertFalse(survey3.isClosed(fixedClock));
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
                LocalDate.now(fixedClock).plusDays(1),
                true,
                Set.of()
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
        survey.setChoices(Set.of(choice1, choice2));
        assertEquals(0L, survey.getTotalVotes());
        Person person1 = new Person(
            1L,
            "Taylor",
            "Alice",
            "aliceT",
            "alice.taylor@example.com",
            "blank",
            null, null, null, null, null, null,
            Set.of(choice1, choice2)
        );
        Person person2 = new Person(
            2L,
            "Smith",
            "Bob",
            "bobS",
            "bob.smith@example.com",
            "blank",
            null, null, null, null, null, null,
            Set.of(choice1)
        );
        choice1.setVoters(Set.of(person1, person2));
        choice2.setVoters(Set.of(person1));
        assertEquals(2L, survey.getTotalVotes());
    }

    /**
     * Tests the addVote and removeVote methods of the Survey entity. It creates a survey with two choices and a person,
     * adds a vote for the person, asserts that the vote count increases, then removes the vote and asserts that the vote count decreases.
     */
    @Test
    void shouldAddAndRemoveVote() {
        Survey survey = Survey.builder()
            .id(1L)
            .question("Question ?")
            .scheduledEnd(LocalDate.now(fixedClock).plusDays(1))
            .multiplicity(true)
            .build();
        Choice choice1 = Choice.builder()
            .id(1L)
            .title("Choice 1")
            .complement("complement")
            .build();
        Choice choice2 = Choice.builder()
            .id(2L)
            .title("Choice 2")
            .complement("complement")
            .build();
        survey.addChoice(choice1);
        survey.addChoice(choice2);
        Person person1 = Person.builder()
            .id(1L)
            .lastname("Taylor")
            .firstname("Alice")
            .username("aliceT")
            .email("alice.taylor@example.com")
            .password("blank")
            .build();
        survey.addVote(choice1.getId(), person1);
        assertEquals(1L, survey.getTotalVotes());
        assertEquals(person1, choice1.getVoters().iterator().next());
        assertTrue(choice1.hasVoted(person1));
        survey.removeVote(choice1.getId(), person1);
        assertEquals(0L, survey.getTotalVotes());
        assertThrows(IllegalArgumentException.class, () -> choice1.removeVote(person1));
    }
}