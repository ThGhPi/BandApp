package com.thghpi.bandapp.band_api.unit.service;
import com.thghpi.bandapp.band_api.dto.ChoiceDto;
import com.thghpi.bandapp.band_api.service.ChoiceServiceImpl;
import com.thghpi.bandapp.band_api.repository.ChoiceRepository;
import com.thghpi.bandapp.band_api.service.mapper.ChoiceMapper;

import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Tests for the {@link ChoiceServiceImpl} class, focusing on the checkLinkComplement method which ensures that a complement is set when a URL is provided without a complement.
 * This is important to maintain a consistent user experience, as choices with URLs should have a clear call-to-action for users to follow the link.
 */
@ExtendWith(MockitoExtension.class)
public class ChoiceServiceTest {
    @Mock
    private ChoiceMapper mapper;
    @Mock
    private ChoiceRepository repository;

    @InjectMocks
    private ChoiceServiceImpl service;
    
    /**
     * Tests the checkLinkComplement method when a URL is present. It creates a choice with a URL and asserts that the method sets a default complement.
     * It also tests that if a complement is already provided, the method does not overwrite it.
     */
    @Test
    void shouldEnsureComplementWhenUrlIsPresent() {
        ChoiceDto choiceWithUrl1 = new ChoiceDto(
            null,
            "Choice with URL",
            null,
            "http://example.com",
            null,
            null
        );
        ChoiceDto result1 = service.checkLinkComplement(choiceWithUrl1);
        ChoiceDto choiceWithUrl2 = new ChoiceDto(
            null,
            "Choice with URL",
            "Lien vers la vidéo",
            "http://example.com",
            null,
            null
        );
        ChoiceDto result2 = service.checkLinkComplement(choiceWithUrl2);
        assertEquals("Cliquez ici pour suivre le lien", result1.getComplement());
        assertEquals("Lien vers la vidéo", result2.getComplement());
    }

    /**
     * Tests the checkLinkComplement method when a URL is absent. It creates a choice without a URL and asserts that the method does not set a complement.
     * It also tests that if a complement is provided without a URL, the method does not remove it.
     */
    @Test
    void shouldNotSetComplementWhenUrlIsAbsent() {
        ChoiceDto choiceWithoutUrl1 = new ChoiceDto(
            null,
            "Choice without URL",
            null,
            null,
            null,
            null
        );
        ChoiceDto result1 = service.checkLinkComplement(choiceWithoutUrl1);
        ChoiceDto choiceWithoutUrl2 = new ChoiceDto(
            null,
            "Choice without URL",
            "Some complement",
            null,
            null,
            null
        );
        ChoiceDto result2 = service.checkLinkComplement(choiceWithoutUrl2);
        assertNull(result1.getComplement());
        assertEquals("Some complement", result2.getComplement());
    }
}
