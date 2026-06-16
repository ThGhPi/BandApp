package com.thghpi.bandapp.band_api.entity;

import java.time.LocalDate;

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
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate publicationDate;
    
    @Column(length = 25, nullable = false)
    private String part;

    @Column(nullable = false)
    private Double size;

    @Column
    private Long speed;

    @OneToOne
    @JoinColumn(nullable = false, name = "file_info_id")
    private FileInfo fileInfo;

    @ManyToOne
    @JoinColumn(name = "piece_id")
    private Piece piece;

    @ManyToOne
    @JoinColumn(name = "section_id")
    private Group section;
}
