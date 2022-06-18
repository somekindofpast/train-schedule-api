package com.codecool.trainscheduleapi.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleSelectionDTO {
    @NotEmpty
    @Size(min = 1, message = "departureLocation must be at least 1 character long")
    @Size(max = 200, message = "departureLocation must not be over 200 characters long")
    private String departureLocation;
    @NotEmpty
    @Size(min = 1, message = "arrivalLocation must be at least 1 character long")
    @Size(max = 200, message = "arrivalLocation must not be over 200 characters long")
    private String arrivalLocation;
}
