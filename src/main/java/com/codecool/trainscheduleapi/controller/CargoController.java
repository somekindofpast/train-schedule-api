package com.codecool.trainscheduleapi.controller;

import com.codecool.trainscheduleapi.entity.Cargo;
import com.codecool.trainscheduleapi.service.CargoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cargo")
public class CargoController {
    private CargoService cargoService;

    @Autowired
    public CargoController(CargoService cargoService) {
        this.cargoService = cargoService;
    }

    @GetMapping
    public List<Cargo> findAll() {
        return cargoService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Cargo> findById(@PathVariable("id") Long id) {
        return cargoService.findById(id);
    }
}
