package com.codecool.trainscheduleapi.DTO;

import com.codecool.trainscheduleapi.entity.Train;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDTO {
    private Time departureTime;
    private String departureLocation;

    private Time arrivalTime;
    private String arrivalLocation;

    private String travelTime;
    private Integer travelDistance;

    private Train train;
}
