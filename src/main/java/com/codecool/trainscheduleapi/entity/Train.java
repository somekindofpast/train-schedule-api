package com.codecool.trainscheduleapi.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JsonManagedReference
    private List<Stop> stops;

    @OneToOne(mappedBy = "train")
    @JsonManagedReference
    private Service service;

    @OneToMany(mappedBy = "train")
    @JsonManagedReference
    private List<Cargo> cargos;
}
