package com.thghpi.bandapp.band_api.dto;
import com.thghpi.bandapp.band_api.entity.enumeration.FileType;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FileInfoDto {
    private Long id;
    private String fileUrl;
    private FileType fileType;
    private List<PlaceDto> places = new ArrayList<PlaceDto>();
}
