package com.thghpi.bandapp.band_api.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Entity
@Builder
@AllArgsConstructor
public class Choice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String title;
    
    @Column(length = 255)
    private String complement;
    
    @Column(length = 255)
    private String url;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "survey_id", nullable = false)
    private Survey survey;

    @ManyToMany
    @JoinTable(
        name = "answer",
        joinColumns = @JoinColumn(name = "choice_id"),
        inverseJoinColumns = @JoinColumn(name = "person_id")
        )
    @Builder.Default
    private List<Person> persons = new ArrayList<Person>();
}
