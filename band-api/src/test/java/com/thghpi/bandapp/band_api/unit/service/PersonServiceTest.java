package com.thghpi.bandapp.band_api.unit.service;
import com.thghpi.bandapp.band_api.dto.GroupDto;
import com.thghpi.bandapp.band_api.dto.PersonDto;
import com.thghpi.bandapp.band_api.dto.InstrumentDto;
import com.thghpi.bandapp.band_api.entity.enumeration.Role;
import com.thghpi.bandapp.band_api.repository.GroupRepository;
import com.thghpi.bandapp.band_api.repository.PersonRepository;
import com.thghpi.bandapp.band_api.service.mapper.PersonMapper;
import com.thghpi.bandapp.band_api.service.PersonServiceImpl;
import com.thghpi.bandapp.band_api.service.exception.BadCUException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

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
        PersonDto personDto1 = new PersonDto(
            null, "John", "Doe",
            "john.doe@example.com", "password123",
            null, Role.MEMBER, null,
            null, null, null,
            null, null
        );
        PersonDto personDto2 = new PersonDto(
            null, "Jane", "Smith",
            "jane.smith@example.com", "password456",
            null, Role.MEMBER, null,
            null, null, null,
            null, null
        );
        PersonDto personDto3 = new PersonDto(
            null, "Jack", "Yang",
            "jack.yang@example.com", "password789",
            null, Role.MEMBER, null,
            null, null, new ArrayList<GroupDto>(),
            new ArrayList<InstrumentDto>(), new HashSet<Long>()
        );
        personDtoList = List.of(personDto1, personDto2, personDto3);
    }

    /**
     * Tests that the service correctly rejects a list of PersonDto instances with lacking IDs.
     */
    @Test
    void shouldRejectPersonDtoListWithLackingIds() {
        for (PersonDto personDto : personDtoList) {
            personDto.setId(null);
        }

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
        when(repository.existsById(1L))
            .thenReturn(false);
        when(repository.existsById(2L))
            .thenReturn(false);
        when(repository.existsById(3L))
            .thenReturn(false);
        for (int i = 0; i < 3; i++) {
            personDtoList.get(i).setId((long) i + 1);
        }
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
       for (int i = 0; i < 3; i++) {
           personDtoList.get(i).setId((long) i + 1);
       }
       when(repository.existsById(1L))
       .thenReturn(true);
        when(repository.existsById(2L))
            .thenReturn(true);
        when(repository.existsById(3L))
            .thenReturn(true);
        assertDoesNotThrow(() -> service.checkIdsForUpdate(personDtoList));
    }
}
