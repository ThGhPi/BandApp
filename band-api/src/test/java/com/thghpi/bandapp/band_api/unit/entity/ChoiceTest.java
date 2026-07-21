package com.thghpi.bandapp.band_api.unit.entity;
import com.thghpi.bandapp.band_api.entity.Choice;
import com.thghpi.bandapp.band_api.entity.Person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

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
        Choice choice = Choice.builder()
            .id(1L)
            .title("Option 1")
            .complement("complement")
            .build();
        assertEquals(0L, choice.getVotes());
        Person person1 = Person.builder()
            .id(1L)
            .lastname("Taylor")
            .firstname("Alice")
            .username("aliceT")
            .email("alice.taylor@example.com")
            .password("blank")
            .choices(Set.of(choice))
            .build();
        Person person2 = Person.builder()
            .id(2L)
            .lastname("Smith")
            .firstname("Bob")
            .username("bobS")
            .email("bob.smith@example.com")
            .password("blank")
            .choices(Set.of(choice))
            .build();
        choice.setPersons(Set.of(person1, person2));
        assertEquals(2L, choice.getVotes());
    }

    /**
     * Tests the checkLinkComplement method when a URL is present. It creates a choice with a URL and asserts that the method sets a default complement.
     * It also tests that if a complement is already provided, the method does not overwrite it.
     */
    @Test
    void shouldEnsureComplementWhenUrlIsPresent() {
        Choice choiceWithUrl1 = Choice.builder()
            .title("Choice with URL")
            .url("http://example.com")
            .build();
        choiceWithUrl1.checkLinkComplement();
        Choice choiceWithUrl2 = Choice.builder()
            .title("Choice with URL")
            .complement("Lien vers la vidéo")
            .url("http://example.com")
            .build();
        choiceWithUrl2.checkLinkComplement();
        assertEquals("Cliquez ici pour suivre le lien", choiceWithUrl1.getComplement());
        assertEquals("Lien vers la vidéo", choiceWithUrl2.getComplement());
    }

    /**
     * Tests the checkLinkComplement method when a URL is absent. It creates a choice without a URL and asserts that the method does not set a complement.
     * It also tests that if a complement is provided without a URL, the method does not remove it.
     */
    @Test
    void shouldNotSetComplementWhenUrlIsAbsent() {
        Choice choiceWithoutUrl1 = Choice.builder()
            .title("Choice without URL")
            .build();
        choiceWithoutUrl1.checkLinkComplement();
        Choice choiceWithoutUrl2 = Choice.builder()
            .title("Choice without URL")
            .complement("Some complement")
            .build();
        choiceWithoutUrl2.checkLinkComplement();
        assertNull(choiceWithoutUrl1.getComplement());
        assertEquals("Some complement", choiceWithoutUrl2.getComplement());
    }
}
