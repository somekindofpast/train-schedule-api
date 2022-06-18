package com.codecool.trainscheduleapi.DTO;

import com.codecool.trainscheduleapi.entity.Cargo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CargoDTO {
    private Long id;
    private Long trainId;
    private String name;
    private String carType;

    public CargoDTO(Cargo cargo) {
        id = cargo.getId();
        if(cargo.getTrain() != null)
            trainId = cargo.getTrain().getId();
        name = cargo.getName();
        carType = cargo.getCarType();
    }
}
