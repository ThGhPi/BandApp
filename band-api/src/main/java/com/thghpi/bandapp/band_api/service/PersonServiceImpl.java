package com.thghpi.bandapp.band_api.service;
import com.thghpi.bandapp.band_api.entity.Group;
import com.thghpi.bandapp.band_api.entity.Person;
import com.thghpi.bandapp.band_api.dto.PersonDto;
import com.thghpi.bandapp.band_api.repository.GroupRepository;
import com.thghpi.bandapp.band_api.repository.PersonRepository;
import com.thghpi.bandapp.band_api.service.mapper.PersonMapper;
import com.thghpi.bandapp.band_api.service.exception.BadCUMessage;
import com.thghpi.bandapp.band_api.service.exception.BadCUException;
import com.thghpi.bandapp.band_api.service.exception.NotFoundMessage;
import com.thghpi.bandapp.band_api.service.exception.NotFoundException;

import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service implementation for managing Person entities. 
 * This class provides methods to perform CRUD operations on Person entities,
 * including retrieving, updating, and deleting persons.
 * It uses a PersonMapper to convert between Person and PersonDto ,
 * and interacts with the PersonRepository and GroupRepository for database operations.
 * PersonServiceImpl implements the PersonService interface, ensuring that all required methods are provided.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {
    /** The PersonMapper instance to be injected */
    private final PersonMapper mapper;
    /** The PersonRepository instance to be injected */
    private final PersonRepository repository;
    /** The GroupRepository instance to be injected */
    private final GroupRepository groupRepository;

    // CREATE methods are not needed for this entity as it needs authentication management
    // and is handled by the AuthenticationServiceImpl.

    // READ methods

    /**
     * Returns the PersonDto of the person with the given id.
     * The method takes a Long id as input,
     * retrieves the corresponding Person entity from the repository,
     * converts it to a PersonDto using the mapper and returns it.
     * @param id the id of the person to retrieve
     * @return the PersonDto of the person with the given id if found
     * @throws NotFoundException if the id is not found in database
     */
    @Override
    public PersonDto getById(@NonNull Long id) {
        return mapper.toDto(
            repository.findById(id)
                .orElseThrow(() -> new NotFoundException(new NotFoundMessage(id, Person.class)))
            );
    }

    /**
     * Returns a list of PersonDto for all the persons in the database.
     * The method retrieves all Person entities from the repository,
     * converts them to PersonDto using the mapper and returns the list.
     * @return a list of PersonDto for all the persons in the database
     */
    @Override
    public List<PersonDto> getAll() {
        return repository.findAll()
            .stream()
            .map(mapper::toDto)
            .toList();
    }

    /**
     * Returns a list of PersonDto for all the persons in the given group.
     * The method retrieves all Person entities from the repository that belong to the specified group,
     * converts them to PersonDto using the mapper and returns the list.
     * @param groupId the id of the group for which to retrieve persons
     * @return a list of PersonDto for all the persons in the given group
     * @throws NotFoundException if when the groupId is not found in database
     */
    @Override
    public List<PersonDto> getByGroupId(@NonNull Long groupId) {
        Group group = groupRepository.findById(groupId)
            .orElseThrow(() -> new NotFoundException(new NotFoundMessage(
                groupId, Group.class
            )));
        return repository.findByGroups(group)
            .stream()
            .map(mapper::toDto)
            .toList();
    }


    // UPDATE methods

    /**
     * Updates the list of persons in the database using the given list of PersonDto.
     * The method takes a list of PersonDto as input,
     * checks if each PersonDto has a valid id and exists in the database,
     * converts them to Person entities using the mapper,
     * saves them to the repository and returns the updated list of PersonDto.
     * @param personDtos the list of PersonDto with updates
     * @return the list of updated PersonDto if the update process is successful
     */
    @Override
    public List<PersonDto> updateMany(List<PersonDto> personDtos) {
        checkIdsForUpdate(personDtos);
        return repository.saveAll(
            Objects.requireNonNull(
            personDtos.stream()
                .map(mapper::toEntity)
                .toList()
        )).stream()
            .map(mapper::toDto)
            .toList();
    }


    // DELETE methods

    /**
     * Deletes the person with the given id from the database.
     * @param id the id of the person to delete
     */
    @Override
    public void deleteOne(@NonNull Long id) {
        repository.deleteById(id);
    }

    /**
     * Deletes the persons using the given list of PersonDto from the database.
     * The method takes a list of PersonDto as input,
     * converts them to Person entities using the mapper,
     * and deletes them from the repository.
     * @param personDtos the list of PersonDto to delete
     * @return a no content response if the deletion process is successful, otherwise an error response
     */
    @Override
    public void deleteMany(List<PersonDto> personDtos) {
        repository.deleteAll(Objects.requireNonNull(
            personDtos.stream()
                .map(mapper::toEntity)
                .toList()
        ));
    }

    /**
     * A method to check the validity of a list of personDtos passed for update
     * @throws BadCUException when encountering a person without id in the list or if one or several ids can't be found in database
     */
    @Override
    public void checkIdsForUpdate(List<PersonDto> personDtos) {
        for (PersonDto personDto : personDtos) {
            if (personDto.getId() == null) {
                throw new BadCUException(new BadCUMessage(
                    false, Person.class, "with null ID",
                    null, null
                ));
            }
        }
        List<Long> ids = personDtos.stream()
            .map(PersonDto::getId)
            .filter(id -> !repository.existsById(Objects.requireNonNull(id)))
            .toList();
        if (!ids.isEmpty()) {
            throw new BadCUException(new BadCUMessage(
                false, Person.class, "with invalid IDs",
                ids, "don't exist in database"
            ));
        }
    }
}
