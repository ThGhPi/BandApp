package com.thghpi.bandapp.band_api.unit.service.mapper;
import com.thghpi.bandapp.band_api.dto.ChoiceDto;
import com.thghpi.bandapp.band_api.entity.Choice;
import com.thghpi.bandapp.band_api.entity.Person;
import com.thghpi.bandapp.band_api.entity.Survey;
import com.thghpi.bandapp.band_api.service.mapper.ChoiceMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Set;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

/**
 * Unit tests for {@link ChoiceMapper}.
 * These tests focus on the mapping logic of the ChoiceMapper, ensuring that Choice entities are correctly converted to ChoiceDto objects and vice versa.
 * The tests cover scenarios such as mapping a Choice with no votes, mapping a Choice with multiple
 * votes, and mapping a ChoiceDto to a Choice entity while correctly handling the survey association.
 * The tests use the MapStruct factory to create an instance of the ChoiceMapper and verify that the mapping results are as expected.
 * The target for tests are the adaption brought to account for fields that need to be calculated (votes) or mapped from an id to an entity (surveyId to survey).
 */
public class ChoiceMapperTest {
    /** The ChoiceMapper instance used for testing. */
    private final ChoiceMapper mapper = Mappers.getMapper(ChoiceMapper.class);

    /**
     * Test that a Choice entity with no votes nor survey association is correctly mapped to a ChoiceDto with 0 votes and a null surveyId.
     * Test that a Choice entity with multiple votes and a survey association is correctly mapped to a ChoiceDto with the correct number of votes and the correct surveyId.
     */
    @Test
    public void shouldMapChoiceToDto() {
        Choice choice1 = Choice.builder()
                .id(1L)
                .title("Choice 1")
                .build();
        assertEquals(0L, mapper.toDto(choice1, null).votes());
        assertNull(mapper.toDto(choice1, null).surveyId());
        Choice choice2 = Choice.builder()
                .id(2L)
                .title("Choice 2")
                .complement("Cliquez ici pour suivre le lien")
                .url("http://example.com")
                .build();
        Survey survey = new Survey();
        survey.setId(1L);
        choice2.setSurvey(survey);
        Person person1 = Person.builder()
            .id(1L)
            .firstname("Alice")
            .lastname("Taylor")
            .username("aliceT")
            .email("alice@example.com")
            .password("blank")
            .choices(Set.of(choice2))
            .build();
        Person person2 = Person.builder()
            .id(2L)
            .firstname("Bob")
            .lastname("Smith")
            .username("bobS")
            .email("bob@example.com")
            .password("blank")
            .choices(Set.of(choice2))
            .build();
        choice2.setVoters(Set.of(person1, person2));
        assertEquals(2L, mapper.toDto(choice2, null).votes());
        assertEquals(1L, mapper.toDto(choice2, null).surveyId());
    }

    /**
     * Test that a ChoiceDto with a surveyId is correctly mapped to a Choice entity with the correct survey association.
     * Test that a ChoiceDto with no surveyId is correctly mapped to a Choice entity with a null survey association.
     */
    @Test
    public void shouldMapChoiceDtoToEntity() {
        ChoiceDto choiceDto1 = new ChoiceDto(
                1L,
                "Choice 1",
                null,
                null,
                1L,
                null
            );
        assertEquals(1L, mapper.toEntity(choiceDto1).getSurvey().getId());
        ChoiceDto choiceDto2 = new ChoiceDto(
                null,
                "Choice 2",
                "Cliquez ici pour suivre le lien",
                "http://example.com",
                null,
                null
            );
        assertNull(mapper.toEntity(choiceDto2).getSurvey());
    }
}
