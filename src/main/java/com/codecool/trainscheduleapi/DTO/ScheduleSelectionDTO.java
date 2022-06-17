package com.codecool.trainscheduleapi.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleSelectionDTO {
    private String departureLocation;
    private String arrivalLocation;
}
