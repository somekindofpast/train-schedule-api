package com.codecool.trainscheduleapi.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainDTO {
    private Long id;
    //@Pattern(regexp="^[a-zA-Z]{3,20}$", message="train type must be of 3 to 20 length with no special characters")
    @NotNull
    @Email
    private String type;
}
