package com.codecool.trainscheduleapi.DTO;

import com.codecool.trainscheduleapi.entity.Cargo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CargoDTO {
    @NotEmpty
    private String name;
    @NotEmpty
    private String carType;

    public CargoDTO(Cargo cargo) {
        name = cargo.getName();
        carType = cargo.getCarType();
    }
}
