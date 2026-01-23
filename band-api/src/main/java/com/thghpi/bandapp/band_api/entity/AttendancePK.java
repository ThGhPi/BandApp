package com.thghpi.bandapp.band_api.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class AttendancePK implements Serializable {
    @Column(name = "person_id")
    private Long personId;

    @Column(name = "event_id")
    private Long eventId;
}
