package com.thghpi.bandapp.band_api.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Data
@Entity
@Builder
public class FileInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_url", length = 255, nullable = false)
    private String fileUrl;

    @Column(name = "file_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private FileType fileType;

    @OneToMany(mappedBy = "fileInfo")
    @Builder.Default
    private List<Place> places = new ArrayList<Place>();

    @ManyToOne
    @JoinColumn(name = "section_id")
    private Group section;
}
