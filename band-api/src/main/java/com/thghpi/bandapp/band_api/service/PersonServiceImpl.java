package com.thghpi.bandapp.band_api.service;
import com.thghpi.bandapp.band_api.dto.PersonDto;
import com.thghpi.bandapp.band_api.repository.PersonRepository;
import com.thghpi.bandapp.band_api.service.mapper.PersonMapper;

import java.util.List;
import java.util.Objects;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {
    private final PersonMapper mapper;
    private final PersonRepository repository;

    // CREATE methods are not needed for this entity as it needs authentication management and is handled by the AuthenticationServiceImpl.

    // READ methods

    /**
     * Returns the PersonDto of the person with the given id.
     * The method takes a Long id as input,
     * retrieves the corresponding Person entity from the repository,
     * converts it to a PersonDto using the mapper and returns it.
     * @param id the id of the person to retrieve
     * @return the PersonDto of the person with the given id if found, otherwise throws an exception
     */
    @Override
    public PersonDto getById(@NonNull Long id) {
        return mapper.toDto(
            repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Person not found"))
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
     */
    @Override
    public List<PersonDto> getByGroupId(Long groupId) {
        return repository.findByGroupId(groupId)
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
     * @return the list of updated PersonDto if the update process is successful, otherwise an error response
     */
    @Override
    public List<PersonDto> updateMany(List<PersonDto> personDtos) {
        for (PersonDto personDto : personDtos) {
            if (personDto.getId() == null) {
                throw new IllegalArgumentException("Person ID must not be null for update");
            } else if (!repository.existsById(
                Objects.requireNonNull(personDto.getId())
            )) {
                throw new IllegalArgumentException("Person with ID " + personDto.getId() + " not found in database");
            }
        }
        return repository.saveAll(
            personDtos.stream()
                .map(mapper::toEntity)
                .toList()
        ).stream()
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
        repository.deleteAll(
            personDtos.stream()
                .map(mapper::toEntity)
                .toList()
        );
    }
}
