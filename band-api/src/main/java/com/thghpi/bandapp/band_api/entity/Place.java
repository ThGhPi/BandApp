package com.thghpi.bandapp.band_api.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Entity
@Builder
@AllArgsConstructor
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String name;

    @Column(length = 255, nullable = false)
    private String address;

    @Column(name = "address_details", length = 255)
    private String addressDetails;
    
    @Column(nullable = false)
    private Double latitude;
    
    @Column(nullable = false)
    private Double longitude;

    @Column
    private Long capacity;

    @OneToMany(mappedBy = "address")
    @Builder.Default
    private List<Person> persons = new ArrayList<Person>();

    @OneToMany(mappedBy = "place")
    @Builder.Default
    private List<Event> events = new ArrayList<Event>();

    @OneToMany(mappedBy = "organisation")
    @Builder.Default
    private List<Organisation> organisations = new ArrayList<Organisation>();

    @ManyToOne
    @JoinColumn(name = "photo_id")
    private FileInfo fileInfo;

    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private PlaceType placeType;
}
