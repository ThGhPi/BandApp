package com.thghpi.bandapp.band_api.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ScoreDto {
    private Long id;
    private LocalDate publicationDate;
    private String part;
    private Double size;
    private Long speed;
    private FileInfoDto fileInfo;
    private PieceDto piece;
    private GroupDto section;
}
