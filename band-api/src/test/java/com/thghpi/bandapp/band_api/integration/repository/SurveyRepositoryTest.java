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
import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * 
 */
public class SurveyRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    ChoiceRepository choiceRepository;

    @Autowired
    private SurveyRepository repository;

    @BeforeEach
    void cleanDatabase() {
        choiceRepository.deleteAll();
        repository.deleteAll();
        personRepository.deleteAll();
    }

    @Test
    void shouldSaveSurvey() {
        Survey survey = Survey.builder()
            .question("Question ?")
            .scheduledEnd(LocalDate.now().plusMonths(1))
            .multiplicity(false)
            .build();
        
        Survey saved = repository.save(survey);

        assertNotNull(saved.getId());
    }

    @Test
    void shouldFindSurveyById() {
        Survey survey = Survey.builder()
            .question("Question ?")
            .scheduledEnd(LocalDate.now().plusMonths(1))
            .multiplicity(false)
            .build();
        
        Survey saved = repository.save(survey);
        Long surveyId = saved.getId();

        assertEquals(
            saved,
            repository.findById(surveyId)
                .orElseThrow()
        );
    }

    @Test
    void shouldFindRecentSurveys() {
        Person person1 = personRepository.save(Person.builder()
            .lastname("Stone")
            .firstname("Alice")
            .username("aliceStone")
            .email("alice.stone@exemple.com")
            .password("password1")
            .role(Role.MEMBER)
            .build());
        Person person2 = personRepository.save(Person.builder()
            .lastname("Smith")
            .firstname("Bob")
            .username("bobSmith")
            .email("bob.smith@exemple.com")
            .password("password2")
            .role(Role.MEMBER)
            .build());

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
            repository.save(survey1),
            repository.save(survey2),
            repository.save(survey3)
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
        choiceRepository.save(choice1);
        choiceRepository.save(choice2);

        List<Survey> recentSurveys = repository.findRecent(LocalDate.now().minusMonths(1));

        assertFalse(recentSurveys.isEmpty());
        assertEquals(2,recentSurveys.size());
        assertEquals(recentSurveys.getFirst(), surveys.getFirst());
        assertEquals(recentSurveys.getLast(), surveys.get(1));
    }
}
