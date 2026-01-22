package com.thghpi.bandapp.band_api.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Entity(name = "work_group")
@Builder
@AllArgsConstructor
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
    private List<FileInfo> filesInfo = new ArrayList<FileInfo>();
    
    @ManyToMany(mappedBy = "groups")
    @Builder.Default
    private List<Person> persons = new ArrayList<Person>();

    @ManyToMany(mappedBy = "group")
    @Builder.Default
    private List<Choice> choices = new ArrayList<Choice>();
}
