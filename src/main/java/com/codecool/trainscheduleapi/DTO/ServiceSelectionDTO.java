package com.codecool.trainscheduleapi.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceSelectionDTO {
    @NotNull
    private Boolean longDistance;
    @NotNull
    private Boolean firstClass;
    @NotNull
    private Boolean secondClass;
    @NotNull
    private Boolean reservationCompulsory;
    @NotNull
    private Boolean supplementCompulsory;
    @NotNull
    private Boolean wheelchairAccess;
    @NotNull
    private Boolean bicycleReservation;
    @NotNull
    private Boolean anyWeatherCondition;
    @NotNull
    private Boolean budapestPass;
}
