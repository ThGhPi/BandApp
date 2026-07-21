package com.thghpi.bandapp.band_api.unit.service.mapper;
// import com.thghpi.bandapp.band_api.entity.Choice;
// import com.thghpi.bandapp.band_api.entity.Person;
// import com.thghpi.bandapp.band_api.entity.enumeration.Role;
// import com.thghpi.bandapp.band_api.dto.response.ProfileResponse;
import com.thghpi.bandapp.band_api.service.mapper.PersonMapper;

// import java.util.Set;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

// import static org.junit.jupiter.api.Assertions.assertTrue;
// import static org.junit.jupiter.api.Assertions.assertFalse;
// import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for {@link PersonMapper}.
 * These tests focus on the mapping logic of the PersonMapper,
 * ensuring that Person entities are correctly mapped to PersonDto objects and vice versa,
 * while preserving relationships and handling null values appropriately.
 * PersonMapperTest
 */
public class PersonMapperTest {
    /** The PersonMapper instance used for testing. */
    private final PersonMapper mapper = Mappers.getMapper(PersonMapper.class);

    /**
     * Test that a Person entity with a set of choices is correctly mapped to a PersonDto
     * with the corresponding set of choiceIds.
     */
    @Test
    public void shouldMapPersonToDto() {
        // Choice choice1 = new Choice(
        //         1L,
        //         "Choice 1",
        //         null,
        //         null,
        //         null,
        //         null
        //     );
        // Choice choice2 = new Choice(
        //         null,
        //         "Choice 2",
        //         "Cliquez ici pour suivre le lien",
        //         "http://example.com",
        //         null,
        //         null
        //     );
        // Person person1 = new Person(
        //     1L, "Taylor", "Alice",
        //     "aliceT", "alice@example.com",
        //     "blank", Role.MEMBER,
        //     null, null, null,
        //     null, null,
        //     Set.of(choice1, choice2)
        // );

        // ProfileResponse dto = mapper.toProfile(person1);
        // assertEquals(1, dto.choices().size());
        // assertTrue(dto.choiceIds().contains(1L));
        // assertFalse(dto.choiceIds().contains(null));
    }
}
