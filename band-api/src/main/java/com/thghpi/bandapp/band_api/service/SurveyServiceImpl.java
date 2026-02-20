package com.thghpi.bandapp.band_api.service;
import com.thghpi.bandapp.band_api.dto.SurveyDto;
import com.thghpi.bandapp.band_api.entity.Survey;
import com.thghpi.bandapp.band_api.repository.SurveyRepository;
import com.thghpi.bandapp.band_api.service.mapper.SurveyMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class SurveyServiceImpl implements SurveyService {
    private final SurveyRepository repository;
    private final SurveyMapper mapper;

    @Override
    public List<SurveyDto> getRecent() {
        LocalDate date = LocalDate.now().minusMonths(1);
        return repository.findRecent(date)
            .stream()
            .map(mapper::toDto)
            .collect(Collectors.toList());
    }

    @Override
    public List<SurveyDto> getPrevious(Long pageNumber) {
        LocalDate date = LocalDate.now().minusMonths(1);
        return repository.findOld(date, pageNumber*5)
            .stream()
            .map(mapper::toDto)
            .collect(Collectors.toList());
    }
}
