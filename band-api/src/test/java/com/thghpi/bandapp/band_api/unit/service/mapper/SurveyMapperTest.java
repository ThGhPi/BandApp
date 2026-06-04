package com.thghpi.bandapp.band_api.unit.service.mapper;
import com.thghpi.bandapp.band_api.entity.Choice;
import com.thghpi.bandapp.band_api.entity.Survey;
import com.thghpi.bandapp.band_api.service.mapper.SurveyMapper;


import java.util.List;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

/**
 * Unit Tests for the SurveyMapper class, which maps between Survey entities and SurveyDto objects.
 * Tests the mapping of the closed and totalVotes fields, as well as the mapping of choices using the ChoiceMapper.
 */
public class SurveyMapperTest {
    private final SurveyMapper mapper = Mappers.getMapper(SurveyMapper.class);

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
                List.of()
            );
        survey1.setChoices(List.of(
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
        assert(mapper.toDto(survey1).getClosed() == false);
        assert(mapper.toDto(survey1).getTotalVotes() == 0L);
        Survey survey2 = new Survey(
                2L,
                "Question ?",
                LocalDate.now().minusDays(1),
                true,
                List.of()
            );
        assert(mapper.toDto(survey2).getClosed() == true);
        assert(mapper.toDto(survey2).getTotalVotes() == 0L);
        assert(mapper.toDto(survey1).getChoices().size() == 2);
    }
}
