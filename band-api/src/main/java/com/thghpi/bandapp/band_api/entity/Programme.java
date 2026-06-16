package com.thghpi.bandapp.band_api.entity;
import com.thghpi.bandapp.band_api.entity.product_key.ProgrammePK;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Programme {
    @EmbeddedId
    private ProgrammePK id;

    @Column(name = "running_order")
    private Integer runningOrder;

    @ManyToOne
    @MapsId("eventId")
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne
    @MapsId("pieceId")
    @JoinColumn(name = "piece_id")
    private Piece piece;
}
