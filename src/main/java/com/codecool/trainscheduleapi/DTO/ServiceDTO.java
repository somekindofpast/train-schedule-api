package com.codecool.trainscheduleapi.DTO;

import com.codecool.trainscheduleapi.entity.Service;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceDTO {
    private Long id;
    private Long trainId;
    private Boolean longDistance;
    private Boolean firstClass;
    private Boolean secondClass;
    private Boolean reservationCompulsory;
    private Boolean supplementCompulsory;
    private Boolean wheelchairAccess;
    private Boolean bicycleReservation;
    private Boolean anyWeatherCondition;
    private Boolean budapestPass;

    public ServiceDTO(Service service) {
        id = service.getId();
        if(service.getTrain() != null)
            trainId = service.getTrain().getId();
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
