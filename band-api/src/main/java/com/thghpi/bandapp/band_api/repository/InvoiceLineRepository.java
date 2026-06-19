package com.thghpi.bandapp.band_api.repository;
import com.thghpi.bandapp.band_api.entity.InvoiceLine;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceLineRepository extends JpaRepository<InvoiceLine,Long> {

}
