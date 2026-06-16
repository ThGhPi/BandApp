package com.thghpi.bandapp.band_api.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.ArrayList;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @OneToMany(mappedBy = "event")
    @Builder.Default
    private List<Programme> programme = new ArrayList<Programme>();

    @ManyToOne(optional = false)
    @JoinColumn(name = "type_id")
    private EventType type;

    @ManyToOne(optional = false)
    @JoinColumn(name = "place_id", nullable = false)
    private Place place;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @ManyToOne(optional = false)
    @JoinColumn(name = "organiser_id", nullable = false)
    private Organisation organiser;
}
