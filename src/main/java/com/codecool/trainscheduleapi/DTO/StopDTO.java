package com.codecool.trainscheduleapi.DTO;

import com.codecool.trainscheduleapi.entity.Stop;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StopDTO {
    private Long id;
    private Long trainId;
    private Integer distance;
    private String name;
    private Time arrivalTime;
    private Time departureTime;
    private Integer platform;

    public StopDTO(Stop stop) {
        if(stop == null)
            return;
        id = stop.getId();
        if(stop.getTrain() != null)
            trainId = stop.getTrain().getId();
        distance = stop.getDistance();
        name = stop.getName();
        arrivalTime = stop.getArrivalTime();
        departureTime = stop.getDepartureTime();
        platform = stop.getPlatform();
    }
}
