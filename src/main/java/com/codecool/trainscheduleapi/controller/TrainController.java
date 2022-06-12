package com.codecool.trainscheduleapi.controller;

import com.codecool.trainscheduleapi.entity.Train;
import com.codecool.trainscheduleapi.service.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/train")
public class TrainController {
    private TrainService trainService;

    @Autowired
    public TrainController(TrainService trainService) {
        this.trainService = trainService;
    }

    @GetMapping
    public List<Train> findAll() {
        return trainService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Train> findById(@PathVariable("id") Long id) {
        return trainService.findById(id);
    }

    @GetMapping("/freight/{cargo_name}")
    public Set<Train> findFreightTrainsByCargoName(@PathVariable("cargo_name") String cargoName) {
        return trainService.findFreightTrainsByCargoName(cargoName);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        trainService.deleteById(id);
    }
}
