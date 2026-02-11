package com.thghpi.bandapp.band_api.entity;

import java.util.ArrayList;
import java.util.List;

import com.thghpi.bandapp.band_api.entity.enumeration.Role;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Entity
@Builder
@AllArgsConstructor
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String lastname;

    @Column(length = 50, nullable = false)
    private String firstname;

    @Column(length = 50, nullable = false, unique = true)
    private String username;

    @Column(length = 50, nullable = false, unique = true)
    private String email;

    @Column(length = 255, nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(name = "phone_number", length = 12, nullable = false)
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Place address;

    @ManyToMany
    @JoinTable(
        name = "participation",
        joinColumns = @JoinColumn(name = "person_id"),
        inverseJoinColumns = @JoinColumn(name = "group_id")
        )
    @Builder.Default
    private List<Group> groups = new ArrayList<Group>();

    @ManyToMany
    @JoinTable(
        name = "player",
        joinColumns = @JoinColumn(name = "person_id"),
        inverseJoinColumns = @JoinColumn(name = "instrument_id")
        )
    @Builder.Default
    private List<Instrument> instruments = new ArrayList<Instrument>();

    @ManyToMany
    @JoinTable(
        name = "answer",
        joinColumns = @JoinColumn(name = "person_id"),
        inverseJoinColumns = @JoinColumn(name = "choice_id")
        )
    @Builder.Default
    private List<Choice> choices = new ArrayList<Choice>();
}
