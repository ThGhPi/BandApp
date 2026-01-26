package com.thghpi.bandapp.band_api.entity;

import com.thghpi.bandapp.band_api.entity.enumeration.AttendanceChoice;
import com.thghpi.bandapp.band_api.entity.product_key.AttendancePK;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data 
@Entity
@Builder
@NoArgsConstructor 
@AllArgsConstructor 
public class Attendance {
    @EmbeddedId
    private AttendancePK id;

    @Column(nullable = false)
    private AttendanceChoice attendanceChoice;

    @ManyToOne
    @MapsId("personId")
    @JoinColumn(name = "person_id")
    private Person person;

    @ManyToOne
    @MapsId("eventId")
    @JoinColumn(name = "event_id")
    private Event event;
}
