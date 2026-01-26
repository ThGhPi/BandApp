package com.thghpi.bandapp.band_api.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class EventType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false, unique = true)
    private String name;
}
