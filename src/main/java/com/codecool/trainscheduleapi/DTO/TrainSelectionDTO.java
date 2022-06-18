package com.codecool.trainscheduleapi.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainSelectionDTO {
    @NotNull
    @Min(value = 0, message = "train id must not be negative")
    private Long id;
    @NotNull
    @Pattern(regexp="^[a-zA-Z]{3,20}$", message="train type must be of 3 to 20 length with no special characters")
    private String type;
}
