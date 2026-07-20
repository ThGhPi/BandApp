package com.thghpi.bandapp.band_api.unit.service;
import com.thghpi.bandapp.band_api.dto.PersonDto;
import com.thghpi.bandapp.band_api.dto.InstrumentDto;
import com.thghpi.bandapp.band_api.repository.GroupRepository;
import com.thghpi.bandapp.band_api.repository.PersonRepository;
import com.thghpi.bandapp.band_api.service.mapper.PersonMapper;
import com.thghpi.bandapp.band_api.service.PersonServiceImpl;
import com.thghpi.bandapp.band_api.service.exception.BadCUException;

import java.util.List;
import java.util.ArrayList;

import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * Unit tests for the {@link PersonServiceImpl} class, focusing on the validation of person ID logic.
 * These tests ensure that the service correctly identifies invalid person IDs during update operations
 * and that it properly handles attempts to update persons with null or non-existing IDs.
 * PersonServiceTest
 */
@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {
    /** The PersonMapper instance to be mocked */
    @Mock
    private PersonMapper mapper;
    /** The PersonRepository instance to be mocked */
    @Mock
    private PersonRepository repository;
    /** The GroupRepository instance to be mocked */
    @Mock
    private GroupRepository groupRepository;
    /** The PersonServiceImpl instance to be tested */
    @InjectMocks
    private PersonServiceImpl service;
    /** A list of PersonDto instances for testing */
    private List<PersonDto> personDtoList;

    /**
     * Sets up the test environment before each test method is executed.
     * Initializes a list of PersonDto instances with various attributes for testing.
     */
    @BeforeEach
    void setUp() {
        personDtoList = List.of();
    }

    /**
     * Tests that the service correctly rejects a list of PersonDto instances with lacking IDs.
     */
    @Test
    void shouldRejectPersonDtoListWithLackingIds() {
        PersonDto personDto1 = new PersonDto(
            null, "John", "Doe",
            "johndoe", "john.doe@example.com",
            null, null, null, null
        );
        PersonDto personDto2 = new PersonDto(
            null, "Jane", "Smith", "janesmith",
            "jane.smith@example.com", null,
            null, null, null
        );
        PersonDto personDto3 = new PersonDto(
            null, "Jack", "Yang",
             "jackyang", "jack.yang@example.com",
            null, null,
            null, new ArrayList<InstrumentDto>()
        );
        personDtoList = List.of(personDto1, personDto2, personDto3);

        BadCUException thrown = assertThrows(
            BadCUException.class,
            () -> service.checkIdsForUpdate(personDtoList)
        );
        assertEquals("Can't update Person with null ID.", thrown.getMessage());
    }

    /**
     * Tests that the service correctly rejects a list of PersonDto instances with non-existing IDs.
     */
    @Test
    void shouldRejectPersonDtoListWithNonExistingIds() {
        PersonDto personDto1 = new PersonDto(
            1L, "John", "Doe",
            "johndoe", "john.doe@example.com",
            null, null, null, null
        );
        PersonDto personDto2 = new PersonDto(
            2L, "Jane", "Smith", "janesmith",
            "jane.smith@example.com", null,
            null, null, null
        );
        PersonDto personDto3 = new PersonDto(
            3L, "Jack", "Yang",
             "jackyang", "jack.yang@example.com",
            null, null,
            null, new ArrayList<InstrumentDto>()
        );
        personDtoList = List.of(personDto1, personDto2, personDto3);
        when(repository.existsById(1L))
            .thenReturn(false);
        when(repository.existsById(2L))
            .thenReturn(false);
        when(repository.existsById(3L))
            .thenReturn(false);

        BadCUException thrown = assertThrows(
            BadCUException.class,
            () -> service.checkIdsForUpdate(personDtoList)
        );
        assertEquals(
            "Can't update Person with invalid IDs : Person with IDs [1, 2, 3] don't exist in database.",
            thrown.getMessage()
        );
    }
    
    /**
     * Tests that the service correctly accepts a list of PersonDto instances with valid IDs.
    */
   @Test
   void shouldAcceptValidPersonDtoList() {       
        PersonDto personDto1 = new PersonDto(
            1L, "John", "Doe",
            "johndoe", "john.doe@example.com",
            null, null, null, null
        );
        PersonDto personDto2 = new PersonDto(
            2L, "Jane", "Smith", "janesmith",
            "jane.smith@example.com", null,
            null, null, null
        );
        PersonDto personDto3 = new PersonDto(
            3L, "Jack", "Yang",
             "jackyang", "jack.yang@example.com",
            null, null,
            null, new ArrayList<InstrumentDto>()
        );
        personDtoList = List.of(personDto1, personDto2, personDto3);
       when(repository.existsById(1L))
       .thenReturn(true);
        when(repository.existsById(2L))
            .thenReturn(true);
        when(repository.existsById(3L))
            .thenReturn(true);
        assertDoesNotThrow(() -> service.checkIdsForUpdate(personDtoList));
    }
}
