package com.thghpi.bandapp.band_api.repository;
import com.thghpi.bandapp.band_api.entity.Group;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {

}
