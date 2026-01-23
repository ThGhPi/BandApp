package com.thghpi.bandapp.band_api.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Data
@Entity
@Builder
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false, unique = true)
    private String name;

    @Column(length = 5, nullable = false)
    private String postcode;

    @OneToMany(mappedBy = "city")
    @Builder.Default
    private List<Place> places = new ArrayList<>();
}
