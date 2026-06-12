package com.thghpi.bandapp.band_api.unit.entity;
import com.thghpi.bandapp.band_api.entity.Choice;
import com.thghpi.bandapp.band_api.entity.Person;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;

import org.junit.jupiter.api.Test;

/**
 * Tests for the Choice entity, focusing on the getVotes() method which calculates the total number of votes based on the associated persons.
 * This test ensures that the getVotes() method correctly counts the number of persons who have selected
 */
public class ChoiceTest {

    /**
     * Tests the getVotes method of the Choice entity. It creates a choice with two persons who have selected it,
     * and asserts that the method correctly calculates the total number of votes for the choice.
     */
    @Test
    void shouldCalculateVotes() {
        Choice choice = new Choice(
            1L,
            "Option 1",
            "complement",
            null,
            null,
            null
        );
        assertEquals(0L, choice.getVotes());
        Person person1 = new Person(
            1L,
            "Taylor",
            "Alice",
            "aliceT",
            "alice.taylor@example.com",
            "blank",
            null,
            null,
            null,
            null,
            null,
            null,
            Set.of(choice)
        );
        Person person2 = new Person(
            2L,
            "Smith",
            "Bob",
            "bobS",
            "bob.smith@example.com",
            "blank",
            null,
            null,
            null,
            null,
            null,
            null,
            Set.of(choice)
        );
        choice.setPersons(Set.of(person1, person2));
        assertEquals(2L, choice.getVotes());
    }
}
