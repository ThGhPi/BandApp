package com.thghpi.bandapp.band_api.entity;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Entity
@Builder
@AllArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(name = "event_date", nullable = false)
    private LocalDate date;

    @Column(name = "event_start", nullable = false)
    private LocalTime start;

    @Column(name = "event_end")
    private LocalTime end;

    @Column
    private LocalTime rdv;

    @Column(length = 255)
    private String details;

    @ManyToOne
    @JoinColumn(name = "place_id", nullable = false)
    private Place place;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;
}
