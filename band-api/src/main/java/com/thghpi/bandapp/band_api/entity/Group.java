package com.thghpi.bandapp.band_api.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.thghpi.bandapp.band_api.entity.enumeration.GroupType;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@Entity(name = "work_group")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(name = "creation_date", nullable = false)
    private LocalDate creationDate;

    @Column(name = "group_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private GroupType groupType;

    @Column(length = 50)
    private String goal;

    @Column(length = 255)
    private String details;
    
    @Column(name = "scheduled_end")
    private LocalDate scheduledEnd;

    @OneToMany(mappedBy = "section")
    @Builder.Default
    private List<Score> scores = new ArrayList<Score>();

    @OneToMany(mappedBy = "group")
    @Builder.Default
    private List<Event> events = new ArrayList<Event>();

    @ManyToMany(mappedBy = "groups")
    @Builder.Default
    private List<Person> persons = new ArrayList<Person>();
}
