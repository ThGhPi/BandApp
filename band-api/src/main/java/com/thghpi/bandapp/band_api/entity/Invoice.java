package com.thghpi.bandapp.band_api.entity;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Data 
@Entity
@Builder
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 6, nullable = false, unique = true)
    private String number;

    @Column(name = "issue_date", nullable = false)
    private LocalDate issueDate;

    @OneToOne(optional = false)
    @JoinColumn(name = "file_id", nullable = false, unique = true)
    private FileInfo fileInfo;

    @ManyToOne(optional = false)
    @JoinColumn(name = "organisation_id", nullable = false)
    private Organisation organisation;
}
