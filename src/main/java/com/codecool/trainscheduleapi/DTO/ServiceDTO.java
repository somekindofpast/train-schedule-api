package com.codecool.trainscheduleapi.DTO;

import com.codecool.trainscheduleapi.entity.Service;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceDTO {
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

    public ServiceDTO(Service service) {
        longDistance = service.getLongDistance();
        firstClass = service.getFirstClass();
        secondClass = service.getSecondClass();
        reservationCompulsory = service.getReservationCompulsory();
        supplementCompulsory = service.getSupplementCompulsory();
        wheelchairAccess = service.getWheelchairAccess();
        bicycleReservation = service.getBicycleReservation();
        anyWeatherCondition = service.getAnyWeatherCondition();
        budapestPass = service.getBudapestPass();
    }
}
