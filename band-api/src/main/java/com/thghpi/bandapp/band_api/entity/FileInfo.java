package com.thghpi.bandapp.band_api.entity;

import java.util.ArrayList;
import java.util.List;

import com.thghpi.bandapp.band_api.entity.enumeration.FileType;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@Entity(name = "file_info")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FileInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_url", length = 255, nullable = false)
    private String fileUrl;

    @Column(name = "file_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private FileType fileType;

    @OneToMany(mappedBy = "photoInfo")
    @Builder.Default
    private List<Place> places = new ArrayList<Place>();
}
