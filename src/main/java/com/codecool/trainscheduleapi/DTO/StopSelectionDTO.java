package com.codecool.trainscheduleapi.DTO;

import com.codecool.trainscheduleapi.entity.Stop;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Time;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StopSelectionDTO {
    @NotEmpty
    @Size(min = 1, message = "stop name must be at least 1 character long")
    @Size(max = 200, message = "stop name must not be over 200 characters long")
    private String name;
    @NotNull
    @Min(value = 0, message = "distance field must not be negative")
    private Integer distance;
    private Time arrivalTime;
    private Time departureTime;
    private Integer platform;
}
