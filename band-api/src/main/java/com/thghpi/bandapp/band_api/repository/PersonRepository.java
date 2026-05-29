package com.thghpi.bandapp.band_api.repository;
import com.thghpi.bandapp.band_api.entity.Group;
import com.thghpi.bandapp.band_api.entity.Person;

import java.util.Optional;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface PersonRepository extends JpaRepository<Person,Long> {

    Optional<Person> findByUsername(String username); // for authentication

    Optional<Person> findByGroups(Group group);
}
