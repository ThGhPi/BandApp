package com.thghpi.bandapp.band_api.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InvoiceLineDto {
    private Long id;
    private BigDecimal rate;
    private EventDto event;
    private InvoiceDto invoice;
}
