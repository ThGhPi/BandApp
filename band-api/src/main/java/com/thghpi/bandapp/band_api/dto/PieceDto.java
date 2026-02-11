package com.thghpi.bandapp.band_api.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PieceDto {
    private Long id;
    private String title;
    private String composer;
    private String number;
    private List<ScoreDto> scores = new ArrayList<ScoreDto>();
    private GroupDto section;
}
