package com.thghpi.bandapp.band_api.dto;
import com.thghpi.bandapp.band_api.entity.enumeration.AttendanceChoice;
import com.thghpi.bandapp.band_api.entity.product_key.AttendancePK;

import lombok.*;

@Data
@AllArgsConstructor
public class AttendanceDto {
    private AttendancePK id;
    private AttendanceChoice attendanceChoice;
    private PersonDto person;
    private EventDto event;
}
