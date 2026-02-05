package com.thghpi.bandapp.band_api.entity.product_key;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class ProgrammePK implements java.io.Serializable {
    @Column(name = "event_id")
    private Long eventId;

    @Column(name = "piece_id")
    private Long pieceId;
}
