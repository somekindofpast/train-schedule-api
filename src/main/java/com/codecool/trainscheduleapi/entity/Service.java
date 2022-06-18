package com.codecool.trainscheduleapi.entity;

import com.codecool.trainscheduleapi.DTO.ServiceSelectionDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Train train;

    @Column(name = "long_distance")
    private Boolean longDistance;

    @Column(name = "first_class")
    private Boolean firstClass;

    @Column(name = "second_class")
    private Boolean secondClass;

    @Column(name = "reservation_compulsory")
    private Boolean reservationCompulsory;

    @Column(name = "supplement_compulsory")
    private Boolean supplementCompulsory;

    @Column(name = "wheelchair_access")
    private Boolean wheelchairAccess;

    @Column(name = "bicycle_reservation")
    private Boolean bicycleReservation;

    @Column(name = "any_weather_condition")
    private Boolean anyWeatherCondition;

    @Column(name = "budapest_pass")
    private Boolean budapestPass;

    public void setServiceSelectionDTO(ServiceSelectionDTO serviceSelectionDTO) {
        if(serviceSelectionDTO == null)
            return;

        this.setLongDistance(serviceSelectionDTO.getLongDistance());
        this.setFirstClass(serviceSelectionDTO.getFirstClass());
        this.setSecondClass(serviceSelectionDTO.getSecondClass());
        this.setReservationCompulsory(serviceSelectionDTO.getReservationCompulsory());
        this.setSupplementCompulsory(serviceSelectionDTO.getSupplementCompulsory());
        this.setWheelchairAccess(serviceSelectionDTO.getWheelchairAccess());
        this.setBicycleReservation(serviceSelectionDTO.getBicycleReservation());
        this.setAnyWeatherCondition(serviceSelectionDTO.getAnyWeatherCondition());
        this.setBudapestPass(serviceSelectionDTO.getBudapestPass());
    }
}
