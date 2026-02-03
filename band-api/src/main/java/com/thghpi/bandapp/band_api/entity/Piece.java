package com.thghpi.bandapp.band_api.entity;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Entity
@Builder
@AllArgsConstructor
public class Piece {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 25, nullable = false)
    private String title;

    @Column(nullable = false)
    private String composer;

    @Column(nullable = false)
    private String number;

    @OneToMany(mappedBy = "piece")
    @Builder.Default
    private List<Score> scores = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "section_id")
    private Group section;
}