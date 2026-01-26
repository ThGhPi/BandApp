package com.thghpi.bandapp.band_api.entity;

import java.util.ArrayList;
import java.util.List;

import com.thghpi.bandapp.band_api.entity.enumeration.Key;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Data
@Entity
@Builder
public class Instrument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String name;
    
    @Column(nullable = false)
    private Key key;
    
    @ManyToMany
    @JoinTable(name = "player")
    @Builder.Default
    private List<Person> persons = new ArrayList<Person>();
}
