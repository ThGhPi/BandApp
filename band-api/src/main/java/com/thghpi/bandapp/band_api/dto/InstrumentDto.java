package com.thghpi.bandapp.band_api.dto;
import com.thghpi.bandapp.band_api.entity.enumeration.Key;

import java.util.ArrayList;
import java.util.List;

/**
 * InstrumentDto record used as the data transfer object
 * for {@link Instrument} entity
 * @param id the id of the instrument in database
 * @param name the name of the intrument
 * @param key the tonality in which it's played
 * @param personIds the list of id of the person playing this instrument in the group
 */
public record InstrumentDto (
    Long id,
    String name,
    Key key,
    List<Long> personIds
) {
    /**
     * Constructor for generating automatically
     * empty lists of id for relations
     * @see InstrumentDto
     */
    public InstrumentDto(
        Long id, String name, Key key
    ) {
        this(id, name, key, new ArrayList<Long>());
    }

    /**
     * Constructor to be used before creation,
     * when the instrument has yet to be saved in database
     * Set id to null
     * @see InstrumentDto
     */
    public InstrumentDto(
        String name, Key key
    ) {
        this(null, name, key);
    }
}
