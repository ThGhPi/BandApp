package com.thghpi.bandapp.band_api.entity;

import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Data 
@Entity
@Builder
public class InvoiceLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(precision = 19, scale = 4, nullable = false)
    private BigDecimal rate;

    @OneToOne
    @JoinColumn(name = "event_id", nullable = false, unique = true)
    private Event event;

    @ManyToOne(optional = false)
    @JoinColumn(name = "invoice_id", nullable = false)
    private Invoice invoice;
}
