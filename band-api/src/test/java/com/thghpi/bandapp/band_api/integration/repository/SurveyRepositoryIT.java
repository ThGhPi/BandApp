package com.thghpi.bandapp.band_api.integration.repository;
import com.thghpi.bandapp.band_api.entity.Choice;
import com.thghpi.bandapp.band_api.entity.Person;
import com.thghpi.bandapp.band_api.entity.Survey;
import com.thghpi.bandapp.band_api.entity.enumeration.Role;
import com.thghpi.bandapp.band_api.repository.ChoiceRepository;
import com.thghpi.bandapp.band_api.repository.PersonRepository;
import com.thghpi.bandapp.band_api.repository.SurveyRepository;
import com.thghpi.bandapp.band_api.integration.AbstractIntegrationTest;

import java.util.Set;
import java.util.List;
import java.util.Objects;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Integration tests for the SurveyRepository interface and implementation by JPA et hibernate.
 * These tests verify the behavior of data persistence and ACIDity during interaction with database,
 * including saving a survey, retrieving surveys by id, and handling invalid input.
 * The tests use autowired repositories for persons and choices entities to test the relations with surveys.
 * @throws Exception if any request to database or any assertion fail.
 */
public class SurveyRepositoryIT extends AbstractIntegrationTest {
    /** PersonRepository instance to test relationship between survey, choice and person */
    @Autowired
    PersonRepository personRepository;
    /** ChoiceRepository instance to test relationship with survey and person */
    @Autowired
    ChoiceRepository choiceRepository;
    /** SurveyRepository instance to test survey-related operations */
    @Autowired
    private SurveyRepository repository;

    /** Clean the database before each test */
    @BeforeEach
    void cleanDatabase() {
        choiceRepository.deleteAll();
        repository.deleteAll();
        personRepository.deleteAll();
    }

    /**
     * Test that a survey can be saved
     */
    @Test
    void shouldSaveSurvey() {
        Survey survey = Objects.requireNonNull(Survey.builder()
            .question("Question ?")
            .scheduledEnd(LocalDate.now().plusMonths(1))
            .multiplicity(false)
            .build());
        
        Survey saved = repository.save(survey);

        assertNotNull(saved.getId());
    }

    /**
     * Test that a survey can be found by its id
     */
    @Test
    void shouldFindSurveyById() {
        Survey survey = Objects.requireNonNull(Survey.builder()
            .question("Question ?")
            .scheduledEnd(LocalDate.now().plusMonths(1))
            .multiplicity(false)
            .build());
        
        Survey saved = repository.save(survey);
        Long surveyId = Objects.requireNonNull(saved.getId());

        assertEquals(
            saved,
            repository.findById(surveyId)
                .orElseThrow()
        );
    }

    /**
     * Test that recent surveys can be found
     */
    @Test
    void shouldFindRecentSurveys() {
        Person person1 = personRepository.save(Objects.requireNonNull(
            Person.builder()
            .lastname("Stone")
            .firstname("Alice")
            .username("aliceStone")
            .email("alice.stone@exemple.com")
            .password("password1")
            .role(Role.MEMBER)
            .build()));
        Person person2 = personRepository.save(Objects.requireNonNull(
            Person.builder()
            .lastname("Smith")
            .firstname("Bob")
            .username("bobSmith")
            .email("bob.smith@exemple.com")
            .password("password2")
            .role(Role.MEMBER)
            .build()));

        Survey survey1 = Survey.builder()
            .question("Favorite color ?")
            .scheduledEnd(LocalDate.now().plusMonths(1))
            .multiplicity(true)
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

        List<Survey> surveys = List.of(
            repository.save(Objects.requireNonNull(survey1)),
            repository.save(Objects.requireNonNull(survey2)),
            repository.save(Objects.requireNonNull(survey3))
        );

        Choice choice1 = Choice.builder()
            .title("choix1")
            .persons(Set.of(person1))
            .survey(surveys.getFirst())
            .build();
        Choice choice2 = Choice.builder()
            .title("choix1")
            .persons(Set.of(person1, person2))
            .survey(surveys.getFirst())
            .build();
        choiceRepository.save(Objects.requireNonNull(choice1));
        choiceRepository.save(Objects.requireNonNull(choice2));

        List<Survey> recentSurveys = repository.findRecent(LocalDate.now().minusMonths(1));

        assertFalse(recentSurveys.isEmpty());
        assertEquals(2,recentSurveys.size());
        assertEquals(recentSurveys.getFirst(), surveys.getFirst());
        assertEquals(recentSurveys.getLast(), surveys.get(1));
    }

    /**
     * Test that old surveys can be found
     */
    @Test
    void shouldFindOldSurveys() {
        Person person1 = personRepository.save(Objects.requireNonNull(
            Person.builder()
            .lastname("Stone")
            .firstname("Alice")
            .username("aliceStone")
            .email("alice.stone@exemple.com")
            .password("password1")
            .role(Role.MEMBER)
            .build()));
        Person person2 = personRepository.save(Objects.requireNonNull(
            Person.builder()
            .lastname("Smith")
            .firstname("Bob")
            .username("bobSmith")
            .email("bob.smith@exemple.com")
            .password("password2")
            .role(Role.MEMBER)
            .build()));

        Survey survey1 = Survey.builder()
            .question("Favorite color ?")
            .scheduledEnd(LocalDate.now().minusDays(35))
            .multiplicity(true)
            .build();
        Survey survey2 = Survey.builder()
            .question("Favorite pet ?")
            .scheduledEnd(LocalDate.now().minusDays(40))
            .multiplicity(false)
            .build();
        Survey survey3 = Survey.builder()
            .question("Favorite town ?")
            .scheduledEnd(LocalDate.now().minusDays(1))
            .multiplicity(false)
            .build();

        List<Survey> surveys = List.of(
            repository.save(Objects.requireNonNull(survey1)),
            repository.save(Objects.requireNonNull(survey2)),
            repository.save(Objects.requireNonNull(survey3))
        );

        Choice choice1 = Choice.builder()
            .title("choix1")
            .persons(Set.of(person1))
            .survey(surveys.getFirst())
            .build();
        Choice choice2 = Choice.builder()
            .title("choix1")
            .persons(Set.of(person1, person2))
            .survey(surveys.getFirst())
            .build();
        choiceRepository.save(Objects.requireNonNull(choice1));
        choiceRepository.save(Objects.requireNonNull(choice2));

        Page<Survey> surveyPage = repository.findByScheduledEndBeforeOrderByScheduledEndDesc(
            LocalDate.now().minusMonths(1), PageRequest.of(0,5)
        );
        assertNotNull(surveyPage);
        assertFalse(surveyPage.hasNext());
        List<Survey> oldSurveys = surveyPage.getContent();
        assertFalse(oldSurveys.isEmpty());
        assertEquals(2,oldSurveys.size());
        assertEquals(oldSurveys.getFirst().getId(), surveys.getFirst().getId());
        assertEquals(oldSurveys.getLast().getId(), surveys.get(1).getId());
    }
}
