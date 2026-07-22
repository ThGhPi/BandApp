package com.thghpi.bandapp.band_api.unit.service;
import com.thghpi.bandapp.band_api.dto.PersonRoleDto;
import com.thghpi.bandapp.band_api.entity.Person;
import com.thghpi.bandapp.band_api.entity.enumeration.Role;
import com.thghpi.bandapp.band_api.repository.GroupRepository;
import com.thghpi.bandapp.band_api.repository.PersonRepository;
import com.thghpi.bandapp.band_api.service.mapper.PersonMapper;
import com.thghpi.bandapp.band_api.service.PersonServiceImpl;
import com.thghpi.bandapp.band_api.service.exception.BadCUException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

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
    private List<PersonRoleDto> personDtoList;
    /** A list of PersonDto instances for testing */
    private List<Person> personList;

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
        setDtoList(List.of());
        BadCUException thrown = assertThrows(
            BadCUException.class,
            () -> service.checkIdsForUpdate(personDtoList)
        );
        assertEquals("Can't update Person with null ID.", thrown.getMessage());
    }

    /**
     * Tests that the service correctly rejects
     * a list of PersonDto instances with non-existing IDs.
     */
    @Test
    void shouldRejectPersonDtoListWithNonExistingIds() {
        setDtoList(List.of(1L, 2L, 3L));
        when(repository.findAllById(Objects.requireNonNull(Set.of(1L, 2L, 3L))))
            .thenReturn(List.of());

        BadCUException thrown = assertThrows(
            BadCUException.class,
            () -> service.updateMany(personDtoList)
        );
        assertEquals(
            "Can't update Person with invalid IDs : Person with IDs [1, 2, 3] don't exist in database.",
            thrown.getMessage()
        );
    }
    
    /**
     * Tests that the service correctly accepts
     * a list of PersonDto instances with valid IDs.
    */
   @Test
   void shouldAcceptValidPersonDtoList() {       
        setDtoList(List.of(1L, 2L, 3L));
        setPersonList();
        when(repository.findAllById(Objects.requireNonNull(Set.of(1L, 2L, 3L))))
            .thenReturn(personList);
        for (int i = 0; i < 3; i++) {
            when(mapper.toEntity(personDtoList.get(i)))
                .thenReturn(personList.get(i));
            when(mapper.toRoleDto(personList.get(i)))
                .thenReturn(personDtoList.get(i));
        }
        when(repository.saveAll(Objects.requireNonNull(personList)))
            .thenReturn(personList);

        assertDoesNotThrow(() -> service.updateMany(personDtoList));
    }

    /**
     * method to set personDtoList field using specified ids before using it for testing
     * @param ids the list of Long ids to set in the dtos
     */
    private void setDtoList(List<Long> ids) {
        PersonRoleDto personDto1 = new PersonRoleDto(
            ids.size() == 0 ? null : ids.get(0),
            "John", "Doe", "johndoe",
            "john.doe@example.com", Role.ADMIN, null, null
        );
        PersonRoleDto personDto2 = new PersonRoleDto(
            ids.size() == 0 ? null : ids.get(1),
            "Jane", "Smith", "janesmith",
            "jane.smith@example.com", Role.ARR, null, null
        );
        PersonRoleDto personDto3 = new PersonRoleDto(
            ids.size() == 0 ? null : ids.get(2),
            "Jack", "Yang", "jackyang",
            "jack.yang@example.com", Role.ORG, null, null
        );
        personDtoList = List.of(personDto1, personDto2, personDto3);
    }

    /**
     * method to set personList field using personDtoList before using it for testing
     */
    private void setPersonList() {
        if (personList == null) {
            personList = new ArrayList<>();
        }
        personList.clear();
        personDtoList.forEach(personDto -> {
            personList.add(
            Person.builder()
                .id(personDto.id())
                .lastname(personDto.lastname())
                .firstname(personDto.firstname())
                .username(personDto.username())
                .email(personDto.email())
                .role(personDto.role())
                .build()
            );
        });
    }
}
