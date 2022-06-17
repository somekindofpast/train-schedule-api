package com.codecool.trainscheduleapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Stop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Train train;

    private Integer distance;
    private String name;

    @Column(name = "arrival_time")
    private Time arrivalTime;

    @Column(name = "departure_time")
    private Time departureTime;

    private Integer platform;
}
