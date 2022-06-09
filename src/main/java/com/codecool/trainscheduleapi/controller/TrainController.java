package com.codecool.trainscheduleapi.controller;

import com.codecool.trainscheduleapi.entity.Train;
import com.codecool.trainscheduleapi.service.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

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
}
