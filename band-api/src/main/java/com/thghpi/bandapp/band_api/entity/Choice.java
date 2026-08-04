package com.thghpi.bandapp.band_api.entity;

import java.util.Set;
import java.util.HashSet;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    private Set<Person> persons = new HashSet<Person>();

    /**
     * Compte le nombre de personnes ayant voté pour ce choix.
     * @return le nombre de personnes ayant une relation avec ce choix.
     */
    public Long getVotes() {
        return persons == null ? 0L : (long) persons.size();
    }

    /**
     * affecte la valeur "Cliquez ici pour suivre le lien" à l'attribut complément
     * si celui-ci est null ou blanc et que l'attribut url n'est ni null ni blanc
     */
    public void checkLinkComplement() {
        if (
            !(url == null || url.isBlank()) &&
             (complement == null || complement.isBlank())
            ) {
            setComplement("Cliquez ici pour suivre le lien");
        }
    }

    /**
     * Ajoute une personne au choix et met à jour la relation bidirectionnelle.
     * @param Person person la personne à ajouter
     */
    public void addPerson(Person person) {
        persons.add(person);
        person.getChoices().add(this);
    }

    /**
     * Supprime une personne du choix et met à jour la relation bidirectionnelle.
     * @param Person person la personne à supprimer
     */
    public void removePerson(Person person) {
        persons.remove(person);
        person.getChoices().remove(this);
    }

    /**
     * Ajoute un vote à ce choix pour la personne donnée.
     * @param Person person la personne qui vote
     * @throws IllegalArgumentException si la personne a déjà voté pour ce choix
     */
    public void addVote(Person person) {
        // Vérifie si la personne a déjà voté pour ce choix
        if (hasVoted(person)) {
            throw new IllegalArgumentException("La personne a déjà voté pour ce choix.");
        }

        // Ajoute la personne au choix
        addPerson(person);
    }

    /**
     * Supprime un vote de ce choix pour la personne donnée.
     * @param Person person la personne qui retire son vote
     * @throws IllegalArgumentException si la personne n'a pas voté pour ce choix
     */
    public void removeVote(Person person) {
        // Vérifie si la personne a voté pour ce choix
        if (!hasVoted(person)) {
            throw new IllegalArgumentException("La personne n'a pas voté pour ce choix.");
        }
        
        // Supprime la personne du choix
        removePerson(person);
    }

    /**
     * Vérifie si la personne a voté pour ce choix.
     * @param Person person la personne à vérifier
     * @return true si la personne a voté pour ce choix, false sinon
     */
    public Boolean hasVoted(Person person) {
        return persons.contains(person);
    }

    /**
     * Override equals methode of Object to ensure correct behavior
     * in the set for relationship with Survey
     * @param Object o object to compare with the this instance
     * @return true when o and the instance have the same attributes or when id is non null is the same and their of type Choice, otherwise false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Choice other)) return false;
        return id != null && id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
