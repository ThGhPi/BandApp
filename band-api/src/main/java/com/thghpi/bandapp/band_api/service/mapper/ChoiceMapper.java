package com.thghpi.bandapp.band_api.service.mapper;
import com.thghpi.bandapp.band_api.dto.ChoiceDto;
import com.thghpi.bandapp.band_api.entity.Choice;

import org.mapstruct.Mapper;

@Mapper(
    componentModel = "spring"
)
public interface ChoiceMapper {
    
    ChoiceDto toDto(Choice choice);

    Choice toEntity(ChoiceDto choice);
}
