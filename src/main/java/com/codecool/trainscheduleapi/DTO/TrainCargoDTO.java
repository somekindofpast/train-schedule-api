package com.codecool.trainscheduleapi.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainCargoDTO {
    @NotNull
    @Min(value = 0, message = "id field must not be negative")
    private Long trainId;
    @NotNull
    @Min(value = 0, message = "id field must not be negative")
    private Long cargoId;
}
