package com.thghpi.bandapp.band_api.repository;
import com.thghpi.bandapp.band_api.entity.Survey;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * An interface to feed springboot JPA and hibernate
 * with methods to retrieve, save and delete surveys in database.
 * Hold specifics methods to retrieve recent and old surveys
 * with their choices and participants.
 */
public interface SurveyRepository extends JpaRepository<Survey,Long> {
    
    /**
     * Méthode pour récuperer tout les sondages, leurs options Choice et leurs votes
     * @see entity.Choice
     * @param date Date de fin de sondage la moins récente retournée (strcit)
     * @return une liste de sondage avec leur choix et leurs votes
     */
    @Query("""
        SELECT DISTINCT s FROM Survey s 
        LEFT JOIN FETCH s.choices c 
        LEFT JOIN FETCH c.voters p
        WHERE s.scheduledEnd >= :date 
        ORDER BY s.scheduledEnd DESC
        """)
    List<Survey> findRecent(@Param("date") LocalDate date);
    
    /**
     * Méthode pour récupérer des sondages plus anciens de manières paginer
     * @param LocalDate date Date de fin de sondage la plus récente retournée
     * @param Pageable pageable lenuméro de page et le nombre de résultats par page
     * @return une page de sondage avec leur choix et leurs votants
     */
    @EntityGraph(attributePaths = {
        "choices",
        "choices.voters"
    })
    Page<Survey> findByScheduledEndBeforeOrderByScheduledEndDesc(
        LocalDate date,
        Pageable pageable
    );
}
