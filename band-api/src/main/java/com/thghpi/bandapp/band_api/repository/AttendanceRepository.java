package com.thghpi.bandapp.band_api.repository;
import com.thghpi.bandapp.band_api.entity.Attendance;
import com.thghpi.bandapp.band_api.entity.product_key.AttendancePK;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, AttendancePK> {

}
