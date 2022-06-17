package com.codecool.trainscheduleapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Train {
    @Id
    private Long id;
    private String type;

    @OneToMany(mappedBy = "train")
    private List<Stop> stops;

    @OneToOne(mappedBy = "train")
    private Service service;

    @OneToMany(mappedBy = "train")
    private List<Cargo> cargos;
}
