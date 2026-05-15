package com.thghpi.bandapp.band_api.controller;
import com.thghpi.bandapp.band_api.dto.PersonDto;
import com.thghpi.bandapp.band_api.service.PersonServiceImpl;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequiredArgsConstructor
@RequestMapping("/band-api/person")
public class PersonController {
    private final PersonServiceImpl service;

    /**
     * For endpoint band-api/person/{id} GET request, returns the PersonDto of the person with the given id.
     * @param id the id of the person to retrieve, taken from the path variable of the request.
     * @return the PersonDto of the person with the given id if successful, otherwise an error response.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PersonDto> getPerson(@PathVariable Long id) {
        PersonDto personDto = service.getById(id);
        return ResponseEntity.ok(personDto);
    }

    /**
     * For endpoint band-api/person/all GET request, returns the list of all persons in the database.
     * @return a list of PersonDto for all the persons in the database.
     */
    @GetMapping("/all")
    public ResponseEntity<List<PersonDto>> getAllPersons() {
        List<PersonDto> personDtos = service.getAll();
        return ResponseEntity.ok(personDtos);
    }

    /**
     * For endpoint band-api/person/group/{groupId} GET request, returns the list of all persons in the database with the given group id.
     * @param groupId the id of the group to retrieve the persons from, taken from the path variable of the request.
     * @return a list of PersonDto for all the persons in the database with the given group id.
     */
    @GetMapping("/group/{groupId}")
    public ResponseEntity<List<PersonDto>> getByGroup(@PathVariable Long groupId) {
        List<PersonDto> personDtos = service.getByGroupId(groupId);
        return ResponseEntity.ok(personDtos);
    }

    /**
     * For PUT request on endpoint band-api/persons, updates the list of persons in the database with the given list of PersonDto.
     * @param personsToUpdate the list of PersonDto with updates, taken from the body of the request.
     * @return the list of updated PersonDto if the update process is successful, otherwise an error response.
     */
    @PutMapping("s")
    public ResponseEntity<List<PersonDto>> updatePersons(@RequestBody List<PersonDto> personsToUpdate) {
        List<PersonDto> personDtos = service.updateMany(personsToUpdate);
        return ResponseEntity.ok(personDtos);
    }

    /**
     * For DELETE request on endpoint band-api/person/{id},
     * deletes the person with the given id from the database.
     * @param id the id of the person to delete, taken from the path variable of the request.
     * @return a no content response if the deletion process is successful, otherwise an error response.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable Long id) {
        service.deleteOne(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("s")
    public ResponseEntity<Void> deletePersons(@RequestBody List<PersonDto> personDtos) {
        service.deleteMany(personDtos);
        return ResponseEntity.noContent().build();
    }
}
