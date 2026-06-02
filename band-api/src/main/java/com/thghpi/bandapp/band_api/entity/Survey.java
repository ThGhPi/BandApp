package com.thghpi.bandapp.band_api.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Représente les sondages.
 */
@Data
@Entity
@Builder
@AllArgsConstructor
public class Survey {
    /**
     * Identifiant du sondage attribué automatiquement à la création.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * La question du sondage.
     */
    @Column(length = 255, nullable = false)
    private String question;

    /**
     * Date de fin du sondage.
     * Le sondage est considéré comme clos
     * à partir du lendemain de cette date.
     */
    @Column(nullable = false, name = "scheduled_end")
    private LocalDate scheduledEnd;

    /**
     * True pour choix multiple, false pour choix unique
     */
    @Column(nullable = false)
    private Boolean multiplicity;

    @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Choice> choices = new ArrayList<Choice>();

    /**
     * Détermine si le sondage est clos
     * @return true si la date de clotûre est passée (stricte)
     */
    public Boolean isClosed() {
        return LocalDate.now().isAfter(scheduledEnd);
    }

    /**
     * Compte le nombre de personnes ayant participés au sondage.
     * @return le nombre de personnes distinctes ayant une relation avec un choix lié au sondage.
     */
    public Long getTotalVotes() {
    return choices.stream()
        .flatMap(choice -> choice.getPersons().stream())
        .distinct()
        .count();
    }
}
