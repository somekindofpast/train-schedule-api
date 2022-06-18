package com.codecool.trainscheduleapi.DTO;

import com.codecool.trainscheduleapi.entity.Cargo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CargoSelectionDTO {
    @NotEmpty
    @Size(min = 1, message = "cargo name must be at least 1 character long")
    @Size(max = 200, message = "cargo name must not be over 200 characters long")
    private String name;
    @NotEmpty
    @Size(min = 1, message = "car type must be at least 1 character long")
    @Size(max = 200, message = "car type must not be over 200 characters long")
    private String carType;
}
