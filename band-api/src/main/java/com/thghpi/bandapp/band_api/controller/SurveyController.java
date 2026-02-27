package com.thghpi.bandapp.band_api.controller;
import com.thghpi.bandapp.band_api.dto.SurveyDto;
import com.thghpi.bandapp.band_api.service.SurveyServiceImpl;

import java.util.List;

import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/band-api/surveys")
public class SurveyController {
    private final SurveyServiceImpl service;

    @GetMapping
    public List<SurveyDto> getCurrentSurveys() {
        return service.getRecent();
    }

    @GetMapping("/page/{pageNumber}")
    public List<SurveyDto> getOlderSurveys(Long pageNumber) {
        return service.getPrevious(pageNumber);
    }
}
