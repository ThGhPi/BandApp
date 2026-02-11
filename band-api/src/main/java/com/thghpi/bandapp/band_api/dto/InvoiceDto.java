package com.thghpi.bandapp.band_api.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InvoiceDto {
    private Long id;
    private String number;
    private LocalDate issueDate;
    private FileInfoDto pdfInfo;
    private List<InvoiceLineDto> invoiceLines = new ArrayList<InvoiceLineDto>();
    private OrganisationDto organisation;
}
