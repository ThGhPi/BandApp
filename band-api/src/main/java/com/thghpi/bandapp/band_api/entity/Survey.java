package com.thghpi.bandapp.band_api.entity;

import java.util.Set;
import java.util.LinkedHashSet;
import java.util.stream.Stream;
import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Représente les sondages.
 */
@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
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

    /**
     * Liste des choix du sondage.
     */
    @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("id ASC")
    @Builder.Default
    private Set<Choice> choices = new LinkedHashSet<Choice>();

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
        if (choices == null || choices.isEmpty()) {
            return 0L;
        }
        return choices.stream()
            .flatMap(choice -> choice.getPersons() != null ? choice.getPersons().stream() : Stream.empty())
            .distinct()
            .count();
    }

    /**
     * Ajoute un choix au sondage et met à jour la relation bidirectionnelle.
     * @param Choice choice le choix à ajouter
     */
    public void addChoice(Choice choice) {
        choices.add(choice);
        choice.setSurvey(this);
    }

    /**
     * Supprime un choix du sondage et met à jour la relation bidirectionnelle.
     * @param Choice choice le choix à supprimer
     */
    public void removeChoice(Choice choice) {
        choices.remove(choice);
        choice.setSurvey(null);
    }
}
