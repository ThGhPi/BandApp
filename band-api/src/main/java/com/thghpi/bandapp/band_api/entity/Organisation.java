package com.thghpi.bandapp.band_api.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Data
@Entity
@Builder
public class Organisation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false, unique = true)
    private String name;

    @Column(name = "siret_number", length = 14, unique = true)
    private String siretNumber;

    @Column(length = 50, unique = true)
    private String email;

    @OneToMany(mappedBy = "organiser")
    @Builder.Default
    private List<Event> events = new ArrayList<Event>();

    @OneToMany(mappedBy = "organisation")
    @Builder.Default
    private List<Invoice> invoices = new ArrayList<Invoice>();

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Place address;
}
