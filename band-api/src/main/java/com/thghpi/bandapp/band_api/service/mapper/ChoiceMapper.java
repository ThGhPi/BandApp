package com.thghpi.bandapp.band_api.service.mapper;
import com.thghpi.bandapp.band_api.dto.ChoiceDto;
import com.thghpi.bandapp.band_api.entity.Choice;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    componentModel = "spring"
)
public interface ChoiceMapper {
    
    @Mapping(target = "votes", expression = "java(choice.getVotes)")
    ChoiceDto toDto(Choice choice);

    Choice toEntity(ChoiceDto choice);
}
