package com.codecool.trainscheduleapi.DTO;

import com.codecool.trainscheduleapi.entity.Stop;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Time;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StopDTO {
    @NotEmpty
    private String name;
    @NotNull
    @Min(value = 0, message = "distance field must not be negative")
    private Integer distance;
    private Time arrivalTime;
    private Time departureTime;
    private Integer platform;

    public StopDTO(Stop stop) {
        name = stop.getName();
        distance = stop.getDistance();
        arrivalTime = stop.getArrivalTime();
        departureTime = stop.getDepartureTime();
        platform = stop.getPlatform();
    }
}
