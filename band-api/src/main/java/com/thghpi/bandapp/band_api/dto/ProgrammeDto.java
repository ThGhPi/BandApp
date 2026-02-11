package com.thghpi.bandapp.band_api.dto;
import com.thghpi.bandapp.band_api.entity.product_key.ProgrammePK;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProgrammeDto {
    private ProgrammePK id;
    private Integer runningOrder;
    private EventDto event;
    private PieceDto piece;
}
