package com.thghpi.bandapp.band_api.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrganisationDto {
    private Long id;
    private String name;
    private String siretNumber;
    private String email;
    private List<EventDto> events = new ArrayList<EventDto>();
    private List<InvoiceDto> invoices = new ArrayList<InvoiceDto>();
    private PlaceDto address;
}
