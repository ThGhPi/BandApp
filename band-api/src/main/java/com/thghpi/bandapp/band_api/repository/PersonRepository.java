package com.thghpi.bandapp.band_api.repository;
import com.thghpi.bandapp.band_api.entity.Person;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person,Long> {

}
