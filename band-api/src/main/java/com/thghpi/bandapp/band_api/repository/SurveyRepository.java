package com.thghpi.bandapp.band_api.repository;
import com.thghpi.bandapp.band_api.entity.Survey;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SurveyRepository extends JpaRepository<Survey,Long> {
    
    /**
     * Méthode pour récuperer tout les sondages, leurs options Choice et leurs votes
     * @see entity.Choice
     * @param date Date de fin de sondage la moins récente retournée (strcit)
     * @return une liste de sondage avec leur choix et leurs votes
     */
    @Query(
        "SELECT s FROM Survey s LEFT JOIN FETCH s.choices c LEFT JOIN FETCH c.persons WHERE s.scheduledEnd >= :date ORDER BY s.scheduledEnd DESC"
    )
    List<Survey> findRecent(LocalDate date);
    
    /**
     * Méthode pour récupérer des sondages plus anciens de manières
     * paginer avec la même logique que findRecent
     * @see findRecent()
     * @param date Date de fin de sondage la plus récente retournée (strcit)
     * @param offset le nombre de résultats à ignorer pour la pagination
     * @return une liste de sondage avec leur choix et leurs votes
     */
    @Query(
        "SELECT s FROM Survey s LEFT JOIN FETCH s.choices c LEFT JOIN FETCH c.persons WHERE s.scheduledEnd < :date ORDER BY s.scheduledEnd DESC LIMIT 5 OFFSET :offset"
    )
    List<Survey> findOld(LocalDate date, Long offset);
}
