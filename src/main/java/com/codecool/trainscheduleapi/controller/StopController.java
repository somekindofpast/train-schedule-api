package com.codecool.trainscheduleapi.controller;

import com.codecool.trainscheduleapi.entity.Stop;
import com.codecool.trainscheduleapi.service.StopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/stop")
public class StopController {
    private StopService stopService;

    @Autowired
    public StopController(StopService stopService) {
        this.stopService = stopService;
    }

    @GetMapping
    public List<Stop> findAll() {
        return stopService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Stop> findById(@PathVariable("id") Long id) {
        return stopService.findById(id);
    }
}
