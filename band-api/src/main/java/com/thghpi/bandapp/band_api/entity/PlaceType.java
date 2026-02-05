package com.thghpi.bandapp.band_api.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "place_type")
public class PlaceType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false, unique = true)
    private String name;
}
