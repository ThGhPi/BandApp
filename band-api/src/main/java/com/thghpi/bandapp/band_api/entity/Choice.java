package com.thghpi.bandapp.band_api.entity;

import java.util.List;
import java.util.ArrayList;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.AllArgsConstructor;

/**
 * Représente les choix d'un sondage.
 * Existe exclusivement en lien avec un sondage, et ne peut pas exister sans lui.
 * Un choix peut être associé à une personne : cela représente un vote de cette personne.
 */
@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Choice {
    /**
     * Identifiant du choix attribué automatiquement à la création.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Le titre du choix.
     */
    @Column(length = 100, nullable = false)
    private String title;
    
    /**
     * Un texte pour le lien facultatif pour ce choix.
     */
    @Column(length = 255)
    private String complement;
    
    /**
     * Un lien facultatif pour ce choix.
     */
    @Column(length = 255)
    private String url;
    
    /**
     * Le sondage auquel ce choix est associé.
     * Un choix ne peut pas exister sans un sondage.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "survey_id", nullable = false)
    private Survey survey;

    /**
     * Les personnes ayant voté pour ce choix.
     */
    @ManyToMany
    @JoinTable(
        name = "answer",
        joinColumns = @JoinColumn(name = "choice_id"),
        inverseJoinColumns = @JoinColumn(name = "person_id")
        )
    @Builder.Default
    private List<Person> persons = new ArrayList<Person>();

    /**
     * Compte le nombre de personnes ayant voté pour ce choix.
     * @return le nombre de personnes ayant une relation avec ce choix.
     */
    public Long getVotes() {
        return persons == null ? 0L : (long) persons.size();
    }
}
